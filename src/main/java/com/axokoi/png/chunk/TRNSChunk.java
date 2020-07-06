package com.axokoi.png.chunk;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Optional;

/**
 * The tRNS chunk specifies either alpha values that are associated with palette entries
 * (for indexed-colour images) or a single transparent colour (for greyscale and truecolour images). The tRNS chunk contains:
 * <p>
 * //color type on hdr
 * // * Colour type 0
 * // * Grey sample value	2 bytes
 * // * Colour type 2
 * // * Red sample value	2 bytes
 * // * Blue sample value	2 bytes
 * // * Green sample value	2 bytes
 * // * Colour type 3
 * // * Alpha for palette index 0	1 byte
 * // * Alpha for palette index 1	1 byte
 * // * ...etc...	1 byte
 * <p>
 *
 * For colour type 3 (indexed-colour), the tRNS chunk contains a series of one-byte alpha values, corresponding to entries in the PLTE chunk. Each entry indicates that pixels of the corresponding palette index shall be treated as having the specified alpha value. Alpha values have the same interpretation as in an 8-bit full alpha channel: 0 is fully transparent, 255 is fully opaque, regardless of image bit depth. The tRNS chunk shall not contain more alpha values than there are palette entries, but a tRNS chunk may contain fewer values than there are palette entries. In this case, the alpha value for all remaining palette entries is assumed to be 255. In the common case in which only palette index 0 need be made transparent, only a one-byte tRNS chunk is needed, and when all palette indices are opaque, the tRNS chunk may be omitted.
 * <p>
 * For colour types 0 or 2, two bytes per sample are used regardless of the image bit depth (see 7.1: Integers and byte order). Pixels of the specified grey sample value or RGB sample values are treated as transparent (equivalent to alpha value 0); all other pixels are to be treated as fully opaque (alpha value 2bitdepth-1). If the image bit depth is less than 16, the least significant bits are used and the others are 0.
 * <p>
 * A tRNS chunk shall not appear for colour types 4 and 6, since a full alpha channel is already present in those cases.
 * <p>
 * NOTE For 16-bit greyscale or truecolour data, only pixels matching the entire 16-bit values in tRNS chunks are transparent. Decoders have to postpone any sample depth rescaling until after the pixels have been tested for transparency.
 */
//todo add verification for these
public class TRNSChunk extends Chunk {
    private static final int RED_OFFSET = 0x00;
    private static final int BLUE_OFFSET = 0x02;
    private static final int GREEN_OFFSET = 0x04;
    private final byte[] greySampleValue = new byte[2];

    TRNSChunk(ByteBuffer buffer) {
        super(buffer);

        if (super.data.length == 0 || super.data.length > 0x20) {
            throw new IllegalArgumentException("Illegal data size : " + data.length);
        }
    }

    Optional<byte[]> getGreySampleValue() {
        if (this.data.length != 0x02) {
            return Optional.empty();
        } else {
            byte[] copyOfData = ByteBuffer.allocate(2).put(this.data).array();
            return Optional.of(copyOfData);
        }
    }

    Optional<byte[]> getRedSampleValue() {
        return readRGBValue(RED_OFFSET);
    }

    Optional<byte[]> getBlueSampleValue() {
        return readRGBValue(BLUE_OFFSET);
    }

    Optional<byte[]> getGreenSampleValue() {
        return readRGBValue(GREEN_OFFSET);
    }

    private Optional<byte[]> readRGBValue(int offset) {
        if (this.data.length != 0x06) {
            return Optional.empty();
        } else {
            ByteBuffer dataBuffer = ByteBuffer.wrap(this.data);
            byte[] sampleValue = new byte[0x02];
            dataBuffer.position(offset);
            sampleValue[0] = dataBuffer.get();
            sampleValue[1] = dataBuffer.get();
            return Optional.of(sampleValue);
        }
    }

    Optional<byte[]> getAlphas() {
        ByteBuffer buffer = ByteBuffer.allocate(data.length);
        buffer.put(this.data);
        return Optional.of(buffer.array());
    }

    @Override
    public String toString() {
        return "[" + super.toString() + " tRNSChunk [Grey value" + Arrays.toString(this.getGreySampleValue().orElse(new byte[0]))
                + "Red value" + Arrays.toString(this.getRedSampleValue().orElse(new byte[0]))
                + "Green value" + Arrays.toString(this.getGreenSampleValue().orElse(new byte[0]))
                + "Blue value" + Arrays.toString(this.getBlueSampleValue().orElse(new byte[0]))
                + "Alpha values" + Arrays.toString(this.getAlphas().orElse(new byte[0]))
                + "]";

    }

}
