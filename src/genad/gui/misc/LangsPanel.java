package genad.gui.misc;

import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.util.zip.*;
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
public class LangsPanel extends javax.swing.JPanel implements Applicable{
	public LangsPanel() {
		initComponents();
		progressBar.setVisible(false);
		setVisible(true);
	}

    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        installBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        pluginTable = new javax.swing.JTable();
        progressBar = new javax.swing.JProgressBar();

        installBtn.setText("Install Language");
        installBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                installBtnActionPerformed(evt);
            }
        });

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder("Installed Languages"));
        fillTable();
        pluginTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jScrollPane1.setViewportView(pluginTable);

        progressBar.setStringPainted(true);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE)
                    .add(layout.createSequentialGroup()
                        .add(progressBar, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(installBtn)))
                .add(12, 12, 12))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(installBtn)
                    .add(progressBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

	private void installBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_installBtnActionPerformed
		//open a zip file an extract the code files into the res/ folder and configuration files in conf/
		final JFileChooser fc=new JFileChooser(System.getProperty("user.home"));
		
		if(fc.showOpenDialog(this)==JFileChooser.APPROVE_OPTION){
			new Thread(new Runnable(){
				public void run(){
					try{
						ZipFile zf=new ZipFile(fc.getSelectedFile());
						progressBar.setVisible(true);
						progressBar.setMaximum(zf.size()+1); //+ registrar el componente en la configuracion
						String idname="";
						Enumeration e=zf.entries();
						for(int i=1;e.hasMoreElements();i++){
							ZipEntry ze=(ZipEntry)e.nextElement();
							if(ze.getName().endsWith("conf.xml"))
								idname=ze.getName().substring("conf/".length(),ze.getName().indexOf("/conf.xml"));
							
							progressBar.setValue(i);
							progressBar.setString("Extracting... "+(int)(progressBar.getPercentComplete()*100)+"%");

							File f=new File(ze.getName());
							if(!f.getParentFile().exists())
								f.getParentFile().mkdirs();

							//actualizar un progress bar de cuantos bytes han salido del archivo.
							DataOutputStream out=new DataOutputStream(new FileOutputStream(f));
							InputStream in=zf.getInputStream(ze);
							byte []buffer =new byte[2048];
							while(in.available()>0){
								int count=in.read(buffer);
								out.write(buffer,0,count);
							}
							out.close();//*/
							try{Thread.sleep(1000);}catch(Exception ex){}
						}
						
						progressBar.setString("Adding... "+(int)(progressBar.getPercentComplete()*100)+"%");
						
						ConfigManager cfgMan=ConfigManager.getInstance();
						cfgMan.installPlugin(idname);
						cfgMan.saveConfiguration();
						
						//actualizar la configuracion
						cfgMan.refreshConfiguration();
						
						//actualizar la tabla de los plugins instalados.
						fillTable();
						progressBar.setVisible(false);
					}catch(RuntimeException e){
						Utils.showError("Fatal Error: "+e.getMessage()+"\n"+e.toString());	
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}).start();
		}
	}//GEN-LAST:event_installBtnActionPerformed

	private void fillTable(){
		Object[][] data=null;
		try{
			ConfigManager cfgMan=ConfigManager.getInstance();
			String[] e=Utils.convert(cfgMan.getPluginsName());
			data=new Object [e.length][3];
			
			for(int i=0;i < e.length;i++){
				String pluginName=e[i];
				data[i]=new Object[]{cfgMan.isPluginActive(pluginName),
									 cfgMan.getPluginConfig(pluginName).getName(), 
									 cfgMan.getPluginConfig(pluginName).getDescription()};
			}
		}catch(Exception e){
			data=new Object[][]{{true,"Name","description"}};
		}

		pluginTable.setModel(new javax.swing.table.DefaultTableModel(data,new String [] { "Active","Name", "Description" }){
			Class[] types = new Class [] { java.lang.Boolean.class, java.lang.String.class,java.lang.String.class};
			boolean[] canEdit = new boolean [] {true, false,false};
			public Class getColumnClass(int columnIndex){ return types [columnIndex]; }
			public boolean isCellEditable(int rowIndex, int columnIndex) {return canEdit [columnIndex];	}
		});
		pluginTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        
		TableColumn column = null;
        column = pluginTable.getColumnModel().getColumn(0);
        column.setResizable(false);
        column.setMaxWidth(55);
        column.setMinWidth(50);
        column.setPreferredWidth(50);
		
		column = pluginTable.getColumnModel().getColumn(1);
        column.setResizable(false);
        column.setMaxWidth(55);
        column.setMinWidth(50);
        column.setPreferredWidth(50);
        pluginTable.getTableHeader().setReorderingAllowed(false);
	}
	
	public boolean apply(){
		try{
			TableModel model=pluginTable.getModel();
			ConfigManager cfgMan=ConfigManager.getInstance();
			for(int i=0,n=model.getRowCount();i<n;i++){
				boolean active=(Boolean)model.getValueAt(i,0);
				String idname=(String)model.getValueAt(i,1);
				cfgMan.activePlugin(idname,active);
			}

			cfgMan.saveConfiguration();
			return true;
		}catch(Exception e){
			return false;
		}
	}	
	
    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JButton installBtn;
    protected javax.swing.JScrollPane jScrollPane1;
    protected javax.swing.JTable pluginTable;
    protected javax.swing.JProgressBar progressBar;
    // End of variables declaration//GEN-END:variables
	
}
