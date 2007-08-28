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
public class FieldsConfigPanel extends javax.swing.JPanel implements Applicable{
	
	public FieldsConfigPanel() {
		initComponents();
		langBoxActionPerformed(null);
		fieldBoxActionPerformed(null);
	}
	
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        langBox = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        fieldBox = new javax.swing.JComboBox();
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

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Language:");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Field Type:");

        fieldBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TextField", "Password", "Email", "Integer" }));
        fieldBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldBoxActionPerformed(evt);
            }
        });

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
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(jLabel4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(jLabel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(fieldBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 121, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(langBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 121, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(langBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 19, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel3))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(fieldBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 19, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel4))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

	private void fieldBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldBoxActionPerformed
		//sacar las opciones para el tipo de campo seleccionado y agregarlo en el modelo de la tabla
		ConfigManager cfgMan=ConfigManager.getInstance();
		PluginConfig pc=cfgMan.getPluginConfig((String)langBox.getSelectedItem());
		FieldConfig fc=pc.getFieldConfig((String)fieldBox.getSelectedItem()); 
		
		//sacar lista de opciones
		String[] k=Utils.convert(fc.getOptions());
		Object[][] data=new Object[fc.getOptionSize()][3];
		for(int i=0;i<data.length;i++){
			data[i][0]=k[i];
			data[i][1]=fc.getOption((String)data[i][0]);
			data[i][2]=fc.getDefault((String)data[i][0]);
		}
		
		optionTable.setData(data);
	}//GEN-LAST:event_fieldBoxActionPerformed

	private void langBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_langBoxActionPerformed
		ConfigManager cfgMan=ConfigManager.getInstance();
		PluginConfig pc=cfgMan.getPluginConfig((String)langBox.getSelectedItem());
		//Hashtable<String,FieldConfig> fc=pc.getFieldsConfig();
		//TODO: eliminar los fields que no tengan opciones de configuracion
		Vector<String> options=new Vector<String>();
		String[] key=pc.getValidTypes();//Utils.convert(fc.keys());
		for(int i=0;i<key.length;i++)
			if(pc.getFieldConfig(key[i]).getOptionSize()!=0)
				options.add(key[i]);
		
		fieldBox.setModel(new DefaultComboBoxModel(options));
	}//GEN-LAST:event_langBoxActionPerformed

	public boolean apply() {
		return true;
	}
	
	
    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JComboBox fieldBox;
    protected javax.swing.JLabel jLabel3;
    protected javax.swing.JLabel jLabel4;
    protected javax.swing.JScrollPane jScrollPane1;
    protected javax.swing.JComboBox langBox;
    protected genad.gui.misc.OptionTable optionTable;
    // End of variables declaration//GEN-END:variables
	
}
