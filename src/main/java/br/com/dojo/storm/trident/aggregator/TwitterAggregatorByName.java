package br.com.dojo.storm.trident.aggregator;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Joiner;

import backtype.storm.tuple.Values;
import br.com.dojo.storm.twitter.Tweet;
import storm.trident.operation.BaseAggregator;
import storm.trident.operation.TridentCollector;
import storm.trident.tuple.TridentTuple;

public class TwitterAggregatorByName extends BaseAggregator<TwitterAggregatorByName.CountByName>{
	
	private static final long serialVersionUID = -5005924969799649883L;

	public static class CountByName implements Serializable{
		private static final long serialVersionUID = -2765733190646316762L;
		private final Map<String, Long> count = new HashMap<String, Long>();
		
		public void incr(String userName){
			if(count.containsKey(userName)){
				Long value = count.get(userName);
				//System.out.println("Updating value: "+userName+ " value:"+value);
				count.put(userName, value+1);
			}else{
				//System.out.println("Putting new value: "+userName);
				count.put(userName, 1l);
			}
		}
		
		@Override
		public String toString() {
			final Joiner.MapJoiner mapJoiner = Joiner.on('\t').withKeyValueSeparator("=");
			return mapJoiner.join(count);
		}
	}

	@Override
	public CountByName init(Object batchId, TridentCollector collector) {
		return new CountByName();
	}

	@Override
	public void aggregate(CountByName val, TridentTuple tuple, TridentCollector collector) {
		Tweet tweet = (Tweet) tuple.get(0);
		String screenName = tweet.getScreenName();
		val.incr(screenName);
	}

	@Override
	public void complete(CountByName val, TridentCollector collector) {
		collector.emit(new Values(val));
	}
}