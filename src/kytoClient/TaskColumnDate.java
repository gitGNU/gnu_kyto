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

import java.text.DateFormat;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TreeEditor;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;

public class TaskColumnDate extends TaskColumn
{
	private Button button;
	private int index = -1;
	
	private void commonInit (int index)
	{
		button = null;
		this.setIndex(index);
	}
		
	public TaskColumnDate(Tree parent, int index)
	{
		super(parent, SWT.CENTER, index);
		commonInit(index);
	}
	
	private void eventEditorhandle(Event event)
	{
		
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		CalendarWindow dialog = new CalendarWindow(shell, SWT.NONE);
		dialog.open();
		
		CellEditedEvent e = new CellEditedEvent(this, CellEditedEvent.KINDS.activated, dialog.getDate());
		button.setText(formatValue(dialog.getDate()));
		this.notify(e);
	}
	
	public Control getNewEditor(TreeEditor editor, String text, CellEditedListener listener)
	{
		if (button != null)
			this.removeEditor();
		button = new Button(this.getParent(), SWT.PUSH);
		button.setVisible(false);
		button.addListener(SWT.Selection, new Listener() {
			public void handleEvent (Event event) {
				eventEditorhandle(event);
			}
		});
		this.addCellEditedListener(listener);
		button.pack();
		button.setText(text);
		button.setSize(this.getWidth(), button.getSize().y);
		editor.minimumWidth = button.getSize().x;
		button.setText(text);
		return button;		
	}

	public String formatValue(String value)
	{
		DateFormat dateformat = DateFormat.getDateInstance();
		Long longdate;
		try
		{
			longdate = new Long (value);
		}
		catch (Exception e)
		{
			return "";
		}
		if (longdate == 0)
			return "";
		Date date = new Date( longdate );
		return dateformat.format(date);
	}
	public String formatValue(long value)
	{
		if (value == 0)
			return "";
		DateFormat dateformat = DateFormat.getDateInstance();
		Date date = new Date(value);
		return dateformat.format(date);
	}
	public void changeEditorText(String text)
	{
		button.setText(text);
		return;
	}
	public void removeEditor()
	{
		if (button == null)
			return;
		button.dispose();
		button = null;
		this.removeAllCellEditedListeners();
	}

	private void setIndex(int index)
   {
	   this.index = index;
   }

	public int getIndex()
   {
	   return index;
   }

}
