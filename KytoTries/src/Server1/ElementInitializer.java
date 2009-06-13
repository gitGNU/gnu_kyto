/**
 * 
 */
package Server1;

import Freeze.ServantInitializer;
import Ice.Identity;
import Ice.Object;
import Ice.ObjectAdapter;

/**
 * @author miguel
 *
 */
public class ElementInitializer implements ServantInitializer
{

	/* (non-Javadoc)
	 * @see Freeze._ServantInitializerOperationsNC#initialize(Ice.ObjectAdapter, Ice.Identity, java.lang.String, Ice.Object)
	 */
	@Override
	public void initialize(ObjectAdapter adapter, Identity id, String facet,
	      Object obj)
	{
		if (obj instanceof TaskI)
		{
			((TaskI)obj).dirty = true;
			((TaskI)obj)._id   = id;
		}
			

	}

}
