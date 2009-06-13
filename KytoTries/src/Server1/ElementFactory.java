/**
 * 
 */
package Server1;

import Ice.Object;
import Ice.ObjectFactory;
import KytoTries.Task;

/**
 * @author miguel
 *
 */
public class ElementFactory implements ObjectFactory
{

	/* (non-Javadoc)
	 * @see Ice._ObjectFactoryOperationsNC#create(java.lang.String)
	 */
	@Override
	public Object create(String type)
	{
		if (Task.ice_staticId().equals(type))
			return new TaskI();
		assert(false);
		return null;
	}

	/* (non-Javadoc)
	 * @see Ice._ObjectFactoryOperationsNC#destroy()
	 */
	@Override
	public void destroy()
	{
		// TODO Auto-generated method stub

	}

}
