package org.ncbi.osidimpl.repository.basic;

public class RecordStructure
implements org.osid.repository.RecordStructure
{
	private static RecordStructure _recordStructure = new RecordStructure();
	
	public static RecordStructure getInstance()
	{
		return _recordStructure;
	}
	
    public String getDisplayName()
    throws org.osid.repository.RepositoryException
    {
        return Configuration.RECORD_STRUCTURE_DISPLAY_NAME;
    }

    public String getDescription()
    throws org.osid.repository.RepositoryException
    {
        return Configuration.RECORD_STRUCTURE_DESCRIPTION;
    }

    public String getFormat()
    throws org.osid.repository.RepositoryException
    {
        return Configuration.RECORD_STRUCTURE_FORMAT;
    }

    public org.osid.shared.Id getId()
    throws org.osid.repository.RepositoryException
    {
        try {
            return Configuration.getInstance().getIdManager().getId(Configuration.RECORD_STRUCTURE_ID);
        }
        catch (Throwable t)
        {
            Utilities.log(t.getMessage());
            throw new org.osid.repository.RepositoryException(org.osid.OsidException.OPERATION_FAILED);
        }
    }

    public org.osid.shared.Type getType()
    throws org.osid.repository.RepositoryException
    {
        return Configuration.RECORD_STRUCTURE_TYPE;
    }

    public org.osid.repository.PartStructureIterator getPartStructures()
    throws org.osid.repository.RepositoryException
    {
        java.util.Vector results = new java.util.Vector();
        try
        {
			int len = Configuration.PartStructureDisplayNames.length;
			for (int i=0; i < len; i++) {
				results.addElement(new PartStructure(Configuration.PartStructureDisplayNames[i],
													 Configuration.PartStructureDescriptions[i],
													 Configuration.PartStructureIDs[i],
													 Configuration.PartStructureTypes[i],
													 Configuration.RECORD_STRUCTURE_ID));
			}
        }
        catch (Throwable t)
        {
            throw new org.osid.repository.RepositoryException(org.osid.OsidException.OPERATION_FAILED);        
        }
        return new PartStructureIterator(results);
    }

    public String getSchema()
    throws org.osid.repository.RepositoryException
    {
        return Configuration.RECORD_STRUCTURE_SCHEMA;
    }

    public boolean isRepeatable()
    throws org.osid.repository.RepositoryException
    {
        return false;
    }

    public boolean validateRecord(org.osid.repository.Record record)
    throws org.osid.repository.RepositoryException
    {
        return true;
    }

    public void updateDisplayName(String displayName)
    throws org.osid.repository.RepositoryException
    {
        throw new org.osid.repository.RepositoryException(org.osid.OsidException.UNIMPLEMENTED);
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
