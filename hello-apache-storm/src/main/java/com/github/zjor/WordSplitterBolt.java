package com.github.zjor;

import lombok.extern.slf4j.Slf4j;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.Arrays;
import java.util.Map;

@Slf4j
public class WordSplitterBolt implements IRichBolt {

    public static final String NAME = "word_splitter_bolt";
    public static final String FIELD_WORD = "word";

    private OutputCollector collector;

    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.collector = outputCollector;
    }

    @Override
    public void execute(Tuple tuple) {
        String data = tuple.getStringByField(DocumentFetcherBolt.FIELD_DOCUMENT);
        Arrays.stream(data.split("\\s"))
                .filter(word -> word.length() > 3)
                .map(String::toLowerCase)
                .forEach(word -> collector.emit(new Values(word)));
    }

    @Override
    public void cleanup() {

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields(FIELD_WORD));
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
