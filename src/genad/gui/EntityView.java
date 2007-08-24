package genad.gui;

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
import genad.gui.misc.*;

/**
 *
 * @author  kronenthaler
 */
public class EntityView extends javax.swing.JPanel {
	private Entity entity;
	
	public EntityView(Entity _entity) {
		entity=_entity;
		initComponents();
		System.err.println(System.getProperty("java.version"));
		classNameTxt.getDocument().addDocumentListener(new DocumentListener(){
			public void changedUpdate(DocumentEvent e){ nameChanged(e);}
			public void insertUpdate(DocumentEvent e){ nameChanged(e);}
			public void removeUpdate(DocumentEvent e){ nameChanged(e);}
		});
	}
	
	private void nameChanged(DocumentEvent evt){
		System.err.println(classNameTxt.getText());
	}
	
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jButton2 = new javax.swing.JButton();
        buttonGroup1 = new javax.swing.ButtonGroup();
        propertiesPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        classNameTxt = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
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
        fieldsPanel1 = new genad.gui.misc.FieldsPanel();

        jButton2.setText("jButton2");

        propertiesPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Properties"));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Class Name:");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Table Name:");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Primary Key:");

        classNameTxt.setText(entity.getName());
        classNameTxt.setVerifyInputWhenFocusTarget(false);

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
                    .add(jTextField4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 149, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(classNameTxt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 149, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTextField2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTextField1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        propertiesPanelLayout.linkSize(new java.awt.Component[] {jLabel1, jLabel2, jLabel3, jLabel4}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        propertiesPanelLayout.linkSize(new java.awt.Component[] {classNameTxt, jTextField1, jTextField2}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        propertiesPanelLayout.setVerticalGroup(
            propertiesPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, propertiesPanelLayout.createSequentialGroup()
                .add(propertiesPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel4)
                    .add(jTextField4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(propertiesPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(classNameTxt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(propertiesPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jTextField2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel2))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(propertiesPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jTextField1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel3))
                .add(34, 34, 34))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Options"));
        pagerChk.setText("Pager");
        pagerChk.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        pagerChk.setMargin(new java.awt.Insets(0, 0, 0, 0));

        searchChk.setText("Search");
        searchChk.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        searchChk.setMargin(new java.awt.Insets(0, 0, 0, 0));

        sortableChk.setText("Sortable");
        sortableChk.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        sortableChk.setMargin(new java.awt.Insets(0, 0, 0, 0));

        justSchemaChk.setText("Just Schema");
        justSchemaChk.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        justSchemaChk.setMargin(new java.awt.Insets(0, 0, 0, 0));

        justPagesChk.setText("Just Pages");
        justPagesChk.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        justPagesChk.setMargin(new java.awt.Insets(0, 0, 0, 0));

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
                .addContainerGap(23, Short.MAX_VALUE))
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
                .addContainerGap(47, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, fieldsPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 588, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(propertiesPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(propertiesPanel, 0, 146, Short.MAX_VALUE)
                    .add(jPanel1, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(fieldsPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
                .add(13, 13, 13))
        );
    }// </editor-fold>//GEN-END:initComponents
	
	
    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.ButtonGroup buttonGroup1;
    protected javax.swing.JTextField classNameTxt;
    protected genad.gui.misc.FieldsPanel fieldsPanel1;
    protected javax.swing.JButton jButton2;
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
    protected javax.swing.JTextField jTextField1;
    protected javax.swing.JTextField jTextField2;
    protected javax.swing.JTextField jTextField4;
    protected javax.swing.JCheckBox justPagesChk;
    protected javax.swing.JCheckBox justSchemaChk;
    protected javax.swing.JCheckBox pagerChk;
    protected javax.swing.JPanel propertiesPanel;
    protected javax.swing.JCheckBox searchChk;
    protected javax.swing.JCheckBox sortableChk;
    // End of variables declaration//GEN-END:variables

	public static void main(String a[]){
		Frame f=new Frame();
		JSplitPane sp=new JSplitPane();
		JTabbedPane tp=new JTabbedPane();
		sp.setRightComponent(tp);
		f.add(sp);
		tp.addTab("aglo",new EntityView(new Entity("pepe",null)));
		tp.setTabComponentAt(0,new TabComponent(tp, 0, "algo"));
		
		f.setSize(1000,768);
		f.setVisible(true);
	}
}