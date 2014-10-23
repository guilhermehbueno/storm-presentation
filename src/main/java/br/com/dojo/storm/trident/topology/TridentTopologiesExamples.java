package br.com.dojo.storm.trident.topology;

import storm.trident.TridentTopology;
import backtype.storm.generated.StormTopology;
import backtype.storm.tuple.Fields;
import br.com.dojo.storm.trident.aggregator.TwitterAggregatorByName;
import br.com.dojo.storm.trident.filter.FilterTweetByScreenName;
import br.com.dojo.storm.trident.function.PrintTweetFunction;
import br.com.dojo.storm.trident.spout.FakeTwitterTridentSpout;

public class TridentTopologiesExamples {
	
	public static StormTopology buildTopologyWithFilterByNameAndPrinter(){
		TridentTopology topology = new TridentTopology();
		FakeTwitterTridentSpout fakeTwitterTridentSpout = new FakeTwitterTridentSpout();
		FilterTweetByScreenName filterTweetByScreenName = new FilterTweetByScreenName("doug");
		PrintTweetFunction printTweetFunction = new PrintTweetFunction();
		topology.newStream("spout", fakeTwitterTridentSpout)
				.each(new Fields("tweet"), filterTweetByScreenName)
				.each(new Fields("tweet"), printTweetFunction);
		
		return topology.build();
	}
	
	public static StormTopology buildTopologyWithAggregatorByNameAndPrinter(){
		TridentTopology topology = new TridentTopology();
		FakeTwitterTridentSpout fakeTwitterTridentSpout = new FakeTwitterTridentSpout();
		PrintTweetFunction printTweetFunction = new PrintTweetFunction();
		topology.newStream("spout", fakeTwitterTridentSpout)
				.aggregate(fields("tweet"), new TwitterAggregatorByName(), fields("summary"))
				.project(fields("summary"))
				.each(fields("summary"), printTweetFunction);
		return topology.build();
	}
	
	
	private static Fields fields(String...names){
		return new Fields(names);
	}
}