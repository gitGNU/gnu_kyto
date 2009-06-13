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

import java.util.EventObject;

/**
 * @author miguel
 *
 */
public class CellEditedEvent extends EventObject
{	
	/**
    * 
    */
	enum KINDS {
		activated,
		unknown
	};

	private static final long serialVersionUID = 1L;
   private KINDS kind = KINDS.unknown;
	private String value = null;
	private int button = 0;
	private String text; 
	private Long longvalue = null;
	
	/**
	 * @param arg0
	 */
	public CellEditedEvent(TaskColumn taskcolumn, KINDS kind, String text)
	{
		super(taskcolumn);
		this.kind = kind;
		this.setText(text);
	}

	public CellEditedEvent(TaskColumn taskcolumn, KINDS kind, Long longvalue)
	{
		super(taskcolumn);
		this.kind = kind;
		this.setLong(longvalue);
	}
	
	public KINDS getKind()
   {
	   return kind;
   }

	public void setValue(String value)
	{
		this.value = value;
	}
	
	public String getValue()
   {
	   return value;
   }

	public void setButton(int button)
   {
	   this.button = button;
   }

	public int getButton()
   {
	   return button;
   }

	public void setText(String text)
   {
	   this.text = text;
   }

	public String getText()
   {
	   return text;
   }

	public void setLong(Long longvalue)
   {
	   this.longvalue = new Long (longvalue);
	   this.text = this.longvalue.toString();
   }

	public Long getLong()
   {
	   return longvalue;
   }
}
