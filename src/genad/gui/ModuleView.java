package genad.gui;

import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import javax.xml.parsers.*;

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
public class ModuleView extends javax.swing.JPanel implements View{
	private Module module;
	
	public ModuleView(){}
	
	public ModuleView(Module _module) {
		module=_module;
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
			data[i]=new Object[]{keys[i], module.getOption(keys[i]), ""};
			//si la opcion es un valor que tiene multiples valores en el cfg, entonces usar como tercer parametro el valor del modulo, 
			//sino usar directamente el valor del modulo en la segunda celda
		}
		
		optionTable.setData(data);
	}
	
	public void update(Model subject){
		
	}

	public void attachToModel(Model subject) {
		subject.attachView(this);
	}
	
    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JScrollPane jScrollPane1;
    protected genad.gui.misc.OptionTable optionTable;
    // End of variables declaration//GEN-END:variables
}
