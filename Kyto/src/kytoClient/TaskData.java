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

package kytoClient;


/**
 * @author miguel
 *
 */
public class TaskData
{
	private static final int CT_ID = 0;
	private static final int CT_BEGIN = 1;
	private static final int CT_END = 2;
	private static final int CT_PARENT = 3;
	private static final int CT_COMPLETED = 4;
	private static final int CT_PRIORITY = 5;
	public static final int SIZE = 6;
	public String id;
	public long begin;
	public long end;
	public String parent;
	public boolean completed;
	public long priority;
	
	public int numChild;
	
	TaskData(String id, long begin, long end, String parent)
	{
		this.id = id;
		this.begin = begin;
		this.end = end;
		this.parent = parent;
		this.completed = false;
		numChild = 0;
	}
	
	/**
	 * Decide how to obtain the value and inserts it. Returns the string associated to the value.
	 * @param item
	 * @param column
	 * @return
	 */
	public void setValue(CellEditedEvent event, TaskColumn column)
	{
		switch (column.getIndex())
		{
		case CT_ID:
			this.id = event.getText();
			break;
		case CT_BEGIN:
			this.begin = event.getLong();
			break;
		case CT_END:
			this.end = event.getLong();
			break;
		case CT_PARENT:
			this.parent = event.getText();
			break;			
		case CT_COMPLETED:
			this.completed = (event.getLong() == 1);
			System.out.println("** "+this.completed+" "+event.getLong());
			break; 
		case CT_PRIORITY:
			this.priority = event.getLong();
			break;
		}
	}

	public String format(TaskColumn column)
   {
		switch (column.getIndex())
		{
		case CT_ID:
			return column.formatValue(this.id); 
		case CT_BEGIN:
			return column.formatValue(this.begin);
		case CT_END:
			return column.formatValue(this.end);
		case CT_PARENT:
			return column.formatValue(this.parent);			
		case CT_COMPLETED:
			return column.formatValue(this.completed?1:0);
		case CT_PRIORITY:
			return column.formatValue(this.priority);
		}
	   
	   return null;
   }
}
