#include <Ice/Identity.ice>

module KytoTries
{
	class Task;
	
	dictionary<Ice::Identity,Task*> TaskDict;
	
	sequence<Task*> TaskPrxSeq;
	
	class Task
	{
		bool     dirty;
		string   title;
		TaskDict children;
		Task*    parent;
		
		["freeze:write"] Task*       createChildTask();
		TaskPrxSeq  getTaskChildren();
		string      getTitle();
	};
};