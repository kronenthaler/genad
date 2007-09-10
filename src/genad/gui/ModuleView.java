package genad.gui;

import java.io.*;
//import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import javax.xml.parsers.*;
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
public class ModuleView extends javax.swing.JPanel implements View{
	private Module module;
	private TabbedContainer container;
	
	public ModuleView(){}
	
	public ModuleView(Module _module, TabbedContainer _container) {
		module=_module;
		container=_container;
		initComponents();
		
		fillTable();
	}
	
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jScrollPane1 = new javax.swing.JScrollPane();
        optionTable = new genad.gui.misc.OptionTable();

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder("Options"));
        jScrollPane1.setViewportView(optionTable);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

	private void fillTable(){
		Object[][] data=null;
		ModuleConfig cfg=module.getModuleConfig();
		
		String[] keys=Utils.convert(cfg.getOptions());
		data=new Object[keys.length][];
		for(int i=0;i<data.length;i++){
			String str=cfg.getOption(keys[i]);
			if(str.indexOf('|')!=-1)
				data[i]=new Object[]{keys[i], cfg.getOption(keys[i]), module.getOption(keys[i])};	
			else
				data[i]=new Object[]{keys[i], module.getOption(keys[i])};	
		}
		
		optionTable.setData(data);
		optionTable.getModel().addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent evt) {
				module.setOption((String)optionTable.getValueAt(evt.getFirstRow(),0),optionTable.getValueAt(evt.getFirstRow(),evt.getColumn()).toString());
			}
		});
	}
	
	public void update(Model subject){
		List l=container.getModel().getTabs();
		for(int i=0,n=l.size();i<n;i++){
			TabData t=(TabData)l.get(i);
			if(t.getComponent()==this)
				container.setTitleAt(i,module.getName()+(module.isChanged()?" *":""));
		}
	}

	public void attachToModel(Model subject) {
		subject.attachView(this);
	}
	
    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JScrollPane jScrollPane1;
    protected genad.gui.misc.OptionTable optionTable;
    // End of variables declaration//GEN-END:variables
}
