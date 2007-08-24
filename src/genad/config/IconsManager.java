package genad.config;

import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.table.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

import genad.*;
import genad.gui.*;
import genad.model.*;
import genad.config.*;
import genad.engine.*;

/**
 *
 *	@author kronenthaler
 */
public class IconsManager{
	//FILE MENU
	public static final ImageIcon NEW=new ImageIcon(IconsManager.class.getResource("/images/icons/filenew.png"));
	public static final ImageIcon OPEN=new ImageIcon(IconsManager.class.getResource("/images/icons/fileopen.png"));
	public static final ImageIcon SAVE=new ImageIcon(IconsManager.class.getResource("/images/icons/filesave.png"));
	public static final ImageIcon SAVEAS=new ImageIcon(IconsManager.class.getResource("/images/icons/filesaveas.png"));
	public static final ImageIcon CLOSE=new ImageIcon(IconsManager.class.getResource("/images/icons/fileclose.png"));
	public static final ImageIcon GENERATE=new ImageIcon(IconsManager.class.getResource("/images/icons/compfile.png"));
	public static final ImageIcon PROPERTIES=new ImageIcon(IconsManager.class.getResource("/images/icons/properties.png"));
	public static final ImageIcon EXIT=new ImageIcon(IconsManager.class.getResource("/images/icons/exit.png"));
	
	//CONFIGURE MENU
	public static final ImageIcon CONFIGURE=new ImageIcon(IconsManager.class.getResource("/images/icons/configure.png"));
	
	//FOR DIALOGS
	public static final ImageIcon OK=new ImageIcon(IconsManager.class.getResource("/images/icons/ok.png"));
	public static final ImageIcon APPLY=new ImageIcon(IconsManager.class.getResource("/images/icons/apply.png"));
	public static final ImageIcon CANCEL=new ImageIcon(IconsManager.class.getResource("/images/icons/cancel.png"));
	
	//TAB
	public static final ImageIcon TABCLOSE=new ImageIcon(IconsManager.class.getResource("/images/icons/tabclose.png"));
	public static final ImageIcon TABCLOSEOVER=new ImageIcon(IconsManager.class.getResource("/images/icons/tabclose_over.png"));

	//ABOUT
	public static final ImageIcon ABOUT=new ImageIcon(IconsManager.class.getResource("/images/about.png"));
	
	//TREE
	public static final ImageIcon ENTITY=new ImageIcon(IconsManager.class.getResource("/images/icons/page.png"));
	public static final ImageIcon ENTITIES=new ImageIcon(IconsManager.class.getResource("/images/icons/folder_page.png"));
	public static final ImageIcon MODULES=new ImageIcon(IconsManager.class.getResource("/images/icons/folder_plugin.png"));
	public static final ImageIcon MODULE=new ImageIcon(IconsManager.class.getResource("/images/icons/plugin.png"));
	public static final ImageIcon PROJECT=new ImageIcon(IconsManager.class.getResource("/images/icons/folder_project.png"));
	public static final ImageIcon FOLDER=new ImageIcon(IconsManager.class.getResource("/images/icons/folder.png"));
	public static final ImageIcon EDITENTITY=new ImageIcon(IconsManager.class.getResource("/images/icons/page_edit.png"));
	public static final ImageIcon ADDENTITY=new ImageIcon(IconsManager.class.getResource("/images/icons/page_add.png"));
	public static final ImageIcon DELENTITY=new ImageIcon(IconsManager.class.getResource("/images/icons/page_delete.png"));
	public static final ImageIcon EDITMODULE=new ImageIcon(IconsManager.class.getResource("/images/icons/plugin_edit.png"));
	
	//ACTIONS
	public static final ImageIcon ADD=new ImageIcon(IconsManager.class.getResource("/images/icons/add.png"));
	public static final ImageIcon DELETE=new ImageIcon(IconsManager.class.getResource("/images/icons/delete.png"));
	public static final ImageIcon MOVEUP=new ImageIcon(IconsManager.class.getResource("/images/icons/arrow_up.png"));
	public static final ImageIcon MOVEDOWN=new ImageIcon(IconsManager.class.getResource("/images/icons/arrow_down.png"));
	public static final ImageIcon OPTIONS=new ImageIcon(IconsManager.class.getResource("/images/icons/options.png"));
	
	//CONFIGURE
	public static final ImageIcon LANGS=new ImageIcon(IconsManager.class.getResource("/images/icons/plugins.png"));
	public static final ImageIcon FIELDS=new ImageIcon(IconsManager.class.getResource("/images/icons/fields.png"));
	public static final ImageIcon MODS=new ImageIcon(IconsManager.class.getResource("/images/icons/module.png"));
}
