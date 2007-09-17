package genad.gui.misc;

import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.table.*;

import genad.*;
import genad.gui.*;
import genad.model.*;
import genad.config.*;

/**
 *
 *	@author kronenthaler
 */
public class OptionTable extends JTable{
	private Object data[][];
	private Object titles[];
	private OptionTableCellEditor editor;
	private boolean editable=false;
	
	public OptionTable(){
		super();
		editor=new OptionTableCellEditor(this);
		setRowHeight(20);
		setTitles(new Object[]{"0","1"});
		setData(new Object[0][0]);
	}
	
	public OptionTable(boolean edit){
		this();
		editable=edit;
	}
	
	/**
	 *	_d[0] label of the option
	 *	_d[1] value or values
	 *	_d[2] selected value if has more than one option
	 *	@param _d Must be a bidimensional array of strings, if a value string contains a | this method will convert to
	 *				JComboBox, otherwise remains as a string editable value.
	 */
	public void setData(Object[][] _d){
		try{
			data=new Object[_d.length][];
			System.arraycopy(_d,0,data,0,_d.length);
			
			editor.clearEditors();
			//parsear la data para poder construir el modelo que se necesita.
			for(int i=0,n=data.length;i<n;i++){
				String text=(String)data[i][1];
				if(text!=null && text.contains("|")){
					JComboBox cb=new JComboBox();
					StringTokenizer strT=new StringTokenizer(text,"|");
					while(strT.hasMoreTokens())
						cb.addItem(strT.nextToken());

					editor.setEditorAt(i,new DefaultCellEditor(cb));
					//aplicar el default option como seleccionado.
					if(data[i].length==3 && data[i][2]!=null)
						cb.setSelectedItem(data[i][2]);
					else 
						cb.setSelectedIndex(0);
					data[i][1]=cb;
				}
			}

			setModel(new DefaultTableModel(data,titles){
				Class[] types = new Class[]{String.class, Component.class};
				boolean[] canEdit = new boolean [] {editable,true};
				public Class getColumnClass(int columnIndex){ return types[columnIndex]; }
				public boolean isCellEditable(int rowIndex, int columnIndex) {return canEdit [columnIndex];	}
			});
			setTableHeader(null);
			getColumn("1").setCellEditor(editor);
			setDefaultRenderer(Object.class, new OptionTableCellRenderer(new DefaultTableCellRenderer()));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void setTitles(Object[] _t){ titles=new String[_t.length]; System.arraycopy(_t,0,titles, 0, _t.length); }
	public void setEditor(OptionTableCellEditor editor){this.editor = editor;}
	
	//public Object[][] getData(){return data;}
	//public Object[] getTitles(){return titles;}
	public OptionTableCellEditor getEditor(){return editor;}

	
	/** Editor for each cell */
	protected static class OptionTableCellEditor implements TableCellEditor, Serializable{
		protected Hashtable<Integer,TableCellEditor> editors;
		protected TableCellEditor editor, defaultEditor;
		protected JTable table;

		/**
		* Constructs a EachRowEditor. create default editor
		*
		* @see TableCellEditor
		* @see DefaultCellEditor
		*/
		public OptionTableCellEditor(JTable table) {
			this.table = table;
			editors = new Hashtable<Integer,TableCellEditor>();
			defaultEditor = new DefaultCellEditor(new JTextField());
		}

		/**
		* @param row
		*            table row
		* @param editor
		*            table cell editor
		*/
		public void setEditorAt(int row, TableCellEditor editor) {
			editors.put(row, editor);
		}

		public Component getTableCellEditorComponent(JTable table, Object value,
														boolean isSelected, int row, int column) {
			return editor.getTableCellEditorComponent(table, value, isSelected, row, column);
		}

		public Object getCellEditorValue() {
			return editor.getCellEditorValue();
		}

		public boolean stopCellEditing() {
			return editor.stopCellEditing();
		}

		public void cancelCellEditing() {
			editor.cancelCellEditing();
		}

		public boolean isCellEditable(EventObject anEvent) {
			if(anEvent instanceof MouseEvent)
				selectEditor((MouseEvent) anEvent);
			return editor.isCellEditable(anEvent);
		}

		public void addCellEditorListener(CellEditorListener l) {
			editor.addCellEditorListener(l);
		}

		public void removeCellEditorListener(CellEditorListener l) {
			editor.removeCellEditorListener(l);
		}

		public boolean shouldSelectCell(EventObject anEvent) {
			if(anEvent instanceof MouseEvent)
				selectEditor((MouseEvent) anEvent);
			return editor.shouldSelectCell(anEvent);
		}

		protected void selectEditor(MouseEvent e) {
			int row;
			if (e == null) row = table.getSelectionModel().getAnchorSelectionIndex();
			else row = table.rowAtPoint(e.getPoint());
			
			editor = (TableCellEditor) editors.get(row);
			if (editor == null)  editor = defaultEditor;
		}

		public void clearEditors() {
			editors.clear();
		}
	}
	
	protected static class OptionTableCellRenderer implements TableCellRenderer{
		TableCellRenderer defaultRenderer;
		public OptionTableCellRenderer(TableCellRenderer dr){
			defaultRenderer=dr;
		}
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			if(value instanceof Component) return (Component)value;
			return defaultRenderer.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,column);
		}
	}
}