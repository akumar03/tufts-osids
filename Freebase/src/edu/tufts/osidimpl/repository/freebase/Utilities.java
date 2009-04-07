package edu.tufts.osidimpl.repository.freebase;

public class Utilities
{
    private static org.osid.logging.WritableLog log = null;
	private static org.osid.OsidContext context = null;

	public static void setOsidContext(org.osid.OsidContext c)
	{
		context = c;
	}

	public static org.osid.OsidContext getOsidContext()
	{
		return context;
	}

	public static void setLog(org.osid.logging.WritableLog l)
	{
		log = l;
	}

	public static void log(String entry)
	{
		try {
			log.appendLog(entry);
		} catch (org.osid.logging.LoggingException lex) {
			// swallow exception since logging is a best attempt to log an exception anyway
		}
	}

	public static void log(Throwable t)
	{
		try {
			t.printStackTrace();
			log.appendLog(t.getMessage());
		} catch (org.osid.logging.LoggingException lex) {
			// swallow exception since logging is a best attempt to log an exception anyway
		}
	}

	public static String typeToString(org.osid.shared.Type type)
	{
		return type.getDomain() + "/" + type.getKeyword() + "@" + type.getAuthority();
	}

	public static org.osid.shared.Type stringToType(String typeString) {
		String authority = "_";
		String domain = "_";
		String keyword = "_";
		try {
			if (typeString != null) {
				int indexSlash = typeString.indexOf("/");
				if (indexSlash != -1) {
					domain = typeString.substring(0,indexSlash);
					int indexAt = typeString.indexOf("@");
					if (indexAt != -1) {
						keyword = typeString.substring(indexSlash+1,indexAt);
						authority = typeString.substring(indexAt+1);
					}
				}
			}
		} catch (Throwable t) {
			// ignore formatting error
		}
		return new Type(authority,domain,keyword);
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