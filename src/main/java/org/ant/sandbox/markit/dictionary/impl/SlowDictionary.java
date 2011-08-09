package org.ant.sandbox.markit.dictionary.impl;

import org.ant.sandbox.markit.dictionary.Dictionary;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Anton Tychyna
 */
public class SlowDictionary implements Dictionary {
    private final Map<String, String> dict = new HashMap<String, String>();

    public synchronized String translate(String word) throws IllegalArgumentException {
        if (!dict.containsKey(word)) {
            throw new IllegalArgumentException(word + " not found.");
        }
        return dict.get(word);
    }

    public synchronized void addToDictionary(String word, String translation) throws IllegalArgumentException {
        if (dict.containsKey(word)) {
            throw new IllegalArgumentException(word + " already exists.");
        }
        dict.put(word, translation);
    }

    /**
     * BTW: potential problem - resulting set is not thread-safe and what even worse - mutable
     * with every change affecting underlying map.
     */
    public synchronized Set<String> getAllWords() {
        return dict.keySet();
    }
}
