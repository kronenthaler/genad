package genad.gui;

import genad.model.Engine;
import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.table.*;
import javax.swing.tree.*;
import com.jgoodies.looks.plastic.*;
import javax.swing.tree.DefaultMutableTreeNode;
import org.netbeans.swing.tabcontrol.*;

import genad.*;
import genad.gui.*;
import genad.model.*;
import genad.config.*;
import genad.gui.misc.*;

/**
 *	
 * @author  kronenthaler
 */
public class Main extends javax.swing.JFrame implements View{
	private static Main me=null;
	private Model model;
	private PriorityQueue<FileRecent> recents;
		
	private Main() {
		try{
			PlasticXPLookAndFeel.setTabStyle("Metal");
			UIManager.setLookAndFeel("com.jgoodies.looks.plastic.PlasticXPLookAndFeel");
		}catch(Exception e){}
		
		loadRecents();
		initComponents();
		setVisible(true);
		
		statusBar.setText("Ready");
	}
	
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        newBtn = new javax.swing.JButton();
        openBtn = new javax.swing.JButton();
        saveBtn = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JSeparator();
        propertiesBtn = new javax.swing.JButton();
        generateBtn = new javax.swing.JButton();
        splitPanel = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabbedPanel=new TabbedContainer(new DefaultTabDataModel(), TabbedContainer.TYPE_EDITOR);
        tree = new  TreeView(tabbedPanel);

