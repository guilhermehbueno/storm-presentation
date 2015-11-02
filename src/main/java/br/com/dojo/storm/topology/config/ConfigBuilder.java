package br.com.dojo.storm.topology.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import backtype.storm.Config;

public class ConfigBuilder {
	
	public void config(String [] args, Config conf){
		System.out.println("Received args: " + StringUtils.join(args) );
		OptionParser parser = new OptionParser();
		List<String> parametersName = extractParametersName(args);
		parametersName.forEach(param -> {
			String formatedParam = param.replaceAll("-", "");
			System.out.println("formatedParam: "+formatedParam);
			parser.accepts(formatedParam).withOptionalArg();
		});
		
		OptionSet options = parser.parse(args);
		
		parametersName.forEach(param -> {
			if(conf!=null){
				String key = param.replaceAll("-", "");
				Object value = options.valueOf(param.replaceAll("-", "")) + "";
				
				if(StringUtils.isNumeric(value+"")){
					value = Integer.parseInt(value+"");
				}
				conf.put(key, value);
				System.out.println(key + " => "+ value);
			}
		});
	}
	
	List<String> extractParametersName(String...args){
		List<String> parametersName = new ArrayList<String>();
		Arrays.asList(args).forEach(argument -> {
			if(argument.startsWith("-")){
				parametersName.add(argument);
			}
		});
		System.out.println("ParameterNames: "+ StringUtils.join(parametersName));
		return parametersName;
	}
	
	static class ConfigParameter{
		private final String name;
		private final String value;
		
		private ConfigParameter(String name, String value) {
			super();
			this.name = name;
			this.value = value;
		}
		public String getName() {
			return name;
		}
		public String getValue() {
			return value;
		}
	}
}