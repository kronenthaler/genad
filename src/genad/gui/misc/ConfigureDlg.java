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
public class ConfigureDlg extends javax.swing.JDialog {
	//@TODO: mover los paneles a clases separadas y agregar esos paneles a la paleta de componentes.
	public ConfigureDlg(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
		//jTabbedPane1.setIconAt(0,new ImageIcon(getClass().getResource("/images/icons/home.png")));
		jTabbedPane1.setIconAt(0,new ImageIcon(getClass().getResource("/images/icons/plugins.png")));
		jTabbedPane1.setIconAt(1,new ImageIcon(getClass().getResource("/images/icons/fields.png")));
		jTabbedPane1.setIconAt(2,new ImageIcon(getClass().getResource("/images/icons/module.png")));
		
		Utils.centerComponent(this);
		setVisible(true);
	}
	
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        cancelBtn = new javax.swing.JButton();
        applyBtn = new javax.swing.JButton();
        okBtn = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        langsPanel1 = new genad.gui.misc.LangsPanel();
        fieldsConfigPanel1 = new genad.gui.misc.FieldsConfigPanel();
        modulesConfigPanel1 = new genad.gui.misc.ModulesConfigPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        cancelBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/cancel.png")));
        cancelBtn.setText("Cancel");
        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtnActionPerformed(evt);
            }
        });

        applyBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/apply.png")));
        applyBtn.setText("Apply");
        applyBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                applyBtnActionPerformed(evt);
            }
        });

        okBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/ok.png")));
        okBtn.setText("OK");

        jTabbedPane1.addTab("Languages", new javax.swing.ImageIcon(getClass().getResource("/images/icons/plugins.png")), langsPanel1);

        jTabbedPane1.addTab("Fields", new javax.swing.ImageIcon(getClass().getResource("/images/icons/fields.png")), fieldsConfigPanel1);

        jTabbedPane1.addTab("Modules", new javax.swing.ImageIcon(getClass().getResource("/images/icons/module.png")), modulesConfigPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(173, Short.MAX_VALUE)
                .addComponent(okBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(applyBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cancelBtn)
                .addContainerGap())
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {applyBtn, cancelBtn, okBtn});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelBtn)
                    .addComponent(applyBtn)
                    .addComponent(okBtn))
                .addContainerGap())
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

	private void applyBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_applyBtnActionPerformed
		
	}//GEN-LAST:event_applyBtnActionPerformed

	private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
		dispose();
		setVisible(false);
	}//GEN-LAST:event_cancelBtnActionPerformed
	
	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new ConfigureDlg(new javax.swing.JFrame(), true).setVisible(true);
			}
		});
	}
	
    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JButton applyBtn;
    protected javax.swing.JButton cancelBtn;
    protected genad.gui.misc.FieldsConfigPanel fieldsConfigPanel1;
    protected javax.swing.JTabbedPane jTabbedPane1;
    protected genad.gui.misc.LangsPanel langsPanel1;
    protected genad.gui.misc.ModulesConfigPanel modulesConfigPanel1;
    protected javax.swing.JButton okBtn;
    // End of variables declaration//GEN-END:variables
	
}
