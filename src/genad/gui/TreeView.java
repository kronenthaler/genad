package genad.gui;

import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import javax.xml.parsers.*;
//import org.w3c.dom.*;

import genad.*;
import genad.gui.*;
import genad.model.*;
import genad.config.*;
import genad.engine.*;

/**
 *
 *	@author kronenthaler
 */
public class TreeView extends JTree implements View{
	protected JPopupMenu contextMenu;
	public TreeView(){
		contextMenu=new JPopupMenu();
		contextMenu.add(new JMenuItem("Edit",new ImageIcon(getClass().getResource("/images/icons/page.png"))));
		contextMenu.add(new JMenuItem("Add Child",new ImageIcon(getClass().getResource("/images/icons/page_add.png"))));
		contextMenu.add(new JMenuItem("Delete",new ImageIcon(getClass().getResource("/images/icons/page_delete.png"))));
		
		add(contextMenu);
	}
	
	public void update(Model subject) {
		//build a DefaultTreeNode and set in the model of tree
		DefaultMutableTreeNode root=new DefaultMutableTreeNode(subject.getLoadedPath());
		DefaultMutableTreeNode ents=new DefaultMutableTreeNode("Entities");
		root.add(new DefaultMutableTreeNode("Modules"));
		root.add(ents);
				
		for(String s : Utils.convert(subject.getEntities()))
			makeTree(ents, subject.getEntity(s));
		
		setModel(new DefaultTreeModel(root));
		updateUI();
	}
	
	private DefaultMutableTreeNode makeTree(DefaultMutableTreeNode root, Entity current){
		DefaultMutableTreeNode child=new DefaultMutableTreeNode(current.getName());
		
		for(String s : Utils.convert(current.getChilds()))
			child.add(makeTree(child, current.getChild(s)));
		
		root.add(child);
		return child;
	}
}
