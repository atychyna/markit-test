package org.ant.sandbox.markit;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Anton Tychyna
 */
public class FastDictionary implements Dictionary {
    private final Map<String, String> dict = new ConcurrentHashMap<String, String>();

    public String translate(String word) throws IllegalArgumentException {
        if (!dict.containsKey(word)) {
            throw new IllegalArgumentException(word + " not found.");
        }
        return dict.get(word);
    }

    public void addToDictionary(String word, String translation) throws IllegalArgumentException {
        if (dict.containsKey(word)) {
            throw new IllegalArgumentException(word + " already exists.");
        }
        dict.put(word, translation);
    }

    public Set<String> getAllWords() {
        return dict.keySet();
    }
}
