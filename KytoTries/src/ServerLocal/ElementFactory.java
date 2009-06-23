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
