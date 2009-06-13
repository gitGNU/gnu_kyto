/** Copyright (C) 2009 Miguel Ángel García Martínez */

/**
 * This file is part of KYTO.
 *
 *   KYTO is free software; you can redistribute it and/or modify 
 * it under the terms of the GNU General Public License as published by 
 * the Free Software Foundation; either version 3 of the License, or 
 * (at your option) any later version.

 *   KYTO is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the 
 * GNU General Public License for more details.

 *   You should have received a copy of the GNU General Public License 
 * along with Pygrep (maybe in file "COPYING"); if not, write to the Free Software 
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 *
 */

package kytoClient;

import java.util.Vector;

import org.eclipse.swt.custom.TreeEditor;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;

public abstract class TaskColumn extends TreeColumn
{
	private Vector<CellEditedListener> listeners;

	public TaskColumn(Tree parent, int style, int index)
   {
	   super(parent, style, index);
	   setMoveable(true);
		setResizable(true);
		setWidth(100);
		
		listeners = new Vector<CellEditedListener>();
   }
	public void addCellEditedListener(CellEditedListener listener)
	{
		listeners.add(listener);
	}
	public void removeCellEditedListener(CellEditedListener listener)
	{
		listeners.remove(listener);
	}	
	public void removeAllCellEditedListeners()
	{
		listeners.clear();
	}
	protected void notify(CellEditedEvent e)
	{
		for (int i=0; i<this.listeners.size(); ++i)
		{
			this.listeners.get(i).cellEdited(e);
		}
	}
	public void setVisibility(boolean enable)
	{
		if (enable)
		{
			setWidth(100);
			setMoveable(true);
			setResizable(true);
		}
		else
		{
			setWidth(0);
			setMoveable(false);
			setResizable(false);
		}
	}
	protected void checkSubclass()
	{
	}
	
	abstract public String formatValue(String value);
	abstract public String formatValue(long value);
	abstract public Control getNewEditor(TreeEditor editor, String text, CellEditedListener listener);
	abstract public void changeEditorText(String text);
	abstract public void removeEditor();
	abstract public int getIndex();
}
