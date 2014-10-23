package br.com.dojo.storm.trident.topology;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.utils.Utils;

public class SimpleTridentTopology {
	public static void main(String[] args) throws Exception {
		List<String> params = new ArrayList<String>();
		for (String arg : args) {
			String[] argValues = arg.split(" ");
			params.addAll(Arrays.asList(argValues));
		}
		if(!params.contains("CLUSTER")){
			SimpleTridentTopology.runLocal(240000);
		}else{
			SimpleTridentTopology.runCluster("DOJO");
		}
	}
	
	public static void runLocal(int runTime) {
		Config conf = new Config();
		LocalCluster cluster=new LocalCluster();;
		conf.setDebug(false);
		conf.setMaxTaskParallelism(100);
		cluster.submitTopology("test", conf, TridentTopologiesExamples.buildTopologyWithAggregatorByNameAndPrinter());
		if (runTime > 0) {
			Utils.sleep(runTime);
			cluster.killTopology("test");
			cluster.shutdown();
		}
	}
	
	public static void runCluster(String name) throws AlreadyAliveException, InvalidTopologyException {
		Config conf = new Config();
		StormSubmitter.submitTopology(name, conf, TridentTopologiesExamples.buildTopologyWithAggregatorByNameAndPrinter());
	}
}