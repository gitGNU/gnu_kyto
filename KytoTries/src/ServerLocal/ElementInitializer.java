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
//			((TaskI)obj).dirty = true;
			((TaskI)obj)._id   = id;
//			((TaskI)obj).children   = new HashMap<Ice.Identity,String>();
		}
	}
}
