/*
This is a file belonging to an axokoi project. The source code has been released under a MPL 2.0 license.
For more information, visit www.axokoi.com or www.github.com/axokoi
*/
package com.axokoi.png.validators;

import com.axokoi.png.PngFile;
import com.axokoi.png.validators.rules.Rule1NumberOfChunks;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

public class PngValidator {

    private static final Collection<Predicate<PngFile>> rules;

    static {
        Collection<Predicate<PngFile>> initialRules = new LinkedList<>();
        //add here all the rules
        initialRules.add(new Rule1NumberOfChunks());
        rules = List.copyOf(initialRules);
    }

    public static boolean validate(PngFile png) {
        return rules.stream()
                .reduce(x -> true, Predicate::and)
                .test(png);
    }

    private PngValidator() {
        throw new UnsupportedOperationException("PngValidator shouldn't be instantiated");
    }
}
