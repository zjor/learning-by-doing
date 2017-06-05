package com.github.zjor;

import lombok.extern.slf4j.Slf4j;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;

import java.util.Comparator;
import java.util.Map;

@Slf4j
public class App {
    public static void main(String[] args) throws InterruptedException {
        Config config = new Config();
        config.setDebug(true);

        TopologyBuilder topologyBuilder = new TopologyBuilder();

        topologyBuilder.setSpout(UrlEmitterSpout.NAME, new UrlEmitterSpout());

        topologyBuilder.setBolt(DocumentFetcherBolt.NAME, new DocumentFetcherBolt())
                .shuffleGrouping(UrlEmitterSpout.NAME);

        topologyBuilder.setBolt(WordSplitterBolt.NAME, new WordSplitterBolt())
                .shuffleGrouping(DocumentFetcherBolt.NAME);

        topologyBuilder.setBolt(WordCounterBolt.NAME, new WordCounterBolt())
                .shuffleGrouping(WordSplitterBolt.NAME);

        LocalCluster cluster = new LocalCluster();

        cluster.submitTopology("MyTopology", config, topologyBuilder.createTopology());

        Thread.sleep(10_000L);

        cluster.shutdown();

        WordFrequencyStorage.dictionary.entrySet().stream()
                .sorted(Comparator.comparingInt(Map.Entry::getValue))
                .forEach(entry -> log.info("{}: {}", entry.getKey(), entry.getValue()));


    }
}
