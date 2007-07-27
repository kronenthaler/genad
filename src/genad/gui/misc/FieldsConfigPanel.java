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
	}
	
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        langBox = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        fieldBox = new javax.swing.JComboBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        optionTable = new javax.swing.JTable();

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

        jScrollPane2.setBorder(javax.swing.BorderFactory.createTitledBorder("Options"));
        optionTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Default Max. Length", null},
                {"Default Min. Length", null},
                {"Default Value", null},
                {"Is visible", null}
            },
            new String [] {
                "", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        optionTable.setTableHeader(null);
        jScrollPane2.setViewportView(optionTable);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(jLabel4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(jLabel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(fieldBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 121, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(langBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 121, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 441, Short.MAX_VALUE))
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
                .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

	private void fieldBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldBoxActionPerformed
		//sacar las opciones para el tipo de campo seleccionado y agregarlo en el modelo de la tabla
		ConfigManager cfgMan=ConfigManager.getInstance();
		PluginConfig pc=cfgMan.getPluginsConfig().get((String)langBox.getSelectedItem());
		FieldConfig fc=pc.getFieldsConfig().get((String)fieldBox.getSelectedItem()); 
		//sacar lista de opciones
	}//GEN-LAST:event_fieldBoxActionPerformed

	private void langBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_langBoxActionPerformed
		ConfigManager cfgMan=ConfigManager.getInstance();
		PluginConfig pc=cfgMan.getPluginsConfig().get((String)langBox.getSelectedItem());
		Hashtable<String,FieldConfig> fc=pc.getFieldsConfig();
		
		fieldBox.setModel(new DefaultComboBoxModel(Utils.convert(fc.keys())));
	}//GEN-LAST:event_langBoxActionPerformed

	public boolean apply() {
		return true;
	}
	
	
    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JComboBox fieldBox;
    protected javax.swing.JLabel jLabel3;
    protected javax.swing.JLabel jLabel4;
    protected javax.swing.JScrollPane jScrollPane2;
    protected javax.swing.JComboBox langBox;
    protected javax.swing.JTable optionTable;
    // End of variables declaration//GEN-END:variables
	
}
