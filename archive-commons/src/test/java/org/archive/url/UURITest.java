/*
 *  This file is part of the Heritrix web crawler (crawler.archive.org).
 *
 *  Licensed to the Internet Archive (IA) by one or more individual 
 *  contributors. 
 *
 *  The IA licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.archive.url;

import java.net.URISyntaxException;

import org.apache.commons.httpclient.URIException;
import org.archive.url.UURI;

import junit.framework.TestCase;

public class UURITest extends TestCase {
    public void testHasScheme() {
        assertTrue(UURI.hasScheme("http://www.archive.org"));
        assertTrue(UURI.hasScheme("http:"));
        assertFalse(UURI.hasScheme("ht/tp://www.archive.org"));
        assertFalse(UURI.hasScheme("/tmp"));
    }
    
    public void testGetFileName() throws URISyntaxException {
        final String filename = "x.arc.gz";
        assertEquals(filename,
            UURI.parseFilename("/tmp/one.two/" + filename));
        assertEquals(filename,
            UURI.parseFilename("http://archive.org/tmp/one.two/" +
                    filename));
        assertEquals(filename,
            UURI.parseFilename("rsync://archive.org/tmp/one.two/" +
                    filename)); 
    }
    
    public void testSchemalessRelative() throws URIException {
        UURI base = new UURI("http://www.archive.org/a", true, "UTF-8");
        UURI relative = new UURI("//www.facebook.com/?href=http://www.archive.org/a", true, "UTF-8");
        assertEquals(null, relative.getScheme());
        assertEquals("www.facebook.com", relative.getAuthority());
        UURI test = new UURI(base, relative);
        assertEquals("http://www.facebook.com/?href=http://www.archive.org/a", test.toString());
    }
}
