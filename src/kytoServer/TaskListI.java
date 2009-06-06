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
package kytoServer;

import java.sql.Date;

import KYTO.IdentityTaskMap;
import KYTO.TaskComplete;
import KYTO.TaskDescription;
import KYTO._TaskDisp;
import Freeze.Connection;
import Ice.Current;
import Ice.Identity;

public class TaskListI extends _TaskDisp {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6624324147160299520L;
	private IdentityTaskMap _itmap;
	private Connection _fconn;

	TaskListI (Ice.Communicator communicator, String envName, String dbName)
	{
		this._fconn = Freeze.Util.createConnection(communicator, envName);
		_itmap = new IdentityTaskMap(this._fconn, dbName);
	}
	
	public void close ()
	{
		this._itmap.close();
	}
	
	@Override
	public TaskDescription getTask(Identity id, Current __current) {
		try {
			return (TaskDescription) this._itmap.get(id);			
		}
		catch (Freeze.DatabaseException ex) {
			
		}
		finally	{
			
		}
		return null;
	}
	
	private String taskDescriptionToString(TaskDescription td) {
		java.util.Date begindate = new java.util.Date(td.begindate);
		String retval = new String("TaskDescription:");
		retval = retval.concat("\tTitle:'").concat(td.title).concat("'");
		retval = retval.concat("\tBegan:'").concat(begindate.toString()).concat("'");
		return retval;
	}

	@Override
	public TaskComplete createTask(Identity parent, Current __current) {
		TaskComplete retval = new TaskComplete();
		retval.fields = new TaskDescription();
		try {
			System.out.println("creating task");
			if ("".equals(parent.name)) 
				retval.fields.parent = (Identity) parent.clone();
			retval.id = new Ice.Identity();
			retval.id.name = Ice.Util.generateUUID();
			retval.fields.title = new String("New Task");
			java.util.Date date = new java.util.Date();
			retval.fields.begindate = date.getTime();
			this._itmap.put(retval.id, retval.fields);
			
			
		} 
		catch (Freeze.DatabaseException ex) {
			
		}
		finally	{
			
		}
		return retval;
	}

	@Override
	public Identity setTitle(String title, Current __current) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String handshake(Current __current) {
		System.out.println ("server> handshake");
		return "handshake, client!!";
	}

	@Override
	public void printTasks(Current __current) {
		Freeze.Map.EntryIterator it = (Freeze.Map.EntryIterator) this._itmap.entrySet().iterator();
		try {
			while (it.hasNext()) {
				java.util.Map.Entry entry = (java.util.Map.Entry) it.next();
				System.out.println("Key: " + entry.getKey() + "; Value: "
						+ taskDescriptionToString((TaskDescription) entry.getValue()));
			}
			
		}
		catch (Freeze.DatabaseException ex) {
			
		}
		finally	{
			it.close();
		}
	}

}
