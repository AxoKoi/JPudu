/*
This is a file belonging to an axokoi project. The source code has been released under a MPL 2.0 license.
For more information, visit www.axokoi.com or www.github.com/axokoi
*/
package com.axokoi.png.chunk;

import java.nio.ByteBuffer;
import java.util.zip.CRC32;

public final class IHDRChunkBuilder {
	//default values
	private byte[] width = new byte[0x04];
	private byte[] height = new byte[0x04];
	private byte bitDepth;
	private byte colourType;
	private byte compressionMethod;
	private byte filterMethod;
	private byte interlaceMethod;

	public IHDRChunkBuilder() {
	//use default values
	}

	public IHDRChunkBuilder(IHDRChunk chunk) {
		this.width = ByteBuffer.allocate(0x04).putInt(chunk.getWidth()).array();
		this.height = ByteBuffer.allocate(0x04).putInt(chunk.getHeight()).array();
		this.bitDepth = chunk.getBitDepth();
		this.colourType = chunk.getColourType();
		this.compressionMethod = chunk.getCompressionMethod();
		this.filterMethod = chunk.getFilterMethod();
		this.interlaceMethod = chunk.getInterlaceMethod();

	}

	public IHDRChunkBuilder setWidth(int width) {
		System.arraycopy(ByteBuffer.allocate(0x04).putInt(width).flip().array(), 0, this.width, 0, 0x04);
		return this;
	}

	public IHDRChunkBuilder setHeight(int height) {
		System.arraycopy(ByteBuffer.allocate(0x04).putInt(height).flip().array(), 0, this.height, 0, 0x04);
		return this;
	}

	public IHDRChunkBuilder setBitDepth(byte bitDepth) {
		this.bitDepth = bitDepth;
		return this;
	}

	public IHDRChunkBuilder setColourType(byte colourType) {
		this.colourType = colourType;
		return this;
	}

	public IHDRChunkBuilder setCompressionMethod(byte compressionMethod) {
		this.compressionMethod = compressionMethod;
		return this;
	}

	public IHDRChunkBuilder setFilterMethod(byte filterMethod) {
		this.filterMethod = filterMethod;
		return this;
	}

	public IHDRChunkBuilder setInterlaceMethod(byte interlaceMethod) {
		this.interlaceMethod = interlaceMethod;
		return this;
	}

	public IHDRChunk build() throws IllegalChunkException {
		final int IHDR_SIZE = 0x19;
		final int DATA_CHUNK_LENGTH = 0x0D;

		IHDRChunk unverifiedChunk = getUnverifiedIHDRChunk(IHDR_SIZE, DATA_CHUNK_LENGTH);

		int crcValue = getCrcValue(unverifiedChunk);

		ByteBuffer verifiedBuffer = ByteBuffer.allocate(IHDR_SIZE)
				.putInt(DATA_CHUNK_LENGTH)
				.put(unverifiedChunk.getDataToVerify())
				.putInt(crcValue)
				.flip();

		return (IHDRChunk) ChunkFactory.parse(verifiedBuffer);
	}

	private int getCrcValue(IHDRChunk unverifiedChunk) {
		CRC32 crc = new CRC32();
		crc.update(unverifiedChunk.getDataToVerify());
		return (int) crc.getValue();

	}

	private IHDRChunk getUnverifiedIHDRChunk(final int IHDR_SIZE, final int CHUNK_LENGTH) {
		ByteBuffer buffer = ByteBuffer.allocate(IHDR_SIZE);
		buffer.putInt(CHUNK_LENGTH).put(AllowedChunkTypes.IHDR.bytes())
				.put(width).put(height)
				.put(bitDepth).put(colourType)
				.put(compressionMethod)
				.put(filterMethod).put(interlaceMethod)
				.putInt(0x00);
		buffer.flip();

		return new IHDRChunk(buffer);
	}
}
