package edu.tufts.osidimpl.repository.nytimes;

import org.json.*;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
public class Configuration
{
	private static Configuration _configuration = new Configuration();

	// ID Manager
	private org.osid.id.IdManager _idManager = null;
	private static final String ID_IMPLEMENTATION = "comet.osidimpl.id.no_persist";

	// Used in loading managers
	private org.osid.OsidContext _osidContext = new org.osid.OsidContext();
	private java.util.Properties _properties = new java.util.Properties();
	private org.osid.repository.RepositoryManager _repositoryManager = null;
	private String _myConfigurationProperty1 = null;

	// Logging Manager
	private static final String LOG_FILENAME = "MyLog";
	private static final String LOGGING_IMPLEMENTATION = "comet.osidimpl.logging.plain";
	private static final String LOGGING_TYPE_AUTHORITY = "mit.edu";
	private static final String LOGGING_TYPE_DOMAIN = "logging";
	private static final String LOGGING_TYPE_FORMAT = "plain";
	private static final String LOGGING_TYPE_PRIORITY = "info";

	// Assuming 1 Repository
	private static final String REPOSITORY_ID_STRING = "a9cb98b3be576fcdc75e6a28a6530607557925337";
	private org.osid.shared.Id _repositoryId = null;
	private static final String _repositoryDisplayName = "NY Times Article Search";
	private static final String _repositoryDescription = "With the Article Search API, you can search New York Times articles from 1981 to today, retrieving headlines, abstracts, lead paragraphs and links to associated multimedia.";
	private static final String _queryPrefix = "http://api.nytimes.com/svc/search/v1/article?&fields=title,body,url,date,multimedia,byline,classifiers_facet,multimedia,date,small_image_url&begin_date=19810101&end_date="+getCurrentDate()+"&offset=REPLACEME2&rank=REPLACEME1&api-key=a9cb98b3be576fcdc75e6a28a6530607:5:57925337&query=";
	private java.util.Vector _repositoryVector = null;
	private static String _rank = "newest";
	// Types for 1 Repository
	private static final org.osid.shared.Type _bootcampRepositoryType = new Type("tufts.edu","repository","nytimes");
	private static final org.osid.shared.Type _bootcampAssetType = new Type("tufts.edu","asset","nytimes");
	private static final org.osid.shared.Type _keywordSearchType = new Type("mit.edu","search","keyword");
	private static final org.osid.shared.Type _titleSearchType = new Type("mit.edu","search","title");
	private java.util.Vector _repositoryTypeVector = null;
	private java.util.Vector _assetTypeVector = null;
	private java.util.Vector _searchTypeVector = null;

	private boolean _debug = false;

	private String _DomainName;

	private String _DisplayName;
	private static boolean _logPerformance = false;

	protected static final int CONTRIBUTOR_INDEX = 0;
	protected static final int COVERAGE_INDEX = 1;
	protected static final int CREATOR_INDEX = 2;
	protected static final int DATE_INDEX = 3;
	protected static final int DESCRIPTION_INDEX = 4;
	protected static final int FORMAT_INDEX = 5;
	protected static final int IDENTIFIER_INDEX = 6;
	protected static final int LANGUAGE_INDEX = 7;
	protected static final int PUBLISHER_INDEX = 8;
	protected static final int RELATION_INDEX = 9;
	protected static final int RIGHTS_INDEX = 10;
	protected static final int SOURCE_INDEX = 11;
	protected static final int SUBJECT_INDEX = 12;
	protected static final int TITLE_INDEX = 13;
	protected static final int TYPE_INDEX = 14;
	protected static final int KEYWORDS_INDEX = 15;
	protected static final int THUMBNAIL_URL_INDEX = 16;
	protected static final int MEDIUM_IMAGE_URL_INDEX = 17;
	protected static final int LARGE_IMAGE_URL_INDEX = 18;
	protected static final int URL_INDEX = 19;

	protected static final String[] PartStructureDisplayNames = {
		"Contributor",
		"Coverage",
		"Creator",
		"Date",
		"Description",
		"Format",
		"Identifier",
		"Language",
		"Publisher",
		"Relation",
		"Rights",
		"Source",
		"Subject",
		"Title",
		"Type",
		"Keywords",
		"ThumbnailURL",
		"MediumImageURL",
		"LargeImageURL",
		"URL"
	};

