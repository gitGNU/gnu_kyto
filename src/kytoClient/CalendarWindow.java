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

import java.util.Calendar;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class CalendarWindow extends org.eclipse.swt.widgets.Dialog {

	private Shell shell;
	private DateTime calendar;
	private Button buttonDelete;
	private Button buttonCurrent;
	private long saveddate = 0;
	
	/**
	* Auto-generated main method to display this 
	* org.eclipse.swt.widgets.Dialog inside a new Shell.
	*/
	public static void main(String[] args) {
		try {
			Display display = Display.getDefault();
			Shell shell = new Shell(display);
			CalendarWindow inst = new CalendarWindow(shell, SWT.NULL);
			inst.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public CalendarWindow(Shell parent, int style) 
	{
		super(parent, style);
	}

	public void open() {
		try {
			Shell parent = getParent();
			shell = new Shell(parent, SWT.MODELESS | SWT.APPLICATION_MODAL);

			shell.setLayout(new FormLayout());
			{
				buttonDelete = new Button(shell, SWT.PUSH | SWT.CENTER);
				buttonDelete.setText("Delete");
				FormData buttonDeleteLData = new FormData();
				buttonDeleteLData.width = 100;
				buttonDeleteLData.height = 21;
				buttonDeleteLData.left =  new FormAttachment(0, 1000, 0);
				buttonDeleteLData.top =  new FormAttachment(880, 1000, 0);
				buttonDeleteLData.bottom =  new FormAttachment(1000, 1000, 0);
				buttonDeleteLData.right =  new FormAttachment(1000, 1000, -100);
				buttonDelete.setLayoutData(buttonDeleteLData);
				buttonDelete.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						buttonDeleteWidgetSelected(evt);
					}
				});
			}
			{
				FormData calendarLData = new FormData();
				calendarLData.width = 202;
				calendarLData.height = 160;
				calendarLData.left =  new FormAttachment(0, 1000, 0);
				calendarLData.right =  new FormAttachment(1000, 1000, 0);
				calendarLData.top =  new FormAttachment(0, 1000, 0);
				calendarLData.bottom =  new FormAttachment(849, 1000, 0);
				calendar = new DateTime(shell, SWT.CALENDAR);
				calendar.setLayoutData(calendarLData);

				calendar.addMouseListener(new MouseListener() {

					@Override
               public void mouseDoubleClick(MouseEvent evt)
               {
						Calendar date = Calendar.getInstance();
						date.clear();
						date.set(calendar.getYear(), calendar.getMonth(), calendar.getDay());	
						saveddate = date.getTimeInMillis();
						
						shell.close();			               
               }

					@Override
               public void mouseDown(MouseEvent arg0)
               {
							               
               }

					@Override
               public void mouseUp(MouseEvent arg0) {}
					
				});
			}
			{
				buttonCurrent = new Button(shell, SWT.PUSH | SWT.CENTER);
				buttonCurrent.setText("Current");
				FormData button1LData = new FormData();
				button1LData.left =  new FormAttachment(0, 1000, 102);
				button1LData.top =  new FormAttachment(880, 1000, 0);
				button1LData.width = 98;
				button1LData.height = 21;
				button1LData.right =  new FormAttachment(1000, 1000, 0);
				button1LData.bottom =  new FormAttachment(1000, 1000, 0);
				buttonCurrent.setLayoutData(button1LData);
				buttonCurrent.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						buttonCurrentWidgetSelected(evt);
					}
				});
			}
			shell.addListener(SWT.Deactivate, new Listener(){
				public void handleEvent (Event event) {
					shell.setVisible(false);
					shell.close();
				}
			});
			
			Calendar date = Calendar.getInstance();
			
			calendar.setDate(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH));
			date.clear();
			date.set(calendar.getYear(), calendar.getMonth(), calendar.getDay());	
			saveddate = date.getTimeInMillis();
			
			shell.layout();
			shell.pack();			
			shell.setLocation(getParent().toDisplay(100, 100));
			shell.open();
			Display display = shell.getDisplay();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public long getDate()
	{
		return saveddate;
	}

	private void buttonCurrentWidgetSelected(SelectionEvent evt)
   {
		
		Calendar date = Calendar.getInstance();

		date.set(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH), 0,0,0);
		saveddate = date.getTimeInMillis();
		
		shell.close();	
   }

	private void buttonDeleteWidgetSelected(SelectionEvent evt) 
	{
		System.out.println("buttonDelete.widgetSelected, event="+evt);
		saveddate = 0;
		
		shell.close();	
	}

}
