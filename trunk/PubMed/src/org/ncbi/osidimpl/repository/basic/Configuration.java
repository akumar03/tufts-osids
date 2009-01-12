/**
 * Modifications by Verbena Consulting include Asset constructor.
 */

package org.ncbi.osidimpl.repository.basic;


import gov.nih.nlm.ncbi.www.soap.eutils.EUtilsServiceLocator;
import gov.nih.nlm.ncbi.www.soap.eutils.EUtilsServiceSoap;
import gov.nih.nlm.ncbi.www.soap.eutils.esearch.ESearchRequest;
import gov.nih.nlm.ncbi.www.soap.eutils.esearch.ESearchResult;
import gov.nih.nlm.ncbi.www.soap.eutils.esummary.ESummaryRequest;
import gov.nih.nlm.ncbi.www.soap.eutils.esummary.ESummaryResult;
public class Configuration {
    private static Configuration _configuration = new Configuration();
    
    // ID Manager
    private org.osid.id.IdManager _idManager = null;
    private static final String ID_IMPLEMENTATION = "comet.osidimpl.id.no_persist";
    
    // Used in loading managers
    private org.osid.OsidContext _osidContext = new org.osid.OsidContext();
    private java.util.Properties _properties = new java.util.Properties();
    private org.osid.repository.RepositoryManager _repositoryManager = null;
    
    // Logging Manager
    private static final String LOG_FILENAME = "NCBI OSID";
    private static final String LOGGING_IMPLEMENTATION = "comet.osidimpl.logging.plain";
    private static final String LOGGING_TYPE_AUTHORITY = "mit.edu";
    private static final String LOGGING_TYPE_DOMAIN = "logging";
    private static final String LOGGING_TYPE_FORMAT = "plain";
    private static final String LOGGING_TYPE_PRIORITY = "info";
    
    // Assuming 1 Repository
    private static final String REPOSITORY_ID_STRING = "ncbi:20080317141300";
    private org.osid.shared.Id _repositoryId = null;
    private static final String _repositoryDisplayName = "PubMed(NCBI)";
    private static final String _repositoryDescription = "NCBI Pubmed Search";
    private java.util.Vector _repositoryVector = null;
//    private static final String _pubmedUrl = "http://www.pubmedcentral.nih.gov/articlerender.fcgi?artid=";
    private static final String _pubmedUrl = "http://www.ncbi.nlm.nih.gov/pubmed/";
    private static final String MAX_HITS = "20";
    
    // Types for 1 Repository
    private static final org.osid.shared.Type _ncbiRepositoryType = new Type("mit.edu","repository","referatory");
    private static final org.osid.shared.Type _ncbiAssetType = new Type("ncbi.org","asset","reference");
    private static final org.osid.shared.Type _keywordSearchType = new Type("mit.edu","search","keyword");
    private java.util.Vector _repositoryTypeVector = null;
    private java.util.Vector _assetTypeVector = null;
    private java.util.Vector _searchTypeVector = null;
    
