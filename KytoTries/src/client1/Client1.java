/**
 * 
 */
package client1;

import KytoTries.Task;
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
		Ice.ObjectPrx obj = communicator().stringToProxy("KytoTries:tcp -h localhost -p 13470");
		TaskPrx prxtask = KytoTries.TaskPrxHelper.checkedCast(obj);
		
		System.out.println( prxtask.createChildTask() );
		TaskPrx[] a = prxtask.getTaskChildren();
		System.out.println("Size: " + a.length);
		
		for (int i=0; i<a.length; ++i)
		{
			String title = a[i].getTitle();
			if (title != null)
				System.out.println(title);
		}
		

		System.out.println("End");
	   return 0;
   }

}
