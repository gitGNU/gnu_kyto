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
import java.io.FileNotFoundException;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.w3c.dom.Attr;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author miguel
 *
 */
public class Config
{
	public class ServerInfo
	{
		String hostname = null;
		String port = null;
		String protocol = null;
		String ref = null;
		public void reset()
      {
	      hostname = null;
	      port = null;
	      protocol = null;
	      ref = null;	      
      }
	}
	
	public class DatabaseInfo
	{
		String ref = null;
		String name = null;
		String server_ref = null; 
	}
	
	private static Document document;
	private static String filename = "kyto.cfg";
	
	public Config()
	{
		try
      {
			File file = new File (filename);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db;
         db = dbf.newDocumentBuilder();
         document = db.parse(file);
      }
      catch (FileNotFoundException e)
      {
      	createNewFile();
      }
      catch (Exception e)
      {
	      // TODO Auto-generated catch block
      	System.out.println(e.getMessage());
	      e.printStackTrace();
      }
      document.getDocumentElement().normalize();
      System.out.println(getDocumentAsString());
	}
	
	public void destroy()
   {
		//set up a transformer
		try
      {
      	TransformerFactory transfac = TransformerFactory.newInstance();
      	Transformer trans = transfac.newTransformer();
      	trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
      	trans.setOutputProperty(OutputKeys.INDENT, "yes");

      	StreamResult result = new StreamResult(new File(filename));
      	DOMSource source = new DOMSource(document);
	      trans.transform(source, result);	      
      }
      catch (TransformerException e)
      {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
      }
   }
	private String getDocumentAsString()
   {
		//set up a transformer
		String xmlString = null;
      try
      {
      	TransformerFactory transfac = TransformerFactory.newInstance();
      	Transformer trans = transfac.newTransformer();
      	trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
      	trans.setOutputProperty(OutputKeys.INDENT, "yes");

      	//create string from xml tree
      	StringWriter sw = new StringWriter();
      	StreamResult result = new StreamResult(sw);
      	DOMSource source = new DOMSource(document);
	      trans.transform(source, result);
	      xmlString = sw.toString();
      }
      catch (TransformerException e)
      {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
      }

	   return xmlString;
   }
	private void createNewFile()
   {
		//We need a Document
		try
      {
			DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder;
         docBuilder = dbfac.newDocumentBuilder();
         document = docBuilder.newDocument();

         ////////////////////////
         //Creating the XML tree
         
         //create the root element and add it to the document
         Element root = document.createElement("kyto");
         document.appendChild(root);
         
         //create a comment and put it in the root element
         Comment comment = document.createComment("Configuration file for kyto. This file is generated. DO NOT EDIT.");
         root.appendChild(comment);
         
         //create metainfo section
         Element metainfo = document.createElement("metainfo");
         metainfo.setAttribute("version", "0.0.1");
         metainfo.setAttribute("lastEditUTCHuman", Calendar.getInstance().getTime().toString());
         metainfo.setAttribute("lastEditUTC", new Long(Calendar.getInstance().getTime().getTime()).toString());
         root.appendChild(metainfo);
         
         //create databases section
         Element databases = document.createElement("databases");
         databases.setAttribute("last_ref", "0");
         root.appendChild(databases);

         //create servers section
         Element servers = document.createElement("servers");
         servers.setAttribute("last_ref", "0");
         root.appendChild(servers);

         // create preferences section
         Element preferences = document.createElement("preferences");
         root.appendChild(preferences);

         //add a text element to the child
         //Text text = document.createTextNode("Filler, ... I could have had a foo!");
         //preferences.appendChild(text);
      }
		catch (ParserConfigurationException e)
      {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
      }
   }
	
	private String getNextRefForNode (Node node) throws Exception
	{
		NamedNodeMap attrlist = node.getAttributes();
		int value;
		String retval;
		
		if (attrlist == null)
			return "0";
		
		for (int i=0; i< attrlist.getLength(); i++)
		{
			Attr attribute = (Attr) attrlist.item(i);
			if (attribute.getName() == "last_ref")
			{
				value = new Integer(attribute.getValue());
				value ++;
				retval = (new Integer(value)).toString();
				attribute.setValue( retval );
				return retval;
			}
		}
		throw new Exception ("Not a valid XML");
	}
	
	private Node getDatabaseElement()
	{
		Element root = document.getDocumentElement();
		NodeList list = root.getChildNodes();
		int i;
		for (i=0; i<list.getLength() && !"databases".equals(list.item(i).getNodeName()); ++i);
		if (i>list.getLength())
			return null;
		return list.item(i);
	}
	
	private Node getServersElement()
	{ 
		Element root = document.getDocumentElement();
		NodeList list = root.getChildNodes();
		int i;
		for (i=0; i<list.getLength() && !"servers".equals(list.item(i).getNodeName()); ++i);
		if (i>list.getLength())
			return null;
		return list.item(i);
		
	}
	