    private static boolean _debug = false;
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
    protected static final int DATE_SUBMITTED_INDEX = 15;
    protected static final int THUMBNAIL_URL_INDEX = 16;
    protected static final int IS_PART_OF_INDEX = 17;
    protected static final int SCORE_INDEX = 18;
    protected static final int URL_INDEX = 19;
    protected static final int COLLECTION_NAME_INDEX = 20;
    protected static final int COLLECTION_THUMBNAIL_URL_INDEX = 21;
    
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
        "Date Submitted",
        "ThumbnailURL",
        "Is Part Of",
        "Score",
        "URL",
        "Collection Name",
        "Collection Thumbnail URL"
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
        "Date the resource was submitted.",
        "Thumbnail URL",
        "URL for what the resource is part of",
        "Relevance score",
        "URL",
        "Name of the collection to which resource belongs",
        "Collection thumbnail URL"
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
        "DateSubmitted.PartStructure.ID",
        "ThumbnailURL.PartStructure.ID",
        "IsPartOf.PartStructure.ID",
        "Score.PartStructure.ID",
        "URL.PartStructure.ID",
        "CollectionName.PartStructure.ID",
        "CollectionThumbnailURL.PartStructure.ID",
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
        "Date Submitted",
        "ThumbnailURL",
        "Is Part Of",
        "Score",
        "URL",
        "Collection Name",
        "Collection Thumbnail URL"
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
        "DateSubmitted.Part.ID",
        "ThumbnailURL.Part.ID",
        "IsPartOf.Part.ID",
        "Score.Part.ID",
        "URL.Part.ID",
        "CollectionName.Part.ID",
        "CollectionThumbnailURL.Part.ID",
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
        "partStructure/dateSubmitted@mit.edu",
        "partStructure/thumbnailURL@mit.edu",
        "partStructure/isPartOf@calstate.edu",
        "partStructure/releavanceScore@calstate.edu",
        "partStructure/URL@mit.edu",
        "partStructure/collectionName@calstate.edu",
        "partStructure/collectionThumbnailURL@calstate.edu"
    };
    
    
    
    protected static final String RECORD_ID = "nsdl.record.id";
    protected static final String RECORD_STRUCTURE_DISPLAY_NAME = "NSDL Record Structure";
    protected static final String RECORD_STRUCTURE_DESCRIPTION = "NSDL Record Structure";
    protected static final String RECORD_STRUCTURE_ID = "nsdl.recordstructure.id";
    protected static final String RECORD_STRUCTURE_FORMAT = "String";
    protected static final String RECORD_STRUCTURE_SCHEMA = "DCPlus";
    protected static final org.osid.shared.Type RECORD_STRUCTURE_TYPE = new Type("nsdl.org","recordStructure","nsdl");
    
    protected static Configuration getInstance() {
        return _configuration;
    }
    
    // Setup ID Manager and Logging Manager
    private Configuration() {
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
    public org.osid.shared.Id getRepositoryId() {
        return _repositoryId;
    }
    
    public String getRepositoryDisplayName() {
        return _repositoryDisplayName;
    }
    
    public String getRepositoryDescription() {
        return _repositoryDescription;
    }
    
    public org.osid.shared.Type getRepositoryType() {
        return _ncbiRepositoryType;
    }
    
    public org.osid.shared.TypeIterator getRepositoryTypes()
    throws org.osid.repository.RepositoryException {
        try {
            if (_repositoryTypeVector == null) {
                _repositoryTypeVector = new java.util.Vector();
                _repositoryTypeVector.addElement(_ncbiRepositoryType);
            }
            return new TypeIterator(_repositoryTypeVector);
        } catch (Throwable t) {
            throw new org.osid.repository.RepositoryException(org.osid.OsidException.OPERATION_FAILED);
        }
    }
    
    public org.osid.shared.TypeIterator getAssetTypes()
    throws org.osid.repository.RepositoryException {
        try {
            if (_assetTypeVector == null) {
                _assetTypeVector = new java.util.Vector();
                _assetTypeVector.addElement(_ncbiAssetType);
            }
            return new TypeIterator(_assetTypeVector);
        } catch (Throwable t) {
            throw new org.osid.repository.RepositoryException(org.osid.OsidException.OPERATION_FAILED);
        }
    }
    
    public org.osid.shared.TypeIterator getSearchTypes()
    throws org.osid.repository.RepositoryException {
        try {
            if (_searchTypeVector == null) {
                _searchTypeVector = new java.util.Vector();
                _searchTypeVector.addElement(_keywordSearchType);
            }
            return new TypeIterator(_searchTypeVector);
        } catch (Throwable t) {
            throw new org.osid.repository.RepositoryException(org.osid.OsidException.OPERATION_FAILED);
        }
    }
    
    public java.util.Vector getRepositories() {
        if (_repositoryVector == null) {
            _repositoryVector = new java.util.Vector();
            _repositoryVector.addElement(new Repository());
        }
        return _repositoryVector;
    }
    
    public org.osid.id.IdManager getIdManager() {
        return _idManager;
    }
    
    public boolean debug() {
        return _debug;
    }
    
    public void setConfiguration(java.util.Properties properties) {
        Object o = properties.getProperty("NCBILogPerformance");
        if (o != null) {
            String s = (String)o;
            _logPerformance = (s.trim().toLowerCase().equals("true"));
        }
        o = properties.getProperty("ncbiDebug");
        if (o != null) {
            String s = (String)o;
            _debug = (s.trim().toLowerCase().equals("true"));
        }
    }
    
    public org.osid.repository.Asset singleAssetSearch(org.osid.shared.Id assetId) {
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
    throws org.osid.repository.RepositoryException {
        long startTime = 0;
        
        if (_logPerformance) {
            Utilities.log("New search " + java.util.Calendar.getInstance().getTime().toString());
            startTime = java.util.Calendar.getInstance().getTimeInMillis();
        }
        if (!(searchCriteria instanceof String)) {
            Utilities.log("invalid criteria");
            throw new org.osid.repository.RepositoryException(org.osid.shared.SharedException.UNKNOWN_TYPE);
        }
        
        
        java.util.Vector assetVector = new java.util.Vector();
        
        try {
            String criteria = (String)searchCriteria;
            //
            EUtilsServiceLocator service = new EUtilsServiceLocator();
            EUtilsServiceSoap utils = service.geteUtilsServiceSoap();
            ESearchRequest parameters = new ESearchRequest();
            parameters.setDb("pubmed");
            parameters.setTerm(criteria);
            parameters.setRetMax(MAX_HITS);
            ESearchResult res = utils.run_eSearch(parameters);
            System.out.println("Searching for: "+criteria);
            for(int i=0; i<res.getIdList().getId().length; i++) {
                
                String description[] = {"Description"};
                String title[] = {"No Title"};
                ESummaryRequest summaryParameters = new ESummaryRequest();
                summaryParameters.setDb("pubmed");
                summaryParameters.setId(res.getIdList().getId()[i]);
                ESummaryResult summaryResults = utils.run_eSummary(summaryParameters);
                for(int j =0; j<summaryResults.getDocSum()[0].getItem().length;j++) {
                    if(summaryResults.getDocSum()[0].getItem()[j] != null &&
                            summaryResults.getDocSum()[0].getItem()[j].get_any() != null) {
                        if(summaryResults.getDocSum()[0].getItem()[j].getName().equalsIgnoreCase("title"))
                            title[0] =summaryResults.getDocSum()[0].getItem()[j].get_any()[0].getValue();
                    }
                }
                String url = _pubmedUrl+res.getIdList().getId()[i];
                Asset asset = new Asset(res.getIdList().getId()[i],null,null,null,null,null,null,null,null,null,null,null,title,null,null,title,null,null,null,null,null,null,url);
                System.out.print(res.getIdList().getId()[i] + " ");
                assetVector.add(asset);
            }
            
            
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new AssetIterator(assetVector);
    }
  /*
 * Copyright 2003-2009 Tufts University  Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 * 
 * http://www.osedu.org/licenses/ECL-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
}