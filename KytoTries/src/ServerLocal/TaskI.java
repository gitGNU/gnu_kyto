/**
 * 
 */
package Server1;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import Freeze.Evictor;
import Freeze.EvictorIterator;
import Ice.Current;
import Ice.Identity;
import Ice.ObjectAdapter;
import KytoTries.Task;
import KytoTries.TaskDictHelper;
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
	   task.parent = TaskPrxHelper.uncheckedCast(__current.adapter.createProxy(__current.id));
	   task.children = new HashMap<Ice.Identity, TaskPrx>();
		
	   TaskPrx proxy = TaskPrxHelper.uncheckedCast(_evictor.add(task,id));
	   
	   children.put(id, proxy);
	   
	   System.out.println ("CreateChildTask");
	   
	   return proxy;
   }

	@Override
   public TaskPrx[] getTaskChildren(Current __current)
   {
		TaskPrx[] tasklist = new TaskPrx[children.size()];
		
		Iterator<TaskPrx> iter = children.values().iterator();
		for (int i=0; i<children.size() && iter.hasNext(); ++i)
		//while (iter.hasNext())
		{
			//TaskPrx data = iter.next();
			
			tasklist[i] = iter.next();
		}
		
		return tasklist;

   }

	@Override
   public String getTitle(Current __current)
   {
	   return title;
   }
}
