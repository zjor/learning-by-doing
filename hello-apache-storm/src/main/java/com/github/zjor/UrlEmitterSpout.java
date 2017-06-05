package com.github.zjor;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichSpout;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class UrlEmitterSpout implements IRichSpout {

    public static final String NAME = "url_emitter_spout";
    public static final String FIELD_URL = "url";

    private TopologyContext context;
    private SpoutOutputCollector collector;

    private Queue<String> urls = new LinkedList<>();

    {
        urls.offer("http://www.gutenberg.org/cache/epub/8001/pg8001.txt");
    }

    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        this.context = topologyContext;
        this.collector = spoutOutputCollector;
    }

    @Override
    public void close() {

    }

    @Override
    public void activate() {

    }

    @Override
    public void deactivate() {

    }

    @Override
    public void nextTuple() {
        if (!urls.isEmpty()) {
            String url = urls.poll();
            collector.emit(new Values(url));
        } else {
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
            }
        }
    }

    @Override
    public void ack(Object o) {

    }

    @Override
    public void fail(Object o) {

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields(FIELD_URL));
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
