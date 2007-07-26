package genad.gui.misc;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import genad.*;

/**
 *
 * @author  kronenthaler
 */
public class TabComponent extends javax.swing.JPanel {
	private JTabbedPane tb;
	private int index;
	private String text;
	
	public TabComponent(JTabbedPane _tb, int _index, String _text) {
		tb=_tb;
		index=_index;
		text=_text;
		initComponents();
	}
	
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        titleLab = new javax.swing.JLabel();
        crossLab = new javax.swing.JLabel();

        setBackground(new java.awt.Color(204, 204, 204));
        titleLab.setBackground(Utils.getColor(Utils.TABBED_SELECTED));
        titleLab.setText(text);

        crossLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/fileclose.png")));
        crossLab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                crossLabMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                crossLabMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                crossLabMouseExited(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(titleLab, javax.swing.GroupLayout.PREFERRED_SIZE, 36, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(crossLab, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(crossLab, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
            .addComponent(titleLab, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

	private void crossLabMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_crossLabMouseExited
		crossLab.setIcon(new ImageIcon(getClass().getResource("/images/icons/fileclose.png")));
	}//GEN-LAST:event_crossLabMouseExited

	private void crossLabMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_crossLabMouseEntered
		crossLab.setIcon(new ImageIcon(getClass().getResource("/images/icons/fileclose_over.png")));
	}//GEN-LAST:event_crossLabMouseEntered

	private void crossLabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_crossLabMouseClicked
		tb.remove(index);
	}//GEN-LAST:event_crossLabMouseClicked
	
	public void setBackground(Color c){
		super.setBackground(c);
		try{
			crossLab.setBackground(c);
		}catch(Exception e){}
	}
	
    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JLabel crossLab;
    protected javax.swing.JLabel titleLab;
    // End of variables declaration//GEN-END:variables
}
