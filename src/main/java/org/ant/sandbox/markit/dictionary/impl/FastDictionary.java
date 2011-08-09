package org.ant.sandbox.markit.dictionary.impl;

import org.ant.sandbox.markit.dictionary.Dictionary;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Anton Tychyna
 */
public class FastDictionary implements Dictionary {
    private final ConcurrentMap<String, String> dict = new ConcurrentHashMap<String, String>();

    public String translate(String word) throws IllegalArgumentException {
        if (!dict.containsKey(word)) {
            throw new IllegalArgumentException(word + " not found.");
        }
        // assuming words are not removed from dictionary
        return dict.get(word);
    }

    public void addToDictionary(String word, String translation) throws IllegalArgumentException {
        String oldTranslation = dict.putIfAbsent(word, translation);
        if (oldTranslation != null) {
            throw new IllegalArgumentException(word + " already exists.");
        }
    }

    public Set<String> getAllWords() {
        return dict.keySet();
    }
}
