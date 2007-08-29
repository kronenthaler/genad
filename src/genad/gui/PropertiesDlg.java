package genad.gui;

import java.io.*;
import java.awt.*;
import java.util.*;
import javax.crypto.NullCipher;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.table.*;

import genad.*;
import genad.model.*;
import genad.config.*;
import genad.engine.*;
import genad.gui.misc.*;

/**
 *
 * @author  kronenthaler
 */
public class PropertiesDlg extends javax.swing.JDialog {
	private Model model;
	private int flag=JOptionPane.CANCEL_OPTION;
	private Hashtable<String,Boolean> mandatories;
	
	public PropertiesDlg(java.awt.Frame parent, boolean modal, Model _model) {
		super(parent, modal);
		model=_model;
		mandatories=new Hashtable<String, Boolean>();
		
		initComponents();
		
		setHandlers();
		
		setTitle("".equals(model.getDestinationPath())?"New Project":"Project Properties");
		Utils.centerComponent(this);
		setVisible(true);
	}
	
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        destPathTxt = new javax.swing.JTextField();
        browseBtn = new javax.swing.JButton();
        ConfigManager cfgMan=ConfigManager.getInstance();
        String[] langs=Utils.convert(cfgMan.getPluginsActive());
        langsCombo = new JComboBox(langs);
        if("".equals(model.getLanguage()))
        langsCombo.setSelectedIndex(0);
        else
        langsCombo.setSelectedItem(model.getLanguage());
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        dDBHostTxt = new javax.swing.JTextField();
        dDBLoginTxt = new javax.swing.JTextField();
        dDBPasswordTxt = new javax.swing.JTextField();
        dDBSchemaTxt = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        modulesTable = new javax.swing.JTable();
        cancelBtn = new javax.swing.JButton();
        okBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Properties");
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("General"));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Path:");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Language:");

        destPathTxt.setText("path");

        browseBtn.setText("...");
        browseBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseBtnActionPerformed(evt);
            }
        });

        langsCombo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                langsComboItemStateChanged(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(jLabel1)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(destPathTxt, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                        .add(7, 7, 7)
                        .add(browseBtn, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 27, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel2)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(langsCombo, 0, 292, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(new java.awt.Component[] {jLabel1, jLabel2}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(destPathTxt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(browseBtn, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 25, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(langsCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel2))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Development Database"));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Host:");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Login:");

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Password:");

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Schema:");

        dDBHostTxt.setText("host");

        dDBLoginTxt.setText("login");

        dDBPasswordTxt.setText("password");

        dDBSchemaTxt.setText("schema");

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel3)
                    .add(jLabel4)
                    .add(jLabel5)
                    .add(jLabel6))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(dDBSchemaTxt, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE)
                    .add(dDBPasswordTxt, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE)
                    .add(dDBLoginTxt, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE)
                    .add(dDBHostTxt, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel2Layout.linkSize(new java.awt.Component[] {jLabel3, jLabel4, jLabel5, jLabel6}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel3)
                    .add(dDBHostTxt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel4)
                    .add(dDBLoginTxt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel5)
                    .add(dDBPasswordTxt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel6)
                    .add(dDBSchemaTxt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder("Modules"));
        modulesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        fillTable();
        jScrollPane1.setViewportView(modulesTable);

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

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 401, Short.MAX_VALUE)
                    .add(jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(layout.createSequentialGroup()
                        .add(okBtn)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(cancelBtn)))
                .addContainerGap())
        );

        layout.linkSize(new java.awt.Component[] {cancelBtn, okBtn}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(cancelBtn)
                    .add(okBtn))
                .addContainerGap())
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

	private void setHandlers(){
		destPathTxt.getDocument().addDocumentListener(new DocumentListener(){
			public void changedUpdate(DocumentEvent e){ 
				destPathTxt.setBackground(!"".equals(destPathTxt.getText())?Color.white:new Color(255,128,128));
			}
			public void insertUpdate(DocumentEvent e){ changedUpdate(e); }
			public void removeUpdate(DocumentEvent e){ changedUpdate(e); }
		});
		
		dDBHostTxt.getDocument().addDocumentListener(new DocumentListener(){
			public void changedUpdate(DocumentEvent e){ 
				dDBHostTxt.setBackground(!"".equals(dDBHostTxt.getText())?Color.white:new Color(255,128,128));
			}
			public void insertUpdate(DocumentEvent e){ changedUpdate(e); }
			public void removeUpdate(DocumentEvent e){ changedUpdate(e); }
		});
				
		dDBLoginTxt.getDocument().addDocumentListener(new DocumentListener(){
			public void changedUpdate(DocumentEvent e){ 
				dDBLoginTxt.setBackground(!"".equals(dDBLoginTxt.getText())?Color.white:new Color(255,128,128));
			}
			public void insertUpdate(DocumentEvent e){ changedUpdate(e); }
			public void removeUpdate(DocumentEvent e){ changedUpdate(e); }
		});
		
		dDBSchemaTxt.getDocument().addDocumentListener(new DocumentListener(){
			public void changedUpdate(DocumentEvent e){ 
				dDBSchemaTxt.setBackground(!"".equals(dDBSchemaTxt.getText())?Color.white:new Color(255,128,128));
			}
			public void insertUpdate(DocumentEvent e){ changedUpdate(e); }
			public void removeUpdate(DocumentEvent e){ changedUpdate(e); }
		});
		
		destPathTxt.setText(model.getDestinationPath());
		dDBHostTxt.setText(model.getDBHost());
        dDBLoginTxt.setText(model.getDBLogin());
        dDBPasswordTxt.setText(model.getDBPassword());
        dDBSchemaTxt.setText(model.getDBSchema());
	}
	
	private void browseBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseBtnActionPerformed
		JFileChooser fc=new JFileChooser();
		fc.setFileSelectionMode(fc.DIRECTORIES_ONLY);
		if(fc.showOpenDialog(this)==JFileChooser.APPROVE_OPTION)
			destPathTxt.setText(fc.getSelectedFile().toString());
	}//GEN-LAST:event_browseBtnActionPerformed

	private void langsComboItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_langsComboItemStateChanged
		fillTable();
	}//GEN-LAST:event_langsComboItemStateChanged

	private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
		flag=JOptionPane.CANCEL_OPTION;
		dispose();
		setVisible(false);
	}//GEN-LAST:event_cancelBtnActionPerformed

	private void okBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okBtnActionPerformed
		if(!model.getLanguage().equals(langsCombo.getSelectedItem().toString()))
			model.clearModules();
		
		model.setLanguage(langsCombo.getSelectedItem().toString());
		model.setDestinationPath(destPathTxt.getText());
		
		model.setDBHost(dDBHostTxt.getText());
		model.setDBLogin(dDBLoginTxt.getText());
		model.setDBPassword(dDBPasswordTxt.getText());
		model.setDBSchema(dDBSchemaTxt.getText());
				
		TableModel tm=modulesTable.getModel();
		for(int i=0;i<tm.getRowCount();i++){
			if((Boolean)tm.getValueAt(i,0))
				model.addModule(tm.getValueAt(i,1).toString());
			else
				model.removeModule(tm.getValueAt(i,1).toString());
		}
		
		flag=JOptionPane.OK_OPTION;
		dispose();
		setVisible(false);
	}//GEN-LAST:event_okBtnActionPerformed
	
    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JButton browseBtn;
    protected javax.swing.JButton cancelBtn;
    protected javax.swing.JTextField dDBHostTxt;
    protected javax.swing.JTextField dDBLoginTxt;
    protected javax.swing.JTextField dDBPasswordTxt;
    protected javax.swing.JTextField dDBSchemaTxt;
    protected javax.swing.JTextField destPathTxt;
    protected javax.swing.JLabel jLabel1;
    protected javax.swing.JLabel jLabel2;
    protected javax.swing.JLabel jLabel3;
    protected javax.swing.JLabel jLabel4;
    protected javax.swing.JLabel jLabel5;
    protected javax.swing.JLabel jLabel6;
    protected javax.swing.JPanel jPanel1;
    protected javax.swing.JPanel jPanel2;
    protected javax.swing.JScrollPane jScrollPane1;
    protected javax.swing.JComboBox langsCombo;
    protected javax.swing.JTable modulesTable;
    protected javax.swing.JButton okBtn;
    // End of variables declaration//GEN-END:variables
	
	public int isApproved(){
		return flag;
	}
	
	private void fillTable(){
		ConfigManager cfgMan=ConfigManager.getInstance();
		PluginConfig pcfg = cfgMan.getPluginConfig(langsCombo.getSelectedItem().toString());
		String[] keys=pcfg.getModulesName();
		Object[][] data=new Object[keys.length][2];
				
		mandatories.clear();
		for(int i=0;i<data.length;i++){
			data[i]=new Object[]{pcfg.getModuleConfig(keys[i]).isMandatory() || model.getModule(keys[i])!=null,keys[i]};
			mandatories.put(keys[i],pcfg.getModuleConfig(keys[i]).isMandatory());
		}
		
		String[] titles=new String[]{"Active","Name"};
		modulesTable.setModel(new ModulesTableModel(data, titles));
		
		TableColumn column = null;
        column = modulesTable.getColumnModel().getColumn(0);
        column.setResizable(false);
        column.setMaxWidth(55);
        column.setMinWidth(50);
        column.setPreferredWidth(50);
	}
	
	private class ModulesTableModel extends DefaultTableModel{
		Class[] types = new Class []{ Boolean.class, String.class };
		boolean[] canEdit = new boolean []{true,false};
		public Class getColumnClass(int columnIndex) { return types[columnIndex]; }
		
		ModulesTableModel(Object[][] data, String[] titles){
			super(data,titles);
		}
		
		public boolean isCellEditable(int rowIndex, int columnIndex){ 
			return canEdit[columnIndex] && !mandatories.get(((Vector)dataVector.get(rowIndex)).get(1));
		}

		public void setValueAt(Object value, int row, int column){
			if(column == 0){
				Boolean b=(Boolean)value;
				
				ConfigManager cfgMan=ConfigManager.getInstance();
				PluginConfig pcfg = cfgMan.getPluginConfig(langsCombo.getSelectedItem().toString());
				
				
				if(b){
					ModuleConfig mcfg = pcfg.getModuleConfig(((Vector)dataVector.get(row)).get(1).toString());
					for(String s : mcfg.getDependencies())
						for(int i=0,n=dataVector.size();i<n;i++)
							if(((Vector)dataVector.get(i)).get(1).equals(s))
								setValueAt(b, i, 0);
				}else{
					//if any selected module has me as dependency alert and abort
					String msg="";
					for(int i=0,n=dataVector.size();i<n;i++){
						ModuleConfig mcfg = pcfg.getModuleConfig(((Vector)dataVector.get(i)).get(1).toString());
						Vector<String> deps=mcfg.getDependencies();
						if((Boolean)((Vector)dataVector.get(i)).get(0) && deps.contains(((Vector)dataVector.get(row)).get(1))){
							msg+="- "+((Vector)dataVector.get(i)).get(1)+"\n";
						}
					}
					
					if(!"".equals(msg)){
						JOptionPane.showMessageDialog(Main.getInstance(),
													  "The following modules depends of this module to work properly:\n" +
													  msg,
													  "Warning",
													  JOptionPane.WARNING_MESSAGE);
						return;
					}
				}
			}
			super.setValueAt(value, row, column);
		}
	}
	
}
