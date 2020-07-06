/*
This is a file belonging to an axokoi project. The source code has been released under a MPL 2.0 license.
For more information, visit www.axokoi.com or www.github.com/axokoi
*/
package com.axokoi.png.chunk.validators;

import com.axokoi.png.chunk.AllowedChunkTypes;
import com.axokoi.png.chunk.Chunk;
import com.axokoi.png.chunk.IHDRChunk;

final class IHDRValidator {

    private IHDRValidator() {
        throw new UnsupportedOperationException("IHDRValidator shouldn't be instantiated");
    }

    static boolean validate(Chunk chunk) {
        if (chunk == null || chunk.getType() != AllowedChunkTypes.IHDR) {
            return false;
        }
        IHDRChunk ihdrChunk = (IHDRChunk) chunk;

        return isValidSize(ihdrChunk)
                && isValidBitDepth(ihdrChunk)
                && isValidColourType(ihdrChunk)
                && isValidCompressionMethod(ihdrChunk)
                && isValidFilterMethod(ihdrChunk)
                && isValidInterlaceMethod(ihdrChunk);

    }

    private static boolean isValidInterlaceMethod(IHDRChunk ihdrChunk) {
        return ihdrChunk.getInterlaceMethod() == 0x00
                || ihdrChunk.getInterlaceMethod() == 0x01;
    }

    private static boolean isValidFilterMethod(IHDRChunk ihdrChunk) {
        return ihdrChunk.getFilterMethod() == 0x00;
    }

    private static boolean isValidCompressionMethod(IHDRChunk ihdrChunk) {
        return ihdrChunk.getCompressionMethod() == 0x00;
    }

    private static boolean isValidColourType(IHDRChunk ihdrChunk) {
        return ihdrChunk.getColourType() == 0x00
                || ihdrChunk.getColourType() == 0x02
                || ihdrChunk.getColourType() == 0x03
                || ihdrChunk.getColourType() == 0x04
                || ihdrChunk.getColourType() == 0x06;
    }

    private static boolean isValidBitDepth(IHDRChunk ihdrChunk) {
        return ihdrChunk.getBitDepth() == 0x01
                || ihdrChunk.getBitDepth() == 0x02
                || ihdrChunk.getBitDepth() == 0x04
                || ihdrChunk.getBitDepth() == 0x08
                || ihdrChunk.getBitDepth() == 0x10;
    }

    private static boolean isValidSize(IHDRChunk ihdrChunk) {
        return ihdrChunk.getWidth() > 0 && ihdrChunk.getHeight() > 0;
    }
}
