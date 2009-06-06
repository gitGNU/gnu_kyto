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

import java.io.File;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import kytoClient.Config.ServerInfo;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TableItem;

/**
 * @author miguel
 *
 */
public class ConfigTester
{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Config config = new Config ();
         ServerInfo server = null;
         config.appendServer("localhost", "100", "tcp");
   		Iterator<ServerInfo> iter = config.getServers().iterator();
   		while (iter.hasNext())
   		{
   			server = iter.next();
   			System.out.println(server.hostname + " " + server.port + " " + server.protocol);
   		}
   		config.destroy();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
