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
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;

public class TaskColumnSpin extends TaskColumn
{
	private int index = -1;
	private Spinner spin;

	public TaskColumnSpin(Tree parent, int index)
	{
		super(parent, SWT.LEFT, index);
		setAlignment(SWT.LEFT);
		this.setIndex(index);
	}

	private void setIndex(int index)
   {
	   this.index = index;
   }

	public int getIndex()
   {
	   return index;
   }

	@Override
   public void changeEditorText(String text)
   {	
		// Do nothing
   }

	@Override
   public String formatValue(String value)
   {
	   return value;
   }

	@Override
   public String formatValue(long value)
   {
	   return (new Long(value)).toString();
   }
	
	private void eventEditorhandle(Event event)
	{
		System.out.println("Modified");
		CellEditedEvent e = new CellEditedEvent(this, CellEditedEvent.KINDS.activated, new Long(spin.getSelection()));
		this.notify(e);
	}
	
	@Override
   public Control getNewEditor(TreeEditor editor, String text,
         CellEditedListener listener)
   {
		if (spin != null)
			this.removeEditor();
		spin = new Spinner(this.getParent(), SWT.NONE);
		spin.setSize(this.getWidth(), spin.getSize().y);
		spin.addListener(SWT.Modify, new Listener() {
			@Override
         public void handleEvent(Event event)
         {
				eventEditorhandle(event);	         
         }
		});
		this.addCellEditedListener(listener);
		editor.minimumWidth = spin.getSize().x;
	   return spin;
   }

	@Override
   public void removeEditor()
   {
		if (this.spin == null)
			return;
		this.spin.dispose();
		this.spin = null;
		this.removeAllCellEditedListeners();
   }
}
