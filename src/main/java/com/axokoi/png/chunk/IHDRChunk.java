/*
This is a file belonging to an axokoi project. The source code has been released under a MPL 2.0 license.
For more information, visit www.axokoi.com or www.github.com/axokoi
*/
package com.axokoi.png.chunk;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public final class IHDRChunk extends Chunk {

    private final byte[] width;
    private final byte[] height;
    private final byte bitDepth;
    private final byte colourType;
    private final byte compressionMethod;
    private final byte filterMethod;
    private final byte interlaceMethod;
//todo The sample depth is the same as the bit depth except in the case of indexed-colour PNG images (colour type 3), in which the sample depth is always 8 bits
    IHDRChunk(ByteBuffer buffer) {
        super(buffer);
        ByteBuffer dataBuffer = ByteBuffer.allocate(data.length);
        dataBuffer.put(super.data);
        dataBuffer.flip();

        width = new byte[0x04];
        dataBuffer.get(width, 0, 0x04);

        height = new byte[0x04];
        dataBuffer.get(height, 0, 0x04);

        bitDepth = dataBuffer.get();
        colourType = dataBuffer.get();
        compressionMethod = dataBuffer.get();
        filterMethod = dataBuffer.get();
        interlaceMethod = dataBuffer.get();
    }

    public int getWidth() {
        return ByteBuffer.wrap(width).order(ByteOrder.BIG_ENDIAN).getInt();
    }

    public int getHeight() {
        return ByteBuffer.wrap(height).order(ByteOrder.BIG_ENDIAN).getInt();
    }

    public byte getBitDepth() {
        return bitDepth;
    }

    public byte getColourType() {
        return colourType;
    }

    public byte getCompressionMethod() {
        return compressionMethod;
    }

    public byte getFilterMethod() {
        return filterMethod;
    }

    public byte getInterlaceMethod() {
        return interlaceMethod;
    }
    
	public String toString() {
		return "IHDRChunk [super.toString() = " + super.toString() + ", width=" + Arrays.toString(width) + ", height=" + Arrays.toString(height) + ", bitDepth="
				+ bitDepth + ", colourType=" + colourType + ", compressionMethod=" + compressionMethod
				+ ", filterMethod=" + filterMethod + ", interlaceMethod=" + interlaceMethod + ", getWidth()="
				+ getWidth() + ", getHeight()=" + getHeight() + ", getBitDepth()=" + getBitDepth()
				+ ", getColourType()=" + getColourType() + ", getCompressionMethod()=" + getCompressionMethod()
				+ ", getFilterMethod()=" + getFilterMethod() + ", getInterlaceMethod()=" + getInterlaceMethod() + "]";
	}
}