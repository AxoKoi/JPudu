/*
This is a file belonging to an axokoi project. The source code has been released under a MPL 2.0 license.
For more information, visit www.axokoi.com or www.github.com/axokoi
*/
package com.axokoi.png;

public class PngFileException extends Exception {
    public PngFileException(String message, Throwable e) {
        super(message, e);
    }

    public PngFileException(String message) {
        super(message);
    }
}