	protected static final String[] PartStructureDescriptions = {
		"An entity responsible for making contributions to the resource.",
		"The spatial or temporal topic of the resource, the spatial applicability of the resource, or the jurisdiction under which the resource is relevant.",
		"An entity primarily responsible for making the resource.",
		"A point or period of time associated with an event in the lifecycle of the resource.",
		"An account of the resource.",
		"The file format, physical medium, or dimensions of the resource.",
		"An unambiguous reference to the resource within a given context.",
		"A language of the resource.",
		"An entity responsible for making the resource available.",
		"A related resource.",
		"Information about rights held in and over the resource.",
		"The resource from which the described resource is derived.",
		"The topic of the resource.",
		"A name given to the resource.",
		"The nature or genre of the resource.",
		"Keywords associated with the resource.",
		"Thumbnail URL",
		"Medium Image URL",
		"Large Image URL",
		"URL"
	};

	protected static final String[] PartStructureIDs = {
		"Contributor.PartStructure.ID",
		"Coverage.PartStructure.ID",
		"Creator.PartStructure.ID",
		"Date.PartStructure.ID",
		"Description.PartStructure.ID",
		"Format.PartStructure.ID",
		"Identifier.PartStructure.ID",
		"Language.PartStructure.ID",
		"Publisher.PartStructure.ID",
		"Relation.PartStructure.ID",
		"Rights.PartStructure.ID",
		"Source.PartStructure.ID",
		"Subject.PartStructure.ID",
		"Title.PartStructure.ID",
		"Type.PartStructure.ID",
		"Keywords.PartStructure.ID",
		"ThumbnailURL.PartStructure.ID",
		"MediumImageURL.PartStructure.ID",
		"LargeImageURL.PartStructure.ID",
		"URL.PartStructure.ID"
	};

	protected static final String[] PartDisplayNames = {
		"Contributor",
		"Coverage",
		"Creator",
		"Date",
		"Description",
		"Format",
		"Identifier",
		"Language",
		"Publisher",
		"Relation",
		"Rights",
		"Source",
		"Subject",
		"Title",
		"Type",
		"Keywords",
		"ThumbnailURL",
		"MediumImageURL",
		"LargeImageURL",
		"URL"
	};

	protected static final String[] PartIDs = {
		"Contributor.Part.ID",
		"Coverage.Part.ID",
		"Creator.Part.ID",
		"Date.Part.ID",
		"Description.Part.ID",
		"Format.Part.ID",
		"Identifier.Part.ID",
		"Language.Part.ID",
		"Publisher.Part.ID",
		"Relation.Part.ID",
		"Rights.Part.ID",
		"Source.Part.ID",
		"Subject.Part.ID",
		"Title.Part.ID",
		"Type.Part.ID",
		"Keywords.Part.ID",
		"ThumbnailURL.Part.ID",
		"MediumImageURL.Part.ID",
		"LargeImageURL.Part.ID",
		"URL.Part.ID"
	};

	protected static final String[] PartStructureTypes = {
		"partStructure/contributor@mit.edu",
		"partStructure/coverage@mit.edu",
		"partStructure/creator@mit.edu",
		"partStructure/date@mit.edu",
		"partStructure/description@mit.edu",
		"partStructure/format@mit.edu",
		"partStructure/identifier@mit.edu",
		"partStructure/language@mit.edu",
		"partStructure/publisher@mit.edu",
		"partStructure/relation@mit.edu",
		"partStructure/rights@mit.edu",
		"partStructure/source@mit.edu",
		"partStructure/subject@mit.edu",
		"partStructure/title@mit.edu",
		"partStructure/type@mit.edu",
		"partStructure/keywords@mit.edu",
		"partStructure/thumbnailURL@mit.edu",
		"partStructure/mediumImageURL@mit.edu",
		"partStructure/largeImageURL@mit.edu",
		"partStructure/URL@mit.edu",
	};

	protected static final String URL_PREFIX = "http://www.tufts.edu";

