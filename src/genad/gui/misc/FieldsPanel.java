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
import genad.gui.misc.*;

/**
 *	
 *	@author  kronenthaler
 */
public class FieldsPanel extends javax.swing.JPanel {
	private Entity entity;
	private LangConfig cfg;
	
	public FieldsPanel(Entity _entity) {
		entity=_entity;
		initComponents();
		
		TableColumn column = null;
        column = fieldsTable.getColumnModel().getColumn(3);
        column.setResizable(false);
        column.setMaxWidth(70);
        column.setMinWidth(65);
        column.setPreferredWidth(65);
		
		column = fieldsTable.getColumnModel().getColumn(6);
        column.setResizable(false);
        column.setMaxWidth(80);
        column.setMinWidth(75);
        column.setPreferredWidth(75);
		
		column = fieldsTable.getColumnModel().getColumn(4);
        column.setResizable(false);
        column.setMaxWidth(55);
        column.setMinWidth(50);
        column.setPreferredWidth(50);
		
		column = fieldsTable.getColumnModel().getColumn(5);
        column.setResizable(false);
        column.setMaxWidth(55);
        column.setMinWidth(50);
        column.setPreferredWidth(50);
				
		ConfigManager cfgMan=ConfigManager.getInstance();
		cfg=cfgMan.getLangConfig(Model.getInstance().getLanguage());
		
		fieldsTable.setRowHeight(20);
		fieldsTable.getTableHeader().setReorderingAllowed(false);
		fieldsTable.setDefaultEditor(JComboBox.class,
									 new DefaultCellEditor(new JComboBox(cfg.getValidTypes())));
		fieldsTable.setDefaultRenderer(String.class, new FieldsTableRenderer());
		
		notifyUI();
	}
	
	public FieldsPanel(){
		this(new Entity("",null));
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
        fieldsTable.setModel(new FieldsTableModel(entity.getFields(),entity));
        fieldsTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_NEXT_COLUMN);
        scrollPanel.setViewportView(fieldsTable);

        addBtn.setIcon(IconsManager.ADD);
        addBtn.setText("Add Field");
        addBtn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        addBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBtnActionPerformed(evt);
            }
        });

        moveUpBtn.setIcon(IconsManager.MOVEUP);
        moveUpBtn.setText("Move Up");
        moveUpBtn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        moveUpBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveUpBtnActionPerformed(evt);
            }
        });

        moveDownBtn.setIcon(IconsManager.MOVEDOWN);
        moveDownBtn.setText("Move Down");
        moveDownBtn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        moveDownBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveDownBtnActionPerformed(evt);
            }
        });

        optionsBtn.setIcon(IconsManager.OPTIONS);
        optionsBtn.setText("Options");
        optionsBtn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        optionsBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optionsBtnActionPerformed(evt);
            }
        });

        removeBtn.setIcon(IconsManager.DELETE);
        removeBtn.setText("Remove");
        removeBtn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
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
                .add(scrollPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                        .add(addBtn, 0, 0, Short.MAX_VALUE)
                        .add(moveUpBtn, 0, 0, Short.MAX_VALUE)
                        .add(optionsBtn, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(moveDownBtn, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 138, Short.MAX_VALUE)
                .add(removeBtn)
                .addContainerGap())
            .add(scrollPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

	private void optionsBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optionsBtnActionPerformed
		if(fieldsTable.getSelectedRow()==-1) return;
		Field field=entity.getFields().get(fieldsTable.getSelectedRow());
		
		if(field.getFieldConfig().getOptionSize()==0 && !field.getFieldConfig().isEditable()){
			Utils.showInformation("This field hasn't options and cannot be added");
			return;
		}
		
		new FieldOptionsDlg(genad.gui.Main.getInstance(), true, field);
	}//GEN-LAST:event_optionsBtnActionPerformed

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
	
    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JButton addBtn;
    protected javax.swing.JTable fieldsTable;
    protected javax.swing.JButton moveDownBtn;
    protected javax.swing.JButton moveUpBtn;
    protected javax.swing.JButton optionsBtn;
    protected javax.swing.JButton removeBtn;
    protected javax.swing.JScrollPane scrollPanel;
    // End of variables declaration//GEN-END:variables

	protected class FieldsTableModel implements TableModel{
		private Vector<Field> fields=new Vector<Field>();
		private String[] titles={"Label","Field Map","Type","Required","Visible","Listable","Searchable"};
		private Class[] classes={String.class, String.class, JComboBox.class, Boolean.class, Boolean.class, Boolean.class, Boolean.class};
		private Entity entity;
		
		public FieldsTableModel(Vector<Field> f,Entity e){
			super();
			fields=f;
			entity=e;
		}
		
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
			String type=fields.get(rowIndex).getType();
			if(type==null) return true;
			
			FieldConfig fc=cfg.getFieldConfig(type);
			
			//0-label, 1-map, 2-type, 3-required, 4-visible, 5-listable
			if(columnIndex==0 || columnIndex==3 || columnIndex==4)
				return fc.isVisible();
			else if(columnIndex==5)
				return fc.isListable();
			else if(columnIndex==6)
				return fc.isSearchable();
			return true;
		}

		public Object getValueAt(int rowIndex, int columnIndex) {
			if(columnIndex==0) return fields.get(rowIndex).getLabel();
			if(columnIndex==1) return fields.get(rowIndex).getMap();
			if(columnIndex==2) return fields.get(rowIndex).getType();
			if(columnIndex==3) return fields.get(rowIndex).isRequired();
			if(columnIndex==4) return fields.get(rowIndex).isVisible();
			if(columnIndex==5) return fields.get(rowIndex).isListable();
			if(columnIndex==6) return fields.get(rowIndex).isSearchable();
			return null;
		}

		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			if(columnIndex==0) fields.get(rowIndex).setLabel(aValue.toString());
			if(columnIndex==1) fields.get(rowIndex).setMap(aValue.toString());
			if(columnIndex==2) fields.get(rowIndex).setType(aValue.toString());
			if(columnIndex==3) fields.get(rowIndex).setRequired((Boolean)aValue);
			if(columnIndex==4) fields.get(rowIndex).setVisible((Boolean)aValue);
			if(columnIndex==5) fields.get(rowIndex).setListable((Boolean)aValue);
			if(columnIndex==6) fields.get(rowIndex).setSearchable((Boolean)aValue);
		}

		public void addRow(){
			entity.addField();
		}

		public void removeRow(int rowIndex){
			entity.removeField(rowIndex);
		}

		public void moveUp(int rowIndex){
			entity.moveUp(rowIndex);
		}

		public void moveDown(int rowIndex){
			entity.moveDown(rowIndex);
		}

		/* useless for now */
		public void addTableModelListener(TableModelListener l){}
		public void removeTableModelListener(TableModelListener l){}
	}
	
	protected static class FieldsTableRenderer extends DefaultTableCellRenderer{
		public Component getTableCellRendererComponent( JTable table, 
													    Object value, 
														boolean isSelected, 
														boolean hasFocus, 
														int row, 
														int column) {
			Component comp=super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			
			if(column == 1 && (value==null || "".equals(value.toString()))){
				comp.setBackground(new Color(255,128,128));
			}else
				comp.setBackground(Color.white);
			
			return comp;
		}
	}
}

