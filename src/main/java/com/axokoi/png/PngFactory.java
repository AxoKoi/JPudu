/*
This is a file belonging to an axokoi project. The source code has been released under a MPL 2.0 license.
For more information, visit www.axokoi.com or www.github.com/axokoi
*/
package com.axokoi.png;

import com.axokoi.png.validators.PngValidator;

import java.nio.ByteBuffer;

public class PngFactory {

    private PngFactory(){
        throw new UnsupportedOperationException("PngFactory shouldn't be instantiated");
    }

    public static PngFile parse(ByteBuffer buffer) throws PngFileException {
        return PngFile.parse(buffer);
    }

    public static PngFile parse(byte[] rawPNG) throws PngFileException {
        ByteBuffer buffer = ByteBuffer.allocate(rawPNG.length);
        buffer.put(rawPNG);
        buffer.flip();
        PngFile pngFile = PngFile.parse(buffer);

        if (PngValidator.validate(pngFile)) {
            return pngFile;
        } else {
            throw new PngFileException("Impossible to validate png file.");
        }
    }

}
