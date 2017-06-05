package com.github.zjor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WordFrequencyStorage {

    public static Map<String, Integer> dictionary = new ConcurrentHashMap<>();

}
