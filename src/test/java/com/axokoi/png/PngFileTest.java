/*
This is a file belonging to an axokoi project. The source code has been released under a MPL 2.0 license.
For more information, visit www.axokoi.com or www.github.com/axokoi
*/
package com.axokoi.png;

import com.axokoi.png.chunk.AllowedChunkTypes;
import com.axokoi.png.chunk.Chunk;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class PngFileTest {
    private static final Path TEST_IMAGE_PATH = Paths.get("src", "test", "resources", "png", "the-cup.png");

    @Test
    public void parse() throws PngFileException, IOException {

        byte[] rawPng = Files.readAllBytes(TEST_IMAGE_PATH);
        PngFile pngFile = PngFactory.parse(rawPng);
        Assert.assertArrayEquals(rawPng, pngFile.asBytes());
    }

    @Test
    public void contains() throws IOException, PngFileException {
        byte[] rawPng = Files.readAllBytes(TEST_IMAGE_PATH);
        PngFile pngFile = PngFactory.parse(rawPng);
        List<Chunk> chunks = pngFile.getChunks();
        List<AllowedChunkTypes> types = chunks.stream().map(Chunk::getType).collect(Collectors.toList());

        Assert.assertTrue(types.contains(AllowedChunkTypes.IHDR));
        Assert.assertTrue(types.contains(AllowedChunkTypes.IDAT));
        Assert.assertTrue(types.contains(AllowedChunkTypes.IEND));
    }
}
