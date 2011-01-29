/* rchome.java.gui.ClientApplication.java */
/*
 * RCHome - For more moderns homes
 * 
 * Copyright (C) 2011 Mônica Nelly   <monica.araujo@itec.ufpa.br>
 * Copyright (C) 2011 Willian Paixao <willian@ufpa.br>
 * 
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

package rchome.java.gui;

import javax.swing.*;

/**
 * 
 * 
 * @author Mônica Nelly   <monica.araujo@itec.ufpa.br>
 * @author Willian Paixao <willian@ufpa.br>
 * @version 0.001
 */
public class ClientApplication {

	private static Tabs tab;

	public static void main(String Args[]) {

		tab = new Tabs();

		tab.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//tab.setIconImage();
		tab.setSize(400, 300);
		tab.setVisible(true);
	}
}
