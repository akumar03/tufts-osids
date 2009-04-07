package edu.tufts.osidimpl.repository.freebase;

public class Record
implements org.osid.repository.Record
{
    private java.util.Vector _partVector = new java.util.Vector();
    private String _displayName = null;
    private org.osid.shared.Id _id = null;
	private org.osid.repository.RecordStructure _recordStructure = new RecordStructure();

    protected Record()
    {
		try {
			_id = Configuration.getInstance().getIdManager().getId(Configuration.RECORD_ID);
		} catch (Throwable t) {
			// ignore
		}
    }

    public String getDisplayName()
		throws org.osid.repository.RepositoryException
    {
        return _displayName;
    }

    public org.osid.shared.Id getId()
		throws org.osid.repository.RepositoryException
    {
        return _id;
    }

    public org.osid.repository.Part createPart(org.osid.shared.Id partStructureId
											   , java.io.Serializable value)
		throws org.osid.repository.RepositoryException
    {
        try
	{
		org.osid.repository.Part part = new Part(partStructureId,value);
		_partVector.addElement(part);
		return part;
	}
        catch (Throwable t)
	{
			t.printStackTrace();
            Utilities.log(t.getMessage());
            throw new org.osid.repository.RepositoryException(org.osid.OsidException.OPERATION_FAILED);
	}
    }

    public void deletePart(org.osid.shared.Id partId)
		throws org.osid.repository.RepositoryException
    {
        try
	{
		for (int i=0, size = _partVector.size(); i < size; i++)
		{
			org.osid.repository.Part part = (org.osid.repository.Part)_partVector.elementAt(i);
			if (part.getId().isEqual(partId))
			{
				_partVector.removeElementAt(i);
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

    public void updateDisplayName(String displayName)
		throws org.osid.repository.RepositoryException
    {
        throw new org.osid.repository.RepositoryException(org.osid.OsidException.UNIMPLEMENTED);
    }

    public org.osid.repository.PartIterator getParts()
		throws org.osid.repository.RepositoryException
    {
        return new PartIterator(_partVector);
    }

    public org.osid.repository.RecordStructure getRecordStructure()
		throws org.osid.repository.RepositoryException
    {
		return _recordStructure;
    }

    public boolean isMultivalued()
		throws org.osid.repository.RepositoryException
    {
        return false;
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
