package com.github.zjor;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Tuple;

import java.util.Map;

public class WordCounterBolt implements IRichBolt {

    public static final String NAME = "word_counter_bolt";

    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {

    }

    @Override
    public void execute(Tuple tuple) {
        String word = tuple.getStringByField(WordSplitterBolt.FIELD_WORD);
        WordFrequencyStorage.dictionary.putIfAbsent(word, 0);
        WordFrequencyStorage.dictionary.put(word, WordFrequencyStorage.dictionary.get(word) + 1);
    }

    @Override
    public void cleanup() {

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
