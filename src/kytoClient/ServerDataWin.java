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
import com.cloudgarden.resource.SWTResourceManager;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;


public class ServerDataWin extends org.eclipse.swt.widgets.Dialog {

	private Shell dialogShell;
	private Label label1;
	private Button buttonOk;
	private Button buttonClose;
	private Combo comboProtocol;
	private Spinner spinPort;
	private Text textHostname;
	private Label label3;
	private Label label2;
	private boolean hasValidValues = false;
	private String protocol;
	private int port;
	private String hostname;

	/**
	* Auto-generated main method to display this 
	* org.eclipse.swt.widgets.Dialog inside a new Shell.
	*/
	public static void main(String[] args) {
		try {
			Display display = Display.getDefault();
			Shell shell = new Shell(display);
			ServerDataWin inst = new ServerDataWin(shell, SWT.NULL);
			inst.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ServerDataWin(Shell parent, int style) {
		super(parent, style);
	}

	public void open() {
		try {
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);

			{
				//Register as a resource user - SWTResourceManager will
				//handle the obtaining and disposing of resources
				SWTResourceManager.registerResourceUser(dialogShell);
			}
			
			
			dialogShell.setLayout(new FormLayout());
			{
				buttonOk = new Button(dialogShell, SWT.PUSH | SWT.CENTER);
				buttonOk.setText("OK");
				FormData buttonOkLData = new FormData();
				buttonOkLData.width = 81;
				buttonOkLData.height = 27;
				buttonOkLData.left =  new FormAttachment(0, 1000, 183);
				buttonOkLData.top =  new FormAttachment(0, 1000, 127);
				buttonOk.setLayoutData(buttonOkLData);
				buttonOk.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						buttonOkWidgetSelected(evt);
					}
				});
			}
			{
				buttonClose = new Button(dialogShell, SWT.PUSH | SWT.CENTER);
				buttonClose.setText("Cancel");
				FormData buttonCloseLData = new FormData();
				buttonCloseLData.width = 72;
				buttonCloseLData.height = 27;
				buttonCloseLData.left =  new FormAttachment(0, 1000, 270);
				buttonCloseLData.top =  new FormAttachment(0, 1000, 127);
				buttonClose.setLayoutData(buttonCloseLData);
				buttonClose.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						buttonCloseWidgetSelected(evt);
					}
				});
			}
			{
				comboProtocol = new Combo(dialogShell, SWT.NONE);
				FormData comboProtocolLData = new FormData();
				comboProtocolLData.width = 187;
				comboProtocolLData.height = 27;
				comboProtocolLData.left =  new FormAttachment(0, 1000, 91);
				comboProtocolLData.top =  new FormAttachment(0, 1000, 66);
				comboProtocol.setLayoutData(comboProtocolLData);
				comboProtocol.setLayoutDeferred(true);
				comboProtocol.setItems(new java.lang.String[] {"TCP","UDP"});
				comboProtocol.select(0);

			}
			{
				spinPort = new Spinner(dialogShell, SWT.NONE);
				FormData text2LData = new FormData();
				text2LData.width = 88;
				text2LData.height = 15;
				text2LData.left =  new FormAttachment(0, 1000, 91);
				text2LData.top =  new FormAttachment(0, 1000, 39);
				spinPort.setLayoutData(text2LData);
				spinPort.setMaximum(65535);
				spinPort.setIncrement(10);
				spinPort.setPageIncrement(100);
				spinPort.setSelection(13467);
			}
			{
				textHostname = new Text(dialogShell, SWT.NONE);
				textHostname.setText("localhost");
				FormData text1LData = new FormData();
				text1LData.width = 247;
				text1LData.height = 15;
				text1LData.left =  new FormAttachment(0, 1000, 91);
				text1LData.top =  new FormAttachment(0, 1000, 12);
				textHostname.setLayoutData(text1LData);
			}
			{
				label3 = new Label(dialogShell, SWT.NONE);
				label3.setText("Protocol");
				FormData label3LData = new FormData();
				label3LData.width = 52;
				label3LData.height = 15;
				label3LData.left =  new FormAttachment(0, 1000, 12);
				label3LData.top =  new FormAttachment(0, 1000, 66);
				label3.setLayoutData(label3LData);
			}
			{
				label2 = new Label(dialogShell, SWT.NONE);
				label2.setText("Port");
				FormData label2LData = new FormData();
				label2LData.width = 26;
				label2LData.height = 15;
				label2LData.left =  new FormAttachment(0, 1000, 12);
				label2LData.top =  new FormAttachment(0, 1000, 39);
				label2.setLayoutData(label2LData);
			}
			{
				label1 = new Label(dialogShell, SWT.NONE);
				label1.setText("Hostname");
				FormData label1LData = new FormData();
				label1LData.width = 67;
				label1LData.height = 15;
				label1LData.left =  new FormAttachment(0, 1000, 12);
				label1LData.top =  new FormAttachment(0, 1000, 12);
				label1.setLayoutData(label1LData);
			}
			dialogShell.layout();
			dialogShell.pack();			
			dialogShell.setSize(360, 195);
			dialogShell.setEnabled(true);
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
	
	private void buttonOkWidgetSelected(SelectionEvent evt) {
		setHostname(textHostname.getText());
		setPort(spinPort.getSelection());
		setProtocol(comboProtocol.getText());
		hasValidValues = true;
		dialogShell.dispose();
	}

	public boolean hasValidValues()
   {
	   return hasValidValues;
   }

	public void setProtocol(String protocol)
   {
	   this.protocol = protocol;
   }

	public String getProtocol()
   {
	   return protocol;
   }

	public void setPort(int port)
   {
	   this.port = port;
   }

	public int getPort()
   {
	   return port;
   }

	public void setHostname(String hostname)
   {
	   this.hostname = hostname;
   }

	public String getHostname()
   {
	   return hostname;
   }

}
