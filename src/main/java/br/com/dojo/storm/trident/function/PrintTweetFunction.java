package br.com.dojo.storm.trident.function;

import storm.trident.operation.BaseFilter;
import storm.trident.tuple.TridentTuple;

public class PrintTweetFunction extends BaseFilter{

	private static final long serialVersionUID = 3676772945883854230L;

	@Override
	public boolean isKeep(TridentTuple tuple) {
		System.out.println(tuple.get(0));
		return true;
	}
}
