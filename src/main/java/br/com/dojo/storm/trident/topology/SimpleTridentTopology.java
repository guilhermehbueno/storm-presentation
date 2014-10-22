package br.com.dojo.storm.trident.topology;

import backtype.storm.Config;
import backtype.storm.LocalCluster;

public class SimpleTridentTopology {
	
	public static void main(String[] args) {
		Config conf = new Config();
		LocalCluster cluster = new LocalCluster();
		//cluster.submitTopology("teste", conf, TridentTopologiesExamples.buildTopologyWithFilterByNameAndPrinter());
		cluster.submitTopology("teste", conf, TridentTopologiesExamples.buildTopologyWithAggregatorByNameAndPrinter());
	}
}