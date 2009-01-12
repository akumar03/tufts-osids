/*
 MERLOT Open Source License Goes Here
 */
package org.ncbi.osidimpl.repository.basic;

public class Metadata
{
	java.util.Vector _assetVector = new java.util.Vector();
	
	public java.util.Vector getAssets()
	{
		return _assetVector;
	}
	
	public Metadata(String xml)
	{
		try {
			javax.xml.parsers.DocumentBuilderFactory dbf = javax.xml.parsers.DocumentBuilderFactory.newInstance();
			javax.xml.parsers.DocumentBuilder db = dbf.newDocumentBuilder();
			org.w3c.dom.Document document = db.parse(new java.io.ByteArrayInputStream(xml.getBytes()));		
			
			// one document per asset
			org.w3c.dom.NodeList documentNodeList = document.getElementsByTagName("document");
			int numDocuments = documentNodeList.getLength();
			for (int i=0; i < numDocuments; i++) {
				
				org.w3c.dom.Element documentElement = (org.w3c.dom.Element)documentNodeList.item(i);
				
				// metadata fields, use arrays where more than one value may appear.
				// multiple values are indiciated by "compund" elements in ncbi schema
				String contributor = null;
				String coverage = null;
				String creator = null;
				String date = null;
				String format = null;
				String identifier = null;
				String language = null;
				String relation = null;
				String rights = null;
				String source = null;
				String type = null;

				String description[] = null;
				String publisher[] = null;
				String subject[] = null;
				String title[] = null;
				
				String dateSubmitted = null;
				String isPartOf = null;
				String score = null;
				
				String collectionName = null;
				String collectionThumbnailURL = null;
				String thumbnailURL = null;

				String URL = null;
				String assetId = null;
				String value = null;
				
				if ((value = getElementValue(documentElement,"dc:contributor")) != null) contributor = value;
				if ((value = getElementValue(documentElement,"dc:coverage")) != null) coverage = value;
				if ((value = getElementValue(documentElement,"dc:creator")) != null) creator = value;
				if ((value = getElementValue(documentElement,"dc:date")) != null) date = value;
				if ((value = getElementValue(documentElement,"dc:format")) != null) format = value;
				if ((value = getElementValue(documentElement,"dc:identifier")) != null) identifier = value;
				if ((value = getElementValue(documentElement,"dc:language")) != null) language = value;
				if ((value = getElementValue(documentElement,"dc:relation")) != null) relation = value;
				if ((value = getElementValue(documentElement,"dc:rights")) != null) rights = value;
				if ((value = getElementValue(documentElement,"dc:source")) != null) source = value;
				if ((value = getElementValue(documentElement,"dc:type")) != null) type = value;
				if ((value = getElementValue(documentElement,"resourceIdentifier")) != null) URL = value;

				description = getElementValues(documentElement,"dc:description");
				publisher = getElementValues(documentElement,"dc:publisher");
				subject = getElementValues(documentElement,"dc:subject");
				title = getElementValues(documentElement,"dc:title");
				if (title == null) title = getElementValues(documentElement,"compundTitle");
								
				if ((value = getElementValue(documentElement,"dct:dateSubmitted")) != null) dateSubmitted = value;
				if ((value = getElementValue(documentElement,"dct:isPartOf")) != null) isPartOf = value;
				if ((value = getElementValue(documentElement,"score")) != null) score = value;
				
				org.w3c.dom.NodeList collectionNodeList = documentElement.getElementsByTagName("collContext");
				if (collectionNodeList.getLength() > 0) {
					org.w3c.dom.Element collectionElement = (org.w3c.dom.Element)collectionNodeList.item(0);
					if ((value = getElementValue(collectionElement,"image")) != null) collectionThumbnailURL = value;
					if ((value = getElementValue(collectionElement,"altText")) != null) collectionName = value;
					if ((value = getElementValue(collectionElement,"oai-id")) != null) assetId = value;
				}
				
				// print results
				if (Configuration.getInstance().debug()) {
					System.out.println("contributor: " + contributor);
					System.out.println("coverage: " + coverage);
					System.out.println("creator: " + creator);
					System.out.println("date: " + date);
					System.out.println("format: " + format);
					System.out.println("identifier: " + identifier);
					System.out.println("language: " + language);
					System.out.println("relation: " + relation);
					System.out.println("rights: " + rights);
					System.out.println("source: " + source);
					System.out.println("type: " + type);
					if (description != null) for (int j=0; j < description.length; j++) System.out.println("description[]:" + description[j]);
					if (publisher != null) for (int j=0; j < publisher.length; j++) System.out.println("publisher[]: " + publisher[j]);
					if (subject != null) for (int j=0; j < subject.length; j++) System.out.println("subject[]: " + subject[j]);
					if (title != null) for (int j=0; j < title.length; j++) System.out.println("title[]: " + title[j]);
					System.out.println("dateSubmitted: " + dateSubmitted);
					System.out.println("isPartOf: " + isPartOf);
					System.out.println("score: " + score);
					System.out.println("collectionName: " + collectionName);
					System.out.println("collectionThumbnailURL: " + collectionThumbnailURL);
					System.out.println("thumbnailURL: " + thumbnailURL);
					System.out.println("URL: " + URL);
				}
				_assetVector.addElement(new Asset(assetId,
												  contributor,
												  coverage,
												  creator,
												  date,
												  format,
												  identifier,
												  language,
												  relation,
												  rights,
												  source,
												  type,
												  description,
												  publisher,
												  subject,
												  title,
												  dateSubmitted,
												  isPartOf,
												  score,
												  collectionName,
												  collectionThumbnailURL,
												  thumbnailURL,
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

