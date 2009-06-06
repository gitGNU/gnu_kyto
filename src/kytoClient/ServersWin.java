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

import kytoClient.Config.ServerInfo;

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

public class ServersWin extends org.eclipse.swt.widgets.Dialog {

	private Shell dialogShell;
	private Table table1;
	private TableColumn tableColumnHostname;
	private TableColumn tableColumnProtocol;
	private TableColumn tableColumnPort;
	private Button buttonConnect;
	private Button buttonClose;
	private Button buttonDel;
	private Button buttonEdit;
	private Button buttonAdd;
	private boolean hasValidValues = false;
	private String hostname;
	private String port;
	private TableColumn tableColumnRef;
	private String protocol;
	private Config config;
	
	private final int HOSTNAME = 0;
	private final int PORT     = 1;
	private final int PROTOCOL = 2;
	private final int REF      = 3;

	/**
	* Auto-generated main method to display this 
	* org.eclipse.swt.widgets.Dialog inside a new Shell.
	*/
	public static void main(String[] args) {
		try {
			Display display = Display.getDefault();
			Shell shell = new Shell(display);
			ServersWin inst = new ServersWin(shell, SWT.NULL, null);
			inst.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ServersWin(Shell parent, int style, Config config) throws Exception {
		super(parent, style);
		this.config = config;		
	}

	public void open() {
		try {
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);

			dialogShell.setLayout(new FormLayout());
			dialogShell.layout();
			dialogShell.pack();			
			dialogShell.setSize(506, 235);
			dialogShell.setText("Servers");
			dialogShell.setEnabled(true);
			{
				buttonConnect = new Button(dialogShell, SWT.PUSH | SWT.CENTER);
				buttonConnect.setText("Connect");
				FormData buttonConnectLData = new FormData();
				buttonConnectLData.width = 66;
				buttonConnectLData.height = 25;
				buttonConnectLData.left =  new FormAttachment(0, 1000, 422);
				buttonConnectLData.top =  new FormAttachment(0, 1000, 111);
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
				buttonCloseLData.left =  new FormAttachment(0, 1000, 422);
				buttonCloseLData.top =  new FormAttachment(0, 1000, 167);
				buttonClose.setLayoutData(buttonCloseLData);
				buttonClose.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						buttonCloseWidgetSelected(evt);
					}
				});
			}
			{
				buttonDel = new Button(dialogShell, SWT.PUSH | SWT.CENTER);
				buttonDel.setText("Delete");
				FormData buttonDelLData = new FormData();
				buttonDelLData.width = 66;
				buttonDelLData.height = 25;
				buttonDelLData.left =  new FormAttachment(0, 1000, 422);
				buttonDelLData.top =  new FormAttachment(0, 1000, 78);
				buttonDel.setLayoutData(buttonDelLData);
				buttonDel.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						buttonDelWidgetSelected(evt);
					}
				});
			}
			{
				buttonEdit = new Button(dialogShell, SWT.PUSH | SWT.CENTER);
				buttonEdit.setText("Edit");
				FormData buttonEditLData = new FormData();
				buttonEditLData.width = 66;
				buttonEditLData.height = 25;
				buttonEditLData.left =  new FormAttachment(0, 1000, 422);
				buttonEditLData.top =  new FormAttachment(0, 1000, 45);
				buttonEdit.setLayoutData(buttonEditLData);
			}
			{
				buttonAdd = new Button(dialogShell, SWT.PUSH | SWT.CENTER);
				buttonAdd.setText("New");
				FormData buttonAddLData = new FormData();
				buttonAddLData.width = 66;
				buttonAddLData.height = 25;
				buttonAddLData.left =  new FormAttachment(0, 1000, 422);
				buttonAddLData.top =  new FormAttachment(0, 1000, 12);
				buttonAdd.setLayoutData(buttonAddLData);
				buttonAdd.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						buttonAddWidgetSelected(evt);
					}
				});
			}
			{
				table1 = new Table(dialogShell, SWT.SINGLE);
				FormData table1LData = new FormData();
				table1LData.width = 378;
				table1LData.height = 141;
				table1LData.left =  new FormAttachment(0, 1000, 12);
				table1LData.top =  new FormAttachment(0, 1000, 12);
				table1.setLayoutData(table1LData);
				table1.setHeaderVisible(true);
				{
					tableColumnHostname = new TableColumn(table1, SWT.NONE);
					tableColumnHostname.setText("Hostname");
					tableColumnHostname.setWidth(206);
				}
				{
					tableColumnPort = new TableColumn(table1, SWT.NONE);
					tableColumnPort.setText("Port");
					tableColumnPort.setWidth(60);
				}
				{
					tableColumnProtocol = new TableColumn(table1, SWT.NONE);
					tableColumnProtocol.setText("Protocol");
					tableColumnProtocol.setWidth(60);
				}
				{
					tableColumnRef = new TableColumn(table1, SWT.NONE);
					tableColumnRef.setText("Ref");
				}
			}
			
			{
				ServerInfo server = null;
				Iterator<ServerInfo> iter = config.getServers().iterator();
				while (iter.hasNext())
				{
					server = iter.next();
					TableItem tableitem = new TableItem(table1, SWT.SINGLE);
					tableitem.setText(HOSTNAME, server.hostname);
					tableitem.setText(PORT    , server.port);
					tableitem.setText(PROTOCOL, server.protocol);
					tableitem.setText(REF     , server.ref);
				}
			}
			
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
	
	private void buttonAddWidgetSelected(SelectionEvent evt) {
		Display display = null;
		Shell shell = null;
		ServerDataWin inst = null;
		try {
			display = Display.getDefault();
			shell = new Shell(display);
			inst = new ServerDataWin(shell, SWT.NULL);
			inst.open();
			if (inst.hasValidValues())
			{
				TableItem item = new TableItem(table1, SWT.SINGLE);
				String ref;
				item.setText(HOSTNAME, inst.getHostname());
				item.setText(PORT    , (new Integer(inst.getPort())).toString());
				item.setText(PROTOCOL, inst.getProtocol());
				ref = config.appendServer(item.getText(HOSTNAME), item.getText(PORT), item.getText(PROTOCOL));
				item.setText(REF,   ref);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (shell != null)
				shell.dispose();
		}
		
	}

	public boolean hasValidValues()
   {
		return hasValidValues;
   }
	
	private void buttonConnectWidgetSelected(SelectionEvent evt) {
		TableItem[] selection = table1.getSelection();
		
		if (selection.length == 0)
			return;
		setHostname(selection[0].getText(HOSTNAME));
		setPort(selection[0].getText(PORT));
		setProtocol(selection[0].getText(PROTOCOL));
		
		hasValidValues = true;
		dialogShell.dispose();
	}

	public void setHostname(String hostname)
   {
	   this.hostname = hostname;
   }

	public String getHostname()
   {
	   return hostname;
   }

	public void setPort(String port)
   {
	   this.port = port;
   }

	public String getPort()
   {
	   return port;
   }

	public void setProtocol(String protocol)
   {
	   this.protocol = protocol;
   }

	public String getProtocol()
   {
	   return protocol;
   }
	
	private void buttonDelWidgetSelected(SelectionEvent evt) {
		TableItem[] item = table1.getSelection();
		if (item.length < 1)
			return;
		try
      {
	      config.deleteServer(item[0].getText(REF));
      }
      catch (Exception e)
      {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
      }
		table1.remove(table1.getSelectionIndices());
	}

}
