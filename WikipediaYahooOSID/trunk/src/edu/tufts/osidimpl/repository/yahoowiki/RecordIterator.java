package edu.tufts.osidimpl.repository.yahoowiki;

public class RecordIterator
implements org.osid.repository.RecordIterator
{
    private java.util.Iterator iterator = null;

    public RecordIterator(java.util.Vector vector)
    throws org.osid.repository.RepositoryException
    {
		this.iterator = vector.iterator();
    }

    public boolean hasNextRecord()
    throws org.osid.repository.RepositoryException
    {
        return iterator.hasNext();
    }

    public org.osid.repository.Record nextRecord()
    throws org.osid.repository.RepositoryException
    {
        if (iterator.hasNext())
        {
            return (org.osid.repository.Record)iterator.next();
        }
        else
        {
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
