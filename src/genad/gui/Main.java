package genad.gui;

import java.awt.*;
import javax.swing.*;
import com.jgoodies.looks.plastic.*;

import genad.*;
import genad.gui.misc.*;

/**
 *
 * @author  kronenthaler
 */
public class Main extends javax.swing.JFrame {
	private static Main me=null;
	private Main() {
		me=this;
		try{
			PlasticXPLookAndFeel.setTabStyle("Metal");
			UIManager.setLookAndFeel("com.jgoodies.looks.plastic.PlasticXPLookAndFeel");
		}catch(Exception e){}
		
		initComponents();
	}
	
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jProgressBar1 = new javax.swing.JProgressBar();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        projectMen = new javax.swing.JMenu();
        newItem = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JSeparator();
        openItem = new javax.swing.JMenuItem();
        openRecentMen = new javax.swing.JMenu();
        closeItem = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JSeparator();
        saveItem = new javax.swing.JMenuItem();
        saveAsItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        propertiesItem = new javax.swing.JMenuItem();
        generateItem = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JSeparator();
        exitItem = new javax.swing.JMenuItem();
        settingsMen = new javax.swing.JMenu();
        configureItem = new javax.swing.JMenuItem();
        helpMen = new javax.swing.JMenu();
        aboutItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("GenAd v1.0");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setState(JFrame.MAXIMIZED_BOTH);
        jSplitPane1.setDividerLocation(200);
        jScrollPane1.setViewportView(jTree1);

        jSplitPane1.setLeftComponent(jScrollPane1);

        jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPane1StateChanged(evt);
            }
        });

        jSplitPane1.setRightComponent(jTabbedPane1);

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jToolBar1.add(jButton1);

        jProgressBar1.setValue(25);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("jLabel1");

        projectMen.setText("Project");
        newItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/filenew.png")));
        newItem.setText("New...");
        projectMen.add(newItem);

        projectMen.add(jSeparator3);

        openItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/fileopen.png")));
        openItem.setText("Open...");
        projectMen.add(openItem);

        openRecentMen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/fileopen.png")));
        openRecentMen.setText("Open Recent");
        projectMen.add(openRecentMen);

        closeItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/fileclose.png")));
        closeItem.setText("Close");
        projectMen.add(closeItem);

        projectMen.add(jSeparator4);

        saveItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/filesave.png")));
        saveItem.setText("Save");
        projectMen.add(saveItem);

        saveAsItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/filesaveas.png")));
        saveAsItem.setText("Save As...");
        projectMen.add(saveAsItem);

        projectMen.add(jSeparator1);

        propertiesItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/properties.png")));
        propertiesItem.setText("Properties...");
        projectMen.add(propertiesItem);

        generateItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/compfile.png")));
        generateItem.setText("Generate...");
        projectMen.add(generateItem);

        projectMen.add(jSeparator2);

        exitItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/exit.png")));
        exitItem.setText("Exit");
        exitItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitItemActionPerformed(evt);
            }
        });

        projectMen.add(exitItem);

        jMenuBar1.add(projectMen);

        settingsMen.setText("Settings");
        configureItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/configure.png")));
        configureItem.setText("Configure GenAd...");
        configureItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                configureItemActionPerformed(evt);
            }
        });

        settingsMen.add(configureItem);

        jMenuBar1.add(settingsMen);

        helpMen.setText("Help");
        aboutItem.setText("About GenAd...");
        aboutItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutItemActionPerformed(evt);
            }
        });

        helpMen.add(aboutItem);

        jMenuBar1.add(helpMen);

        setJMenuBar(jMenuBar1);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jToolBar1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 475, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jProgressBar1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 148, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jSplitPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jToolBar1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 25, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jSplitPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jProgressBar1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel1))
                .addContainerGap())
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

	private void exitItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitItemActionPerformed
		System.exit(0);
	}//GEN-LAST:event_exitItemActionPerformed

	private void configureItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_configureItemActionPerformed
		new ConfigureDlg(this,true);
	}//GEN-LAST:event_configureItemActionPerformed

	private void jTabbedPane1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane1StateChanged
		try{
			int index=jTabbedPane1.getSelectedIndex();
			Component comp=jTabbedPane1.getTabComponentAt(index);
			
			if(comp!=null){
				System.out.println(jTabbedPane1.getComponentCount());
				for(int i=0;i<jTabbedPane1.getComponentCount();i++){
					jTabbedPane1.getTabComponentAt(i)
					.setBackground(Utils.getColor(i==index?Utils.TABBED_SELECTED:Utils.TABBED_BACKGROUND));
				}
			}
		}catch(Exception e){}
	}//GEN-LAST:event_jTabbedPane1StateChanged

	private void aboutItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutItemActionPerformed
		new AboutDlg(this,true);
	}//GEN-LAST:event_aboutItemActionPerformed

	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
		jTabbedPane1.addTab("texto", new EntityView());
		jTabbedPane1.setTabComponentAt(0,new TabComponent(jTabbedPane1, 0, "hola"));
		
		jTabbedPane1.addTab("texto mas largo", new JPanel());
		jTabbedPane1.setTabComponentAt(1,new TabComponent(jTabbedPane1, 1, "Mother fucker"));
		
		jTabbedPane1.setSelectedIndex(0);
		jTabbedPane1StateChanged(null);
	}//GEN-LAST:event_jButton1ActionPerformed
		
    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JMenuItem aboutItem;
    protected javax.swing.JMenuItem closeItem;
    protected javax.swing.JMenuItem configureItem;
    protected javax.swing.JMenuItem exitItem;
    protected javax.swing.JMenuItem generateItem;
    protected javax.swing.JMenu helpMen;
    protected javax.swing.JButton jButton1;
    protected javax.swing.JLabel jLabel1;
    protected javax.swing.JMenuBar jMenuBar1;
    protected javax.swing.JProgressBar jProgressBar1;
    protected javax.swing.JScrollPane jScrollPane1;
    protected javax.swing.JSeparator jSeparator1;
    protected javax.swing.JSeparator jSeparator2;
    protected javax.swing.JSeparator jSeparator3;
    protected javax.swing.JSeparator jSeparator4;
    protected javax.swing.JSplitPane jSplitPane1;
    protected javax.swing.JTabbedPane jTabbedPane1;
    protected javax.swing.JToolBar jToolBar1;
    protected javax.swing.JTree jTree1;
    protected javax.swing.JMenuItem newItem;
    protected javax.swing.JMenuItem openItem;
    protected javax.swing.JMenu openRecentMen;
    protected javax.swing.JMenu projectMen;
    protected javax.swing.JMenuItem propertiesItem;
    protected javax.swing.JMenuItem saveAsItem;
    protected javax.swing.JMenuItem saveItem;
    protected javax.swing.JMenu settingsMen;
    // End of variables declaration//GEN-END:variables

	public static Main getInstance(){
		if(me==null)
			me=new Main();
		return me;
	}
}
