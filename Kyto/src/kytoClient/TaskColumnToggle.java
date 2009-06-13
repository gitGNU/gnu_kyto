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

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TreeEditor;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;

/**
 * @author miguel
 *
 */
public class TaskColumnToggle extends TaskColumn
{
	private int index;
	private Button button;
	
	/**
	 * @param parent
	 * @param style
	 * @param index
	 */
	public TaskColumnToggle(Tree parent, int index)
	{
		super(parent, SWT.CENTER, index);
		setAlignment(SWT.CENTER);
		this.setIndex(index);
	}

	/* (non-Javadoc)
	 * @see kytoClient.TaskColumn#changeEditorText(java.lang.String)
	 */
	@Override
	public void changeEditorText(String text)
	{
		// do nothing
	}

	/* (non-Javadoc)
	 * @see kytoClient.TaskColumn#formatValue(java.lang.String)
	 */
	@Override
	public String formatValue(String value)
	{
		System.out.println(value);
		return "";
	}

	/* (non-Javadoc)
	 * @see kytoClient.TaskColumn#formatValue(long)
	 */
	@Override
	public String formatValue(long value)
	{
		System.out.println(value);
		return (value==0)?"F":"V";
	}
	
	private void eventEditorhandle(Event event)
	{
		System.out.println("pasa");
		CellEditedEvent e = new CellEditedEvent(this, CellEditedEvent.KINDS.activated, new Long(button.getSelection()?1:0));
		this.notify(e);
	}

	/* (non-Javadoc)
	 * @see kytoClient.TaskColumn#getNewEditor(org.eclipse.swt.custom.TreeEditor, java.lang.String, kytoClient.CellEditedListener)
	 */
	@Override
	public Control getNewEditor(TreeEditor editor, String text,
	      CellEditedListener listener)
	{
		button = new Button(this.getParent(), SWT.CHECK);
		button.setSize(this.getWidth(), button.getSize().y);
		editor.minimumWidth = button.getSize().x;
		button.addListener(SWT.Selection, new Listener() {
			@Override
         public void handleEvent(Event event)
         {
				eventEditorhandle(event);	         
         }
		});
		return button;
	}

	/* (non-Javadoc)
	 * @see kytoClient.TaskColumn#removeEditor()
	 */
	@Override
	public void removeEditor()
	{
		if (this.button == null)
			return;
		this.button.dispose();
		this.button = null;
		this.removeAllCellEditedListeners();
	}

	public void setIndex(int index)
   {
	   this.index = index;
   }

	/* (non-Javadoc)
	 * @see kytoClient.TaskColumn#getIndex()
	 */
	@Override
	public int getIndex()
	{
		return index;
	}
}
