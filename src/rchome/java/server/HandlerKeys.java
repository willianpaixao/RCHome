/* rchome.java.server.HandlerKeys.java */
/*
 * RCHome - For more moderns homes
 * 
 * Copyright (C) 2011 Monica Nelly   <monica.araujo@itec.ufpa.br>
 * Copyright (C) 2011 Willian Paixao <willian@ufpa.br>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package rchome.java.server;

import java.io.*;
import java.security.*;
import java.security.spec.*;

/**
 * 
 * @author Monica Nelly <monica.araujo@itec.ufpa.br>
 * @see java.security
 * @since 0.01
 */
public class HandlerKeys {

	private HouseContents contents;
	private KeyPair       keyPair  = null;
	private SecureRandom  rng;
	private String        keyPath  = null;

	public HandlerKeys() {

		contents = new HouseContents("server");
		keyPath  = contents.getContent("path") + "/keys/";

		if(contents.getContent("haveKeys").equals("true"))
			keyPair = loadKeyPair();
		else if(contents.getContent("haveKeys").equals("false")) {
			keyPair = generateKeyPair();
			storageKeyPair();
			if(contents.setContent("heveKeys", "true"))
				HandlerLog.logger.info("Public and private keys created. Stored in default path");
		} else
			HandlerLog.logger.severe(contents.getContent("haveKeys") + "is a invalid option.");
	}

	public HandlerKeys(char arg) {

		contents = new HouseContents("server");
		keyPath  = contents.getContent("path") + "/keys/";

		switch(arg) {
		case 'g':
			keyPair = generateKeyPair();
			storageKeyPair();
			break;
		case 's':
			keyPair = loadKeyPair();
			break;
		default:
			keyPair = loadKeyPair();
			break;
		}
	}

	public KeyPair generateKeyPair() {

		try {
			KeyPairGenerator keys = KeyPairGenerator.getInstance("RSA");
			rng                   = SecureRandom.getInstance("SHA1PRNG", "SUN");

			rng.setSeed(Long.parseLong(contents.getContent("seed")));
			keys.initialize(Integer.parseInt(contents.getContent("keySize")), rng);

			return keys.generateKeyPair();
		} catch (NoSuchAlgorithmException e) {
			HandlerLog.logger.throwing("GeneratorPair", "generateKeyPair", e);
			HandlerLog.logger.severe("RSA doesn't exist or isn't installed");
		} catch (NoSuchProviderException e) {
			HandlerLog.logger.throwing("GeneratorPair", "generateKeyPair", e);
			HandlerLog.logger.severe("'SUN' provider doesn't exist or isn't installed");
		} catch (IllegalArgumentException e) {
			HandlerLog.logger.throwing("GeneratorPair", "generateKeyPair", e);
		}

		return (null);
	}

	public KeyPair loadKeyPair() {

		byte[] privenc = null;
		byte[] pubenc  = null;

		try {
		File            privFile   = new File(keyPath + "private.key");
		FileInputStream privStream = new FileInputStream(keyPath + "private.key");
		File            pubFile    = new File(keyPath + "public.key");
		FileInputStream pubStream  = new FileInputStream(keyPath + "public.key");
		privenc = new byte[(int) privFile.length()];
		pubenc  = new byte[(int) pubFile.length()];

		privStream.read(privenc);
		privStream.close();
		pubStream.read(pubenc);
		pubStream.close();
		} catch(FileNotFoundException e) {
			HandlerLog.logger.throwing("GeneratorPair", "loadKeyPair", e);
		} catch(IOException e) {
			HandlerLog.logger.throwing("GeneratorPair", "loadKeyPair", e);
		} finally {
			try {
				KeyFactory         keyFactory     = KeyFactory.getInstance("RSA");
				X509EncodedKeySpec publicKeySpec  = new X509EncodedKeySpec(pubenc);
				PublicKey          pub            = keyFactory.generatePublic(publicKeySpec);

				PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privenc);
				PrivateKey          priv           = keyFactory.generatePrivate(privateKeySpec);

				return (new KeyPair(pub, priv));
			} catch (InvalidKeySpecException e) {
			} catch (NoSuchAlgorithmException e) {
				HandlerLog.logger.throwing("GeneratorPair", "signData", e);
				HandlerLog.logger.severe("'SHA1withRSA' doesn't exist or isn't installed");
			}
		}
		return (null);
	}

	public byte[] signData(byte[] in, PrivateKey key) {

		try {
			Signature signer = Signature.getInstance("SHA1withRSA");

			signer.initSign(key);
			signer.update(in);

			return signer.sign();
		} catch (InvalidKeyException e) {
			HandlerLog.logger.throwing("GeneratorPair", "signData", e);
		} catch (NoSuchAlgorithmException e) {
			HandlerLog.logger.throwing("GeneratorPair", "signData", e);
			HandlerLog.logger.severe("'SHA1withRSA' doesn't exist or isn't installed");
		} catch (SignatureException e) {
			HandlerLog.logger.throwing("GeneratorPair", "signData", e);
		}

		return (null);
	}

	public void storageKeyPair() {

		try {
			/*
			 * NOTE: This path is temporary, please change this.
			 */
			FileOutputStream privStream = new FileOutputStream(keyPath + "private.key");
			FileOutputStream pubStream  = new FileOutputStream(keyPath + "public.key");

			privStream.write(keyPair.getPrivate().getEncoded());
			privStream.close();
			pubStream.write(keyPair.getPublic().getEncoded());
			pubStream.close();

		} catch(FileNotFoundException e) {
			HandlerLog.logger.throwing("GeneratorPair", "storageKeys", e);
		} catch(IOException e) {
			HandlerLog.logger.throwing("GeneratorPair", "storageKeys", e);
		}
	}

	public boolean verifySignData(byte[] in, byte[] data, PublicKey key) {

		try {
			Signature signer = Signature.getInstance("SHA1withRSA");

			signer.initVerify(key);
			signer.update(in);

			return signer.verify(data);
		} catch (InvalidKeyException e) {
			HandlerLog.logger.throwing("GeneratorPair", "verifySignData", e);
		} catch (NoSuchAlgorithmException e) {
			HandlerLog.logger.throwing("GeneratorPair", "verifySignData", e);
			HandlerLog.logger.severe("'SHA1withRSA' doesn't exist or isn't installed");
		} catch (SignatureException e) {
			HandlerLog.logger.throwing("GeneratorPair", "verifySignData", e);
		}

		return (false);
	}
}