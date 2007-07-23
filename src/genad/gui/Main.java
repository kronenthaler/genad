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
        jMenu1 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JSeparator();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem7 = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JSeparator();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JSeparator();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem8 = new javax.swing.JMenuItem();

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

        jMenu1.setText("Project");
        jMenuItem2.setText("New...");
        jMenu1.add(jMenuItem2);

        jMenu1.add(jSeparator3);

        jMenuItem1.setText("Open...");
        jMenu1.add(jMenuItem1);

        jMenu2.setText("Open Recent");
        jMenu1.add(jMenu2);

        jMenuItem7.setText("Close");
        jMenu1.add(jMenuItem7);

        jMenu1.add(jSeparator4);

        jMenuItem3.setText("Save");
        jMenu1.add(jMenuItem3);

        jMenuItem4.setText("Save As...");
        jMenu1.add(jMenuItem4);

        jMenu1.add(jSeparator1);

        jMenuItem5.setText("Properties...");
        jMenu1.add(jMenuItem5);

        jMenuItem10.setText("Generate...");
        jMenu1.add(jMenuItem10);

        jMenu1.add(jSeparator2);

        jMenuItem6.setText("Exit");
        jMenu1.add(jMenuItem6);

        jMenuBar1.add(jMenu1);

        jMenu4.setText("Settings");
        jMenuItem9.setText("Configure GenAd...");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });

        jMenu4.add(jMenuItem9);

        jMenuBar1.add(jMenu4);

        jMenu3.setText("Help");
        jMenuItem8.setText("About GenAd...");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });

        jMenu3.add(jMenuItem8);

        jMenuBar1.add(jMenu3);

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

	private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
		new ConfigureDlg(this,true);
	}//GEN-LAST:event_jMenuItem9ActionPerformed

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

	private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
		new AboutDlg(this,true);
	}//GEN-LAST:event_jMenuItem8ActionPerformed

	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
		jTabbedPane1.addTab("texto", new EntityView());
		jTabbedPane1.setTabComponentAt(0,new TabComponent(jTabbedPane1, 0, "hola"));
		
	//	jTabbedPane1.addTab("texto mas largo", new JPanel());
	//	jTabbedPane1.setTabComponentAt(1,new TabComponent(jTabbedPane1, 1, "Mother fucker"));
		
		jTabbedPane1.setSelectedIndex(0);
		jTabbedPane1StateChanged(null);
	}//GEN-LAST:event_jButton1ActionPerformed
		
    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JButton jButton1;
    protected javax.swing.JLabel jLabel1;
    protected javax.swing.JMenu jMenu1;
    protected javax.swing.JMenu jMenu2;
    protected javax.swing.JMenu jMenu3;
    protected javax.swing.JMenu jMenu4;
    protected javax.swing.JMenuBar jMenuBar1;
    protected javax.swing.JMenuItem jMenuItem1;
    protected javax.swing.JMenuItem jMenuItem10;
    protected javax.swing.JMenuItem jMenuItem2;
    protected javax.swing.JMenuItem jMenuItem3;
    protected javax.swing.JMenuItem jMenuItem4;
    protected javax.swing.JMenuItem jMenuItem5;
    protected javax.swing.JMenuItem jMenuItem6;
    protected javax.swing.JMenuItem jMenuItem7;
    protected javax.swing.JMenuItem jMenuItem8;
    protected javax.swing.JMenuItem jMenuItem9;
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
    // End of variables declaration//GEN-END:variables

	public static Main getInstance(){
		if(me==null)
			me=new Main();
		return me;
	}
}
