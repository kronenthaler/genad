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
public class ModulesConfigPanel extends javax.swing.JPanel implements Applicable{
	
	public ModulesConfigPanel() {
		initComponents();
		langBoxActionPerformed(null);
		moduleBoxActionPerformed(null);
	}
	
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        langBox = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        moduleBox = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        optionTable = new genad.gui.misc.OptionTable();

        ConfigManager cfgMan=ConfigManager.getInstance();
        Vector<String> installed=cfgMan.getPluginsInstalled();
        langBox.setModel(new DefaultComboBoxModel(installed.toArray(new String[0])));
        langBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                langBoxActionPerformed(evt);
            }
        });

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Module:");

        moduleBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Persistence", "Upload", "RTE", "Users" }));
        moduleBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moduleBoxActionPerformed(evt);
            }
        });

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Language:");

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder("Options"));
        optionTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(optionTable);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 441, Short.MAX_VALUE)
                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                        .add(layout.createSequentialGroup()
                            .add(jLabel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 77, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(langBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 121, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(layout.createSequentialGroup()
                            .add(jLabel4)
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                            .add(moduleBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 121, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        layout.linkSize(new java.awt.Component[] {jLabel3, jLabel4}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(langBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 19, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel3))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel4)
                    .add(moduleBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 19, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

	private void moduleBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moduleBoxActionPerformed
		//sacar las opciones para el tipo de campo seleccionado y agregarlo en el modelo de la tabla
		ConfigManager cfgMan=ConfigManager.getInstance();
		PluginConfig pc=cfgMan.getPluginConfig((String)langBox.getSelectedItem());
		ModuleConfig mc=pc.getModuleConfig((String)moduleBox.getSelectedItem()); 
		
		//sacar lista de opciones
		String[] k=Utils.convert(mc.getOptions());
		Object[][] data=new Object[mc.getOptionSize()][3];
		for(int i=0;i<data.length;i++){
			data[i][0]=k[i];
			data[i][1]=mc.getOption((String)data[i][0]);
			data[i][2]=mc.getDefault((String)data[i][0]);
		}
		
		optionTable.setData(data);
	}//GEN-LAST:event_moduleBoxActionPerformed

	private void langBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_langBoxActionPerformed
		ConfigManager cfgMan=ConfigManager.getInstance();
		PluginConfig pc=cfgMan.getPluginConfig((String)langBox.getSelectedItem());
		
		Vector<String> options=new Vector<String>();
		String[] key=pc.getModulesName();
		for(int i=0;i<key.length;i++)
			if(pc.getModuleConfig(key[i]).getOptionSize()!=0)
				options.add(key[i]);
		
		moduleBox.setModel(new DefaultComboBoxModel(options));
	}//GEN-LAST:event_langBoxActionPerformed

	public boolean apply() {
		return true;
	}
	
    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JLabel jLabel3;
    protected javax.swing.JLabel jLabel4;
    protected javax.swing.JScrollPane jScrollPane1;
    protected javax.swing.JComboBox langBox;
    protected javax.swing.JComboBox moduleBox;
    protected genad.gui.misc.OptionTable optionTable;
    // End of variables declaration//GEN-END:variables
	
}
