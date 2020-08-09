package com.axokoi.png.chunk;

import java.nio.ByteBuffer;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.junit.Assert;

import junit.framework.TestCase;

public class IHDRChunkBuilderTest extends TestCase {

	public void testBuild() throws IllegalChunkException, DecoderException {
		byte[] rawChunk = Hex.decodeHex("0000000D4948445200000500000004C70806000000B27EDA4F");

		IHDRChunk ihdrChunk = new IHDRChunkBuilder().setWidth(0x500)
				.setHeight(0x4C7)
				.setBitDepth((byte) 0x08)
				.setColourType((byte) 0x06).build();

		Assert.assertArrayEquals(rawChunk, ihdrChunk.bytes());
	}

	public void testBuildWithChunkAsModel() throws IllegalChunkException, DecoderException {
		byte[] rawChunk = Hex.decodeHex("0000000D4948445200000500000004C70806000000B27EDA4F");
		IHDRChunk modelChunk = new IHDRChunk(ByteBuffer.allocate(rawChunk.length).put(rawChunk).flip());

		IHDRChunk ihdrChunk = new IHDRChunkBuilder(modelChunk).setWidth(0x600)
				.setHeight(0x5C7)
				.build();

		assertEquals(0x600, ihdrChunk.getWidth());
		assertEquals(0x5C7, ihdrChunk.getHeight());
		assertEquals(0x08, ihdrChunk.getBitDepth());
		assertEquals(0x06, ihdrChunk.getColourType());
	}
}