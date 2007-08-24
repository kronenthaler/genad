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
		
		jTabbedPane1.setIconAt(0,IconsManager.LANGS);
		//jTabbedPane1.setIconAt(1,IconsManager.FIELDS);
		//jTabbedPane1.setIconAt(2,IconsManager.MODS);
		
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        cancelBtn.setIcon(IconsManager.CANCEL);
        cancelBtn.setText("Cancel");
        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtnActionPerformed(evt);
            }
        });

        applyBtn.setIcon(IconsManager.APPLY);
        applyBtn.setText("Apply");
        applyBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                applyBtnActionPerformed(evt);
            }
        });

        okBtn.setIcon(IconsManager.OK);
        okBtn.setText("OK");
        okBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okBtnActionPerformed(evt);
            }
        });

        jTabbedPane1.addTab("Languages", new javax.swing.ImageIcon(getClass().getResource("/images/icons/plugins.png")), langsPanel1);

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

	private void okBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okBtnActionPerformed
		applyBtnActionPerformed(evt);
		dispose();
		setVisible(false);
	}//GEN-LAST:event_okBtnActionPerformed

	private void applyBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_applyBtnActionPerformed
		langsPanel1.apply();
		//fieldsConfigPanel1.apply();
		//modulesConfigPanel1.apply();
	}//GEN-LAST:event_applyBtnActionPerformed

	private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
		ConfigManager.getInstance().refreshConfiguration(); //doing rollback from any unsaved data
		dispose();
		setVisible(false);
	}//GEN-LAST:event_cancelBtnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JButton applyBtn;
    protected javax.swing.JButton cancelBtn;
    protected javax.swing.JTabbedPane jTabbedPane1;
    protected genad.gui.misc.LangsPanel langsPanel1;
    protected javax.swing.JButton okBtn;
    // End of variables declaration//GEN-END:variables
	
}
