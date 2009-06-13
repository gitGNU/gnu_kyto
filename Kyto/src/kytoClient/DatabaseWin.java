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

import java.util.Iterator;
import java.util.Set;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class DatabaseWin extends org.eclipse.swt.widgets.Dialog {

	private Shell dialogShell;
	private Table table1;
	private TableColumn tableColumn1;
	private Button buttonConnect;
	private Button buttonClose;
	private boolean cancelled;
	private String databaseName;

	/**
	* Auto-generated main method to display this 
	* org.eclipse.swt.widgets.Dialog inside a new Shell.
	*/
	public static void main(String[] args) {
		try {
			Display display = Display.getDefault();
			Shell shell = new Shell(display);
			DatabaseWin inst = new DatabaseWin(shell, SWT.NULL);
			inst.open(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DatabaseWin(Shell parent, int style) {
		super(parent, style);
	}
	
	public void open(Set<String> data) {
		try {
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);

			dialogShell.setLayout(new FormLayout());
			dialogShell.layout();
			dialogShell.pack();			
			dialogShell.setText("Databases");
			{
				buttonConnect = new Button(dialogShell, SWT.PUSH | SWT.CENTER);
				buttonConnect.setText("Connect");
				FormData buttonConnectLData = new FormData();
				buttonConnectLData.width = 66;
				buttonConnectLData.height = 25;
				buttonConnectLData.left =  new FormAttachment(0, 1000, 119);
				buttonConnectLData.top =  new FormAttachment(0, 1000, 215);
				buttonConnect.setLayoutData(buttonConnectLData);
				buttonConnect.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						buttonConnectWidgetSelected(evt);
					}
				});
			}
			{
				buttonClose = new Button(dialogShell, SWT.PUSH | SWT.CENTER);
				buttonClose.setText("Close");
				FormData buttonCloseLData = new FormData();
				buttonCloseLData.width = 66;
				buttonCloseLData.height = 25;
				buttonCloseLData.left =  new FormAttachment(0, 1000, 191);
				buttonCloseLData.top =  new FormAttachment(0, 1000, 215);
				buttonClose.setLayoutData(buttonCloseLData);
				buttonClose.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						buttonCloseWidgetSelected(evt);
					}
				});
			}
			{
				table1 = new Table(dialogShell, SWT.NONE);
				FormData table1LData = new FormData();
				table1LData.width = 227;
				table1LData.height = 162;
				table1LData.left =  new FormAttachment(0, 1000, 12);
				table1LData.top =  new FormAttachment(0, 1000, 12);
				table1.setLayoutData(table1LData);
				table1.setHeaderVisible(true);
				table1.setLinesVisible(true);
				{
					tableColumn1 = new TableColumn(table1, SWT.NONE);
					table1.setSortColumn(tableColumn1);
					tableColumn1.setText("Databases");
					tableColumn1.setWidth(60);
				}
			}
			
			{
				if (data != null)
				{
					Iterator<String> iter = data.iterator();
					while (iter.hasNext())
					{
						TableItem item = new TableItem(table1, SWT.NONE);
						item.setText(iter.next());
					}
				}
			}
			dialogShell.setSize(250, 200);
			dialogShell.setLocation(getParent().toDisplay(100, 100));
			dialogShell.open();
			Display display = dialogShell.getDisplay();
			while (!dialogShell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void buttonCloseWidgetSelected(SelectionEvent evt) {
		dialogShell.dispose();
	}
	
	private void buttonConnectWidgetSelected(SelectionEvent evt) {
		TableItem[] selection = table1.getSelection();
		if (selection.length < 1)
			return;
		setDatabaseName(selection[0].getText()); 
		setCancelled(false);
		dialogShell.dispose();
	}

	private void setCancelled(boolean cancelled)
   {
	   this.cancelled = cancelled;
   }

	public boolean isCancelled()
   {
	   return cancelled;
   }

	private void setDatabaseName(String databaseName)
   {
	   this.databaseName = databaseName;
   }

	public String getDatabaseName()
   {
	   return databaseName;
   }

}
