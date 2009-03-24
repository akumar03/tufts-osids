package edu.tufts.osidimpl.repository.yahoowiki;

import org.json.JSONArray;
import org.json.JSONObject;

public class Metadata
{
	java.util.Vector _assetVector = new java.util.Vector();
	
	public java.util.Vector getAssets()
	{
		return _assetVector;
	}
	
	public Metadata(JSONObject jo)
	{
		try {
//			 A JSONArray is an ordered sequence of values. Its external form is a
	        // string wrapped in square brackets with commas between the values.
	        JSONArray ja;

	        // Get the JSONObject value associated with the search result key.
	        jo = jo.getJSONObject("ResultSet");

	        //System.out.println(jo.toString());

	        // Get the JSONArray value associated with the Result key
	        ja = jo.getJSONArray("Result");

	        // Get the number of search results in this set
	        int resultCount = ja.length();
	        
			
			for (int i=0; i < resultCount; i++) {
				
				JSONObject resultObject = ja.getJSONObject(i);
				
				String type = null;

				String description = null;				
				String title = null;								
				String URL = null;				
				String value = null;
				
				if ((value = (String)resultObject.get("MimeType")) != null) 
						type = value;

				description = (String)resultObject.get("Summary");				
				title = (String)resultObject.get("Title");
				URL = (String)resultObject.get("Url");

				// print results
				if (Configuration.getInstance().debug()) {
					System.out.println("type: " + type);
					System.out.println("description: " + description);
					System.out.println("URL: " + URL);
					System.out.println("title: " + title);
					System.out.println("asset id: " + i);
				}
				
				_assetVector.addElement(new Asset(new Integer(i).toString(),
													type,
													description,
													title,
													URL));													  
			}				
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
		
	private String getElementValue(org.w3c.dom.Element element, String tag)
	{
		// return an element's value
		try {
			org.w3c.dom.NodeList nodeList = element.getElementsByTagName(tag);
			if (nodeList.getLength() > 0) {
				org.w3c.dom.Element e = (org.w3c.dom.Element)nodeList.item(0);
				if (e.hasChildNodes()) {
					String value = e.getFirstChild().getNodeValue();
					return value;
				}
			}
		} catch (Throwable t) {
		}
		return null;
	}
	
	private String[] getElementValues(org.w3c.dom.Element element, String tag)
	{
		// handle one or more elements
		java.util.Vector v = new java.util.Vector();
		try {
			org.w3c.dom.NodeList nodeList = element.getElementsByTagName(tag);
			int numNodes = nodeList.getLength();
			for (int i=0; i < numNodes; i++) {
				org.w3c.dom.Element e = (org.w3c.dom.Element)nodeList.item(i);
				if (e.hasChildNodes()) {
					v.addElement(e.getFirstChild().getNodeValue());
				}
			}
		} catch (Throwable t) {
		}
		// convert element values stores in a vector into a string array
		int size = v.size();
		if (size > 0) {
			String s[] = new String[size];
			for (int i=0; i < size; i++) {
				s[i] = (String)v.elementAt(i);
			}
			return s;
		}
		return null;
	}
}

