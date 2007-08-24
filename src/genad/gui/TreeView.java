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
	private JPopupMenu contextMenu;
	private JMenuItem edit, add, delete;
	private int x,y;
	
	public TreeView(){
		contextMenu=new JPopupMenu();
		contextMenu.add(edit=new JMenuItem("Edit",new ImageIcon(getClass().getResource("/images/icons/page.png"))));
		contextMenu.add(add=new JMenuItem("Add Child",new ImageIcon(getClass().getResource("/images/icons/page_add.png"))));
		contextMenu.add(delete=new JMenuItem("Delete",new ImageIcon(getClass().getResource("/images/icons/page_delete.png"))));
		
		add(contextMenu);
		setCellRenderer(new TreeViewRenderer());
		
		setModel(new DefaultTreeModel(new DefaultMutableTreeNode("No project loaded")));
		setHandlers();
	}
	
	private void setHandlers(){
		edit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				editEntity(evt);
			}
		});
		add.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				addEntity(evt);
			}
		});
		delete.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				deleteEntity(evt);
			}
		});
		
		addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                treeMouseClicked(evt);
            }
        });
	}
	
	private void editEntity(ActionEvent evt){
		TreePath tp=getPathForLocation(x,y);
		Object[] path=tp.getPath();
		//for(Object t: path)
		//	System.out.println(t);
		JOptionPane.showMessageDialog(Main.getInstance(),path[path.length-1]);
	}
	
	private void addEntity(ActionEvent evt){
		String name=JOptionPane.showInputDialog(Main.getInstance(),"Entity name");
		TreePath tp=getPathForLocation(x,y);
		Object[] path=tp.getPath();
		
		
	}
	
	private void deleteEntity(ActionEvent evt){
		
	}
	
	private void treeMouseClicked(MouseEvent evt){
		if(evt.getButton()==evt.BUTTON3){
			TreePath tp=getPathForLocation(x=evt.getX(),y=evt.getY());
			if(tp==null) return;
			
			Object[] path=tp.getPath();
			if(path.length>=2){
				edit.setEnabled(path.length!=2 || !path[1].toString().equalsIgnoreCase("entities"));
				delete.setEnabled(path.length!=2 || !path[1].toString().equalsIgnoreCase("entities"));
				contextMenu.show(evt.getComponent(),x,y);
			}
		}
	}
	
	private DefaultMutableTreeNode makeTree(DefaultMutableTreeNode root, Entity current){
		DefaultMutableTreeNode child=new DefaultMutableTreeNode(current.getName());
		
		for(String s : Utils.convert(current.getChilds()))
			child.add(makeTree(child, current.getChild(s)));
		
		root.add(child);
		return child;
	}		
			
	public void update(Model subject) {
		//build a DefaultTreeNode and set in the model of tree
		DefaultMutableTreeNode root=new DefaultMutableTreeNode(subject.getLoadedPath());
		
		DefaultMutableTreeNode mods=new DefaultMutableTreeNode("Modules");
		root.add(mods);
		for(String s : Utils.convert(subject.getModules()))
			mods.add(new DefaultMutableTreeNode(s));
		
		DefaultMutableTreeNode ents=new DefaultMutableTreeNode("Entities");
		root.add(ents);
		for(String s : Utils.convert(subject.getEntities()))
			makeTree(ents, subject.getEntity(s));
		
		setModel(new DefaultTreeModel(root));
		updateUI();
	}
	
	public void attachToModel(Model model){
		model.attachView(this);
	}
}

class TreeViewRenderer extends DefaultTreeCellRenderer{
	public Component getTreeCellRendererComponent(JTree tree, Object value,
													  boolean sel, boolean expanded, boolean leaf, int row,
													  boolean hasFocus) {
		super.getTreeCellRendererComponent(tree,value,selected,expanded,leaf,row,hasFocus);
		if(value instanceof DefaultMutableTreeNode){
			DefaultMutableTreeNode node=(DefaultMutableTreeNode)value;
			TreeNode path[]=node.getPath();
			if(node.getParent()==null)//root node
				setIcon(new ImageIcon(getClass().getResource("/images/icons/folder_project.png")));
			else if(path.length==2){
				if(path[1].toString().equalsIgnoreCase("Entities"))
					setIcon(new ImageIcon(getClass().getResource("/images/icons/folder_page.png")));
				else if(path[1].toString().equalsIgnoreCase("Modules"))
					setIcon(new ImageIcon(getClass().getResource("/images/icons/folder_plugin.png")));
				else
					setIcon(new ImageIcon(getClass().getResource("/images/icons/folder.png"))); //default folder icon
				
			}else{
				if(path[1].toString().equalsIgnoreCase("Entities"))
					setIcon(new ImageIcon(getClass().getResource("/images/icons/page.png")));
				else if(path[1].toString().equalsIgnoreCase("Modules"))
					setIcon(new ImageIcon(getClass().getResource("/images/icons/plugin.png")));
			}
		}
		
		return this;
	}
}