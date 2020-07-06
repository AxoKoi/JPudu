/*
This is a file belonging to an axokoi project. The source code has been released under a MPL 2.0 license.
For more information, visit www.axokoi.com or www.github.com/axokoi
*/
package com.axokoi.png.chunk;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Optional;

public enum AllowedChunkTypes {

    IHDR("IHDR"),
    PLTE("PLTE"),
    IDAT("IDAT"),
    IEND("IEND"),
    cHRM("cHRM"),
    gAMA("gAMA"),
    iCCP("iCCP"),
    sBIT("sBIT"),
    sRGB("sRGB"),
    bKGD("bKGD"),
    hIST("hIST"),
    tRNS("tRNS"),
    pHYs("pHYs"),
    sPLT("sPLT"),
    tIME("tIME"),
    iTXt("iTXt"),
    tEXt("tEXt"),
    zTXT("zTXT");


    private final String name;

    AllowedChunkTypes(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public byte[] bytes() {
        return this.name.getBytes(StandardCharsets.US_ASCII);
    }

    public static Optional<AllowedChunkTypes> fromName(String name) {
        return Arrays.stream(AllowedChunkTypes.values())
                .filter(x -> x.getName().equals(name))
                .findFirst();
    }

    public static Optional<AllowedChunkTypes> fromBytes(byte[] rawName) {
        return Arrays.stream(AllowedChunkTypes.values())
                .filter(x -> Arrays.equals(x.bytes(),rawName))
                .findFirst();
    }
}
