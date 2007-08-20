package genad.gui.misc;

import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
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
public class AboutDlg extends javax.swing.JDialog {
	public AboutDlg(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
		
		Utils.centerComponent(this);
		setVisible(true);
	}
	
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        /*
        panel = new javax.swing.JPanel();
        */
        panel=new AboutPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);
        setResizable(false);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                aboutClosed(evt);
            }
        });

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 276, Short.MAX_VALUE)
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 325, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

	private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
	
	}//GEN-LAST:event_formMouseClicked

	private void aboutClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_aboutClosed
		((AboutPanel)panel).kill();	
	}//GEN-LAST:event_aboutClosed
	
    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JPanel panel;
    // End of variables declaration//GEN-END:variables
	
	private class AboutPanel extends JPanel{
		private int y;
		private boolean loop=false,kill=false,intro=true;
		private String msg="<b>Project Leader</b>\n" +
							"Ignacio Calderon\n\n"+
							"In a galaxy far, far away...\n" +
							"esto no va a bajarse de linea\n" +
							"mas crap de relleno para ver que \n" +
							"tan abajo queda el texto\n" +
							"difuminado";
		private String intromsg="Distributed under\n" +
									"GNU General Public License\n" +
									"(GPL) terms\n \n" +
									"<b>Technologies involved </b>\n"+
									"Java 6\n"+					
									"XML & XSL\n\n"+					
									"Dojo Toolkit\n"+
									"Ajax";
		private Image background;
		private Font font=new Font("Courier New",Font.PLAIN,14);
		private RenderingHints rh = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		AboutPanel(){
			background=new ImageIcon(getClass().getResource("/images/about.png")).getImage();
			y=background.getHeight(this);
			new Thread(new Runnable(){
				public void run(){
					try{Thread.sleep(5000);}catch(Exception e){}
					intro=false;
					while(!kill){
						try{Thread.sleep(20);}catch(Exception e){}
						y--;
						
						repaint();
						
						if(loop){
							intro=true;
							repaint();
							try{Thread.sleep(5000);}catch(Exception e){}
							intro=false;
							
							y=getHeight();
							loop=false;
						}
					}
				}
			}).start();
		}
		
		public void paint(Graphics g2){
			Graphics2D g=(Graphics2D)g2;
			g.setRenderingHints(rh);
			
			g.setColor(Color.white);
			g.fillRect(0,0,getWidth(),getHeight());

			g.setColor(Color.black);
			g.setFont(font);
			
			if(!intro)
				drawMultlineText(g,msg,y);
			else
				drawMultlineText(g,intromsg,100);
			//System.out.println(msg+" @ "+x);
			g.drawImage(background, 0,0, this);
		}

		public void update(Graphics g){
			paint(g);
		}
		
		private void drawMultlineText(Graphics g, String msg, int baseY){
			StringTokenizer strT=new StringTokenizer(msg, "\n");
			int cont=0;
			while(strT.hasMoreTokens()){
				String str=strT.nextToken();
				if(str.startsWith("<b>") && str.endsWith("</b>")){
					font=font.deriveFont(Font.BOLD);
					str=str.replaceAll("<b>","");
					str=str.replaceAll("</b>","");
					g.setFont(font);
				}
				g.drawString(str,(getWidth()-(str.length()*8))>>1, baseY+(20*cont++));
				font=font.deriveFont(Font.PLAIN);
				g.setFont(font);
			}
			
			if(baseY+(20*cont)<0) loop=true;
		}
		
		protected void kill(){
			kill=true;
		}
	}
}
