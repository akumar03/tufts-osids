package edu.tufts.osidimpl.repository.yahoowiki;

public class SharedProperties implements org.osid.shared.Properties {
    private java.util.Map map = new java.util.HashMap();
    private org.osid.shared.Type type = new Type("mit.edu", "shared", "empty");

    public SharedProperties() throws org.osid.shared.SharedException {
    }

    public SharedProperties(java.util.Map map, org.osid.shared.Type type)
        throws org.osid.shared.SharedException {
        this.map = map;
        this.type = type;
    }

    public org.osid.shared.ObjectIterator getKeys()
        throws org.osid.shared.SharedException {
        return new ObjectIterator(new java.util.Vector(this.map.keySet()));
    }

    public java.io.Serializable getProperty(java.io.Serializable key)
        throws org.osid.shared.SharedException {
        if (this.map.containsKey(key)) {
            return (java.io.Serializable) this.map.get(key);
        } else {
            throw new org.osid.shared.SharedException(org.osid.shared.SharedException.UNKNOWN_KEY);
        }
    }

    public org.osid.shared.Type getType()
        throws org.osid.shared.SharedException {
        return this.type;
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
