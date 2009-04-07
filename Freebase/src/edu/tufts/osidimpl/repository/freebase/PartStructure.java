package edu.tufts.osidimpl.repository.freebase;

public class PartStructure
implements org.osid.repository.PartStructure
{
    private String _displayName = null;
    private String _description = null;
    private org.osid.shared.Id _id = null;
    private org.osid.shared.Type _type = null;
	private org.osid.repository.RecordStructure _recordStructure = null;

    protected PartStructure(String displayName,
							String description,
							String partStructureIdString,
							String typeString,
							String recordStructureIdString)
    throws org.osid.repository.RepositoryException
    {
		_displayName = displayName;
		_description = description;

		try {
			_id = Configuration.getInstance().getIdManager().getId(partStructureIdString);
			_type = Utilities.stringToType(typeString);
		} catch (Throwable t) {
			throw new org.osid.repository.RepositoryException(org.osid.OsidException.OPERATION_FAILED);
		}

		_recordStructure = new RecordStructure();
    }

    public String getDisplayName()
    throws org.osid.repository.RepositoryException
    {
        return _displayName;
    }

    public String getDescription()
    throws org.osid.repository.RepositoryException
    {
        return _description;
    }

    public org.osid.shared.Id getId()
    throws org.osid.repository.RepositoryException
    {
        return _id;
    }

    public org.osid.shared.Type getType()
    throws org.osid.repository.RepositoryException
    {
        return _type;
    }

    public org.osid.repository.PartStructureIterator getPartStructures()
    throws org.osid.repository.RepositoryException
    {
        return new PartStructureIterator(new java.util.Vector());
    }

    public org.osid.repository.RecordStructure getRecordStructure()
    throws org.osid.repository.RepositoryException
    {
        return _recordStructure;
    }

    public boolean isMandatory()
    throws org.osid.repository.RepositoryException
    {
        return false;
    }

    public boolean isPopulatedByRepository()
    throws org.osid.repository.RepositoryException
    {
        return false;
    }

    public boolean isRepeatable()
    throws org.osid.repository.RepositoryException
    {
        return false;
    }

    public boolean validatePart(org.osid.repository.Part part)
    throws org.osid.repository.RepositoryException
    {
        return true;
    }

    public void updateDisplayName(String displayName)
    throws org.osid.repository.RepositoryException
    {
        throw new org.osid.repository.RepositoryException
            (org.osid.OsidException.UNIMPLEMENTED);
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
