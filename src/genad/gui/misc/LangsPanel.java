package genad.gui.misc;

import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.table.*;

import genad.*;
import genad.gui.*;
import genad.model.*;
import genad.config.*;
import genad.engine.*;

/**
 *
 * @author  kronenthaler
 */
public class LangsPanel extends javax.swing.JPanel implements Applicable{
	public LangsPanel() {
		initComponents();
	}

    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        installBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        pluginTable = new javax.swing.JTable();

        installBtn.setText("Install Language");
        installBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                installBtnActionPerformed(evt);
            }
        });

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder("Installed Languages"));
        Object[][] data=null;
        try{
            ConfigManager cfgMan=ConfigManager.getInstance();
            Hashtable<String, PluginConfig> configs=cfgMan.getPluginsConfig();

            data=new Object [configs.size()][2];
            Enumeration<String> e=configs.keys();
            for(int i=0;e.hasMoreElements();i++){
                String pluginName=e.nextElement();
                data[i]=new Object[]{cfgMan.getPluginsActive().get(pluginName), configs.get(pluginName).getDescription()};
            }
        }catch(Exception e){
            data=new Object[][]{{true,"Epac"}};
        }

        //*/
        pluginTable.setModel(new javax.swing.table.DefaultTableModel(data,new String [] { "Active", "Description" }){
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        pluginTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        TableColumn column = null;
        column = pluginTable.getColumnModel().getColumn(0);
        column.setResizable(false);
        column.setMaxWidth(55);
        column.setMinWidth(50);
        column.setPreferredWidth(50);
        pluginTable.getTableHeader().setReorderingAllowed(false);

        jScrollPane1.setViewportView(pluginTable);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE)
                    .add(installBtn))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(installBtn)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

	private void installBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_installBtnActionPerformed
		// TODO add your handling code here:
	}//GEN-LAST:event_installBtnActionPerformed

	public boolean apply(){
		return true;
	}	
	
    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JButton installBtn;
    protected javax.swing.JScrollPane jScrollPane1;
    protected javax.swing.JTable pluginTable;
    // End of variables declaration//GEN-END:variables
	
}
