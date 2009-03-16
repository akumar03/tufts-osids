	package edu.tufts.osidimpl.repository.flickr;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.json.JSONArray;
import org.json.JSONObject;

public class Asset
implements org.osid.repository.Asset
{
	String queryPrefix ="http://api.flickr.com/services/rest/?method=flickr.photos.getInfo&api_key=093a05d30b0be54c8b18d227fec80b6b&format=json&photo_id=";
	private Configuration configuration = Configuration.getInstance();
    private org.osid.shared.Type assetType = new Type("tufts.edu","asset","flickr");
    private org.osid.shared.Type recordStructureType = configuration.RECORD_STRUCTURE_TYPE;
    private org.osid.shared.Id _id = null;
    private org.osid.shared.Id _repositoryId = null;
    private String _displayName = null;
    private java.util.Vector recordVector = new java.util.Vector();
    private String content = null;
    private org.osid.shared.Id recordStructureId = null;
	private String _description;
    protected Asset(String id,
			String owner,
			String secret,
			String server,
			String farm,
			String title)		    
    throws org.osid.repository.RepositoryException
    {

    	// set display name, description, id, and any metadata (in records)
    	 _displayName = title;

    	_repositoryId = configuration.getRepositoryId();
    	try {
    		org.osid.id.IdManager idManager = configuration.getIdManager();
    		_id = configuration.getIdManager().getId(id);
    		
    		org.osid.repository.Record record = createRecord(RecordStructure.getInstance().getId());
    		//record.createPart(idManager.getId(configuration.PartStructureIDs[configuration.LARGE_IMAGE_URL_INDEX]),"http://www.tufts.edu/home/flash/images/ftrPhotoHome.jpg");
  if (configuration.skipMetadata().equals("false"))
  {
    		String query = queryPrefix + id;
    		HttpClient client = new HttpClient();
            GetMethod method = new GetMethod(query);
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
    	    
    	    jo = jo.getJSONObject("photo");
    	    
    	    
//    	    if (value != null) record.createPart(idManager.getId(configuration.PartStructureIDs[configuration.]),value);
    	    String value = jo.optString("media");
    	    if (value != null) record.createPart(idManager.getId(configuration.PartStructureIDs[configuration.FORMAT_INDEX]),value);
    	    
    	    
    	    JSONObject desc = jo.optJSONObject("description");
    	    value = desc.getString("_content");
    	    if (value != null) 
    	    	{
    	    	_description = value;
    	    		record.createPart(idManager.getId(configuration.PartStructureIDs[configuration.DESCRIPTION_INDEX]),value);
    	    	}
    	    
    	    
    	    JSONObject ownerO = jo.optJSONObject("owner");
    	    value = ownerO.getString("realname");
    	    if (value != null) record.createPart(idManager.getId(configuration.PartStructureIDs[configuration.CREATOR_INDEX]),value);
    	    
    	
    	    JSONObject tags = jo.optJSONObject("tags");
//    	    System.out.println("T:" + tags.toString());
    	    if (tags != null)
    	    {
    	    
    	    //int length = tags.length();
 //   	    JSONArray tagsA = tags.names();
    	    //System.out.println(tagsA.toString());
    	    JSONArray tagsA2 = tags.optJSONArray("tag");
    	    for (int i=0;i<tagsA2.length();i++)
    	    {   
    	    	JSONObject o = tagsA2.optJSONObject(i);
        	    
        	    if (o == null)
        	    	continue;
    	    	String s = o.optString("raw");
        	    
    	    	if (s != null)
    	    		record.createPart(idManager.getId(configuration.PartStructureIDs[configuration.KEYWORDS_INDEX]),s);
    	    }
    	    }
    	    
    	    
    	    JSONObject urls = jo.optJSONObject("urls");
    	    if (urls != null)
    	    {
    	    
    	    //int length = tags.length();
 //   	    JSONArray tagsA = tags.names();
    	    //System.out.println(tagsA.toString());
    	    JSONArray tagsA2 = urls.optJSONArray("url");
    	    for (int i=0;i<tagsA2.length();i++)
    	    {   
    	    	JSONObject o = tagsA2.optJSONObject(i);
        	    
        	    if (o == null)
        	    	continue;
    	    	String s = o.optString("_content");
        	    
    	    	if (s != null)
    	    		record.createPart(idManager.getId(configuration.PartStructureIDs[configuration.URL_INDEX]),s);
    	    }
    	    }

  }
    		//thumbnail http:// farm[farm].static.flickr.com/[server]/[primary]_[secret]_s.jpg
    		//full image http://farm2.static.flickr.com/1150/722073942_c2bf23686e.jpg
    	    String thumb = "http://farm" + farm + ".static.flickr.com/" + server +"/" + id + "_" + secret +"_s.jpg";
    	    String med = "http://farm" + farm + ".static.flickr.com/" + server +"/" + id + "_" + secret +".jpg";
    	    String big = "http://farm" + farm + ".static.flickr.com/" + server +"/" + id + "_" + secret +".jpg";
    	    record.createPart(idManager.getId(configuration.PartStructureIDs[configuration.THUMBNAIL_URL_INDEX]),thumb);
    	    record.createPart(idManager.getId(configuration.PartStructureIDs[configuration.MEDIUM_IMAGE_URL_INDEX]),med);
    	    record.createPart(idManager.getId(configuration.PartStructureIDs[configuration.LARGE_IMAGE_URL_INDEX]),big);
    	  
    	  

/*    		if (type != null) record.createPart(idManager.getId(configuration.PartStructureIDs[configuration.TYPE_INDEX]),type);
    		if (description != null) {
    				record.createPart(idManager.getId(configuration.PartStructureIDs[configuration.DESCRIPTION_INDEX]),description);
    			}
    		
    		//if (title != null) {    		
    	//			record.createPart(idManager.getId(configuration.PartStructureIDs[configuration.TITLE_INDEX]),title);    			
    	//	}

    		if (URL != null) record.createPart(idManager.getId(configuration.PartStructureIDs[configuration.URL_INDEX]),URL);*/
    		
    	}
    	catch (Throwable t) {
    		Utilities.log(t.getMessage());
    		t.printStackTrace();
    	}
    }
    
    public String getDisplayName()
    throws org.osid.repository.RepositoryException
    {
        return _displayName;
    }

    public void updateDisplayName(String displayName)
    throws org.osid.repository.RepositoryException
    {
        if (displayName == null)
        {
            throw new org.osid.repository.RepositoryException(org.osid.shared.SharedException.NULL_ARGUMENT);
        }
        _displayName = displayName;
    }

    public String getDescription()
    throws org.osid.repository.RepositoryException
    {
        return _description;
    }

    public void updateDescription(String description)
    throws org.osid.repository.RepositoryException
    {
        if (description == null)
        {
            throw new org.osid.repository.RepositoryException(org.osid.shared.SharedException.NULL_ARGUMENT);
        }
        _description = description;
    }

    public org.osid.shared.Id getId()
    throws org.osid.repository.RepositoryException
    {
        return _id;
    }

    public org.osid.shared.Id getRepository()
    throws org.osid.repository.RepositoryException
    {
        return _repositoryId;
    }

    public java.io.Serializable getContent()
    throws org.osid.repository.RepositoryException
    {
		return null;
    }

    public void updateContent(java.io.Serializable content)
    throws org.osid.repository.RepositoryException
    {
        throw new org.osid.repository.RepositoryException(org.osid.OsidException.UNIMPLEMENTED);
    }

    public void addAsset(org.osid.shared.Id assetId)
    throws org.osid.repository.RepositoryException
    {
        throw new org.osid.repository.RepositoryException(org.osid.OsidException.UNIMPLEMENTED);
    }

    public void removeAsset(org.osid.shared.Id assetId
                          , boolean includeChildren)
    throws org.osid.repository.RepositoryException
    {
        throw new org.osid.repository.RepositoryException(org.osid.OsidException.UNIMPLEMENTED);
    }

    public org.osid.repository.AssetIterator getAssets()
    throws org.osid.repository.RepositoryException
    {
        return new AssetIterator(new java.util.Vector());
    }

    public org.osid.repository.AssetIterator getAssetsByType(org.osid.shared.Type assetType)
    throws org.osid.repository.RepositoryException
    {
        if (assetType == null)
        {
            throw new org.osid.repository.RepositoryException(org.osid.shared.SharedException.NULL_ARGUMENT);
        }
        return new AssetIterator(new java.util.Vector());
    }

    public org.osid.repository.Record createRecord(org.osid.shared.Id recordStructureId)
    throws org.osid.repository.RepositoryException
    {
        if (recordStructureId == null)
        {
            throw new org.osid.repository.RepositoryException(org.osid.shared.SharedException.NULL_ARGUMENT);
        }
        try
        {
            org.osid.repository.Record record = new Record();
            this.recordVector.addElement(record);
            return record;
        }
        catch (Throwable t)
        {
            Utilities.log(t.getMessage());
            throw new org.osid.repository.RepositoryException(org.osid.OsidException.OPERATION_FAILED);
        }
    }

    public void inheritRecordStructure(org.osid.shared.Id assetId
                                     , org.osid.shared.Id recordStructureId)
    throws org.osid.repository.RepositoryException
    {
        throw new org.osid.repository.RepositoryException(org.osid.OsidException.UNIMPLEMENTED);
    }

    public void copyRecordStructure(org.osid.shared.Id assetId
                                  , org.osid.shared.Id recordStructureId)
    throws org.osid.repository.RepositoryException
    {
        throw new org.osid.repository.RepositoryException(org.osid.OsidException.UNIMPLEMENTED);
    }

    public void deleteRecord(org.osid.shared.Id recordId)
    throws org.osid.repository.RepositoryException
    {
        if (recordId == null)
        {
            throw new org.osid.repository.RepositoryException(org.osid.shared.SharedException.NULL_ARGUMENT);
        }
        try
        {
            for (int i=0, size = this.recordVector.size(); i < size; i++)
            {
                org.osid.repository.Record record = (org.osid.repository.Record)this.recordVector.elementAt(i);
                if (record.getId().isEqual(recordId))
                {
                    this.recordVector.removeElementAt(i);
                    return;
                }
            }
            throw new org.osid.repository.RepositoryException(org.osid.shared.SharedException.UNKNOWN_ID);
        }
        catch (Throwable t)
        {
            Utilities.log(t.getMessage());
            throw new org.osid.repository.RepositoryException(org.osid.OsidException.OPERATION_FAILED);
        }
    }

    public org.osid.repository.RecordIterator getRecords()
    throws org.osid.repository.RepositoryException
    {
        return new RecordIterator(this.recordVector);
    }

    public org.osid.repository.RecordIterator getRecordsByRecordStructure(org.osid.shared.Id recordStructureId)
    throws org.osid.repository.RepositoryException
    {
        if (recordStructureId == null)
        {
            throw new org.osid.repository.RepositoryException(org.osid.shared.SharedException.NULL_ARGUMENT);
        }
        try
        {
            return new RecordIterator(this.recordVector);
        }
        catch (Throwable t)
        {
            Utilities.log(t.getMessage());
            throw new org.osid.repository.RepositoryException(org.osid.OsidException.OPERATION_FAILED);
        }
    }

    public org.osid.shared.Type getAssetType()
    throws org.osid.repository.RepositoryException
    {
        return this.assetType;
    }

    public org.osid.repository.RecordStructureIterator getRecordStructures()
    throws org.osid.repository.RepositoryException
    {
        java.util.Vector results = new java.util.Vector();
        results.addElement(new RecordStructure());
        return new RecordStructureIterator(results);
    }

    public org.osid.repository.RecordStructure getContentRecordStructure()
    throws org.osid.repository.RepositoryException
    {
        return new RecordStructure();
    }

    public org.osid.repository.Record getRecord(org.osid.shared.Id recordId)
    throws org.osid.repository.RepositoryException
    {
        if (recordId == null)
        {
            throw new org.osid.repository.RepositoryException(org.osid.shared.SharedException.NULL_ARGUMENT);
        }
        try
        {
            for (int i=0, size = this.recordVector.size(); i < size; i++)
            {
                org.osid.repository.Record record = (org.osid.repository.Record)this.recordVector.elementAt(i);
                if (record.getId().isEqual(recordId))
                {
                    return record;
                }
            }
            throw new org.osid.repository.RepositoryException(org.osid.shared.SharedException.UNKNOWN_ID);
        }
        catch (Throwable t)
        {
            Utilities.log(t.getMessage());
            throw new org.osid.repository.RepositoryException(org.osid.OsidException.OPERATION_FAILED);
        }
    }

    public org.osid.repository.Part getPart(org.osid.shared.Id partId)
    throws org.osid.repository.RepositoryException
    {
        if (partId == null)
        {
            throw new org.osid.repository.RepositoryException(org.osid.shared.SharedException.NULL_ARGUMENT);
        }
        try
        {
            for (int i=0, size = this.recordVector.size(); i < size; i++)
            {
                org.osid.repository.Record record = (org.osid.repository.Record)this.recordVector.elementAt(i);
                org.osid.repository.PartIterator partIterator = record.getParts();
                while (partIterator.hasNextPart())
                {
                    org.osid.repository.Part part = partIterator.nextPart();
                    if (part.getId().isEqual(partId))
                    {
                        return part;
                    }
                }
            }
            throw new org.osid.repository.RepositoryException(org.osid.shared.SharedException.UNKNOWN_ID);
        }
        catch (Throwable t)
        {
            Utilities.log(t.getMessage());
            throw new org.osid.repository.RepositoryException(org.osid.OsidException.OPERATION_FAILED);
        }
    }

    public java.io.Serializable getPartValue(org.osid.shared.Id partId)
    throws org.osid.repository.RepositoryException
    {
        org.osid.repository.Part part = getPart(partId);
        return part.getValue();
    }

    public org.osid.repository.PartIterator getPartByPart(org.osid.shared.Id partStructureId)
    throws org.osid.repository.RepositoryException
    {
        if (partStructureId == null)
        {
            throw new org.osid.repository.RepositoryException(org.osid.shared.SharedException.NULL_ARGUMENT);
        }
        try
        {
            java.util.Vector results = new java.util.Vector();
            for (int i=0, size = this.recordVector.size(); i < size; i++)
            {
                org.osid.repository.Record record = (org.osid.repository.Record)this.recordVector.elementAt(i);
                org.osid.repository.PartIterator partIterator = record.getParts();
                while (partIterator.hasNextPart())
                {
                    org.osid.repository.Part part = partIterator.nextPart();
                    if (part.getPartStructure().getId().isEqual(partStructureId))
                    {
                        results.addElement(part);
                    }
                }
            }
            return new PartIterator(results);
        }
        catch (Throwable t)
        {
            Utilities.log(t.getMessage());
            throw new org.osid.repository.RepositoryException(org.osid.OsidException.OPERATION_FAILED);
        }
    }

    public org.osid.shared.ObjectIterator getPartValueByPart(org.osid.shared.Id partStructureId)
    throws org.osid.repository.RepositoryException
    {
        java.util.Vector results = new java.util.Vector();
        org.osid.repository.PartIterator partIterator = getPartByPart(partStructureId);
        while (partIterator.hasNextPart())
        {
            results.addElement(partIterator.nextPart().getValue());
        }
        try
        {
            return new ObjectIterator(results);
        }
        catch (Throwable t)
        {
            Utilities.log(t.getMessage());
            throw new org.osid.repository.RepositoryException(org.osid.OsidException.OPERATION_FAILED);
        }
    }

    public long getEffectiveDate()
    throws org.osid.repository.RepositoryException
    {
        throw new org.osid.repository.RepositoryException(org.osid.OsidException.UNIMPLEMENTED);
    }

    public void updateEffectiveDate(long effectiveDate)
    throws org.osid.repository.RepositoryException
    {
        throw new org.osid.repository.RepositoryException(org.osid.OsidException.UNIMPLEMENTED);
    }

    public long getExpirationDate()
    throws org.osid.repository.RepositoryException
    {
        throw new org.osid.repository.RepositoryException(org.osid.OsidException.UNIMPLEMENTED);
    }

    public void updateExpirationDate(long expirationDate)
    throws org.osid.repository.RepositoryException
    {
        throw new org.osid.repository.RepositoryException(org.osid.OsidException.UNIMPLEMENTED);
    }

    public org.osid.shared.ObjectIterator getPartValuesByPartStructure(org.osid.shared.Id partStructureId)
    throws org.osid.repository.RepositoryException
    {
        if (partStructureId == null)
        {
            throw new org.osid.repository.RepositoryException(org.osid.shared.SharedException.NULL_ARGUMENT);
        }
        try
        {
            java.util.Vector results = new java.util.Vector();
            org.osid.repository.PartIterator partIterator = getPartsByPartStructure(partStructureId);
            while (partIterator.hasNextPart())
            {
                org.osid.repository.Part part = partIterator.nextPart();
                results.addElement(part.getValue());
            }
            return new ObjectIterator(results);
        }
        catch (Throwable t)
        {
            Utilities.log(t.getMessage());
            throw new org.osid.repository.RepositoryException(org.osid.OsidException.OPERATION_FAILED);
        }
    }

    public org.osid.repository.PartIterator getPartsByPartStructure(org.osid.shared.Id partStructureId)
    throws org.osid.repository.RepositoryException
    {
        if (partStructureId == null)
        {
            throw new org.osid.repository.RepositoryException(org.osid.shared.SharedException.NULL_ARGUMENT);
        }
        try
        {
            java.util.Vector results = new java.util.Vector();
            org.osid.repository.RecordIterator recordIterator = getRecords();
            while (recordIterator.hasNextRecord())
            {
                org.osid.repository.Record record = recordIterator.nextRecord();
                org.osid.repository.PartIterator partIterator = record.getParts();
                while (partIterator.hasNextPart())
                {
                    org.osid.repository.Part part = partIterator.nextPart();
                    if (part.getPartStructure().getId().isEqual(partStructureId))
                    {
                        results.addElement(part);
                    }
                }
            }
            return new PartIterator(results);
        }
        catch (Throwable t)
        {
            Utilities.log(t.getMessage());
            throw new org.osid.repository.RepositoryException(org.osid.OsidException.OPERATION_FAILED);
        }
    }

    public org.osid.repository.RecordIterator getRecordsByRecordStructureType(org.osid.shared.Type recordStructureType)
    throws org.osid.repository.RepositoryException
    {
        if (recordStructureType == null)
        {
            throw new org.osid.repository.RepositoryException(org.osid.shared.SharedException.NULL_ARGUMENT);
        }

        if (!recordStructureType.isEqual(this.recordStructureType))
        {
            throw new org.osid.repository.RepositoryException(org.osid.shared.SharedException.UNKNOWN_TYPE);
        }

        java.util.Vector results = new java.util.Vector();
        for (int i=0, size = this.recordVector.size(); i < size; i++)
        {
            org.osid.repository.Record r = (org.osid.repository.Record)this.recordVector.elementAt(i);
            if (r.getRecordStructure().getType().isEqual(recordStructureType))
            {
                results.addElement(r);
            }
        }
        return new RecordIterator(results);
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
