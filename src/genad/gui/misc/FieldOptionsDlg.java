package genad.gui.misc;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

import genad.*;
import genad.model.*;
import genad.config.*;

/**
 *	hacer que se pueda editar la columna del nombre para los campos especiales que se especifiquen en el FieldConfig
 *	@author  kronenthaler
 */
public class FieldOptionsDlg extends javax.swing.JDialog {
	private Field field;
	
	public FieldOptionsDlg(java.awt.Frame parent, boolean modal, Field f) {
		super(parent, modal);
		field=f;
		
		initComponents();
		
		Utils.centerComponent(this);
		setVisible(true);
	}
	
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        cancelBtn = new javax.swing.JButton();
        okBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new OptionTable(field.getFieldConfig().isEditable());
        addBtn = new javax.swing.JButton();
        removeBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Field Options");
        cancelBtn.setIcon(IconsManager.CANCEL);
        cancelBtn.setText("Cancel");
        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtnActionPerformed(evt);
            }
        });

        okBtn.setIcon(IconsManager.OK);
        okBtn.setText("OK");
        okBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okBtnActionPerformed(evt);
            }
        });

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder("Options"));
        fillTable();
        jScrollPane1.setViewportView(table);

        addBtn.setIcon(IconsManager.ADD);
        addBtn.setText("Add");
        addBtn.setEnabled(field.getFieldConfig().isEditable());
        addBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBtnActionPerformed(evt);
            }
        });

        removeBtn.setIcon(IconsManager.DELETE);
        removeBtn.setText("Remove");
        removeBtn.setEnabled(field.getFieldConfig().isEditable());
        removeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeBtnActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(addBtn)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(removeBtn)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 48, Short.MAX_VALUE)
                        .add(okBtn)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(cancelBtn))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 375, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        layout.linkSize(new java.awt.Component[] {addBtn, removeBtn}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        layout.linkSize(new java.awt.Component[] {cancelBtn, okBtn}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 275, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(cancelBtn)
                    .add(okBtn)
                    .add(addBtn)
                    .add(removeBtn))
                .addContainerGap())
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

	private void removeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeBtnActionPerformed
		int index=table.getSelectedRow();
		if(index!=-1){
			DefaultTableModel tb=(DefaultTableModel)table.getModel();
			tb.removeRow(index);
			table.updateUI();
		}
	}//GEN-LAST:event_removeBtnActionPerformed

	private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnActionPerformed
		TableModel tb=table.getModel();
		//get all the fields and insert one more
		Object data[][] =new Object[tb.getRowCount()+1][];
		for(int i=0;i<data.length-1;i++)
			data[i]=new Object[]{ tb.getValueAt(i,0), tb.getValueAt(i,1) };
		
		data[data.length-1]=new Object[]{"",""};
		
		table.setData(data);
	}//GEN-LAST:event_addBtnActionPerformed

	private void okBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okBtnActionPerformed
		TableModel tb=table.getModel();
		
		if(tb.getRowCount()==0){
			Utils.showError("At least one option must be defined.");
			return;
		}
		
		Hashtable<String, Boolean> repeated=new Hashtable<String, Boolean>();
		for(int i=0,n=tb.getRowCount();i<n;i++){
			if("".equals(tb.getValueAt(i,0).toString())){
				Utils.showError("The option's name cannot be empty.");
				return;
			}
			
			if(repeated.get(tb.getValueAt(i,0).toString())!=null){
				Utils.showError("The option's name must be unique.");
				return;
			}
			repeated.put(tb.getValueAt(i,0).toString(), true);
		}
			
		//eliminar las opciones que no se marquen
		field.cleanOptions();
		for(int i=0,n=tb.getRowCount();i<n;i++)
			field.setOption(tb.getValueAt(i,0).toString(), tb.getValueAt(i,1).toString());
		
		cancelBtnActionPerformed(null);
	}//GEN-LAST:event_okBtnActionPerformed

	private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
		dispose();
		setVisible(false);
	}//GEN-LAST:event_cancelBtnActionPerformed
	
    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JButton addBtn;
    protected javax.swing.JButton cancelBtn;
    protected javax.swing.JScrollPane jScrollPane1;
    protected javax.swing.JButton okBtn;
    protected javax.swing.JButton removeBtn;
    protected genad.gui.misc.OptionTable table;
    // End of variables declaration//GEN-END:variables

	private void fillTable(){
		try{
			FieldConfig cfg=field.getFieldConfig();
			
			String[] keys=Utils.convert(field.getOptions());
			Object[][] data=new Object[keys.length][];
			
			for(int i=0;i<data.length;i++){
				//if the default option has a | the field value is the selected one
				String str=cfg.getOption(keys[i]);
				if(str!=null && str.indexOf('|')!=-1)
					data[i]=new Object[]{keys[i], cfg.getOption(keys[i]), field.getOption(keys[i])};	
				else
					data[i]=new Object[]{keys[i], field.getOption(keys[i])};
			}
						
			table.setData(data);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}