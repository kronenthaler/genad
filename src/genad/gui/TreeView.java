package genad.gui;

import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import javax.xml.parsers.*;
import org.netbeans.swing.tabcontrol.*;

import genad.*;
import genad.gui.*;
import genad.model.*;
import genad.config.*;
import genad.gui.misc.*;

/**
 *
 *	@author kronenthaler
 */
public class TreeView extends JTree implements View{
	private JPopupMenu entityContextMenu,moduleContextMenu, relationContextMenu;
	private JMenuItem editEntity, addEntity, deleteEntity, 
					  editModule,
					  addRelation, deleteRelation, editRelation;
	private TabbedContainer viewer;
	private int x,y;
		
	public TreeView(TabbedContainer _viewer){
		viewer=_viewer;
		
		entityContextMenu=new JPopupMenu();
		entityContextMenu.add(editEntity=new JMenuItem("Edit",IconsManager.EDITENTITY));
		entityContextMenu.add(addEntity=new JMenuItem("Add Child",IconsManager.ADDENTITY));
		entityContextMenu.add(deleteEntity=new JMenuItem("Delete",IconsManager.DELENTITY));
		add(entityContextMenu);
		
		moduleContextMenu=new JPopupMenu();
		moduleContextMenu.add(editModule=new JMenuItem("Edit",IconsManager.EDITMODULE));
		add(moduleContextMenu);

		relationContextMenu = new JPopupMenu();
		relationContextMenu.add(addRelation = new JMenuItem("Add", IconsManager.EDITRELATION));
		relationContextMenu.add(editRelation = new JMenuItem("Edit", IconsManager.EDITRELATION));
		relationContextMenu.add(deleteRelation = new JMenuItem("Delete", IconsManager.DELRELATION));

		setCellRenderer(new TreeViewRenderer());
		update((Model)null);
		setHandlers();
	}
	
