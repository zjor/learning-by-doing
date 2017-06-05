package com.github.zjor;

import lombok.extern.slf4j.Slf4j;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;

@Slf4j
public class DocumentFetcherBolt implements IRichBolt {
    public static final String NAME = "document_fetcher_bolt";
    public static final String FIELD_DOCUMENT = "document";

    private OutputCollector collector;

    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.collector = outputCollector;
    }

    @Override
    public void execute(Tuple tuple) {
        String url = tuple.getStringByField(UrlEmitterSpout.FIELD_URL);
        log.info("Fetching URL: {}", url);
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new URL(url).openStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                collector.emit(new Values(inputLine));
            }
        } catch (Exception e) {
            log.error("Failed to process batch of data: " + e.getMessage(), e);
        }
    }

    @Override
    public void cleanup() {

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields(FIELD_DOCUMENT));
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
