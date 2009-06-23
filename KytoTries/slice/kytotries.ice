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

#include <Ice/Identity.ice>

module KytoTries
{
	class Task;
	
	sequence<Ice::Identity> TaskIdList;
	dictionary<Ice::Identity, string> TaskIdHash;
	sequence<Task*>         TaskPrxList;
	
	
	class TaskData
	{
		int      counter;  /* To syncronize with remote server */
		string   title;    /* Title of the task */ 

		TaskIdHash children;  
		Ice::Identity   parent;
		
		bool     deleted; /* For security, no task will be deleted really. */
	};
	
	class Task extends TaskData
	{
		bool     dirty;    /* FALSE: It is syncronized with remote server */
		
		["freeze:write"] Task*       createChildTask();
		["freeze:write"] TaskData    updateTask(TaskData data);
		
		TaskPrxList      getTaskChildren();
		TaskData         getTaskData();
	};
	
	interface Plan
	{
		Task* searchTask(Ice::Identity id);
	};
};