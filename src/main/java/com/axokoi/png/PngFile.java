/*
This is a file belonging to an axokoi project. The source code has been released under a MPL 2.0 license.
For more information, visit www.axokoi.com or www.github.com/axokoi
*/
package com.axokoi.png;

import com.axokoi.png.chunk.AllowedChunkTypes;
import com.axokoi.png.chunk.Chunk;
import com.axokoi.png.chunk.ChunkFactory;
import com.axokoi.png.chunk.IllegalChunkException;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;

public class PngFile {

    public static final String MAGIC_NUMBER = "89504E470D0A1A0A";
    public static final int MAGIC_NUMBER_SIZE = 0x08;
    private final List<Chunk> chunks;
    private int size;

    private PngFile(ByteBuffer buffer) throws PngFileException {
        try {
            checkMagicNumber(buffer);
            chunks = new LinkedList<>();
            Chunk chunk;
            size = MAGIC_NUMBER_SIZE;

            do {
                chunk = readChunk(buffer);
                chunks.add(chunk);
                size += chunk.size();
            } while (!chunk.getType().equals(AllowedChunkTypes.IEND));

        } catch (IllegalArgumentException e) {
            throw new PngFileException("Impossible to create Png from raw binary", e);
        }
    }

    private Chunk readChunk(ByteBuffer buffer) throws PngFileException {
        try {
            return ChunkFactory.parse(buffer);
        } catch (IllegalChunkException e) {
            throw new PngFileException("Impossible to read the chunk.", e);
        }
    }

    private void checkMagicNumber(ByteBuffer buffer) {
        String magicNumber = readMagicNumber(buffer);
        if (!magicNumber.equalsIgnoreCase(MAGIC_NUMBER)) {
            throw new IllegalArgumentException("Wrong magic number. Expected" + MAGIC_NUMBER + " but was" + magicNumber);
        }
    }

    private String readMagicNumber(ByteBuffer buffer) {
        if (buffer.remaining() < MAGIC_NUMBER_SIZE) {
            throw new IllegalArgumentException("Not enough remaining buffer data to read Magic Number. Expected at leats" +
                    MAGIC_NUMBER_SIZE + " bytes");
        }
        byte[] rawString = new byte[MAGIC_NUMBER_SIZE];
        buffer.get(rawString, 0, MAGIC_NUMBER_SIZE);
        return String.valueOf(Hex.encodeHex(rawString));

    }

    public static PngFile parse(ByteBuffer buffer) throws PngFileException {
        return new PngFile(buffer);
    }

    public byte[] asBytes() {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(size);
            buffer.put(Hex.decodeHex(MAGIC_NUMBER));
            chunks.stream().map(Chunk::bytes).forEach(buffer::put);
            return buffer.array();
        } catch (DecoderException e) {
            throw new RuntimeException("Impossible to decode magic number: " + MAGIC_NUMBER, e);
        }
    }

    public List<Chunk> getChunks() {
        return List.copyOf(chunks);
    }
}