	public String appendDatabase (String name) throws DOMException, Exception
	{
		Node database = getDatabaseElement();
		String ref;
		if (database == null)
			return null;
		ref = getNextRefForNode(database);
		Element newChild = document.createElement("database");
		newChild.setAttribute("ref", ref);
		newChild.setAttribute("name", name);
		database.appendChild(newChild);		
		return ref;
	}
	public boolean appendDatabase (String name, String servernum)
	{
		Node database = getDatabaseElement();
		if (database == null)
			return false;
		Element newChild = document.createElement("database");
		newChild.setAttribute("name", name);
		newChild.setAttribute("server_id", servernum);
		database.appendChild(newChild);		
		return true;
	}

	
	public String appendServer (String hostname, String port, String protocol) throws DOMException, Exception
	{
	
		
		Node servers = getServersElement();
		String ref;
		if (servers == null)
			return null;
		ref = getNextRefForNode(document.getDocumentElement().getElementsByTagName("servers").item(0));
		Element newChild = document.createElement("server");
		newChild.setAttribute("ref", ref);
		newChild.setAttribute("hostname", hostname);
		newChild.setAttribute("port", port);
		newChild.setAttribute("protocol", protocol);
		servers.appendChild(newChild);
		
		return ref;
	}
	
	public Vector<ServerInfo> getServers () throws Exception
	{
		Vector<ServerInfo> retval = new Vector<ServerInfo>();
		Node servers = getServersElement();
		if (servers == null)
			return null;
		Node child;
		NodeList children = servers.getChildNodes();
		for (int i=0; i<children.getLength(); ++i)
		{
			child = children.item(i);
			
			if (!"server".equals(child.getNodeName()))
				continue;
			
			ServerInfo element = new ServerInfo();
			NamedNodeMap attributes = child.getAttributes();
			for (int j=0; j<attributes.getLength(); ++j)
			{
				Attr attrib = (Attr) attributes.item(j);
				if ("ref".equals(attrib.getName()))
					element.ref = attrib.getValue();
				else if ("port".equals(attrib.getName()))
					element.port = attrib.getValue();
				else if ("hostname".equals(attrib.getName()))
					element.hostname = attrib.getValue();
				else if ("protocol".equals(attrib.getName()))
					element.protocol = attrib.getValue();
			}
			retval.add(element);
		}
		
		return retval;
	}
	
	public ServerInfo getServer (String ref) throws Exception
	{
		Node server  = getServerNode(ref);
				
		if (ref == null)
			return null;
		
		ServerInfo element = new ServerInfo();
		NamedNodeMap attributes = server.getAttributes();
		element.reset();
			
		for (int i=0; i<attributes.getLength(); ++i)
		{
			Attr attrib = (Attr) attributes.item(i);
			if ("ref".equals(attrib.getName()))
				element.ref = attrib.getValue();
			else if ("port".equals(attrib.getName()))
				element.port = attrib.getValue();
			else if ("hostname".equals(attrib.getName()))
				element.hostname = attrib.getValue();
			else if ("protocol".equals(attrib.getName()))
				element.protocol = attrib.getValue();
		}
		
		return element;
	}
	
	public Vector<DatabaseInfo> getDatabases () throws Exception
	{
		Vector<DatabaseInfo> retval = new Vector<DatabaseInfo>();
		Node servers = getServersElement();
		
		Node child;
		
		for (child = servers.getFirstChild(); child != null; child = child.getNextSibling())
		{
			if (!"server".equals(child.getNodeName()))
				throw new Exception ("Invalid XML");
			DatabaseInfo element = new DatabaseInfo();
			NamedNodeMap attributes = child.getAttributes();
			for (int i=0; i<attributes.getLength(); ++i)
			{
				Attr attrib = (Attr) attributes.item(i);
				if ("ref".equals(attrib.getName()))
					element.ref = attrib.getValue();
				else if ("name".equals(attrib.getName()))
					element.name = attrib.getValue();
			}
			retval.add(element);
		}		
		
		return retval;
	}
	
	public void deleteDatabase(String ref) throws Exception
	{
		Node database = getDatabaseElement();
		if (ref == null)
			return;
		for (Node child = database.getFirstChild(); child != null; child = child.getNextSibling())
		{
			if (!"server".equals(child.getNodeName()))
				throw new Exception ("Invalid XML");
			NamedNodeMap attributes = child.getAttributes();
			for (int i=0; i<attributes.getLength(); ++i)
			{
				Attr attrib = (Attr) attributes.item(i);
				if ("ref".equals(attrib.getName()) && ref.equals(attrib.getValue()))
					database.removeChild(child);
			}
		}
	}
	public Node getServerNode(String ref)
	{
		Node servers = getServersElement();
		if (ref == null)
			return null;
		for (Node child = servers.getFirstChild(); child != null; child = child.getNextSibling())
		{
			if (!"server".equals(child.getNodeName()))
				continue;
			NamedNodeMap attributes = child.getAttributes();
			for (int i=0; i<attributes.getLength(); ++i)
			{
				Attr attrib = (Attr) attributes.item(i);
				if ("ref".equals(attrib.getName()) && ref.equals(attrib.getValue()))
					return child;
			}
		}
		return null;
	}
	
	public void deleteServer(String ref) throws Exception
	{
		// FIXME: It must delete all databases associated.
		Node servers = getServersElement();
		Node server  = getServerNode(ref);
		if (server != null)
			servers.removeChild(server);
	}
}
