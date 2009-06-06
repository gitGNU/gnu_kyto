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
package kytoServer;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import Ice.Communicator;
import Ice.Current;
import IceStorm.AlreadySubscribed;
import IceStorm.BadQoS;
import IceStorm.NoSuchTopic;
import IceStorm.TopicExists;
import IceStorm.TopicManagerPrx;
import IceStorm.TopicPrx;
import KYTO.ERRORS;
import KYTO.IdentityTaskMap;
import KYTO._ManagementDisp;

public class ManagementI extends _ManagementDisp
{
   private static final long serialVersionUID = 1L;
   private TopicManagerPrx topicManagerPrx = null;
	private Communicator communicator;

	public ManagementI(Ice.Communicator communicator, TopicManagerPrx topicManagerPrx)
   {
	   super();
	   this.topicManagerPrx = topicManagerPrx;
	   this.communicator    = communicator;
	   
	   Map<String, TopicPrx> current_topics = this.topicManagerPrx.retrieveAll();
	   Set<String> keys = current_topics.keySet();
	   Iterator<String> iter = keys.iterator();
	   while (iter.hasNext())
	   {
	   	String name = iter.next();
	   	TaskListI tasklist = new TaskListI(communicator,"kytodb", name);
	   	Ice.ObjectAdapter oa = topicManagerPrx.ice_getConnection().getAdapter();
			Ice.ObjectPrx prx = oa.addWithUUID(tasklist);

	      try
	      {
		      current_topics.get(name).subscribeAndGetPublisher(null, prx);
	      }
	      catch (AlreadySubscribed e)
	      {
		      // TODO Auto-generated catch block
		      e.printStackTrace();
	      }
	      catch (BadQoS e)
	      {
		      // TODO Auto-generated catch block
		      e.printStackTrace();
	      }
	   }
   }

	private ERRORS createChannel (String name)
	{
		ERRORS retval = ERRORS.ERROK;
		
		
		
		return retval;
	}
	
	@Override
   public ERRORS createDatabase(String name, Current __current)
   {
		ERRORS retval = ERRORS.ERRDBCREATION;
		String realname = name + ".kyto";
		
		System.out.println("Llega OK");

		TopicPrx topicprx = null;
		/* Create the channel */
		try
      {
	      topicprx = topicManagerPrx.create(realname);
	      retval = ERRORS.ERROK;
      }
      catch (TopicExists e)
      {
      	retval = ERRORS.ERRDBEXISTS;
      	try
         {
      		topicprx = topicManagerPrx.retrieve(realname);
         }
         catch (NoSuchTopic e1)
         {
	         // TODO Auto-generated catch block
	         e1.printStackTrace();
         }
      }
		
      /* Connect to the channel */
      TaskListI tasklist = new TaskListI(communicator,"kytodb", realname);
      Ice.ObjectAdapter oa = __current.con.getAdapter();
		Ice.ObjectPrx prx = oa.addWithUUID(tasklist);

      try
      {
	      topicprx.subscribeAndGetPublisher(null, prx);
      }
      catch (AlreadySubscribed e)
      {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
      }
      catch (BadQoS e)
      {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
      }
      
      
	   return retval;
   }

}
