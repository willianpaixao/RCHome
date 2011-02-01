/* rchome.java.gui.Tabs.java */
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

package rchome.java.client;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * 
 * 
 * @author Mônica Nelly   <monica.araujo@itec.ufpa.br>
 * @author Willian Paixao <willian@ufpa.br>
 * @version 0.001
 */
public class Tabs extends JFrame implements ItemListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1856584754922605455L;

	private JMenu        menu;
	private JMenuBar     menuBar;
	private JMenuItem    menuClose;

	private JTabbedPane  house;

	private JPanel       masterRoom;
	private JCheckBox    masterRoomDVD;
	private JCheckBox    masterRoomLight;
	private JCheckBox    masterRoomTv;

	private JPanel       bathroom;
	private JCheckBox    bathroomLight;

	private JPanel       livingRoom;
	private JCheckBox    livingRoomLight1;
	private JCheckBox    livingRoomLight2;
	private JCheckBox    livingRoomTv;

	public Tabs() {

		super("RCHome");

		menu            = new JMenu("File");
		menuBar         = new JMenuBar();
		menuClose       = new JMenuItem("Close");
	
		house           = new JTabbedPane();

		masterRoom      = new JPanel(new GridLayout());
		masterRoomDVD   = new JCheckBox("DVD");
		masterRoomTv    = new JCheckBox("TV");
		masterRoomLight = new JCheckBox("Light");


		bathroom        = new JPanel(new GridLayout());
		bathroomLight   = new JCheckBox("Light");

		livingRoom      = new JPanel(new GridLayout());
		livingRoomTv    = new JCheckBox("TV");
		livingRoomLight1= new JCheckBox("Light 1");
		livingRoomLight2= new JCheckBox("Light 2");

		setJMenuBar(menuBar);
		menuBar.add(menu);
		menu.setMnemonic('F');
		menu.add(menuClose);
		menuClose.setMnemonic('C');

		masterRoomTv.addItemListener(this);
		masterRoomDVD.addItemListener(this);
		masterRoomLight.addItemListener(this);

		bathroomLight.addItemListener(this);

		livingRoomTv.addItemListener(this);
		livingRoomLight1.addItemListener(this);
		livingRoomLight2.addItemListener(this);

		masterRoom.setLayout(new GridLayout(5,2));
		masterRoom.setMinimumSize(new Dimension(400, 300));
		masterRoom.setPreferredSize(new Dimension(800, 600));
		masterRoom.add(masterRoomTv);
		masterRoom.add(masterRoomDVD);
		masterRoom.add(masterRoomLight);

		bathroom.setLayout(new GridLayout(5,2));
		bathroom.setMinimumSize(new Dimension(400, 300));
		bathroom.setPreferredSize(new Dimension(800, 600));
		bathroom.add(bathroomLight);

		livingRoom.setLayout(new GridLayout(5,2));
		livingRoom.setMinimumSize(new Dimension(400, 300));
		livingRoom.setPreferredSize(new Dimension(800, 600));
		livingRoom.add(livingRoomTv);
		livingRoom.add(livingRoomLight1);
		livingRoom.add(livingRoomLight2);

		house.addTab("Master Room", null, masterRoom, "Parent's room");
		house.addTab("Living room", null, livingRoom, "Living room");
		house.addTab("Bathroom", null, bathroom, "Main bathroom");

		add(house);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {

		if(masterRoomTv.isSelected() == true)
			Client.send("h1");
		else
			Client.send("l1");

		if(masterRoomDVD.isSelected() == true)
			Client.send("h2");
		else
			Client.send("l2");

		if(masterRoomLight.isSelected() == true)
			Client.send("h3");
		else
			Client.send("l3");

		if(livingRoomTv.isSelected() == true)
			Client.send("h4");
		else
			Client.send("l4");

		if(livingRoomLight1.isSelected() == true)
			Client.send("h5");
		else
			Client.send("l5");

		if(livingRoomLight2.isSelected() == true)
			Client.send("h6");
		else
			Client.send("l6");

		if(bathroomLight.isSelected() == true)
			Client.send("h7");
		else
			Client.send("l7");
	}
}
