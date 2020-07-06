/*
This is a file belonging to an axokoi project. The source code has been released under a MPL 2.0 license.
For more information, visit www.axokoi.com or www.github.com/axokoi
*/
package com.axokoi.png.chunk;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;

/**
 * The four-byte chunk type field contains the decimal values
 * <p>
 * 80 76 84 69
 * The PLTE chunk contains from 1 to 256 palette entries, each a three-byte series of the form:
 * <p>
 * Red	1 byte
 * Green	1 byte
 * Blue	1 byte
 * The number of entries is determined from the chunk length. A chunk length not divisible by 3 is an error.
 * <p>
 * This chunk shall appear for colour type 3, and may appear for colour types 2 and 6; it shall not appear for colour types 0 and 4. There shall not be more than one PLTE chunk.
 * <p>
 * For colour type 3 (indexed-colour), the PLTE chunk is required. The first entry in PLTE is referenced by pixel value 0, the second by pixel value 1, etc. The number of palette entries shall not exceed the range that can be represented in the image bit depth (for example, 24 = 16 for a bit depth of 4). It is permissible to have fewer entries than the bit depth would allow. In that case, any out-of-range pixel value found in the image data is an error.
 * <p>
 * For colour types 2 and 6 (truecolour and truecolour with alpha), the PLTE chunk is optional. If present, it provides a suggested set of colours (from 1 to 256) to which the truecolour image can be quantized if it cannot be displayed directly. It is, however, recommended that the sPLT chunk be used for this purpose, rather than the PLTE chunk. If neither PLTE nor sPLT chunks are present and the image cannot be displayed directly, quantization has to be done by the viewing system. However, it is often preferable for the selection of colours to be done once by the PNG encoder. (See 12.6: Suggested palettes.)
 * <p>
 * Note that the palette uses 8 bits (1 byte) per sample regardless of the image bit depth. In particular, the palette is 8 bits deep even when it is a suggested quantization of a 16-bit truecolour image.
 * <p>
 * There is no requirement that the palette entries all be used by the image, nor that they all be different.
 */

public class PLTEChunk extends Chunk {
    private static final int PALETTE_ENTRY_SIZE = 3;
    List<RGB> paletteEntries;

    PLTEChunk(ByteBuffer buffer) {
        super(buffer);
        paletteEntries = new LinkedList<>();
        if (isValidNumberOfEntries()) {
            throw new IllegalArgumentException("Wrong number of entries. Expected between 1 to 256 but was: "
                    + 1.0 * super.data.length / PALETTE_ENTRY_SIZE);
        }

        ByteBuffer dataBuffer = ByteBuffer.wrap(super.data);
        do {
            paletteEntries.add(new RGB(dataBuffer));
        } while (dataBuffer.remaining() > 0);
    }

    private boolean isValidNumberOfEntries() {
        int remainder = super.data.length % PALETTE_ENTRY_SIZE;
        if (remainder != 0) {
            return false;
        }

        int numberOfEntries = super.data.length / PALETTE_ENTRY_SIZE;
        return numberOfEntries != 0 && numberOfEntries <= 256;
    }

    public List<RGB> getPaletteEntries() {
        return List.copyOf(paletteEntries);
    }

    static class RGB {
        private final byte red;
        private final byte green;
        private final byte blue;

        private RGB(ByteBuffer buffer) {
            if (buffer.remaining() < PALETTE_ENTRY_SIZE) {
                throw new IllegalArgumentException("Error when reading RGB parameters for PLTE Chunk. Remaining bytes should be at least 3 but was "
                        + buffer.remaining());
            }
            red = buffer.get();
            green = buffer.get();
            blue = buffer.get();
        }

        public byte getRed() {
            return red;
        }

        public byte getGreen() {
            return green;
        }

        public byte getBlue() {
            return blue;
        }
    }
    
	public String toString() {
		return "PLTEChunk [paletteEntries=" + paletteEntries + ", isValidNumberOfEntries()=" + isValidNumberOfEntries()
		+ ", getPaletteEntries()=" + getPaletteEntries() + "]";
	}
}