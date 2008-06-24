package genad.gui.misc;

import java.util.*;

import genad.*;
import genad.gui.*;
import genad.model.*;

/**
 *
 * @author  kronenthaler
 */
public class ProgressDlg extends javax.swing.JDialog implements View{
	private Engine engine;
	private Thread runner;
	
	public ProgressDlg(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
		Utils.centerComponent(this);
	}
	
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        progress = new javax.swing.JProgressBar();
        cancelBtn = new javax.swing.JButton();
        label = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Generating...");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        progress.setStringPainted(true);

        cancelBtn.setText("Cancel");
        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtnActionPerformed(evt);
            }
        });

        label.setText("Calculating directories to copy...");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, label, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, progress, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, cancelBtn))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(label)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(progress, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(cancelBtn)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

	private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
		if(Utils.showConfirm("Are you really sure?")){
			engine.stopGeneration();
			dispose();
			setVisible(false);
		}
	}//GEN-LAST:event_cancelBtnActionPerformed

	private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
		cancelBtnActionPerformed(null);
	}//GEN-LAST:event_formWindowClosed
	
	public void setText(String text){ label.setText(text); }
	public void setProgress(int value){ progress.setValue(value); }
	public int getProgress(){ return progress.getValue(); }
	public void setMaximum(int value){ progress.setMaximum(value); }
	
	public void update(Model subject){
		//drop
	}

	public void attachToModel(Model subject){ 
		if(subject instanceof Engine){ //must be an Engine instance.
			engine=(Engine)subject;
			final Engine e=engine;
			e.attachView(this);
			runner = new Thread(new Runnable(){
				public void run(){
					ArrayList<DelayedFile> files=e.generate();
					if(!e.wasStopped()){
						if(files!=null && files.size()!=0)
							new ReplaceFilesDlg(genad.gui.Main.getInstance(), true, files).setVisible(true);
						Utils.showInformation("Generation complete successfully.");
					}else
						Utils.showWarning("Generation aborted by user.");
					setVisible(false);
				}
			});
			runner.start();
		}
	}
	
    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JButton cancelBtn;
    protected javax.swing.JLabel label;
    protected javax.swing.JProgressBar progress;
    // End of variables declaration//GEN-END:variables
}
