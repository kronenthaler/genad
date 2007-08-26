package genad.gui;

import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.table.*;
import com.jgoodies.looks.plastic.*;
import org.netbeans.swing.tabcontrol.*;

import genad.*;
import genad.gui.*;
import genad.model.*;
import genad.config.*;
import genad.engine.*;
import genad.gui.misc.*;

/**
 *	
 * @author  kronenthaler
 */
public class Main extends javax.swing.JFrame implements View{
	private static Main me=null;
	private Model model;
		
	private Main() {
		try{
			PlasticXPLookAndFeel.setTabStyle("Metal");
			UIManager.setLookAndFeel("com.jgoodies.looks.plastic.PlasticXPLookAndFeel");
		}catch(Exception e){}
		
		initComponents();
		splitPanel.setRightComponent(tabbedPanel);
		
		setVisible(true);
	}
	
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
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

        jProgressBar1 = new javax.swing.JProgressBar();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar2 = new javax.swing.JMenuBar();
        projectMen = new javax.swing.JMenu();
        newItem = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JSeparator();
        openItem = new javax.swing.JMenuItem();
        openRecentMen1 = new javax.swing.JMenu();
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
        setTitle("GenAd v1.0");
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
        jScrollPane1.setViewportView(tree);

        splitPanel.setLeftComponent(jScrollPane1);

        jProgressBar1.setValue(25);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("jLabel1");

        projectMen.setText("Project");
        newItem.setIcon(IconsManager.NEW);
        newItem.setText("New...");
        newItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newActionPerformed(evt);
            }
        });

        projectMen.add(newItem);

        projectMen.add(jSeparator6);

        openItem.setIcon(IconsManager.OPEN);
        openItem.setText("Open...");
        openItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openActionPerformed(evt);
            }
        });

        projectMen.add(openItem);

        openRecentMen1.setIcon(IconsManager.OPEN);
        openRecentMen1.setText("Open Recent");
        openRecentMen1.setEnabled(false);
        projectMen.add(openRecentMen1);

        closeItem.setIcon(IconsManager.CLOSE);
        closeItem.setText("Close");
        closeItem.setEnabled(false);
        projectMen.add(closeItem);

        projectMen.add(jSeparator7);

        saveItem.setIcon(IconsManager.SAVE);
        saveItem.setText("Save");
        saveItem.setEnabled(false);
        saveItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveActionPerformed(evt);
            }
        });

        projectMen.add(saveItem);

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

        propertiesItem.setIcon(IconsManager.PROPERTIES);
        propertiesItem.setText("Properties...");
        propertiesItem.setEnabled(false);
        propertiesItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                propertiesActionPerformed(evt);
            }
        });

        projectMen.add(propertiesItem);

        generateItem.setIcon(IconsManager.GENERATE);
        generateItem.setText("Generate...");
        generateItem.setEnabled(false);
        generateItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateItemgenerateActionPerformed(evt);
            }
        });

        projectMen.add(generateItem);

        projectMen.add(jSeparator9);

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
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 493, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jProgressBar1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 148, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .add(jToolBar1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 677, Short.MAX_VALUE)
            .add(splitPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 677, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jToolBar1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 25, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(splitPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jProgressBar1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel1))
                .addContainerGap())
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

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

	private void generateItemgenerateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generateItemgenerateActionPerformed
		// TODO add your handling code here:
	}//GEN-LAST:event_generateItemgenerateActionPerformed

	private void windowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_windowClosing
		exitItemActionPerformed(null);
	}//GEN-LAST:event_windowClosing

	private void generateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generateActionPerformed
		// TODO add your handling code here:
	}//GEN-LAST:event_generateActionPerformed

	private void propertiesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_propertiesActionPerformed
		new PropertiesDlg(this, true);
	}//GEN-LAST:event_propertiesActionPerformed

	private void saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActionPerformed
		if(model.getLoadedPath()==null){
			saveAsActionPerformed(evt); 
			return;
		}
		if(!model.save(new File(model.getLoadedPath())))
			JOptionPane.showMessageDialog(this,
										  "An error occur saving the project, try another path or name",
										  "Error",
										  JOptionPane.ERROR_MESSAGE);
	}//GEN-LAST:event_saveActionPerformed

	private void openActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openActionPerformed
		if(model!=null && model.isChanged()){
			int opt=JOptionPane.showConfirmDialog(this,
												  "The project was changed. Do you want to save the changes?",
												  "Confirmation",
												  JOptionPane.YES_NO_CANCEL_OPTION);
			if(opt==JOptionPane.CANCEL_OPTION) return;
			if(opt==JOptionPane.YES_OPTION){
				saveActionPerformed(evt);
				if(model.isChanged()) return; //yes and cancel in the saveas dialog
			}
		}
		
		JFileChooser fc=new JFileChooser("."/*System.getProperty("user.home")*/);
		fc.setFileFilter(new ProjectFileFilter());
		
		if(fc.showOpenDialog(this)==JFileChooser.APPROVE_OPTION){
			tabbedPanel.getModel().removeTabs(0, tabbedPanel.getTabCount());//'close' all tabs
			
			model=Model.getInstance(true);//create a new model, ONLY CAN BE CALL FROM HERE and newActionPerformed
			model.load(fc.getSelectedFile());
			
			attachToModel(model);
		}
	}//GEN-LAST:event_openActionPerformed

	private void newActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newActionPerformed
		new PropertiesDlg(this, true);
	}//GEN-LAST:event_newActionPerformed
		
    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JMenuItem aboutItem;
    protected javax.swing.JMenuItem closeItem;
    protected javax.swing.JMenuItem configureItem;
    protected javax.swing.JMenuItem exitItem;
    protected javax.swing.JButton generateBtn;
    protected javax.swing.JMenuItem generateItem;
    protected javax.swing.JMenu helpMen;
    protected javax.swing.JLabel jLabel1;
    protected javax.swing.JMenuBar jMenuBar2;
    protected javax.swing.JProgressBar jProgressBar1;
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
    protected javax.swing.JMenu openRecentMen1;
    protected javax.swing.JMenu projectMen;
    protected javax.swing.JButton propertiesBtn;
    protected javax.swing.JMenuItem propertiesItem;
    protected javax.swing.JMenuItem saveAsItem;
    protected javax.swing.JButton saveBtn;
    protected javax.swing.JMenuItem saveItem;
    protected javax.swing.JMenu settingsMen;
    protected javax.swing.JSplitPane splitPanel;
    protected javax.swing.JTree tree;
    // End of variables declaration//GEN-END:variables
	protected TabbedContainer tabbedPanel;
		
	public static Main getInstance(){
		if(me==null) me=new Main();
		return me;
	}

	public void update(Model subject) {
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

	public void attachToModel(Model subject) {
		model=subject;
		model.attachView(this);
		((TreeView)tree).attachToModel(model);
	}
	
	private static class ProjectFileFilter extends javax.swing.filechooser.FileFilter{
		public boolean accept(File f) {
			return f.isDirectory() || f.getAbsolutePath().endsWith(".xml");
		}
		public String getDescription() {
			return "GenAd Project Files";
		}
	};
}