	protected static final String RECORD_ID = "nytimes.record.id";
	protected static final String RECORD_STRUCTURE_DISPLAY_NAME = "NY Times Record Structure";
	protected static final String RECORD_STRUCTURE_DESCRIPTION = "NY Times Record Structure";
	protected static final String RECORD_STRUCTURE_ID = "nytimes.recordstructure.id";
	protected static final String RECORD_STRUCTURE_FORMAT = "String";
	protected static final String RECORD_STRUCTURE_SCHEMA = "DCPlusImage";
	protected static final org.osid.shared.Type RECORD_STRUCTURE_TYPE = new Type("tufts.edu","recordStructure","nytimes");

	protected static Configuration getInstance()
	{
		return _configuration;
	}

	// Setup ID Manager and Logging Manager
	private Configuration()
	{
		try {
			org.osid.logging.LoggingManager loggingManager = (org.osid.logging.LoggingManager)org.osid.OsidLoader.getManager("org.osid.logging.LoggingManager",
																															 LOGGING_IMPLEMENTATION,
																															 _osidContext,
																															 _properties);
			org.osid.logging.WritableLog log = null;
			try {
				log = loggingManager.getLogForWriting(LOG_FILENAME);
			} catch (org.osid.logging.LoggingException lex) {
				log = loggingManager.createLog(LOG_FILENAME);
			}
			log.assignFormatType(new Type(LOGGING_TYPE_AUTHORITY,LOGGING_TYPE_DOMAIN,LOGGING_TYPE_FORMAT));
			log.assignPriorityType(new Type(LOGGING_TYPE_AUTHORITY,LOGGING_TYPE_DOMAIN,LOGGING_TYPE_PRIORITY));
			Utilities.setLog(log);

			_idManager = (org.osid.id.IdManager)org.osid.OsidLoader.getManager("org.osid.id.IdManager",
																			   ID_IMPLEMENTATION,
																			   _osidContext,
																			   _properties);
			_repositoryId = _idManager.getId(REPOSITORY_ID_STRING);
		} catch (Throwable t) {
			Utilities.log(t);
		}
	}

	// Some attributes of a single Repository
	public org.osid.shared.Id getRepositoryId()
	{
		return _repositoryId;
	}

	
	public String getRepositoryDisplayName()
	{
		return (_DisplayName != null ? _DisplayName : _repositoryDisplayName);
	}

	public String getRepositoryDescription()
	{
		return _repositoryDescription;
	}
	
	public String getRank()
	{
		return _rank;
	}

	public void setRank(String rank)
	{
		_rank = rank;
	}
	
	public org.osid.shared.Type getRepositoryType()
	{
		return _bootcampRepositoryType;
	}

	public org.osid.shared.TypeIterator getRepositoryTypes()
		throws org.osid.repository.RepositoryException
	{
		try {
			if (_repositoryTypeVector == null) {
				_repositoryTypeVector = new java.util.Vector();
				_repositoryTypeVector.addElement(_bootcampRepositoryType);
			}
			return new TypeIterator(_repositoryTypeVector);
		} catch (Throwable t) {
			throw new org.osid.repository.RepositoryException(org.osid.OsidException.OPERATION_FAILED);
		}
	}

	public org.osid.shared.TypeIterator getAssetTypes()
		throws org.osid.repository.RepositoryException
	{
		try {
			if (_assetTypeVector == null) {
				_assetTypeVector = new java.util.Vector();
				_assetTypeVector.addElement(_bootcampAssetType);
			}
			return new TypeIterator(_assetTypeVector);
		} catch (Throwable t) {
			throw new org.osid.repository.RepositoryException(org.osid.OsidException.OPERATION_FAILED);
		}
	}

	public org.osid.shared.TypeIterator getSearchTypes()
		throws org.osid.repository.RepositoryException
    {
		try {
			if (_searchTypeVector == null) {
				_searchTypeVector = new java.util.Vector();
				_searchTypeVector.addElement(_keywordSearchType);
				_searchTypeVector.addElement(_titleSearchType);
			}
			return new TypeIterator(_searchTypeVector);
		} catch (Throwable t) {
			throw new org.osid.repository.RepositoryException(org.osid.OsidException.OPERATION_FAILED);
		}
    }

	public java.util.Vector getRepositories()
	{
		if (_repositoryVector == null) {
			_repositoryVector = new java.util.Vector();
			_repositoryVector.addElement(new Repository());
		}
		return _repositoryVector;
	}

	public org.osid.id.IdManager getIdManager()
	{
		return _idManager;
	}

