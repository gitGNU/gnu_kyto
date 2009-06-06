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

import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

import Ice.Identity;
import IceStorm.TopicManagerPrx;
import IceStorm.TopicPrx;

import com.cloudgarden.resource.SWTResourceManager;


/**
 * @author miguel
 */
public class KytoClient extends Ice.Application
{
	public class connectiondata
	{
		String	hostname;
		String	protocol;
		String	port;
	};

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		KytoClient app = new KytoClient();
		app.main("Cliente", args);
	}

	private Display	display;
	private Shell	 shell;
	private MenuItem menuItemNew;
	private MenuItem menuItemNewLocal;
	private Menu menu3;
	private CTabFolder cTabFolder;
	private MenuItem menuItemExit;
	private MenuItem menuItem1;
	private MenuItem menuItemConnect;
	private Menu menu2;
	private MenuItem menuItemconnection;
	private Menu menu1;
	private Config configuration;

	@Override
	public int run(String[] args)
	{
		configuration = new Config();
		this.display = new Display();
		this.shell = new Shell(display);
		shell.setText("Keep Your Tasks Organized");
		shell.setLayout(new FillLayout());
		{
			//Register as a resource user - SWTResourceManager will
			//handle the obtaining and disposing of resources
			SWTResourceManager.registerResourceUser(shell);
		}
		
		{
			cTabFolder = new CTabFolder(shell, SWT.V_SCROLL);
			cTabFolder.setLayout(new FillLayout());
			//cTabFolder.setBounds(0, 0, 594, 225);
			cTabFolder.setSimple(false);
			cTabFolder.setMRUVisible(true);
			cTabFolder.setSelectionBackground(
					new Color[]{SWTResourceManager.getColor(0xff, 0xff, 0xff), 
							SWTResourceManager.getColor(0xbe, 0xc9, 0xd0)},
					new int[]{ 75 },
					true);
			cTabFolder.setSelection(0);
		}
		{
			menu1 = new Menu(this.shell, SWT.BAR);
			this.shell.setMenuBar(menu1);
			{
				menuItemconnection = new MenuItem(menu1, SWT.CASCADE);
				menuItemconnection.setText("&Connection");
				{
					menu2 = new Menu(menuItemconnection);
					menuItemconnection.setMenu(menu2);
					{
						menuItemNew = new MenuItem(menu2, SWT.CASCADE);
						menuItemNew.setText("Create Database");
						{
							menu3 = new Menu(menuItemNew);
							menuItemNew.setMenu(menu3);
							{
								menuItemNewLocal = new MenuItem(menu3, SWT.PUSH);
								menuItemNewLocal.setText("&Local (No connection)");
								menuItemNewLocal.addSelectionListener(new SelectionAdapter() {
									public void widgetSelected(SelectionEvent evt) {
										menuItemNewLocalWidgetSelected(evt);
									}
								});
							}
						}
					}
					{
						menuItemConnect = new MenuItem(menu2, SWT.PUSH);
						menuItemConnect.setText("Connect...");
						menuItemConnect.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent evt) {
								menuItemConnectWidgetSelected(evt);
							}
						});
					}
					{
						menuItem1 = new MenuItem(menu2, SWT.SEPARATOR);
						menuItem1.setText("menuItem1");
					}
					{
						menuItemExit = new MenuItem(menu2, SWT.PUSH);
						menuItemExit.setText("Exit");
						menuItemExit.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent evt) {
								menuItemExitWidgetSelected(evt);
							}
						});
					}
				}
			}
		}
		this.shell.pack();
		shell.setSize(606, 277);
		this.shell.open();

		while (!this.shell.isDisposed())
		{
			if (!display.readAndDispatch()) display.sleep();
		}
		configuration.destroy();
		display.dispose();

		return 0;
	}
	
	private void menuItemConnectWidgetSelected(SelectionEvent evt) {
		Display display = null;
		Shell shell = null;
		ServersWin inst = null;
		try {
			display = Display.getDefault();
			shell = new Shell(display);
			inst = new ServersWin(shell, SWT.NONE, configuration);
			inst.open();
			if (inst.hasValidValues())
			{
				iceConnect(inst.getHostname(), inst.getPort(), inst.getProtocol());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (shell != null)
				shell.dispose();
		}
	}
	
	private void menuItemExitWidgetSelected(SelectionEvent evt) {
		this.shell.dispose();
	}
	
	private void menuItemNewLocalWidgetSelected(SelectionEvent evt)
	{
		TaskList tasklist = new TaskList(cTabFolder, SWT.CLOSE);
		//cTabItem1.setControl(tasklist);
		tasklist.setText("Tasklist" + this.cTabFolder.getItemCount());
	}
	
	private void iceConnect(String hostname, String port, String protocol)
	{
		try
		{
			String prxstr = String.format("Kyto/TopicManager:%s -h %s -p %s", protocol.toLowerCase(), hostname, port);
			Ice.ObjectPrx objprx = communicator().stringToProxy(prxstr);
			TopicManagerPrx topicManagerPrx = IceStorm.TopicManagerPrxHelper.checkedCast(objprx);
			Map<String, TopicPrx> databaselist = topicManagerPrx.retrieveAll();
			
			DatabaseWin dialog = new DatabaseWin(shell, SWT.NONE);
			dialog.open(databaselist.keySet());
			if (dialog.isCancelled())
				return;
		}
		finally
		{
		}
	}

}
