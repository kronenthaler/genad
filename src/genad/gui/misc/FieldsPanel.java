package genad.gui.misc;

import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.TableModelListener;
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
public class FieldsPanel extends javax.swing.JPanel {
	public FieldsPanel() {
		initComponents();
		notifyUI();
	}
	
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        scrollPanel = new javax.swing.JScrollPane();
        fieldsTable = new javax.swing.JTable();
        addBtn = new javax.swing.JButton();
        moveUpBtn = new javax.swing.JButton();
        moveDownBtn = new javax.swing.JButton();
        optionsBtn = new javax.swing.JButton();
        removeBtn = new javax.swing.JButton();

        setBorder(javax.swing.BorderFactory.createTitledBorder("Fields"));
        fieldsTable.setModel(new FieldsTableModel());
        scrollPanel.setViewportView(fieldsTable);

        addBtn.setText("Add Field");
        addBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBtnActionPerformed(evt);
            }
        });

        moveUpBtn.setText("Move Up");
        moveUpBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveUpBtnActionPerformed(evt);
            }
        });

        moveDownBtn.setText("Move Down");
        moveDownBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveDownBtnActionPerformed(evt);
            }
        });

        optionsBtn.setText("Options");

        removeBtn.setText("Remove");
        removeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeBtnActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .add(scrollPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                    .add(addBtn, 0, 0, Short.MAX_VALUE)
                    .add(moveUpBtn, 0, 0, Short.MAX_VALUE)
                    .add(optionsBtn, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(moveDownBtn, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(removeBtn, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 108, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        layout.linkSize(new java.awt.Component[] {addBtn, moveDownBtn, moveUpBtn, optionsBtn, removeBtn}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(addBtn, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 25, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(moveUpBtn)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(moveDownBtn)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(optionsBtn)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 141, Short.MAX_VALUE)
                .add(removeBtn))
            .add(scrollPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

	private void moveDownBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moveDownBtnActionPerformed
		((FieldsTableModel)fieldsTable.getModel()).moveDown(fieldsTable.getSelectedRow());
		notifyUI();
	}//GEN-LAST:event_moveDownBtnActionPerformed

	private void moveUpBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moveUpBtnActionPerformed
		((FieldsTableModel)fieldsTable.getModel()).moveUp(fieldsTable.getSelectedRow());
		notifyUI();
	}//GEN-LAST:event_moveUpBtnActionPerformed

	private void removeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeBtnActionPerformed
		((FieldsTableModel)fieldsTable.getModel()).removeRow(fieldsTable.getSelectedRow());
		notifyUI();
	}//GEN-LAST:event_removeBtnActionPerformed

	private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnActionPerformed
		((FieldsTableModel)fieldsTable.getModel()).addRow();
		notifyUI();
	}//GEN-LAST:event_addBtnActionPerformed
	
	private void notifyUI(){
		scrollPanel.revalidate();
		fieldsTable.updateUI();//*/
	}
	
	/*@Deprecated
	public static void main(String []a){
		Frame f=new Frame();
		f.add(new FieldsPanel());
		f.setSize(500,500);
		f.setVisible(true);
	}*/
	
    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JButton addBtn;
    protected javax.swing.JTable fieldsTable;
    protected javax.swing.JButton moveDownBtn;
    protected javax.swing.JButton moveUpBtn;
    protected javax.swing.JButton optionsBtn;
    protected javax.swing.JButton removeBtn;
    protected javax.swing.JScrollPane scrollPanel;
    // End of variables declaration//GEN-END:variables
}

class FieldsTableModel implements TableModel{
	private Vector<Field> fields=new Vector<Field>();
	private String[] titles={"Label","Field Map","Type","Required","Visible","Listable"};
	private Class[] classes={String.class, String.class, JComboBox.class, Boolean.class, Boolean.class, Boolean.class};
	
	public int getRowCount() {
		return fields.size();
	}

	public int getColumnCount() {
		return titles.length;
	}

	public String getColumnName(int columnIndex) {
		return titles[columnIndex];
	}

	public Class getColumnClass(int columnIndex) {
		return classes[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		if(columnIndex==0) return fields.get(rowIndex).label;
		if(columnIndex==1) return fields.get(rowIndex).map;
		if(columnIndex==2) return fields.get(rowIndex).type;
		if(columnIndex==3) return fields.get(rowIndex).required;
		if(columnIndex==4) return fields.get(rowIndex).visible;
		if(columnIndex==5) return fields.get(rowIndex).listable;
		return null;
	}

	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if(columnIndex==0) fields.get(rowIndex).label=aValue.toString();
		if(columnIndex==1) fields.get(rowIndex).map=aValue.toString();
		if(columnIndex==2) fields.get(rowIndex).type=aValue.toString();
		if(columnIndex==3) fields.get(rowIndex).required=(Boolean)aValue;
		if(columnIndex==4) fields.get(rowIndex).visible=(Boolean)aValue;
		if(columnIndex==5) fields.get(rowIndex).listable=(Boolean)aValue;
	}
	
	public void addRow(){
		fields.add(new Field());
	}
	
	public void removeRow(int rowIndex){
		if(rowIndex!=-1)
			fields.remove(rowIndex);
	}
	
	public void moveUp(int rowIndex){
		if(rowIndex!=-1 && rowIndex>0){
			Field aux=fields.get(rowIndex);
			fields.remove(rowIndex);
			fields.insertElementAt(aux,rowIndex-1);
		}
	}
	
	public void moveDown(int rowIndex){
		if(rowIndex!=-1 && rowIndex+1<fields.size()){
			Field aux=fields.get(rowIndex);
			fields.remove(rowIndex);
			fields.insertElementAt(aux,rowIndex+1);
		}
	}
	
	/* useless for now */
	public void addTableModelListener(TableModelListener l){}
	public void removeTableModelListener(TableModelListener l){}
}

class Field{
	public String label, map, type;
	public boolean required,visible,listable;
}