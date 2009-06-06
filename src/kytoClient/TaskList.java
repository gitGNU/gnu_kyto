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
import java.util.Date;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.TreeEditor;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Widget;

import com.cloudgarden.resource.SWTResourceManager;

public class TaskList extends CTabItem
{
	private Tree tasktree;
	protected Widget lastSelection;
	private HashMap<String, TaskData> datatree;
	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}

	public TaskList(CTabFolder parent, int style)
   {
	   super(parent, style);
	   initGUI();
   }
	
	public void buildTree()
	{
		tasktree = new Tree(this.getParent(), SWT.BORDER | SWT.MULTI | SWT.DOUBLE_BUFFERED | SWT.VIRTUAL);
		tasktree.setLinesVisible(true);
		tasktree.setHeaderVisible(true);
		tasktree.setLayout(new FillLayout());
		{
			/* Column 1: task id */
			TaskColumnText column = new TaskColumnText(tasktree,  0);
			column.setText("ID");
			//column.setVisibility(false);
		}
		
		{
			/* Column 2: Begin Date */
			TaskColumnDate column = new TaskColumnDate(tasktree, 1);
			column.setText("Begin");
		}
			
		{
			/* Column 3: End Date */
			TaskColumnDate column = new TaskColumnDate(tasktree, 2);
			column.setText("End");
		}
		
		{
			/* Column 4: Parent */
			TaskColumnText column = new TaskColumnText(tasktree, 3);
			column.setText("PARENT");
			//column.setVisibility(false);
		}
		
		{
			/* Column 5: Completed */
			TaskColumnToggle column = new TaskColumnToggle(tasktree, 4);
			column.setText("Completed");
			//column.setVisibility(false);
		}
		
		{
			/* Column 6: Priority */
			TaskColumnSpin column = new TaskColumnSpin(tasktree, 5);
			column.setText("Priority");
			//column.setVisibility(false);
		}
		this.setControl(tasktree);
		
		{
			tasktree.addListener (SWT.Selection, new Listener () {

				public void handleEvent (Event event) {
					setEditorsForItem((TreeItem) event.item);					
				}
			});
		}
		
		{
			tasktree.addListener(SWT.SetData, new Listener() {
				public void handleEvent(Event event) {
					TreeItem item = (TreeItem)event.item;
					TaskData data = datatree.get(getIdForTreeItem(item));
					String[] texts = new String[TaskData.SIZE];

					if (data != null)
					{
						for (int i=0; i<TaskData.SIZE; ++i)
						{
							texts[i] = data.format((TaskColumn) tasktree.getColumn(i));
							item.setItemCount(data.numChild);
						}
					}
					item.setText(texts);
				}
			});
		}
	}
	
	private String getIdForTreeItem(TreeItem item)
	{
		TreeItem ti = item;
		TreeItem parent;
		String strid = new String("");
		while (true)
		{
			parent = ti.getParentItem();
			if (parent == null)
				break;
			strid = String.format("%10d%s", parent.indexOf(ti), strid);
			ti = parent;
		}
		strid = String.format("%10d%s", ti.getParent().indexOf(ti), strid);
		return strid;
	}
	
	private void setEditorsForItem (TreeItem item)
	{
		// remove previous editors
		for (int i=0; i<tasktree.getColumnCount(); ++i)
			((TaskColumn)tasktree.getColumn(i)).removeEditor();
		
		for (int i=0; i<TaskData.SIZE; i++) 
		{
			TreeEditor editor = new TreeEditor (tasktree);
			TaskColumn column = (TaskColumn) tasktree.getColumn(i);
			
			Control control = column.getNewEditor(editor, item.getText(i), new CellEditedListener() {
				public void cellEdited(CellEditedEvent event)
				{
					TaskColumn column = (TaskColumn) event.getSource();
					TreeItem[] selection = tasktree.getSelection();
					
					for (int j=0; j< selection.length; ++j)
					{
						TaskData data = datatree.get(getIdForTreeItem(selection[j]));
						data.setValue(event, column);
						selection[j].setText(column.getIndex(), data.format(column));
					}
					System.out.println("__"+column);
				}
			});
						
			if (control != null)
			{
				editor.setEditor (control, item, i);
			}
		}	
	}
	
	private void initGUI() {

		try {
			buildTree();
			
			{
				datatree = new HashMap<String, TaskData>(); 
				
				for (int i=0; i<10; ++i)
				{
					Date date = new Date();
					TaskData data = new TaskData("hola" + i, date.getTime(), 0, null);
					datatree.put(String.format("%10d", i), data);
					
					for (int j=0; j<10; ++j)
					{
						TaskData subdata = new TaskData("hola" + j+(10*i), date.getTime(), 0, data.id);
						datatree.put(String.format("%10d%10d", i, j), subdata);	
						data.numChild ++;
					}
				}
			}
			tasktree.setItemCount(10);
			this.getParent().setSelection(this.getParent().getItemCount()-1);
			this.setShowClose(true);
			this.pack();
		} catch(Exception e) {
			System.out.println("TaskList error");
			e.printStackTrace();
		}
	}

	private void pack()
   {
   }
}
