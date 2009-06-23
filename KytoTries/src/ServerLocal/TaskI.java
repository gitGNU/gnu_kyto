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
package ServerLocal;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import Freeze.Evictor;
import Ice.Current;
import Ice.Identity;
import Ice.ObjectAdapter;
import KytoTries.Task;
import KytoTries.TaskData;
import KytoTries.TaskPrx;
import KytoTries.TaskPrxHelper;

/**
 * @author miguel
 *
 */
public class TaskI extends Task
{

	/**
    * 
    */
   private static final long serialVersionUID = 1L;
	public static ObjectAdapter _adapter;
	public static Evictor _evictor;
	public Identity _id;
	
	public TaskI ()
	{
		this.title = new String ("New Task");
	}
	
	public TaskI (Identity id)
	{
		this._id = id;	
		this.title = new String("New Task");
	}
	
	
	
	
	@Override
   public TaskPrx createChildTask(Current __current)
   {
	   Identity id = __current.adapter.getCommunicator().stringToIdentity(Ice.Util.generateUUID());
	   
	   TaskI task = new TaskI(id);
	   task.dirty = true;
	   task.parent = (Identity) __current.id.clone();
	   task.children = new HashMap<Ice.Identity,String>();
	   
	   children.put(id, null);

	   TaskPrx proxy = TaskPrxHelper.uncheckedCast(_evictor.add(task,id));

	   System.out.println ("CreateChildTask");
	   
	   return proxy;
   }

	@Override
   public TaskPrx[] getTaskChildren(Current __current)
   {
	   System.out.println ("getTaskChildren");

		Set<Identity> keyset = children.keySet();
		TaskPrx[] retval = new TaskPrx [keyset.size()];
		Iterator<Identity> iterator = keyset.iterator();
		for (int i = 0; i < keyset.size(); ++i)
		{
			retval[i] = TaskPrxHelper.uncheckedCast(
	            __current.adapter.createProxy(iterator.next()));
		}
		return retval;
   }

	@Override
   public TaskData getTaskData(Current __current)
   {
		TaskData retval = new TaskData();
		retval.title    = new String(title);
		retval.parent   = (Identity) parent.clone();
		retval.counter  = counter;
		retval.deleted  = deleted;
		
		retval.children = new HashMap<Identity, String>();
		Set<Identity> keylist = children.keySet();
		for (Iterator<Identity> iter = keylist.iterator(); iter.hasNext(); )
			retval.children.put(iter.next(), null); 
	   return retval;
   }

	@Override
   public TaskData updateTask(TaskData data, Current __current)
   {
	   this.parent = (Identity) data.parent.clone();
	   this.title  = data.title;
	   this.counter = data.counter;
	   this.deleted = data.deleted;
	   
	   children.clear();
	   
		Set<Identity> keylist = data.children.keySet();
		for (Iterator<Identity> iter = keylist.iterator(); iter.hasNext(); )
			children.put(iter.next(), null); 	   
	   return data;
   }
}
