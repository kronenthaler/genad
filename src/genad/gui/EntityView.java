package genad.gui;

import java.io.*;
import java.awt.Color;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.table.*;
import org.netbeans.swing.tabcontrol.*;

import genad.*;
import genad.gui.*;
import genad.model.*;
import genad.config.*;
import genad.gui.misc.*;

/**
 *
 * @author  kronenthaler
 */
public class EntityView extends javax.swing.JPanel implements View{
	private Entity entity;
	private TabbedContainer container;
	
	public EntityView(Entity _entity,TabbedContainer _container) {
		entity=_entity;
		container=_container;
		
		initComponents();
		
		setHandlers();
		setVisible(true);
	}
	
	private void setHandlers(){
		classNameTxt.getDocument().addDocumentListener(new DocumentListener(){
			public void changedUpdate(DocumentEvent e){ 
				classNameTxt.setBackground(entity.setName(classNameTxt.getText())?Color.white:new Color(255,128,128));
				//classNameTxt.setText(classNameTxt.getText().trim());
			}
			public void insertUpdate(DocumentEvent e){ changedUpdate(e); }
			public void removeUpdate(DocumentEvent e){ changedUpdate(e); }
		});
		
		titleTxt.getDocument().addDocumentListener(new DocumentListener(){
			public void changedUpdate(DocumentEvent e){ 
				titleTxt.setBackground(entity.setTitle(titleTxt.getText())?Color.white:new Color(255,128,128));
				//titleTxt.setText(titleTxt.getText().trim());
			}
			public void insertUpdate(DocumentEvent e){ changedUpdate(e); }
			public void removeUpdate(DocumentEvent e){ changedUpdate(e); }
		});
		
		tableNameTxt.getDocument().addDocumentListener(new DocumentListener(){
			public void changedUpdate(DocumentEvent e){ 
				tableNameTxt.setBackground(entity.setTableName(tableNameTxt.getText())?Color.white:new Color(255,128,128));
				//tableNameTxt.setText(tableNameTxt.getText().trim());
			}
			public void insertUpdate(DocumentEvent e){ changedUpdate(e); }
			public void removeUpdate(DocumentEvent e){ changedUpdate(e); }
		});
		
		primaryKeyTxt.getDocument().addDocumentListener(new DocumentListener(){
			public void changedUpdate(DocumentEvent e){
				primaryKeyTxt.setBackground(entity.setPrimaryKey(primaryKeyTxt.getText())?Color.white:new Color(255,128,128));
				//primaryKeyTxt.setText(primaryKeyTxt.getText().trim());
			}
			public void insertUpdate(DocumentEvent e){ changedUpdate(e); }
			public void removeUpdate(DocumentEvent e){ changedUpdate(e); }
		});
	}
		
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        buttonGroup1 = new javax.swing.ButtonGroup();
        propertiesPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        primaryKeyTxt = new javax.swing.JTextField();
        tableNameTxt = new javax.swing.JTextField();
        classNameTxt = new javax.swing.JTextField();
        titleTxt = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        pagerChk = new javax.swing.JCheckBox();
        searchChk = new javax.swing.JCheckBox();
        sortableChk = new javax.swing.JCheckBox();
        justSchemaChk = new javax.swing.JCheckBox();
        justPagesChk = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        fieldsPanel = new FieldsPanel(entity);

        propertiesPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Properties"));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Class Name:");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Table Name:");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Primary Key:");

        primaryKeyTxt.setText(entity.getPrimaryKey());

        tableNameTxt.setText(entity.getTableName());

        classNameTxt.setText(entity.getName());
        classNameTxt.setVerifyInputWhenFocusTarget(false);

        titleTxt.setText(entity.getTitle());

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Section Name:");

        org.jdesktop.layout.GroupLayout propertiesPanelLayout = new org.jdesktop.layout.GroupLayout(propertiesPanel);
        propertiesPanel.setLayout(propertiesPanelLayout);
        propertiesPanelLayout.setHorizontalGroup(
            propertiesPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(propertiesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(propertiesPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(propertiesPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(jLabel4)
                        .add(propertiesPanelLayout.createSequentialGroup()
                            .add(1, 1, 1)
                            .add(jLabel1))
                        .add(jLabel2))
                    .add(jLabel3))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(propertiesPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(titleTxt, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                    .add(tableNameTxt, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                    .add(primaryKeyTxt, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                    .add(classNameTxt, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE))
                .addContainerGap())
        );

        propertiesPanelLayout.linkSize(new java.awt.Component[] {jLabel1, jLabel2, jLabel3, jLabel4}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        propertiesPanelLayout.setVerticalGroup(
            propertiesPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, propertiesPanelLayout.createSequentialGroup()
                .add(propertiesPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel4)
                    .add(titleTxt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(propertiesPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(classNameTxt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(propertiesPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel2)
                    .add(tableNameTxt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(propertiesPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel3)
                    .add(primaryKeyTxt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(34, 34, 34))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Options"));
        pagerChk.setSelected(entity.hasPager());
        pagerChk.setText("Pager");
        pagerChk.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        pagerChk.setMargin(new java.awt.Insets(0, 0, 0, 0));
        pagerChk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pagerChkActionPerformed(evt);
            }
        });

        searchChk.setSelected(entity.hasSearch());
        searchChk.setText("Search");
        searchChk.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        searchChk.setMargin(new java.awt.Insets(0, 0, 0, 0));
        searchChk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchChkActionPerformed(evt);
            }
        });

        sortableChk.setSelected(entity.isSortable());
        sortableChk.setText("Sortable");
        sortableChk.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        sortableChk.setMargin(new java.awt.Insets(0, 0, 0, 0));
        sortableChk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sortableChkActionPerformed(evt);
            }
        });

        justSchemaChk.setSelected(entity.hasJustSchema());
        justSchemaChk.setText("Just Schema");
        justSchemaChk.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        justSchemaChk.setMargin(new java.awt.Insets(0, 0, 0, 0));
        justSchemaChk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                justSchemaChkActionPerformed(evt);
            }
        });

        justPagesChk.setSelected(entity.hasJustPages());
        justPagesChk.setText("Just Pages");
        justPagesChk.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        justPagesChk.setMargin(new java.awt.Insets(0, 0, 0, 0));
        justPagesChk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                justPagesChkActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(pagerChk)
                    .add(searchChk)
                    .add(sortableChk)
                    .add(justSchemaChk)
                    .add(justPagesChk))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(new java.awt.Component[] {justPagesChk, justSchemaChk, pagerChk, searchChk, sortableChk}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(pagerChk)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(searchChk)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(sortableChk)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(justSchemaChk)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(justPagesChk)
                .addContainerGap(7, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Permissions"));
        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setText("Standard (CRUD)");
        jRadioButton1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jRadioButton1.setMargin(new java.awt.Insets(0, 0, 0, 0));

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("Standard + Others");
        jRadioButton2.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jRadioButton2.setMargin(new java.awt.Insets(0, 0, 0, 0));

        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setText("Others");
        jRadioButton3.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jRadioButton3.setMargin(new java.awt.Insets(0, 0, 0, 0));

        buttonGroup1.add(jRadioButton4);
        jRadioButton4.setText("None");
        jRadioButton4.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jRadioButton4.setMargin(new java.awt.Insets(0, 0, 0, 0));

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jRadioButton1)
                    .add(jRadioButton2)
                    .add(jRadioButton3)
                    .add(jRadioButton4))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(new java.awt.Component[] {jRadioButton1, jRadioButton2, jRadioButton3, jRadioButton4}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .add(jRadioButton1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jRadioButton2)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jRadioButton3)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jRadioButton4)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(propertiesPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(12, 12, 12))
                    .add(layout.createSequentialGroup()
                        .add(fieldsPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 663, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanel1, 0, 130, Short.MAX_VALUE)
                    .add(propertiesPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(fieldsPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

	private void justPagesChkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_justPagesChkActionPerformed
		entity.setJustPages(justPagesChk.isSelected());
	}//GEN-LAST:event_justPagesChkActionPerformed

	private void justSchemaChkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_justSchemaChkActionPerformed
		entity.setJustSchema(justSchemaChk.isSelected());
	}//GEN-LAST:event_justSchemaChkActionPerformed

	private void sortableChkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sortableChkActionPerformed
		entity.setSortable(sortableChk.isSelected());
	}//GEN-LAST:event_sortableChkActionPerformed

	private void searchChkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchChkActionPerformed
		entity.setSearch(searchChk.isSelected());
	}//GEN-LAST:event_searchChkActionPerformed

	private void pagerChkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pagerChkActionPerformed
		entity.setPager(pagerChk.isSelected());
	}//GEN-LAST:event_pagerChkActionPerformed

	public void update(Model subject){
		List l=container.getModel().getTabs();
		for(int i=0,n=l.size();i<n;i++){
			TabData t=(TabData)l.get(i);
			if(t.getComponent()==this){
				container.setTitleAt(i,entity.getName()+(entity.isChanged()?" *":""));
			}
		}
	}

	public void attachToModel(Model subject){
		subject.attachView(this);
	}
	
	
    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.ButtonGroup buttonGroup1;
    protected javax.swing.JTextField classNameTxt;
    protected genad.gui.misc.FieldsPanel fieldsPanel;
    protected javax.swing.JLabel jLabel1;
    protected javax.swing.JLabel jLabel2;
    protected javax.swing.JLabel jLabel3;
    protected javax.swing.JLabel jLabel4;
    protected javax.swing.JPanel jPanel1;
    protected javax.swing.JPanel jPanel2;
    protected javax.swing.JRadioButton jRadioButton1;
    protected javax.swing.JRadioButton jRadioButton2;
    protected javax.swing.JRadioButton jRadioButton3;
    protected javax.swing.JRadioButton jRadioButton4;
    protected javax.swing.JCheckBox justPagesChk;
    protected javax.swing.JCheckBox justSchemaChk;
    protected javax.swing.JCheckBox pagerChk;
    protected javax.swing.JTextField primaryKeyTxt;
    protected javax.swing.JPanel propertiesPanel;
    protected javax.swing.JCheckBox searchChk;
    protected javax.swing.JCheckBox sortableChk;
    protected javax.swing.JTextField tableNameTxt;
    protected javax.swing.JTextField titleTxt;
    // End of variables declaration//GEN-END:variables
}