package br.com.dojo.storm.trident.spout;

import java.util.Map;

import backtype.storm.task.TopologyContext;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import br.com.dojo.storm.twitter.FakeTwitterStream;
import br.com.dojo.storm.twitter.Tweet;
import storm.trident.operation.TridentCollector;
import storm.trident.spout.IBatchSpout;

public class FakeTwitterTridentSpout implements IBatchSpout{

	private static final long serialVersionUID = 965678462568013664L;
	private FakeTwitterStream fakeTwitterStream;
	private int BATCH_SIZE=100;

	@Override
	public void open(Map conf, TopologyContext context) {
		//System.out.println("Executing open...");
		try {
			this.fakeTwitterStream = new FakeTwitterStream(-1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void emitBatch(long batchId, TridentCollector collector) {
		//System.out.println("Emiting Batch...");
		for (int i = 0; i < BATCH_SIZE; i++) {
			Tweet tweet = getNextTweet();
			if(tweet!=null){
				//System.out.println("Emitting: "+tweet);
				Values values = new Values(tweet);
				collector.emit(values);
			}
		}
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public Tweet getNextTweet(){
		return this.fakeTwitterStream.getNextTweet();
	}

	@Override
	public void ack(long batchId) {
		//System.out.println("Acking: "+batchId);
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Fields getOutputFields() {
		return new Fields("tweet");
	}
	
	public static void main(String[] args) {
		FakeTwitterTridentSpout spout = new FakeTwitterTridentSpout();
		spout.open(null, null);
		System.out.println(spout.getNextTweet());
	}
}
