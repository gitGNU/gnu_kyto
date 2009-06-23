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

import Ice.Identity;
import Ice.ObjectPrx;
import KytoTries.Task;

/**
 * @author miguel
 *
 */
public class ServerLocal extends Ice.Application
{
	private String _envName;

	public ServerLocal(String envName)
   {
       _envName = envName;
   }

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		ServerLocal app = new ServerLocal("db");
      int status = app.main("Server", args, "config.server");
      System.exit(status);


	}

	@Override
   public int run(String[] arg0)
   {
	   // Install object Factories
		Ice.ObjectFactory factory = new ElementFactory();
	   communicator().addObjectFactory(factory, Task.ice_staticId());
	   
	   // Create object adapter (stored in the _adapter static member)
	   Ice.ObjectAdapter adapter = communicator().createObjectAdapterWithEndpoints("KytoTries", "tcp -h localhost -p 13471");
	   
	   TaskI._adapter = adapter;
	   
	   // Create the Freeze evictor (Stored in the _evictor static member)
	   Freeze.ServantInitializer init = new ElementInitializer();
	   /*
	   Index[] idx = new Index[1];
	   idx[0] = new idxId("");
	   */
	   TaskI._evictor = Freeze.Util.createTransactionalEvictor (adapter, 
	   		_envName, "evictorKyto", null, init, null, true);
	   
	   
	   adapter.addServantLocator(TaskI._evictor, "");
	   
	   // Create the root node if it doesn't exists
	   Identity rootId = Ice.Util.stringToIdentity("KytoTries");
	   if (!TaskI._evictor.hasObject(rootId))
	   {
	   	TaskI root = new TaskI();
	   	root.dirty = true;
	   	root.title = "/";
	   	root.children = new HashMap<Ice.Identity,String>();
	   	root._id = rootId;
	   	root.parent = Ice.Util.stringToIdentity("none");
	   	
	   	ObjectPrx prx = TaskI._evictor.add(root, rootId);
	   	System.out.println("New servant -> " + communicator().proxyToString(prx));
	   }
	   
	   // Ready to accept requests
	   adapter.activate();
	   
	   System.out.println("Ready.");
	   // Wait
	   communicator().waitForShutdown();
	   
	   return 0;
   }

}