	public boolean debug()
	{
		return _debug;
	}

	public void setConfiguration(java.util.Properties properties)
	{
		Object o = properties.getProperty("NYTimesSearchConfigurationProperty");
		if (o != null) {
			_myConfigurationProperty1 = (String)o;
		}
		o = properties.getProperty("NYTimesSearchDebug");
		if (o != null) {
			String s = (String)o;
			_debug = (s.trim().toLowerCase().equals("true"));
		}
		
		o = properties.getProperty("NYTimesSearchLogPerformance");
		if (o != null) {
			String s = (String)o;
			_logPerformance = (s.trim().toLowerCase().equals("true"));
		}
		
		o = properties.getProperty("DomainName");
		if (o != null) {
			String s = (String)o;
			_DomainName = s;
		}
		
		o = properties.getProperty("DisplayName");
		if (o != null) {
			String s = (String)o;
			_DisplayName = s;
		}
		
		o = properties.getProperty("Rank");
		if (o != null) {
			String s = (String)o;
			_rank = s;
			_queryPrefix.replaceAll("REPLACEME1", _rank);
		}
	}

	public org.osid.repository.Asset singleAssetSearch(org.osid.shared.Id assetId)
	{
		try {
			String idString = assetId.getIdString();
			// make some use of idString
			//return new Asset("some title", "some description", idString);
		} catch (Throwable t) {
			Utilities.log(t);
		}
		return null;
	}

	public org.osid.repository.AssetIterator assetsSearch(java.io.Serializable searchCriteria, org.osid.shared.Type searchType)
		throws org.osid.repository.RepositoryException
	{
		long startTime=0;
		
		if (_logPerformance) {
			Utilities.log("New search " + java.util.Calendar.getInstance().getTime().toString());
			startTime = java.util.Calendar.getInstance().getTimeInMillis();
		}
		
		if ( (!searchType.isEqual(_keywordSearchType)) && (!searchType.isEqual(_titleSearchType)) ) {
			throw new org.osid.repository.RepositoryException(org.osid.shared.SharedException.UNKNOWN_TYPE);
		}
		if (!(searchCriteria instanceof String))
		{
			Utilities.log("invalid criteria");
			throw new org.osid.repository.RepositoryException(org.osid.shared.SharedException.UNKNOWN_TYPE);
		}

		java.util.Vector aggregateVector = new java.util.Vector();
		java.util.Vector assetVector = new java.util.Vector();

		try {
			String criteria = (String)searchCriteria;
			//criteria = criteria.trim().toLowerCase();
			criteria = java.net.URLEncoder.encode(criteria,"UTF-8");			
			
			
			HttpClient client = new HttpClient();
			int offset = 0;
	        for (int i = 0; i< 5; i++)
	        {
	        	
	        	String query = _queryPrefix + criteria;
	        	query = query.replaceAll("REPLACEME2", new Integer(i).toString());
	        	query = query.replaceAll("REPLACEME1", _rank);
				if (_DomainName != null && _DomainName.length() > 0)
				{
					_DomainName = java.net.URLEncoder.encode(_DomainName,"UTF-8");
					query = query + "&site=" + _DomainName;
				}
				
				if (_configuration.debug()) 
					System.out.println("Query: " + query);
				
				GetMethod method = new GetMethod(query);
	
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
	
		       // System.out.println(jsonString);
		        // Construct a JSONObject from a source JSON text string.
		        // A JSONObject is an unordered collection of name/value pairs. Its external
		        // form is a string wrapped in curly braces with colons between the names
		        // and values, and commas between the values and names.
		        JSONObject jo = new JSONObject(jsonString);
		        
		        Metadata metadata = new Metadata(jo);
				assetVector = metadata.getAssets();
				aggregateVector.addAll(assetVector);
	        }
	        
			if (_logPerformance) Utilities.log("Milliseconds to constructed assets " + (java.util.Calendar.getInstance().getTimeInMillis() - startTime));

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new AssetIterator(aggregateVector);
	}
	 
	
		  public static String getCurrentDate()
		  {
		    String DATE_FORMAT = "yyyyMMdd";
		    SimpleDateFormat sdf =
		          new SimpleDateFormat(DATE_FORMAT);
		    Calendar c1 = Calendar.getInstance(); // today

		    return sdf.format(c1.getTime());
		  
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