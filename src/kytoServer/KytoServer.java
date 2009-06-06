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

import java.io.IOException;

import Ice.ObjectPrx;
import IceStorm.AlreadySubscribed;
import IceStorm.BadQoS;
import IceStorm.NoSuchTopic;
import IceStorm.TopicExists;
import IceStorm.TopicManagerPrx;

/**
 * @author miguel
 *
 */

public class KytoServer extends Ice.Application {
	public KytoServer() {
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	 	KytoServer app = new KytoServer();
	  	app.main("Servidor", args);
	}

	
	private void createTopic(TopicManagerPrx topicManagerPrx, String topic)
	{
		try
		{
			topicManagerPrx.retrieve(topic);
		}
		catch (IceStorm.NoSuchTopic e)
		{			
			try
         {
	         topicManagerPrx.create(topic);
         }
         catch (TopicExists e1)
         {
	         // TODO Auto-generated catch block
	         e1.printStackTrace();
         }
		}
	}
	
	private void createMainTopic (TopicManagerPrx topicManagerPrx) throws NoSuchTopic, BadQoS 
	{
		Ice.ObjectAdapter oa = communicator().createObjectAdapterWithEndpoints("Kyto", "default");
		Ice.ObjectPrx prx = oa.addWithUUID(new ManagementI(communicator(), topicManagerPrx));
		
		IceStorm.TopicPrx t;
		
		try
      {
	      t = topicManagerPrx.create("Kyto");
      }
      catch (TopicExists e)
      {
   		t = topicManagerPrx.retrieve("Kyto");
      }
		try
      {
	      t.subscribeAndGetPublisher(null, prx);
      }
      catch (AlreadySubscribed e)
      {
      	System.out.println("Already subscribed");
      }
		oa.activate();

	}
	
	@Override
	public int run(String[] arg0) {
		shutdownOnInterrupt();
		
		String endpoints = String.format("Kyto/TopicManager:%s -h %s -p %s", "tcp", "localhost", "13467");
		ObjectPrx prx = communicator().stringToProxy(endpoints);
		
		TopicManagerPrx topicManagerPrx = IceStorm.TopicManagerPrxHelper.checkedCast(prx);

		try
      {
	      createMainTopic(topicManagerPrx);
      }
      catch (NoSuchTopic e)
      {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
      }
      catch (BadQoS e)
      {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
      }
      
      
		createTopic(topicManagerPrx, "database1.kyto");

		
		
		//Freeze.Evictor evictor = Freeze.Util.createBackgroundSaveEvictor(adapter, _envName, "tasks", null, null, true);
        //evictor.setSize(3);
    
        //adapter.addServantLocator(evictor, "task");

		//TaskListI tasklist = new TaskListI(communicator(), _envName, "tasks");
		//adapter.add(tasklist, communicator().stringToIdentity("tasklist"));
		//adapter.activate();
		
		
		System.out.println("Ready.");
		
		communicator().waitForShutdown();

		return 0;
	}

}