        statusBar = new javax.swing.JLabel();
        jMenuBar2 = new javax.swing.JMenuBar();
        projectMen = new javax.swing.JMenu();
        newItem = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JSeparator();
        openItem = new javax.swing.JMenuItem();
        openRecentMen = new javax.swing.JMenu();
        closeItem = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JSeparator();
        saveItem = new javax.swing.JMenuItem();
        saveAsItem = new javax.swing.JMenuItem();
        jSeparator8 = new javax.swing.JSeparator();
        propertiesItem = new javax.swing.JMenuItem();
        generateItem = new javax.swing.JMenuItem();
        jSeparator9 = new javax.swing.JSeparator();
        exitItem = new javax.swing.JMenuItem();
        settingsMen = new javax.swing.JMenu();
        configureItem = new javax.swing.JMenuItem();
        helpMen = new javax.swing.JMenu();
        aboutItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("GenAd v1.5");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setState(JFrame.MAXIMIZED_BOTH);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                Main.this.windowClosing(evt);
            }
        });

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        newBtn.setIcon(IconsManager.NEW);
        newBtn.setMargin(new java.awt.Insets(5, 5, 5, 5));
        newBtn.setMaximumSize(new java.awt.Dimension(24, 24));
        newBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newActionPerformed(evt);
            }
        });
        jToolBar1.add(newBtn);

        openBtn.setIcon(IconsManager.OPEN);
        openBtn.setMargin(new java.awt.Insets(5, 5, 5, 5));
        openBtn.setMaximumSize(new java.awt.Dimension(24, 24));
        openBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openActionPerformed(evt);
            }
        });
        jToolBar1.add(openBtn);

        saveBtn.setIcon(IconsManager.SAVE);
        saveBtn.setEnabled(false);
        saveBtn.setMargin(new java.awt.Insets(5, 5, 5, 5));
        saveBtn.setMaximumSize(new java.awt.Dimension(24, 24));
        saveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveActionPerformed(evt);
            }
        });
        jToolBar1.add(saveBtn);

        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator5.setMaximumSize(new java.awt.Dimension(5, 666));
        jSeparator5.setPreferredSize(new java.awt.Dimension(50, 5));
        jToolBar1.add(jSeparator5);

        propertiesBtn.setIcon(IconsManager.PROPERTIES);
        propertiesBtn.setToolTipText("Edit the properties of the project");
        propertiesBtn.setEnabled(false);
        propertiesBtn.setMargin(new java.awt.Insets(5, 5, 5, 5));
        propertiesBtn.setMaximumSize(new java.awt.Dimension(24, 24));
        propertiesBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                propertiesActionPerformed(evt);
            }
        });
        jToolBar1.add(propertiesBtn);

        generateBtn.setIcon(IconsManager.GENERATE);
        generateBtn.setToolTipText("Generate the project files");
        generateBtn.setEnabled(false);
        generateBtn.setMargin(new java.awt.Insets(5, 5, 5, 5));
        generateBtn.setMaximumSize(new java.awt.Dimension(24, 24));
        generateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateActionPerformed(evt);
            }
        });
        jToolBar1.add(generateBtn);

        splitPanel.setDividerLocation(200);
        splitPanel.setDividerSize(7);
        splitPanel.setRightComponent(tabbedPanel);

        jScrollPane1.setViewportView(tree);

        splitPanel.setLeftComponent(jScrollPane1);

        statusBar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusBar.setText("jLabel1");

        projectMen.setText("Project");

        newItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        newItem.setIcon(IconsManager.NEW);
        newItem.setText("New Project...");
        newItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newActionPerformed(evt);
            }
        });
        projectMen.add(newItem);
        projectMen.add(jSeparator6);

        openItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        openItem.setIcon(IconsManager.OPEN);
        openItem.setText("Open...");
        openItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openActionPerformed(evt);
            }
        });
        projectMen.add(openItem);

        openRecentMen.setEnabled(recents.size()>0);
        Iterator<FileRecent> it=recents.iterator();
        for(int i=0,n=recents.size();i<4 && i<n;i++){
            final JMenuItem mi=new JMenuItem(it.next().path);
            mi.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent evt){
                    if(model!=null && model.isChanged()){
                        int opt=JOptionPane.showConfirmDialog(Main.getInstance(),
                            "The project was changed. Do you want to save the changes?",
                            "Confirmation",
                            JOptionPane.YES_NO_CANCEL_OPTION);
                        if(opt==JOptionPane.CANCEL_OPTION) return;
                        if(opt==JOptionPane.YES_OPTION){
                            saveActionPerformed(evt);
                            if(model.isChanged()) return; //yes and cancel in the saveas dialog
                        }
                    }

                    tabbedPanel.getModel().removeTabs(0, tabbedPanel.getTabCount());//'close' all tabs

                    model=Model.getInstance(true);//create a new model, ONLY CAN BE CALL FROM HERE and newActionPerformed
                    if(model.load(new File(mi.getText()))){
                        recents.add(new FileRecent(System.currentTimeMillis(),mi.getText()));
                        attachToModel(model);
                    }else
                    Utils.showError("The project is corrupted and can't be opened");
                }
            });
            openRecentMen.add(mi);
        }
        openRecentMen.setIcon(IconsManager.OPEN);
        openRecentMen.setText("Open Recent");
        projectMen.add(openRecentMen);

        closeItem.setIcon(IconsManager.CLOSE);
        closeItem.setText("Close");
        closeItem.setEnabled(false);
        closeItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeItemActionPerformed(evt);
            }
        });
        projectMen.add(closeItem);
        projectMen.add(jSeparator7);

        saveItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        saveItem.setIcon(IconsManager.SAVE);
        saveItem.setText("Save");
        saveItem.setEnabled(false);
        saveItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveActionPerformed(evt);
            }
        });
        projectMen.add(saveItem);

        saveAsItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        saveAsItem.setIcon(IconsManager.SAVEAS);
        saveAsItem.setText("Save As...");
        saveAsItem.setEnabled(false);
        saveAsItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveAsActionPerformed(evt);
            }
        });
        projectMen.add(saveAsItem);
        projectMen.add(jSeparator8);

        propertiesItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        propertiesItem.setIcon(IconsManager.PROPERTIES);
        propertiesItem.setText("Properties...");
        propertiesItem.setEnabled(false);
        propertiesItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                propertiesActionPerformed(evt);
            }
        });
        projectMen.add(propertiesItem);

        generateItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, java.awt.event.InputEvent.CTRL_MASK));
        generateItem.setIcon(IconsManager.GENERATE);
        generateItem.setText("Generate...");
        generateItem.setEnabled(false);
        generateItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateActionPerformed(evt);
            }
        });
        projectMen.add(generateItem);
        projectMen.add(jSeparator9);

        exitItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        exitItem.setIcon(IconsManager.EXIT);
        exitItem.setText("Exit");
        exitItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitItemActionPerformed(evt);
            }
        });
        projectMen.add(exitItem);

        jMenuBar2.add(projectMen);

        settingsMen.setText("Settings");

        configureItem.setIcon(IconsManager.CONFIGURE);
        configureItem.setText("Configure GenAd...");
        configureItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                configureItemActionPerformed(evt);
            }
        });
        settingsMen.add(configureItem);

        jMenuBar2.add(settingsMen);

        helpMen.setText("Help");

        aboutItem.setText("About GenAd...");
        aboutItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutItemActionPerformed(evt);
            }
        });
        helpMen.add(aboutItem);

        jMenuBar2.add(helpMen);

        setJMenuBar(jMenuBar2);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jToolBar1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 677, Short.MAX_VALUE)
            .add(splitPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 677, Short.MAX_VALUE)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, statusBar, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 677, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jToolBar1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 25, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(splitPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(statusBar)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

	private void closeItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeItemActionPerformed
		if(!checkOpen()) return;
		
		tabbedPanel.getModel().removeTabs(0,tabbedPanel.getTabCount());
		model.dettachView(this);
		model.dettachView((TreeView)tree);
		//dettachFromModel, eliminarme de la lista de viewers de ese model.
	}//GEN-LAST:event_closeItemActionPerformed

	private void saveAsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveAsActionPerformed
		JFileChooser fc=new JFileChooser("."/*System.getProperty("user.home")*/);
		fc.setFileFilter(new ProjectFileFilter());
		if(fc.showSaveDialog(this)==JFileChooser.APPROVE_OPTION){
			model.setLoadedPath(fc.getSelectedFile().toString());
			saveActionPerformed(evt);
		}
	}//GEN-LAST:event_saveAsActionPerformed

	private void aboutItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutItemActionPerformed
		new AboutDlg(this,true);
	}//GEN-LAST:event_aboutItemActionPerformed

	private void configureItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_configureItemActionPerformed
		new ConfigureDlg(this,true);
	}//GEN-LAST:event_configureItemActionPerformed

	private void exitItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitItemActionPerformed
		saveRecents();
		if(model!=null && model.isChanged()){
			int opt=JOptionPane.showConfirmDialog(this,"The project was changed. Do you want to save the changes?","Confirmation",JOptionPane.YES_NO_CANCEL_OPTION);
			if(opt==JOptionPane.NO_OPTION) System.exit(0);
			if(opt==JOptionPane.CANCEL_OPTION) return;
			saveActionPerformed(null);
			if(!model.isChanged()) //click in yes but cancel in the save as.
				System.exit(0); 
		}else
			System.exit(0);
	}//GEN-LAST:event_exitItemActionPerformed

	private void windowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_windowClosing
		exitItemActionPerformed(null);
	}//GEN-LAST:event_windowClosing

	private void generateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generateActionPerformed
		saveActionPerformed(evt);
		
		if(new File(model.getDestinationPath()).exists() && 
		   JOptionPane.showConfirmDialog(this,
										 "WARNING: The destination path exists and maybe isn't empty, some files will be replaced without confirmation.\nDo you want to continue?",
										 "Confirmation", 
										 JOptionPane.YES_NO_OPTION)==JOptionPane.NO_OPTION)
			return;
		
		ProgressDlg pDlg=new ProgressDlg(this, true);
		Engine e=new Engine(pDlg);
		e.setModel(model);
		pDlg.attachToModel(e);
		pDlg.setVisible(true);
		
	}//GEN-LAST:event_generateActionPerformed

	private void propertiesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_propertiesActionPerformed
		new PropertiesDlg(this, true, Model.getInstance());
	}//GEN-LAST:event_propertiesActionPerformed

	private void saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActionPerformed
		try{
			if(model.getLoadedPath()==null){
				saveAsActionPerformed(evt); 
				return;
			}
			if(!model.save(new File(model.getLoadedPath())))
				Utils.showError("An error occur saving the project. Try another path or name.");
		}catch(ArrayIndexOutOfBoundsException e){
			Utils.showWarning("Project has not been saved");
		}catch(Exception e){
			Utils.showError("Unexpected error");
		}
	}//GEN-LAST:event_saveActionPerformed

	private void openActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openActionPerformed
		if(!checkOpen()) return;
		
		JFileChooser fc=new JFileChooser("."/*System.getProperty("user.home")*/);
		fc.setFileFilter(new ProjectFileFilter());
		
		if(fc.showOpenDialog(this)==JFileChooser.APPROVE_OPTION){
			tabbedPanel.getModel().removeTabs(0, tabbedPanel.getTabCount());//'close' all tabs
			
			model=Model.getInstance(true);//create a new model, ONLY CAN BE CALL FROM HERE and newActionPerformed
			if(model.load(fc.getSelectedFile())){
				recents.add(new FileRecent(System.currentTimeMillis(),fc.getSelectedFile().toString()));
				attachToModel(model);
			}else
				Utils.showError("The project is corrupted and can't be opened");
				
		}
	}//GEN-LAST:event_openActionPerformed

	private void newActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newActionPerformed
		if(!checkOpen()) return;
		
		if(new PropertiesDlg(this, true, Model.getInstance(true)).isApproved()==JOptionPane.OK_OPTION)
			attachToModel(Model.getInstance());
	}//GEN-LAST:event_newActionPerformed
		
    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JMenuItem aboutItem;
    protected javax.swing.JMenuItem closeItem;
    protected javax.swing.JMenuItem configureItem;
    protected javax.swing.JMenuItem exitItem;
    protected javax.swing.JButton generateBtn;
    protected javax.swing.JMenuItem generateItem;
    protected javax.swing.JMenu helpMen;
    protected javax.swing.JMenuBar jMenuBar2;
    protected javax.swing.JScrollPane jScrollPane1;
    protected javax.swing.JSeparator jSeparator5;
    protected javax.swing.JSeparator jSeparator6;
    protected javax.swing.JSeparator jSeparator7;
    protected javax.swing.JSeparator jSeparator8;
    protected javax.swing.JSeparator jSeparator9;
    protected javax.swing.JToolBar jToolBar1;
    protected javax.swing.JButton newBtn;
    protected javax.swing.JMenuItem newItem;
    protected javax.swing.JButton openBtn;
    protected javax.swing.JMenuItem openItem;
    protected javax.swing.JMenu openRecentMen;
    protected javax.swing.JMenu projectMen;
    protected javax.swing.JButton propertiesBtn;
    protected javax.swing.JMenuItem propertiesItem;
    protected javax.swing.JMenuItem saveAsItem;
    protected javax.swing.JButton saveBtn;
    protected javax.swing.JMenuItem saveItem;
    protected javax.swing.JMenu settingsMen;
    protected javax.swing.JSplitPane splitPanel;
    protected javax.swing.JLabel statusBar;
    protected javax.swing.JTree tree;
    // End of variables declaration//GEN-END:variables
	protected TabbedContainer tabbedPanel;
		
	public static Main getInstance(){
		if(me==null) me=new Main();
		return me;
	}
	
	private void loadRecents(){
		recents=new PriorityQueue<FileRecent>();
		try{
			File f=new File(System.getProperty("user.home")+"/.genad/");
			if(!f.exists())
				f.mkdirs();
				
			Scanner in=new Scanner(new File(System.getProperty("user.home")+"/.genad/recents"));
			int i=0;
			while(in.hasNextLong() && i++<4)
				recents.add(new FileRecent(in.nextLong(), in.nextLine().trim()));
			
			in.close();
		}catch(Exception e){}
	}
	
	private void saveRecents(){
		try{
			PrintStream out=new PrintStream(new FileOutputStream(System.getProperty("user.home")+"/.genad/recents"),true);
			Hashtable<String, Boolean> exists=new Hashtable<String, Boolean>();
			for(int i=0;i<4 && !recents.isEmpty();i++){
				FileRecent fr=recents.poll();
				if(exists.get(fr.path)==null){
					out.println(fr);
					exists.put(fr.path,true);
				}
			}
			
			out.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void update(Model subject) {
		if(subject==null){
			setTitle("GenAd v1.0");
			
			saveBtn.setEnabled(false);
			saveItem.setEnabled(false);
			saveAsItem.setEnabled(false);

			closeItem.setEnabled(false);

			propertiesBtn.setEnabled(false);
			propertiesItem.setEnabled(false);

			generateBtn.setEnabled(false);
			generateItem.setEnabled(false);
		}else{
			setTitle("GenAd - "+subject.getLoadedPath()+(subject.isChanged()?" *":""));

			saveBtn.setEnabled(subject.isChanged());
			saveItem.setEnabled(subject.isChanged());
			saveAsItem.setEnabled(true);

			closeItem.setEnabled(subject.getLoadedPath()!=null);

			propertiesBtn.setEnabled(subject.getLoadedPath()!=null);
			propertiesItem.setEnabled(subject.getLoadedPath()!=null);

			generateBtn.setEnabled(subject.getLoadedPath()!=null);
			generateItem.setEnabled(subject.getLoadedPath()!=null);
		}
	}

	public void attachToModel(Model subject){
		model=subject;
		model.attachView(this);
		((TreeView)tree).attachToModel(model);
	}

	private boolean checkOpen() {
		if(model!=null && model.isChanged()){
			int opt=JOptionPane.showConfirmDialog(this,
												  "The project was changed. Do you want to save the changes?",
												  "Confirmation",
												  JOptionPane.YES_NO_CANCEL_OPTION);
			if(opt==JOptionPane.CANCEL_OPTION) return false;
			if(opt==JOptionPane.YES_OPTION){
				saveActionPerformed(null);
				if(model.isChanged()) return false; //yes and cancel in the saveas dialog
			}
		}
		return true;
	}
	
	private static class ProjectFileFilter extends javax.swing.filechooser.FileFilter{
		public boolean accept(File f) {
			return f.isDirectory() || f.getAbsolutePath().endsWith(".xml");
		}
		public String getDescription() {
			return "GenAd Project Files";
		}
	}
	
	private static class FileRecent implements Comparable<FileRecent>{
		protected long date;
		protected String path; 
		public FileRecent(long d,String p){
			date=d;
			path=p;
		}
		
		public String toString(){
			return date+" "+path;
		}

		public int compareTo(FileRecent o){
			return (o.date > date)?1:-1;
		}
	}
}
