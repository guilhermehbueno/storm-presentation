package br.com.dojo.storm.trident.filter;

import br.com.dojo.storm.twitter.Tweet;
import storm.trident.operation.BaseFilter;
import storm.trident.tuple.TridentTuple;

public class FilterTweetByScreenName extends BaseFilter{
	
	private static final long serialVersionUID = 1906103937748816758L;
	private final String screenName;

	public FilterTweetByScreenName(String username) {
		super();
		this.screenName = username;
	}

	@Override
	public boolean isKeep(TridentTuple tuple) {
		//System.out.println("Executing: iskeep");
		Tweet tweet = (Tweet) tuple.get(0);
		if(this.screenName.equalsIgnoreCase(tweet.getScreenName())){
			return true;
		}
		return false;
	}
}
