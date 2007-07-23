package genad;

import java.awt.*;
import javax.swing.*;

/**
 *
 *	@author kronenthaler
 */
public class Utils{
	public static final String PANEL_BACKGROUND="Panel.background";
	public static final String TABBED_SELECTED="TabbedPane.selected";
	public static final String TABBED_BACKGROUND="TabbedPane.background";
	public static final String LIST_BACKGROUND="List.background";	
	
	public static Color getColor(String context){
		return javax.swing.UIManager.getDefaults().getColor(context);
	}
}
