package org.ant.sandbox.markit.dictionary;

import java.util.Set;

/**
 * @author Anton Tychyna
 */
public interface Dictionary {
    String translate(String word) throws IllegalArgumentException;

    void addToDictionary(String word, String translation) throws IllegalArgumentException;

    Set<String> getAllWords();
}
