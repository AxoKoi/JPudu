/*
This is a file belonging to an axokoi project. The source code has been released under a MPL 2.0 license.
For more information, visit www.axokoi.com or www.github.com/axokoi
*/
package com.axokoi.png.chunk;

import com.axokoi.png.chunk.validators.ChunkValidatorFacade;

import java.nio.ByteBuffer;
import java.util.Arrays;

public final class ChunkFactory {

    private ChunkFactory() {
        throw new UnsupportedOperationException("Chunk Factory doesn't need to be instantiated");
    }

    public static Chunk parse(ByteBuffer buffer) throws IllegalChunkException {
        if (buffer.remaining() < Chunk.DATA_LENGTH_SIZE + Chunk.TYPE_SIZE + Chunk.CRC_SIZE) {
            throw new IllegalArgumentException("Not enough remaining data on buffer. Needed at least:"
                    + Chunk.DATA_LENGTH_SIZE + Chunk.TYPE_SIZE + Chunk.CRC_SIZE + " bytes");
        }
        buffer.mark();
        buffer.position(buffer.position() + Chunk.DATA_LENGTH_SIZE);

        byte[] rawType = new byte[Chunk.TYPE_SIZE];
        buffer.get(rawType, 0, Chunk.TYPE_SIZE);
        buffer.reset();

        AllowedChunkTypes type = AllowedChunkTypes.fromBytes(rawType)
                .orElseThrow(() -> new IllegalArgumentException("Unknown chunk type: " + Arrays.toString(rawType)));

        Chunk newChunk = createChunk(buffer, type);
        if (ChunkValidatorFacade.validate(newChunk)) {
            return newChunk;
        } else {
            throw new IllegalChunkException("The given chunk was not validated", newChunk);
        }
    }

    private static Chunk createChunk(ByteBuffer buffer, AllowedChunkTypes type) {
        switch (type) {
            case IHDR:
                return new IHDRChunk(buffer);
            case PLTE:
                return new PLTEChunk(buffer);
            case IDAT:
                return new IDATChunk(buffer);
            case IEND:
                return new IENDChunk(buffer);
            case cHRM:
            case gAMA:
            case iCCP:
            case sBIT:
            case sRGB:
            case bKGD:
            case hIST:
                return new DefaultChunk(buffer);
            case tRNS:
                return new TRNSChunk(buffer);
            case pHYs:
            case sPLT:
            case tIME:
            case iTXt:
            case tEXt:
            case zTXT:
                return new DefaultChunk(buffer);

            default:
                throw new UnsupportedOperationException("Unsupported chunk to be created:" + type);
        }
    }

}
