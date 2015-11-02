package br.com.dojo.storm.topology.config;

import java.util.List;

import junit.framework.Assert;

import org.testng.annotations.Test;

public class ConfigBuilderTest {
	
	@Test
	public void shouldParseArgs(){
		new ConfigBuilder().config(new String[]{"-printer.bolt.parallelism","20","-topology.message.timeout.secs","60","-topology.max.spout.pending","20","-topology.acker.executors","20","-num.workers","3","-rabbit.prefetch.count","10","-CLUSTER","CLUSTER","-storm.log.dir","/var/log/storm","-deployed_by", "guilhermehbueno"}, null);
	}
	
	@Test
	public void shouldExtractParametersName(){
		List<String> names = new ConfigBuilder().extractParametersName("-a","b");
		Assert.assertNotNull(names);
		Assert.assertTrue(names.contains("-a"));
		Assert.assertTrue(names.size() == 1);
	}
	
	@Test
	public void shouldExtractAllParametersName(){
		//List<String> names = new ConfigBuilder().extractParametersName("-printer.bolt.parallelism","20","-topology.message.timeout.secs","60","-topology.max.spout.pending","20","-topology.acker.executors","20","-num.workers","3","-rabbit.prefetch.count","10","-CLUSTER","CLUSTER","-storm.log.dir","/var/log/storm","-deployed_by", "guilhermehbueno");
		List<String> names = new ConfigBuilder().extractParametersName("-printer.bolt.parallelism", "20", "-topology.message.timeout.secs", "60", "-topology.max.spout.pending", "20", "-topology.acker.executors", "20", "-num.workers", "3", "-rabbit.prefetch.count", "10", "-CLUSTER", "CLUSTER", "-storm.log.dir", "/var/log/storm", "-deployed_by", "guilhermehbueno");
		Assert.assertNotNull(names);
		Assert.assertTrue(names.size() >0);
	}
}