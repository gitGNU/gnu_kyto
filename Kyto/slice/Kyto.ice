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

module KYTO {
	struct TaskDescription 
	{
		["KYTO:renderas=text;"]				string        title; 
		["KYTO:renderas=date;"]				long          begindate;
		["KYTO:renderas=date;"]				long          enddate;
		["KYTO:renderas=none;"]				Ice::Identity parent;
	};
	
	struct TaskComplete
	{
		Ice::Identity   id;
		TaskDescription fields;
	};
	
  	interface Task {
		["freeze:read"]  TaskDescription getTask(Ice::Identity id);
		["freeze:write"] Ice::Identity setTitle (string title);
		["freeze:write"] TaskComplete  createTask (Ice::Identity parent);
		["freeze:read"]  void printTasks ();
							  string handshake();
  	};
  	
  	enum ERRORS {
  		ERROK,
  		ERRDBEXISTS,
  		ERRDBCREATION
  	};
  	
  	interface Management {
  		 ERRORS  createDatabase (string name);
  	};
  	
  	//sequence<Task*> TaskPrxSeq;
  	
  	sequence<string>  stringseq; 
  	
  	struct TaskDBDescription
  	{
  		string filename;
  	};
};
