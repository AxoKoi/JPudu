/*
This is a file belonging to an axokoi project. The source code has been released under a MPL 2.0 license.
For more information, visit www.axokoi.com or www.github.com/axokoi
*/

package com.axokoi.png.chunk;

public class IllegalChunkException extends Exception {

    public IllegalChunkException(String message, Chunk chunk) {
        super(message + "\n" + chunk);
    }

    public IllegalChunkException(String message) {
        super(message);
    }
}
