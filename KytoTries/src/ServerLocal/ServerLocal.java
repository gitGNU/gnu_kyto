/**
 * 
 */
package Server1;

import Ice.Identity;
import Ice.ObjectPrx;
import KytoTries.Task;

/**
 * @author miguel
 *
 */
public class Server1 extends Ice.Application
{
	private String _envName;

	public Server1(String envName)
   {
       _envName = envName;
   }

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		Server1 app = new Server1("db");
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
	   Ice.ObjectAdapter adapter = communicator().createObjectAdapterWithEndpoints("KytoTries", "tcp -h localhost -p 13470");
	   
	   TaskI._adapter = adapter;
	   
	   // Create the Freeze evictor (Stored in the _evictor static member)
	   Freeze.ServantInitializer init = new ElementInitializer();
	   Freeze.Evictor evictor = Freeze.Util.createTransactionalEvictor (adapter, 
	   		_envName, "evictorKyto", null, init, null, true);
	   TaskI._evictor = evictor;
	   
	   adapter.addServantLocator(evictor, "");
	   
	   // Create the root node if it doesn't exists
	   Identity rootId = Ice.Util.stringToIdentity("KytoTries");
	   if (!evictor.hasObject(rootId))
	   {
	   	TaskI root = new TaskI();
	   	root.dirty = true;
	   	root.title = "/";
	   	
	   	ObjectPrx prx = evictor.add(root, rootId);
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
