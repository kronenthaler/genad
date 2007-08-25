package genad.gui.misc;

import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
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
public class ConfigureDlg extends javax.swing.JDialog {
	
	public ConfigureDlg(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
		Utils.centerComponent(this);
		setVisible(true);
	}
	
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jTabbedPane1 = new javax.swing.JTabbedPane();
        langsPanel = new LangsPanel();
        fieldsConfigPanel = new FieldsConfigPanel();
        modulesConfigPanel = new ModulesConfigPanel();
        okBtn = new javax.swing.JButton();
        applyBtn = new javax.swing.JButton();
        cancelBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.layout.GroupLayout langsPanelLayout = new org.jdesktop.layout.GroupLayout(langsPanel);
        langsPanel.setLayout(langsPanelLayout);
        langsPanelLayout.setHorizontalGroup(
            langsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 419, Short.MAX_VALUE)
        );
        langsPanelLayout.setVerticalGroup(
            langsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 230, Short.MAX_VALUE)
        );
        jTabbedPane1.addTab("Languages", langsPanel);

        org.jdesktop.layout.GroupLayout fieldsConfigPanelLayout = new org.jdesktop.layout.GroupLayout(fieldsConfigPanel);
        fieldsConfigPanel.setLayout(fieldsConfigPanelLayout);
        fieldsConfigPanelLayout.setHorizontalGroup(
            fieldsConfigPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 419, Short.MAX_VALUE)
        );
        fieldsConfigPanelLayout.setVerticalGroup(
            fieldsConfigPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 230, Short.MAX_VALUE)
        );
        jTabbedPane1.addTab("Fields", fieldsConfigPanel);

        org.jdesktop.layout.GroupLayout modulesConfigPanelLayout = new org.jdesktop.layout.GroupLayout(modulesConfigPanel);
        modulesConfigPanel.setLayout(modulesConfigPanelLayout);
        modulesConfigPanelLayout.setHorizontalGroup(
            modulesConfigPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 419, Short.MAX_VALUE)
        );
        modulesConfigPanelLayout.setVerticalGroup(
            modulesConfigPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 230, Short.MAX_VALUE)
        );
        jTabbedPane1.addTab("Modules", modulesConfigPanel);

        okBtn.setIcon(IconsManager.OK);
        okBtn.setText("OK");
        okBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okBtnActionPerformed(evt);
            }
        });

        applyBtn.setIcon(IconsManager.APPLY);
        applyBtn.setText("Apply");
        applyBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                applyBtnActionPerformed(evt);
            }
        });

        cancelBtn.setIcon(IconsManager.CANCEL);
        cancelBtn.setText("Cancel");
        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtnActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap(175, Short.MAX_VALUE)
                .add(okBtn)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(applyBtn)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(cancelBtn)
                .addContainerGap())
            .add(jTabbedPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
        );

        layout.linkSize(new java.awt.Component[] {applyBtn, cancelBtn, okBtn}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .add(jTabbedPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 257, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(cancelBtn)
                    .add(applyBtn)
                    .add(okBtn))
                .addContainerGap())
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

	private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
		ConfigManager.getInstance().refreshConfiguration(); //doing rollback from any unsaved data
		dispose();
		setVisible(false);
	}//GEN-LAST:event_cancelBtnActionPerformed

	private void applyBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_applyBtnActionPerformed
		((Applicable)langsPanel).apply();
		((Applicable)fieldsConfigPanel).apply();
		((Applicable)modulesConfigPanel).apply();
	}//GEN-LAST:event_applyBtnActionPerformed

	private void okBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okBtnActionPerformed
		applyBtnActionPerformed(evt);
		dispose();
		setVisible(false);
	}//GEN-LAST:event_okBtnActionPerformed
	
    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JButton applyBtn;
    protected javax.swing.JButton cancelBtn;
    protected javax.swing.JPanel fieldsConfigPanel;
    protected javax.swing.JTabbedPane jTabbedPane1;
    protected javax.swing.JPanel langsPanel;
    protected javax.swing.JPanel modulesConfigPanel;
    protected javax.swing.JButton okBtn;
    // End of variables declaration//GEN-END:variables
}
