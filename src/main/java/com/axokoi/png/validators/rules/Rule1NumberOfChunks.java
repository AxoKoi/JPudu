/*
This is a file belonging to an axokoi project. The source code has been released under a MPL 2.0 license.
For more information, visit www.axokoi.com or www.github.com/axokoi
*/
package com.axokoi.png.validators.rules;


import com.axokoi.png.PngFile;
import com.axokoi.png.chunk.AllowedChunkTypes;
import com.axokoi.png.chunk.Chunk;

import java.util.List;
import java.util.function.Predicate;

public class Rule1NumberOfChunks implements Predicate<PngFile> {

    @Override
    public boolean test(PngFile png) {
        List<Chunk> chunks = png.getChunks();
        boolean isIHDRCorrect = chunks.get(0).getType().equals(AllowedChunkTypes.IHDR);
        boolean isIENDCorrect = chunks.get(chunks.size() - 1).getType().equals(AllowedChunkTypes.IEND);

        boolean isIDATCorrect = chunks.stream()
                .map(Chunk::getType)
                .anyMatch(x -> x.equals(AllowedChunkTypes.IDAT));

        return isIHDRCorrect && isIENDCorrect && isIDATCorrect;
    }
}
