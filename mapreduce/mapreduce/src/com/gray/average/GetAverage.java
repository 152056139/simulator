package com.gray.average;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;

public class GetAverage {

	public static void main(String[] args) throws IOException {
		JobConf conf = new JobConf(GetAverage.class);

		conf.setJobName("BodyTemperature");
		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(IntWritable.class);
		
		switch(args[2]) {
		case "BodyTemperature":
			conf.setMapperClass(MapperBodyTemperature.class);
			break;
		case "LowBloodPressure":
			conf.setMapperClass(MapperLowBloodPressure.class);
			break;
		case "HighBloodPressure":
			conf.setMapperClass(MapperHighBloodPressure.class);
		case "HeartRate":
			conf.setMapperClass(MapperHeartRate.class);
			break;
		case "BreathRate":
			conf.setMapperClass(MapperBreateRate.class);
			break;
		}
		
		conf.setCombinerClass(MyReducer.class);
		conf.setReducerClass(MyReducer.class);
		conf.setInputFormat(TextInputFormat.class);
		conf.setOutputFormat(TextOutputFormat.class);

		FileInputFormat.setInputPaths(conf, new Path(args[0]));
		FileOutputFormat.setOutputPath(conf, new Path(args[1]));
		
		JobClient.runJob(conf);
	}
}
