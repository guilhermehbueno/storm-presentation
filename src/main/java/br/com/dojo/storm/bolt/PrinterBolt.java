package br.com.dojo.storm.bolt;

import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;

public class PrinterBolt extends BaseRichBolt{

	private static final long serialVersionUID = 7897551985817409932L;
	private OutputCollector collector;

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
	}

	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
	}

	@Override
	public void execute(Tuple input) {
		try{
			System.out.println(input.getValue(0));
			this.collector.ack(input);
		}catch(Exception exc){
			this.collector.fail(input);
		}
	}
}