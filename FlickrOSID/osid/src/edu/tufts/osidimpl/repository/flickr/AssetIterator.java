package edu.tufts.osidimpl.repository.flickr;

import org.json.JSONArray;
import org.json.JSONObject;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.*;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.*;

public class AssetIterator
implements org.osid.repository.AssetIterator
{
    private java.util.Iterator _iterator = null;
	private JSONArray _ja;
	private String _query = null;
	private int _page = 1;
	private int _maxReturns = 100;
	private int _currentReturn = 0;
	
    protected AssetIterator(java.util.Vector vector)
    {
        _iterator = vector.iterator();
    }

	protected AssetIterator(String criteria, String domain, String prefix)
	{
		//System.out.println(criteria);
		//System.out.println(domain);
		//System.out.println(prefix);
		try {
			String c = java.net.URLEncoder.encode(criteria,"UTF-8");
			_query = prefix + c;
			if (domain != null && domain.length() > 0)
			{
				domain = java.net.URLEncoder.encode(domain,"UTF-8");
				_query = _query + "&site=" + domain;
			}		
			getMore();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void getMore()
	{
		//System.out.println("getting more " + _query + "&page=" + _page);
		try {
			HttpClient client = new HttpClient();
			GetMethod method = new GetMethod(_query + "&page=" + _page++);
			method.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY); 
			client.getParams().setParameter(HttpMethodParams.USER_AGENT,
											"Visual Understanding Environment http://vue.tufts.edu");
											// Send GET request
			int statusCode = client.executeMethod(method);
			
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + method.getStatusLine());
			}
			InputStream rstream = null;
			
			// Get the response body
			rstream = method.getResponseBodyAsStream();
			
			// Process the response from Yahoo! Web Services
			BufferedReader br = new BufferedReader(new InputStreamReader(rstream));
			String jsonString = "";
			String line;
			while ((line = br.readLine()) != null) {
				jsonString += line;
			}
			br.close();
			
			// Construct a JSONObject from a source JSON text string.
			// A JSONObject is an unordered collection of name/value pairs. Its external
			// form is a string wrapped in curly braces with colons between the names
			// and values, and commas between the values and names.
			jsonString = jsonString.substring(14);
			jsonString = jsonString.substring(0,jsonString.length()-1);
			JSONObject jo = new JSONObject(jsonString);

			// A JSONArray is an ordered sequence of values. Its external form is a
	        // string wrapped in square brackets with commas between the values.
			
	        // Get the JSONObject value associated with the search result key.
	        jo = jo.getJSONObject("photos");
			
	        //System.out.println(jo.toString());
			
	        // Get the JSONArray value associated with the Result key
	        _ja = jo.getJSONArray("photo");
			
	        // Get the number of search results in this set
	        int resultCount = _ja.length();
			//System.out.println("results: " + resultCount);
			
			java.util.Vector vector = new java.util.Vector();
			
			for (int i=0; i < resultCount; i++) {
				
				JSONObject resultObject = _ja.getJSONObject(i);
				
				String id = null;
				String owner = null;
				String secret = null;
				String server = null;
				Integer farm = null;
				String title = null;
				
				id = (String)resultObject.getString("id");
				owner = (String)resultObject.get("owner");
				secret = (String)resultObject.getString("secret");
				server = (String)resultObject.get("server");				
				farm = (Integer)resultObject.get("farm");
				title = (String)resultObject.get("title");
				
				// print results
				/*
				 System.out.println("id: " + id);
				 System.out.println("owner: " + owner);
				 System.out.println("secret: " + secret);
				 System.out.println("server: " + server);
				 System.out.println("farm: " + farm);
				 */
				//System.out.println("title: " + title);
				
				vector.addElement(new Asset(id,
											owner,
											secret,
											server,
											farm.toString(),
											title));	
			}
			_iterator = vector.iterator();
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
	}

	public boolean hasNextAsset()
		throws org.osid.repository.RepositoryException
    {
		if (_currentReturn++ >= _maxReturns) return false;
		
        if (_iterator.hasNext()) {
			return true;
		} else {
			getMore();
			return _iterator.hasNext();
		}
    }

    public org.osid.repository.Asset nextAsset()
		throws org.osid.repository.RepositoryException
    {
        if (_iterator.hasNext()) {
			return (org.osid.repository.Asset)_iterator.next();
        } else {
			throw new org.osid.repository.RepositoryException(org.osid.shared.SharedException.NO_MORE_ITERATOR_ELEMENTS);
		}
    }
	
	/**
		The MIT License
	 
	 Copyright (c) 2008, Massachusetts Institute of Technology

	 Permission is hereby granted, free of charge, to any person obtaining a copy
	 of this software and associated documentation files (the "Software"), to deal
	 in the Software without restriction, including without limitation the rights
	 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
	 copies of the Software, and to permit persons to whom the Software is
	 furnished to do so, subject to the following conditions:

	 The above copyright notice and this permission notice shall be included in
	 all copies or substantial portions of the Software.

	 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
	 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
	 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
	 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
	 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
	 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
	 THE SOFTWARE.

	 */
}