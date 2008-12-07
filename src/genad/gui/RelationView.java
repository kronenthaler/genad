package genad.gui;

import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import genad.*;
import genad.config.*;
import genad.model.*;
import org.netbeans.swing.tabcontrol.TabbedContainer;

/**
 *
 * @author  kronenthaler
 */
public class RelationView extends javax.swing.JPanel implements View{
	private Relation relation;
	private TabbedContainer container;
	
    /** Creates new form RelationView */
    public RelationView(Relation _relation,TabbedContainer _container) {
		relation = _relation;
		container = _container;
		
        initComponents();
		
		setHandlers();
		setVisible(true);
	}
	
	/** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fieldsPanel1 = new genad.gui.misc.FieldsPanel();
        jPanel3 = new javax.swing.JPanel();
        pagerChk = new javax.swing.JCheckBox();
        searchChk = new javax.swing.JCheckBox();
        sortableChk = new javax.swing.JCheckBox();
        justSchemaChk = new javax.swing.JCheckBox();
        justPagesChk = new javax.swing.JCheckBox();
        jPanel4 = new javax.swing.JPanel();
        standardRadio = new javax.swing.JRadioButton();
        standardPlusRadio = new javax.swing.JRadioButton();
        othersRadio = new javax.swing.JRadioButton();
        noneRadio = new javax.swing.JRadioButton();
        propertiesPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        tableNameTxt = new javax.swing.JTextField();
        classNameTxt = new javax.swing.JTextField();
        titleTxt = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        entsOut = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        entsIn = new javax.swing.JList();
        jPanel1 = new javax.swing.JPanel();
        addEntBtn = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        delEntBtn = new javax.swing.JButton();

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Options"));

        pagerChk.setSelected(relation.hasPager());
        pagerChk.setText("Pager");
        pagerChk.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        pagerChk.setMargin(new java.awt.Insets(0, 0, 0, 0));
        pagerChk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pagerChkActionPerformed(evt);
            }
        });

        searchChk.setSelected(relation.hasSearch());
        searchChk.setText("Search");
        searchChk.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        searchChk.setMargin(new java.awt.Insets(0, 0, 0, 0));
        searchChk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchChkActionPerformed(evt);
            }
        });

        sortableChk.setSelected(relation.isSortable());
        sortableChk.setText("Sortable");
        sortableChk.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        sortableChk.setMargin(new java.awt.Insets(0, 0, 0, 0));
        sortableChk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sortableChkActionPerformed(evt);
            }
        });

        justSchemaChk.setSelected(relation.hasJustSchema());
        justSchemaChk.setText("Just Schema");
        justSchemaChk.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        justSchemaChk.setMargin(new java.awt.Insets(0, 0, 0, 0));
        justSchemaChk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                justSchemaChkActionPerformed(evt);
            }
        });

        justPagesChk.setSelected(relation.hasJustPages());
        justPagesChk.setText("Just Pages");
        justPagesChk.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        justPagesChk.setMargin(new java.awt.Insets(0, 0, 0, 0));
        justPagesChk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                justPagesChkActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(pagerChk)
                    .add(searchChk)
                    .add(sortableChk)
                    .add(justSchemaChk)
                    .add(justPagesChk))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .add(pagerChk)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(searchChk)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(sortableChk)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(justSchemaChk)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(justPagesChk)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Permissions"));

        standardRadio.setText("Standard (CRUD)");
        standardRadio.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        standardRadio.setMargin(new java.awt.Insets(0, 0, 0, 0));

        standardPlusRadio.setText("Standard + Others");
        standardPlusRadio.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        standardPlusRadio.setMargin(new java.awt.Insets(0, 0, 0, 0));

        othersRadio.setText("Others");
        othersRadio.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        othersRadio.setMargin(new java.awt.Insets(0, 0, 0, 0));

        noneRadio.setText("None");
        noneRadio.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        noneRadio.setMargin(new java.awt.Insets(0, 0, 0, 0));

        org.jdesktop.layout.GroupLayout jPanel4Layout = new org.jdesktop.layout.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(standardRadio)
                    .add(standardPlusRadio)
                    .add(othersRadio)
                    .add(noneRadio))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel4Layout.createSequentialGroup()
                .add(standardRadio)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(standardPlusRadio)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(othersRadio)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(noneRadio)
                .addContainerGap(48, Short.MAX_VALUE))
        );

        propertiesPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Properties"));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Class Name:");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Table Name:");

        tableNameTxt.setText(relation.getTableName());

        classNameTxt.setText(relation.getName());
        classNameTxt.setVerifyInputWhenFocusTarget(false);

        titleTxt.setText(relation.getTitle());

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Title:");

        org.jdesktop.layout.GroupLayout propertiesPanelLayout = new org.jdesktop.layout.GroupLayout(propertiesPanel);
        propertiesPanel.setLayout(propertiesPanelLayout);
        propertiesPanelLayout.setHorizontalGroup(
            propertiesPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(propertiesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(propertiesPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(propertiesPanelLayout.createSequentialGroup()
                        .add(jLabel4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 77, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(titleTxt, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE))
                    .add(propertiesPanelLayout.createSequentialGroup()
                        .add(propertiesPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(propertiesPanelLayout.createSequentialGroup()
                                .add(1, 1, 1)
                                .add(jLabel1))
                            .add(jLabel2))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(propertiesPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(tableNameTxt, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                            .add(classNameTxt, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE))))
                .addContainerGap())
        );

        propertiesPanelLayout.linkSize(new java.awt.Component[] {jLabel1, jLabel2, jLabel4}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        propertiesPanelLayout.setVerticalGroup(
            propertiesPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(propertiesPanelLayout.createSequentialGroup()
                .add(propertiesPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel4)
                    .add(titleTxt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(propertiesPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(classNameTxt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(propertiesPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel2)
                    .add(tableNameTxt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(54, Short.MAX_VALUE))
        );

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder("Entities Available"));

        entsOut.setModel(new DefaultListModel());
        jScrollPane1.setViewportView(entsOut);

        jScrollPane2.setBorder(javax.swing.BorderFactory.createTitledBorder("Entities Related"));

        entsIn.setModel(new DefaultListModel());
        jScrollPane2.setViewportView(entsIn);

        addEntBtn.setIcon(IconsManager.ADD);
        addEntBtn.setText("Add");
        addEntBtn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        addEntBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addEntBtnActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(addEntBtn, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .add(addEntBtn))
        );

        delEntBtn.setIcon(IconsManager.DELETE);
        delEntBtn.setText("Remove");
        delEntBtn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        delEntBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delEntBtnActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(delEntBtn, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .add(delEntBtn)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, fieldsPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 594, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(propertiesPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        layout.linkSize(new java.awt.Component[] {jPanel1, jPanel2}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(jPanel4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(propertiesPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                    .add(layout.createSequentialGroup()
                        .add(8, 8, 8)
                        .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(fieldsPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

	private void pagerChkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pagerChkActionPerformed
		relation.setPager(pagerChk.isSelected());
}//GEN-LAST:event_pagerChkActionPerformed

	private void searchChkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchChkActionPerformed
		relation.setSearch(searchChk.isSelected());
}//GEN-LAST:event_searchChkActionPerformed

	private void sortableChkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sortableChkActionPerformed
		relation.setSortable(sortableChk.isSelected());
}//GEN-LAST:event_sortableChkActionPerformed

	private void justSchemaChkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_justSchemaChkActionPerformed
		relation.setJustSchema(justSchemaChk.isSelected());
}//GEN-LAST:event_justSchemaChkActionPerformed

	private void justPagesChkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_justPagesChkActionPerformed
		relation.setJustPages(justPagesChk.isSelected());
}//GEN-LAST:event_justPagesChkActionPerformed

	private void addEntBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addEntBtnActionPerformed
		DefaultListModel mIn = (DefaultListModel)entsIn.getModel();
		DefaultListModel mOut = (DefaultListModel)entsOut.getModel();

		Object[] valsOut = entsOut.getSelectedValues();
		for(Object o : valsOut){
			mIn.addElement(o);
			relation.addEntity(o.toString());
			mOut.removeElement(o);
		}
}//GEN-LAST:event_addEntBtnActionPerformed

	private void delEntBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delEntBtnActionPerformed
		DefaultListModel mIn = (DefaultListModel)entsIn.getModel();
		DefaultListModel mOut = (DefaultListModel)entsOut.getModel();

		Object[] valsIn = entsIn.getSelectedValues();
		for(Object o : valsIn){
			mOut.addElement(o);
			relation.removeEntity(o.toString());
			mIn.removeElement(o);
		}
	}//GEN-LAST:event_delEntBtnActionPerformed

	private void setHandlers(){
		classNameTxt.getDocument().addDocumentListener(new DocumentListener(){
			public void changedUpdate(DocumentEvent e){
				classNameTxt.setBackground(relation.setName(classNameTxt.getText())?Color.white:new Color(255,128,128));
				//classNameTxt.setText(classNameTxt.getText().trim());
			}
			public void insertUpdate(DocumentEvent e){ changedUpdate(e); }
			public void removeUpdate(DocumentEvent e){ changedUpdate(e); }
		});

		titleTxt.getDocument().addDocumentListener(new DocumentListener(){
			public void changedUpdate(DocumentEvent e){
				titleTxt.setBackground(relation.setTitle(titleTxt.getText())?Color.white:new Color(255,128,128));
				//titleTxt.setText(titleTxt.getText().trim());
			}
			public void insertUpdate(DocumentEvent e){ changedUpdate(e); }
			public void removeUpdate(DocumentEvent e){ changedUpdate(e); }
		});

		tableNameTxt.getDocument().addDocumentListener(new DocumentListener(){
			public void changedUpdate(DocumentEvent e){
				tableNameTxt.setBackground(relation.setTableName(tableNameTxt.getText())?Color.white:new Color(255,128,128));
				//tableNameTxt.setText(tableNameTxt.getText().trim());
			}
			public void insertUpdate(DocumentEvent e){ changedUpdate(e); }
			public void removeUpdate(DocumentEvent e){ changedUpdate(e); }
		});

		
		ActionListener al=new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				if(standardRadio.isSelected())
					relation.setPermissions(Entity.STANDARD);
				else if(standardPlusRadio.isSelected())
					relation.setPermissions(Entity.STANDARD_PLUS);
				else if(othersRadio.isSelected())
					relation.setPermissions(Entity.OTHERS);
				else
					relation.setPermissions(Entity.NONE);
			}
		};

		standardRadio.addActionListener(al);
		standardPlusRadio.addActionListener(al);
		othersRadio.addActionListener(al);
		noneRadio.addActionListener(al);

		standardRadio.setSelected(relation.getPermissions().equals(Entity.STANDARD));
		standardPlusRadio.setSelected(relation.getPermissions().equals(Entity.STANDARD_PLUS));
		othersRadio.setSelected(relation.getPermissions().equals(Entity.OTHERS));
		noneRadio.setSelected(relation.getPermissions().equals(Entity.NONE));
	}
	
	public void update(Model subject){
		System.out.println("updating");
		//refresh fields
		entsIn.removeAll();
		entsOut.removeAll();

		DefaultListModel m = (DefaultListModel)entsIn.getModel();
		m.removeAllElements();
		Vector<Entity> inEnts = relation.getEntities();
		for(Entity ent : inEnts)
			m.addElement(ent.getName());

		m = (DefaultListModel) entsOut.getModel();
		m.removeAllElements();
		Vector<Entity> allEnts = subject.getAllEntities();
		for(Entity ent : allEnts)
			if(!inEnts.contains(ent))
				m.addElement(ent.getName());
	}

	public void attachToModel(Model subject){
		subject.attachView(this);
	}
	
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addEntBtn;
    private javax.swing.JTextField classNameTxt;
    private javax.swing.JButton delEntBtn;
    private javax.swing.JList entsIn;
    private javax.swing.JList entsOut;
    private genad.gui.misc.FieldsPanel fieldsPanel1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JCheckBox justPagesChk;
    private javax.swing.JCheckBox justSchemaChk;
    private javax.swing.JRadioButton noneRadio;
    private javax.swing.JRadioButton othersRadio;
    private javax.swing.JCheckBox pagerChk;
    private javax.swing.JPanel propertiesPanel;
    private javax.swing.JCheckBox searchChk;
    private javax.swing.JCheckBox sortableChk;
    private javax.swing.JRadioButton standardPlusRadio;
    private javax.swing.JRadioButton standardRadio;
    private javax.swing.JTextField tableNameTxt;
    private javax.swing.JTextField titleTxt;
    // End of variables declaration//GEN-END:variables
}
