package edu.tufts.osidimpl.repository.nytimes;

public class Repository
implements org.osid.repository.Repository
{
    private java.util.Vector _assetVector = new java.util.Vector();
	private Configuration _configuration = Configuration.getInstance();

	public String getDisplayName()
		throws org.osid.repository.RepositoryException
    {
        return _configuration.getRepositoryDisplayName();
    }

    public void updateDisplayName(String displayName)
		throws org.osid.repository.RepositoryException
    {
        if (displayName == null) {
            throw new org.osid.repository.RepositoryException(org.osid.shared.SharedException.NULL_ARGUMENT);
        }
        throw new org.osid.repository.RepositoryException(org.osid.OsidException.UNIMPLEMENTED);
    }

    public String getDescription()
		throws org.osid.repository.RepositoryException
    {
        return _configuration.getRepositoryDescription();
    }

    public void updateDescription(String description)
    throws org.osid.repository.RepositoryException
    {
        if (description == null) {
            throw new org.osid.repository.RepositoryException(org.osid.shared.SharedException.NULL_ARGUMENT);
        }
        throw new org.osid.repository.RepositoryException(org.osid.OsidException.UNIMPLEMENTED);
    }

    public org.osid.shared.Id getId()
		throws org.osid.repository.RepositoryException
    {
        return _configuration.getRepositoryId();
    }

    public org.osid.shared.Type getType()
		throws org.osid.repository.RepositoryException
    {
        return _configuration.getRepositoryType();
    }

    public org.osid.repository.Asset createAsset(String displayName,
                                                 String description,
                                                 org.osid.shared.Type assetType)
		throws org.osid.repository.RepositoryException
    {
        if ( (displayName == null ) || (description == null) || (assetType == null) ) {
            throw new org.osid.repository.RepositoryException(org.osid.shared.SharedException.NULL_ARGUMENT);
        }
		try {
			org.osid.shared.TypeIterator assetTypeIterator = _configuration.getAssetTypes();
			while (assetTypeIterator.hasNextType()) {
				if (assetTypeIterator.nextType().isEqual(assetType)) {
					// create here
					throw new org.osid.repository.RepositoryException(org.osid.OsidException.UNIMPLEMENTED);
				}
			}
		} catch (org.osid.repository.RepositoryException rex) {
			throw new org.osid.repository.RepositoryException(rex.getMessage());
		} catch (Throwable t) {
			throw new org.osid.repository.RepositoryException(org.osid.OsidException.OPERATION_FAILED);
		}
		throw new org.osid.repository.RepositoryException(org.osid.shared.SharedException.UNKNOWN_TYPE);
    }

    public void deleteAsset(org.osid.shared.Id assetId)
		throws org.osid.repository.RepositoryException
    {
        if (assetId == null) {
            throw new org.osid.repository.RepositoryException(org.osid.shared.SharedException.NULL_ARGUMENT);
        }
        throw new org.osid.repository.RepositoryException(org.osid.OsidException.UNIMPLEMENTED);
    }

    public org.osid.repository.AssetIterator getAssets()
		throws org.osid.repository.RepositoryException
    {
		/* Modify this if browsing is possible.  One straightforward approach is to have a container asset type.  Assets
		   of this type exist soley to hold other assets.  Use the Asset.getAssets() method to expose successive layers
		   of containment.
		*/
        throw new org.osid.repository.RepositoryException(org.osid.OsidException.UNIMPLEMENTED);
    }

    public org.osid.repository.AssetIterator getAssetsByType(org.osid.shared.Type assetType)
		throws org.osid.repository.RepositoryException
    {
        if (assetType == null) {
            throw new org.osid.repository.RepositoryException(org.osid.shared.SharedException.NULL_ARGUMENT);
        }
		// modify this if there is more than one asset type
		return getAssets();
    }

    public org.osid.shared.TypeIterator getAssetTypes()
		throws org.osid.repository.RepositoryException
    {
		return _configuration.getAssetTypes();
    }

    public org.osid.repository.RecordStructureIterator getRecordStructures()
		throws org.osid.repository.RepositoryException
    {
		// Modify this if needed.  Multiple asset types might warrant more record structures.
		// Another case is different metadata groupings.
        java.util.Vector results = new java.util.Vector();
        results.addElement(new RecordStructure());
        return new RecordStructureIterator(results);
    }

    public org.osid.repository.RecordStructureIterator getRecordStructuresByType(org.osid.shared.Type recordStructureType)
		throws org.osid.repository.RepositoryException
    {
        if (recordStructureType == null) {
            throw new org.osid.repository.RepositoryException(org.osid.shared.SharedException.NULL_ARGUMENT);
        }

		java.util.Vector results = new java.util.Vector();
		org.osid.repository.RecordStructureIterator recordStructureIterator = getRecordStructures();
		while (recordStructureIterator.hasNextRecordStructure()) {
			org.osid.repository.RecordStructure recordStructure = recordStructureIterator.nextRecordStructure();
			if (recordStructure.getType().isEqual(recordStructureType)) {
				results.addElement(recordStructure);
			}
		}
		return new RecordStructureIterator(results);
    }

    public org.osid.repository.RecordStructureIterator getMandatoryRecordStructures(org.osid.shared.Type assetType)
		throws org.osid.repository.RepositoryException
    {
        if (assetType == null) {
            throw new org.osid.repository.RepositoryException(org.osid.shared.SharedException.NULL_ARGUMENT);
        }
		// modify this if record strucutres vary with asset type
		return getRecordStructures();
    }

    public org.osid.shared.TypeIterator getSearchTypes()
		throws org.osid.repository.RepositoryException
    {
		return _configuration.getSearchTypes();
    }

    public org.osid.shared.TypeIterator getStatusTypes()
		throws org.osid.repository.RepositoryException
    {
		// modify this if assets have status
        java.util.Vector results = new java.util.Vector();
        try {
            results.addElement(new Type("mit.edu","asset","valid"));
            return new TypeIterator(results);
        } catch (Throwable t) {
            Utilities.log(t.getMessage());
            throw new org.osid.repository.RepositoryException(org.osid.repository.RepositoryException.OPERATION_FAILED);
        }
    }

    public org.osid.shared.Type getStatus(org.osid.shared.Id assetId)
		throws org.osid.repository.RepositoryException
    {
		// modify this if assets have status
        return new Type("mit.edu","asset","valid");
    }

    public boolean validateAsset(org.osid.shared.Id assetId)
		throws org.osid.repository.RepositoryException
    {
        return true;
    }

    public void invalidateAsset(org.osid.shared.Id assetId)
		throws org.osid.repository.RepositoryException
    {
        throw new org.osid.repository.RepositoryException(org.osid.OsidException.UNIMPLEMENTED);
    }

    public org.osid.repository.Asset getAsset(org.osid.shared.Id assetId)
		throws org.osid.repository.RepositoryException
    {
    	//MK - Not implementing search By ID
    	throw new org.osid.repository.RepositoryException(org.osid.shared.SharedException.UNIMPLEMENTED);
    }

    public org.osid.repository.Asset getAssetByDate(org.osid.shared.Id assetId,
                                                   long date)
		throws org.osid.repository.RepositoryException
    {
        throw new org.osid.repository.RepositoryException(org.osid.OsidException.UNIMPLEMENTED);
    }

    public org.osid.shared.LongValueIterator getAssetDates(org.osid.shared.Id assetId)
		throws org.osid.repository.RepositoryException
    {
        throw new org.osid.repository.RepositoryException(org.osid.OsidException.UNIMPLEMENTED);
    }

    public org.osid.repository.AssetIterator getAssetsBySearch(java.io.Serializable searchCriteria,
                                                               org.osid.shared.Type searchType,
                                                               org.osid.shared.Properties searchProperties)
		throws org.osid.repository.RepositoryException
    {
		if ( (searchCriteria == null) || (searchType == null) ) {
			throw new org.osid.repository.RepositoryException(org.osid.shared.SharedException.NULL_ARGUMENT);
		}
		return _configuration.assetsSearch(searchCriteria, searchType);
    }

    public org.osid.shared.Id copyAsset(org.osid.repository.Asset asset)
		throws org.osid.repository.RepositoryException
    {
        throw new org.osid.repository.RepositoryException(org.osid.OsidException.UNIMPLEMENTED);
    }

	// use these properties-related methods if you want to offer some metadata
    public org.osid.shared.PropertiesIterator getProperties()
		throws org.osid.repository.RepositoryException
    {
        try {
            return new PropertiesIterator(new java.util.Vector());
        } catch (Throwable t) {
            Utilities.log(t.getMessage());
            throw new org.osid.repository.RepositoryException(org.osid.repository.RepositoryException.OPERATION_FAILED);
        }
    }

	// use these properties-related methods if you want to offer some metadata
    public org.osid.shared.Properties getPropertiesByType(org.osid.shared.Type propertiesType)
		throws org.osid.repository.RepositoryException
    {
        if (propertiesType == null) {
            throw new org.osid.repository.RepositoryException(org.osid.shared.SharedException.NULL_ARGUMENT);
        }
		try {
			return new SharedProperties();
		} catch (Throwable t) {
		}
		return null;
    }

	// use these properties-related methods if you want to offer some metadata
    public org.osid.shared.TypeIterator getPropertyTypes()
		throws org.osid.repository.RepositoryException
    {
        try {
            return new TypeIterator(new java.util.Vector());
        } catch (Throwable t) {
            Utilities.log(t.getMessage());
            throw new org.osid.repository.RepositoryException(org.osid.repository.RepositoryException.OPERATION_FAILED);
        }
    }

    protected void addAsset(org.osid.repository.Asset asset)
		throws org.osid.repository.RepositoryException
    {
        _assetVector.addElement(asset);
    }

    public boolean supportsUpdate()
		throws org.osid.repository.RepositoryException
    {
		// modify this if updating is supported
        return false;
    }

    public boolean supportsVersioning()
		throws org.osid.repository.RepositoryException
    {
		// modify this if updating is supported
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
