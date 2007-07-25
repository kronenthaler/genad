package genad.gui.misc;

import genad.gui.misc.*;
import java.util.*;
import java.awt.*;

import genad.*;

/**
 *
 * @author  kronenthaler
 */
public class FieldsPanel extends javax.swing.JPanel {
	private Vector<FieldPanel> vector=new Vector<FieldPanel>();
	private int selectedIndex=-1;
	
	public FieldsPanel() {
		initComponents();
		notifyUI();
	}
	
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        scrollPanel = new javax.swing.JScrollPane();
        fieldsPanel = new javax.swing.JPanel();
        addBtn = new javax.swing.JButton();
        moveUpBtn = new javax.swing.JButton();
        moveDownBtn = new javax.swing.JButton();
        optionsBtn = new javax.swing.JButton();
        removeBtn = new javax.swing.JButton();

        setBorder(javax.swing.BorderFactory.createTitledBorder("Fields"));
        fieldsPanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                fieldsPanelMouseMoved(evt);
            }
        });
        fieldsPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fieldsPanelMouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout fieldsPanelLayout = new org.jdesktop.layout.GroupLayout(fieldsPanel);
        fieldsPanel.setLayout(fieldsPanelLayout);
        fieldsPanelLayout.setHorizontalGroup(
            fieldsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 261, Short.MAX_VALUE)
        );
        fieldsPanelLayout.setVerticalGroup(
            fieldsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 275, Short.MAX_VALUE)
        );
        scrollPanel.setViewportView(fieldsPanel);

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
                .add(scrollPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
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
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 135, Short.MAX_VALUE)
                .add(removeBtn))
            .add(scrollPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

	private void moveDownBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moveDownBtnActionPerformed
		//swap with selectedIndex+1
		FieldPanel fp=vector.get(selectedIndex);
		Point p=fp.getLocation();
		fp.setLocation(vector.get(selectedIndex+1).getLocation());
		vector.get(selectedIndex+1).setLocation(p);
		vector.remove(selectedIndex);
		vector.insertElementAt(fp,selectedIndex+1);
		selectedIndex++;
		notifyUI();
	}//GEN-LAST:event_moveDownBtnActionPerformed

	private void moveUpBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moveUpBtnActionPerformed
		FieldPanel fp=vector.get(selectedIndex);
		Point p=fp.getLocation();
		fp.setLocation(vector.get(selectedIndex-1).getLocation());
		vector.get(selectedIndex-1).setLocation(p);
		vector.remove(selectedIndex);
		vector.insertElementAt(fp,selectedIndex-1);
		selectedIndex--;
		notifyUI();
	}//GEN-LAST:event_moveUpBtnActionPerformed

	private void removeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeBtnActionPerformed
		//eliminar el selected index y mover todos los que vienen despues en y
		for(int i=selectedIndex+1;i<vector.size();i++){
			FieldPanel fp=vector.get(i);
			fp.setLocation(fp.getLocation().x,fp.getLocation().y-fp.getHeight());
		}
		
		FieldPanel fp=vector.get(selectedIndex);
		fp.setVisible(false);
		fieldsPanel.remove(fp);
		
		vector.remove(selectedIndex);
		selectedIndex=-1;
		
		if(vector.size()==0) 
			fieldsPanel.setPreferredSize(new Dimension(0,0));
		notifyUI();
	}//GEN-LAST:event_removeBtnActionPerformed

	private void fieldsPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fieldsPanelMouseClicked
		int y=evt.getY();
		if(vector.size()>0){
			int index=y/vector.get(0).getHeight();
			if(index<vector.size()){
				selectedIndex=index;
				for(int i=0;i<vector.size();i++)
					vector.get(i).setBackground(i==selectedIndex?Color.lightGray:Utils.getColor(Utils.PANEL_BACKGROUND));
			}else
				selectedIndex=-1;
		}else
			selectedIndex=-1;
		notifyUI();
	}//GEN-LAST:event_fieldsPanelMouseClicked

	private void fieldsPanelMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fieldsPanelMouseMoved
		int y=evt.getY();
		if(vector.size()>0){
			int index=y/vector.get(0).getHeight();
			if(index<vector.size()){
				for(int i=0;i<vector.size();i++){
					if(i!=selectedIndex){
						vector.get(i).setSize(Math.max((int)getSize().getWidth(),vector.get(i).getWidth()),vector.get(i).getHeight());
						vector.get(i).setBackground(Utils.getColor(i==index?Utils.LIST_BACKGROUND:Utils.PANEL_BACKGROUND));
					}
				}
			}
		}
		notifyUI();
	}//GEN-LAST:event_fieldsPanelMouseMoved

	private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnActionPerformed
		FieldPanel fp=new FieldPanel();
		fp.setSize(fp.getPreferredSize());
		fp.setLocation(0,fp.getHeight()*vector.size());
		
		vector.add(fp);
		fieldsPanel.add(fp);
		fieldsPanel.setPreferredSize(new Dimension(fp.getWidth(),fp.getHeight()*vector.size()));
		notifyUI();
	}//GEN-LAST:event_addBtnActionPerformed
	
	private void notifyUI(){
		removeBtn.setEnabled(vector.size()!=0 && selectedIndex!=-1);
		moveDownBtn.setEnabled(removeBtn.isEnabled() && selectedIndex!=vector.size()-1);
		moveUpBtn.setEnabled(removeBtn.isEnabled() && selectedIndex!=0);
		
		fieldsPanel.repaint();
		scrollPanel.revalidate();
	}
	
    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JButton addBtn;
    protected javax.swing.JPanel fieldsPanel;
    protected javax.swing.JButton moveDownBtn;
    protected javax.swing.JButton moveUpBtn;
    protected javax.swing.JButton optionsBtn;
    protected javax.swing.JButton removeBtn;
    protected javax.swing.JScrollPane scrollPanel;
    // End of variables declaration//GEN-END:variables
	
}