	private void setHandlers(){
		editEntity.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				editEntity(evt);
			}
		});
		addEntity.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				addEntity(evt);
			}
		});
		deleteEntity.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				deleteEntity(evt);
			}
		});
		
		editModule.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				editModule(evt);
			}
		});

		addRelation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				addRelation(evt);
			}
		});

		editRelation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				editRelation(evt);
			}
		});

		deleteRelation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				deleteRelation(evt);
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
		Model model=Model.getInstance();
		Entity current=model.getEntity(path[2].toString());
		
		for(int i=3;i<path.length;i++)
			current=current.getChild(path[i].toString());
		
		for(int i=0,n=viewer.getTabCount();i<n;i++){
			if(viewer.getModel().getTab(i).getTooltip().equals(path[path.length-1].toString())){
				viewer.getSelectionModel().setSelectedIndex(i);
				return;
			}
		}
		
		EntityView ent=new EntityView(current, viewer);
		TabData td=new TabData(ent,IconsManager.ENTITY,path[path.length-1].toString(),path[path.length-1].toString());
		viewer.getModel().addTab(viewer.getTabCount(), td);
		viewer.getSelectionModel().setSelectedIndex(viewer.getTabCount()-1);
		ent.attachToModel(Model.getInstance());
	}
	
	private void addEntity(ActionEvent evt){
		String name=JOptionPane.showInputDialog(Main.getInstance(),"Entity name");
		if(name==null || name.equals("")) return;
		
		TreePath tp=getPathForLocation(x,y);
		Object[] path=tp.getPath();
		Model model=Model.getInstance();
		if(path.length==2){
			if(!model.addEntity(Utils.sanitize(name)))
				Utils.showError("An entity with this name already exists");
		}else{ //subentidad
			Entity current=model.getEntity(path[2].toString());
			for(int i=3;i<path.length;i++)
				current=current.getChild(path[i].toString());
			
			if(!current.addChild(Utils.sanitize(name)))
				Utils.showError("An entity with this name already exists");
		}
	}
	
	private void deleteEntity(ActionEvent evt){
		TreePath tp=getPathForLocation(x,y);
		Object[] path=tp.getPath();
		Model model=Model.getInstance();
		if(path.length==3){
			if(JOptionPane.showConfirmDialog(Main.getInstance(),
											 "Are you really sure?",
											 "Confirmation",
											 JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
				model.removeEntity(path[2].toString());
				for(int i=0,n=viewer.getTabCount();i<n;i++){
					if(viewer.getModel().getTab(i).getTooltip().equals(path[2].toString())){
						viewer.getModel().removeTab(i);
						break;
					}
				}
			}
		}else{
			Entity current=model.getEntity(path[2].toString());
			for(int i=3;i<path.length-1;i++)
				current=current.getChild(path[i].toString());
			
			if(JOptionPane.showConfirmDialog(Main.getInstance(),
											 "Are you really sure?",
											 "Confirmation",
											 JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
				current.removeChild(path[path.length-1].toString());
				for(int i=0,n=viewer.getTabCount();i<n;i++){
					if(viewer.getModel().getTab(i).getTooltip().equals(path[path.length-1].toString())){
						viewer.getModel().removeTab(i);
						break;
					}
				}
			}
		}
	}
	
	private void editModule(ActionEvent evt){
		TreePath tp=getPathForLocation(x,y);
		Object[] path=tp.getPath();
		
		Module current=Model.getInstance().getModule(path[path.length-1].toString());
		if(current.getModuleConfig().getOptionSize()==0){
			Utils.showInformation("The module '"+current.getName()+"' hasn't options to configure");
			return;
		}
		
		for(int i=0,n=viewer.getTabCount();i<n;i++){
			if(viewer.getModel().getTab(i).getTooltip().equals(path[path.length-1].toString())){
				viewer.getSelectionModel().setSelectedIndex(i);
				return;
			}
		}
		
		ModuleView ent=new ModuleView(current,viewer);
		TabData td=new TabData(ent,IconsManager.MODULE,path[path.length-1].toString(),path[path.length-1].toString());
		viewer.getModel().addTab(viewer.getTabCount(), td);
		viewer.getSelectionModel().setSelectedIndex(viewer.getTabCount()-1);
		ent.attachToModel(Model.getInstance());
	}

	private void addRelation(ActionEvent evt){
		String name=JOptionPane.showInputDialog(Main.getInstance(),"Relation name");
		if(name==null || name.equals("")) return;

		TreePath tp=getPathForLocation(x,y);
		Object[] path=tp.getPath();
		Model model=Model.getInstance();
		if(path.length==2){
			if(!model.addRelation(Utils.sanitize(name)))
				Utils.showError("An entity with this name already exists");
		}
	}

	private void editRelation(ActionEvent evt){
		TreePath tp=getPathForLocation(x,y);
		Object[] path=tp.getPath();

		Relation current=Model.getInstance().getRelation(path[path.length-1].toString());
		
		for(int i=0,n=viewer.getTabCount();i<n;i++){
			if(viewer.getModel().getTab(i).getTooltip().equals(path[path.length-1].toString())){
				viewer.getSelectionModel().setSelectedIndex(i);
				return;
			}
		}

		RelationView ent=new RelationView(current,viewer);
		TabData td=new TabData(ent,IconsManager.RELATION,path[path.length-1].toString(),path[path.length-1].toString());
		viewer.getModel().addTab(viewer.getTabCount(), td);
		viewer.getSelectionModel().setSelectedIndex(viewer.getTabCount()-1);
		ent.attachToModel(Model.getInstance());
	}

	private void deleteRelation(ActionEvent evt){
		TreePath tp=getPathForLocation(x,y);
		Object[] path=tp.getPath();
		Model model=Model.getInstance();
		if(path.length==3){
			if(JOptionPane.showConfirmDialog(Main.getInstance(),
											 "Are you really sure?",
											 "Confirmation",
											 JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
				model.removeRelation(path[2].toString());
				for(int i=0,n=viewer.getTabCount();i<n;i++){
					if(viewer.getModel().getTab(i).getTooltip().equals(path[2].toString())){
						viewer.getModel().removeTab(i);
						break;
					}
				}
			}
		}
	}
	
	private void treeMouseClicked(MouseEvent evt){
		TreePath tp=getPathForLocation(x=evt.getX(),y=evt.getY());
		if(tp==null) return;

		Object[] path=tp.getPath();
		if(evt.getButton()==evt.BUTTON3){
			if(path.length>=2 && path[1].toString().equalsIgnoreCase("entities")){
				editEntity.setEnabled(path.length!=2 || !path[1].toString().equalsIgnoreCase("entities"));
				deleteEntity.setEnabled(path.length!=2 || !path[1].toString().equalsIgnoreCase("entities"));
				entityContextMenu.show(evt.getComponent(),x,y);
			}else if(path.length>2 && path[1].toString().equalsIgnoreCase("modules")){
				moduleContextMenu.show(evt.getComponent(),x,y);
			}else if(path.length>=2 && path[1].toString().equalsIgnoreCase("relations")){
				editRelation.setEnabled(path.length!=2 || !path[1].toString().equalsIgnoreCase("relations"));
				deleteRelation.setEnabled(path.length!=2 || !path[1].toString().equalsIgnoreCase("relations"));
				relationContextMenu.show(evt.getComponent(),x,y);
			}
		}else{
			if(evt.getClickCount()==2){
				if(path.length>2 && path[1].toString().equalsIgnoreCase("entities"))
					editEntity(null);
				else if(path.length>2 && path[1].toString().equalsIgnoreCase("modules"))
					editModule(null);//open for edition
				else if(path.length>2 && path[1].toString().equalsIgnoreCase("relations"))
					editRelation(null);
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
		if(subject==null){
			setModel(new DefaultTreeModel(new DefaultMutableTreeNode("No project loaded")));
		}else{
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

			DefaultMutableTreeNode rels = new DefaultMutableTreeNode("Relations");
			root.add(rels);

			for(String s: Utils.convert(subject.getRelations()))
				rels.add(new DefaultMutableTreeNode(s));

			setModel(new DefaultTreeModel(root));
			for(int i=0;i<getRowCount();i++)
				expandRow(i);
		}	
		updateUI();
	}
	
	public void attachToModel(Model model){
		model.attachView(this);
	}
	
	private static class TreeViewRenderer extends DefaultTreeCellRenderer{
		public Component getTreeCellRendererComponent(JTree tree, Object value,
														  boolean sel, boolean expanded, boolean leaf, int row,
														  boolean hasFocus) {
			super.getTreeCellRendererComponent(tree,value,selected,expanded,leaf,row,hasFocus);

			if(value instanceof DefaultMutableTreeNode){
				DefaultMutableTreeNode node=(DefaultMutableTreeNode)value;
				TreeNode path[]=node.getPath();
				if(node.getParent()==null)//root node
					setIcon(IconsManager.PROJECT);
				else if(path.length==2){
					if(path[1].toString().equalsIgnoreCase("Entities"))
						setIcon(IconsManager.ENTITIES);
					else if(path[1].toString().equalsIgnoreCase("Modules"))
						setIcon(IconsManager.MODULES);
					else if(path[1].toString().equalsIgnoreCase("Relations"))
						setIcon(IconsManager.RELATIONS);
					else
						setIcon(IconsManager.FOLDER);
				}else{
					if(path[1].toString().equalsIgnoreCase("Entities"))
						setIcon(IconsManager.ENTITY);
					else if(path[1].toString().equalsIgnoreCase("Modules"))
						setIcon(IconsManager.MODULE);
					else if(path[1].toString().equalsIgnoreCase("Relations"))
						setIcon(IconsManager.RELATION);
				}
			}

			return this;
		}
	}
}