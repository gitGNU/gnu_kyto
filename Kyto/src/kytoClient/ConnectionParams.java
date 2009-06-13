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
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;


/**
 * @author miguel
 */
public class ConnectionParams extends org.eclipse.swt.widgets.Dialog {

	private Shell dialogShell;
	private Text hostname;
	private Label label1;
	private Spinner port;
	private Text example;
	private Label label4;
	private Combo protocol;
	private Label label3;
	private Button cancelbutton;
	private Button acceptbutton;
	private Label label2;
	private String conndata;

	/**
	 * Auto-generated main method to display this
	 * org.eclipse.swt.widgets.Dialog inside a new Shell.
	 */
	public static void main(String[] args) {
		try {
			Display display = Display.getDefault();
			Shell shell = new Shell(display);
			ConnectionParams inst = new ConnectionParams(shell, SWT.NULL);
			inst.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ConnectionParams(Shell parent, int style) {
		super(parent, style);
	}

	public void open() {
		try {
			Shell parent = getParent();
			this.dialogShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);

			this.dialogShell.setLayout(new FormLayout());
			dialogShell.setText("Connection Parameters");
			{
				example = new Text(dialogShell, SWT.BORDER);
				example.setText("localhost -p 8453");
				FormData exampleLData = new FormData();
				exampleLData.width = 178;
				exampleLData.height = 15;
				exampleLData.left =  new FormAttachment(0, 1000, 104);
				exampleLData.top =  new FormAttachment(0, 1000, 112);
				example.setLayoutData(exampleLData);
				example.setEnabled(false);
				example.setEditable(false);
			}
			{
				label4 = new Label(dialogShell, SWT.NONE);
				label4.setText("End Points");
				FormData label4LData = new FormData();
				label4LData.width = 94;
				label4LData.height = 15;
				label4LData.left =  new FormAttachment(0, 1000, 12);
				label4LData.top =  new FormAttachment(0, 1000, 112);
				label4.setLayoutData(label4LData);
			}
			{
				protocol = new Combo(dialogShell, SWT.NONE);
				FormData protocolLData = new FormData();
				protocolLData.width = 183;
				protocolLData.height = 25;
				protocolLData.left =  new FormAttachment(0, 1000, 104);
				protocolLData.top =  new FormAttachment(0, 1000, 78);
				protocol.setLayoutData(protocolLData);
				protocol.addModifyListener(new ModifyListener() {
					public void modifyText(ModifyEvent evt) {
						System.out.println("protocol.modifyText, event="+evt);
						setExampleText();
					}
				});
			}
			{
				this.cancelbutton = new Button(this.dialogShell, SWT.PUSH | SWT.CENTER);
				this.cancelbutton.setText("Cancel");
				FormData cancelbuttonLData = new FormData();
				cancelbuttonLData.width = 53;
				cancelbuttonLData.height = 25;
				cancelbuttonLData.left =  new FormAttachment(0, 1000, 235);
				cancelbuttonLData.top =  new FormAttachment(0, 1000, 147);
				cancelbutton.setLayoutData(cancelbuttonLData);
				cancelbutton.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						cancelbuttonWidgetSelected(evt);
					}
				});
			}
			{
				this.acceptbutton = new Button(this.dialogShell, SWT.PUSH | SWT.CENTER);
				dialogShell.setDefaultButton(acceptbutton);
				this.acceptbutton.setText("OK");
				FormData button1LData = new FormData();
				button1LData.width = 60;
				button1LData.height = 25;
				button1LData.left =  new FormAttachment(0, 1000, 161);
				button1LData.top =  new FormAttachment(0, 1000, 147);
				acceptbutton.setLayoutData(button1LData);
				this.acceptbutton.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent evt) {
						acceptbuttonWidgetSelected(evt);
					}
				});
			}
			this.dialogShell.layout();
			this.dialogShell.pack();
			dialogShell.setSize(312, 218);
			{
				this.port = new Spinner(this.dialogShell, SWT.NONE);
				FormData portLData = new FormData();
				portLData.width = 169;
				portLData.height = 15;
				portLData.left =  new FormAttachment(0, 1000, 104);
				portLData.top =  new FormAttachment(0, 1000, 45);
				port.setLayoutData(portLData);
				port.setMaximum(65000);
				port.setSelection(8453);
				port.setPageIncrement(100);
				port.addModifyListener(new ModifyListener() {
					public void modifyText(ModifyEvent evt) {
						System.out.println("port.modifyText, event="+evt);
						setExampleText();
					}
					
				});
			}
			{
				this.label2 = new Label(this.dialogShell, SWT.NONE);
				this.label2.setText("Port");
				FormData label2LData = new FormData();
				label2LData.width = 26;
				label2LData.height = 15;
				label2LData.left =  new FormAttachment(0, 1000, 12);
				label2LData.top =  new FormAttachment(0, 1000, 45);
				label2.setLayoutData(label2LData);
			}
			{
				this.label1 = new Label(this.dialogShell, SWT.NONE);
				this.label1.setText("Hostname");
				FormData label1LData = new FormData();
				label1LData.width = 67;
				label1LData.height = 15;
				label1LData.left =  new FormAttachment(0, 1000, 12);
				label1LData.top =  new FormAttachment(0, 1000, 12);
				this.label1.setLayoutData(label1LData);
			}
			{
				this.hostname = new Text(this.dialogShell, SWT.NONE);
				this.hostname.setText("localhost");
				FormData hostnameLData = new FormData();
				hostnameLData.width = 181;
				hostnameLData.height = 15;
				hostnameLData.left =  new FormAttachment(0, 1000, 104);
				hostnameLData.top =  new FormAttachment(0, 1000, 12);
				hostname.setLayoutData(hostnameLData);
				hostname.addModifyListener(new ModifyListener() {
					public void modifyText(ModifyEvent evt) {
						System.out.println("hostname.modifyText, event="+evt);
						setExampleText();
					}
				});
			}
			{
				label3 = new Label(dialogShell, SWT.NONE);
				label3.setText("Protocol");
				FormData label3LData = new FormData();
				label3LData.left =  new FormAttachment(0, 1000, 12);
				label3LData.top =  new FormAttachment(0, 1000, 78);
				label3LData.width = 67;
				label3LData.height = 15;
				label3.setLayoutData(label3LData);
			}

			protocol.add("tcp", 0);
			protocol.add("udp", 1);
			protocol.select(0);

			
			this.dialogShell.setLocation(getParent().toDisplay(100, 100));
			this.dialogShell.open();
			Display display = this.dialogShell.getDisplay();
			while (!this.dialogShell.isDisposed()) {
				if (!display.readAndDispatch()) display.sleep();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void acceptbuttonWidgetSelected(SelectionEvent evt) {
		this.conndata = this.example.getText();
		this.dialogShell.dispose();
	}
	
	private void cancelbuttonWidgetSelected(SelectionEvent evt) {
		this.conndata = null;
		this.dialogShell.dispose();
	}
	
	private void setExampleText ()
	{
		String str = new String("");
		
		if (this.protocol.getText().length() > 0)
			str = str.concat(" -t:").concat(this.protocol.getText());
		if (this.hostname.getText().length() > 0)
			str = str.concat(" -h ").concat(this.hostname.getText());
		if (this.port.getText().length() > 0)
			str = str.concat(" -p ").concat(this.port.getText());
		
		this.example.setText(str);
	}

	public String getConndata()
   {
		return this.conndata;
   }

	public void close()
   {
   }
}
