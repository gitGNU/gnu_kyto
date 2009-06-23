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
package client1;

import KytoTries.TaskData;
import KytoTries.TaskPrx;

/**
 * @author miguel
 *
 */
public class Client1 extends Ice.Application
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		Client1 app = new Client1();
		app.main("Cliente", args);
	}

	@Override
   public int run(String[] arg0)
   {
		Ice.ObjectPrx obj = communicator().stringToProxy("KytoTries:tcp -h localhost -p 13471");
		TaskPrx prxtask = KytoTries.TaskPrxHelper.checkedCast(obj);
		if (true)
		{
			System.out.println( prxtask.createChildTask() );
		}
		if(true)
		{
			
		}
		
		TaskPrx[] a = prxtask.getTaskChildren();
		System.out.println("Size: " + a.length);
		
		for (int i=0; i<a.length; ++i)
		{
			TaskData taskdata = a[i].getTaskData();
			System.out.println(taskdata.title);
			TaskData data = a[i].getTaskData();
			data.title += " " + i;
			a[i].updateTask(data);
		}
   
		if(false)
		{
			//a[0].deleteTask();
		}
		
		
		System.out.println("End");
	   return 0;
   }

}
