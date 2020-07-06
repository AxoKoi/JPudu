/*
This is a file belonging to an axokoi project. The source code has been released under a MPL 2.0 license.
For more information, visit www.axokoi.com or www.github.com/axokoi
*/
package com.axokoi.png.chunk;

import java.nio.ByteBuffer;

/**
 * The four-byte chunk type field contains the decimal values
 *
 * 73 68 65 84
 * The IDAT chunk contains the actual image data which is the output stream of the compression algorithm.
 */
public class IDATChunk extends Chunk {
    IDATChunk(ByteBuffer buffer) {
        super(buffer);
    }
    
	public String toString() {
		return "IDATChunk [ super.toString()=" + super.toString() +" ]";
	}
}