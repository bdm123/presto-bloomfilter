/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.facebook.presto.bloomfilter;

import io.airlift.slice.Slice;
import io.airlift.slice.Slices;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class TestBloomFilterSerialization {
    @Test
    public void testBloomFilterCreate()
    {
        // Fresh instance
        BloomFilter bf = BloomFilter.newInstance();

        // Add a little bit of data
        bf.put(Slices.wrappedBuffer("robin".getBytes()));

        // Serialize
        Slice ser = bf.serialize();

        // Validate not empty
        assertNotNull(ser);
        assertTrue(ser.length() > 0);

        // Deserialize
        BloomFilter bf2 = BloomFilter.newInstance(ser);

        // Validate
        assertTrue(bf2.mightContain(Slices.wrappedBuffer("robin".getBytes())));
        assertFalse(bf2.mightContain(Slices.wrappedBuffer("not-in-here".getBytes())));
    }
}