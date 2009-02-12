package edu.tufts.osidimpl.repository.nytimes;

public class Part
implements org.osid.repository.Part
{
    private org.osid.repository.PartStructure _partStructure = null;
    private org.osid.shared.Id _partStructureId = null;
    private java.io.Serializable _value = null;
    private String _displayName = null;
    private org.osid.shared.Id _id = null;

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

    protected Part(org.osid.shared.Id partStructureId
				   , java.io.Serializable value)
		throws org.osid.repository.RepositoryException
    {
        _partStructureId = partStructureId;
        _value = value;
		org.osid.id.IdManager idManager = Configuration.getInstance().getIdManager();
		try
        {
			String idString = partStructureId.getIdString();
			int len = Configuration.PartStructureDisplayNames.length;
			for (int i=0; i < len; i++) {
				if (idString.equals(Configuration.PartStructureIDs[i])) {
					_partStructure = new PartStructure(Configuration.PartStructureDisplayNames[i],
													   Configuration.PartStructureDescriptions[i],
													   idString,
													   Configuration.PartStructureTypes[i],
													   Configuration.RECORD_STRUCTURE_ID);
					_displayName = Configuration.PartStructureDisplayNames[i];
					_id = idManager.getId(Configuration.PartIDs[i]);
					return;
				}
			}
			System.out.println("Unrecognized part structure " + idString + ".  Value is " + value);
		} catch (Throwable t) {
            Utilities.log(t.getMessage());
            throw new org.osid.repository.RepositoryException(org.osid.repository.RepositoryException.OPERATION_FAILED);
        }
    }

    public org.osid.repository.Part createPart(org.osid.shared.Id partStructureId
											   , java.io.Serializable value)
		throws org.osid.repository.RepositoryException
    {
        throw new org.osid.repository.RepositoryException(org.osid.OsidException.UNIMPLEMENTED);
    }

    public void deletePart(org.osid.shared.Id partStructureId)
		throws org.osid.repository.RepositoryException
    {
        throw new org.osid.repository.RepositoryException(org.osid.OsidException.UNIMPLEMENTED);
    }

    public void updateDisplayName(String displayName)
		throws org.osid.repository.RepositoryException
    {
        throw new org.osid.repository.RepositoryException(org.osid.OsidException.UNIMPLEMENTED);
    }

    public org.osid.repository.PartIterator getParts()
		throws org.osid.repository.RepositoryException
    {
        return new PartIterator(new java.util.Vector());
    }

    public org.osid.repository.PartStructure getPartStructure()
		throws org.osid.repository.RepositoryException
    {
		return _partStructure;
    }

    public java.io.Serializable getValue()
		throws org.osid.repository.RepositoryException
    {
		return _value;
    }

    public void updateValue(java.io.Serializable value)
		throws org.osid.repository.RepositoryException
    {
        _value = value;
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
