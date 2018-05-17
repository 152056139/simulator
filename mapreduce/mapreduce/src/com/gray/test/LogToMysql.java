package com.gray.test;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.lib.db.DBOutputFormat;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class LogToMysql extends Configured implements Tool {

	/**
	 * 实现LogInfo
	 * 
	 * LogInfo需要向mysql中写入数据
	 */

	public static class LogInfo implements Writable, DBWritable {

		private String logType;// 数据类型
		private String logDate;// 数据时间
		private int logNum;// 数量

		public LogInfo() {
			super();
		}

		public LogInfo(String logType, String logDate, int logNum) {
			super();
			this.logType = logType;
			this.logDate = logDate;
			this.logNum = logNum;
		}

		@Override
		public void readFields(ResultSet resultSet) throws SQLException {
			this.logType = resultSet.getString(1);
			this.logDate = resultSet.getString(2);
			this.logNum = resultSet.getInt(3);

		}

		@Override
		public void write(PreparedStatement statement) throws SQLException {
			statement.setString(1, this.logType);
			statement.setString(2, this.logDate);
			statement.setInt(3, this.logNum);

		}

		@Override
		public void readFields(DataInput in) throws IOException {

		}

		@Override
		public void write(DataOutput out) throws IOException {
			// TODO Auto-generated method stub

		}

		@Override
		public String toString() {
			return "LogInfo [logType=" + logType + ", logDate=" + logDate + ", logNum=" + logNum + "]";
		}

	}

	public static class LogMapClass extends Mapper<LongWritable, Text, Text, LongWritable> {

		String tempTime = null;

		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

			Text newText = transformTextToUTF8(value, "GBK");
			String oneLine = newText.toString();
			if (oneLine.startsWith("201")) {
				tempTime = oneLine.substring(0, 10);
			}
			;

			if (oneLine.contains("[") && oneLine.contains("]")) {
				String logInfo = oneLine.substring(oneLine.indexOf("[") + 1, oneLine.indexOf("]"));

				context.write(new Text(tempTime + "," + logInfo), new LongWritable(1));
			} else if (!StringUtils.isBlank(oneLine)) {
				context.write(new Text(tempTime + "," + "OTHER"), new LongWritable(1));
			}

		}

		private Text transformTextToUTF8(Text text, String encoding) {
			String value = null;
			try {
				value = new String(text.getBytes(), 0, text.getLength(), encoding);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return new Text(value);
		}

	}

	public static class LogReducerClass extends Reducer<Text, LongWritable, LogInfo, LogInfo> {
		@Override
		protected void reduce(Text key, Iterable<LongWritable> values, Context context)
				throws IOException, InterruptedException {
			long count = 0;
			for (LongWritable value : values) {
				count += value.get();
			}

			String[] arr = key.toString().split(",");

			context.write(new LogInfo(arr[1], arr[0], (int) count), null);

		}
	}

	public int run(String[] arg0) throws Exception {
		// 读取配置文件
		Configuration conf = new Configuration();
		DBConfiguration.configureDB(conf, "com.mysql.jdbc.Driver", "jdbc:mysql://192.168.220.151:3306/test", "root",
				"newpass");

		// 新建一个任务
		Job job = new Job(conf, "DBOutputormatDemo");
		// 设置主类
		job.setJarByClass(LogToMysql.class);

		// 输入路径
		FileInputFormat.addInputPath(job, new Path("E:\\hadooptestdata\\in\\log"));
		// Mapper
		job.setMapperClass(LogMapClass.class);
		// Reducer
		job.setReducerClass(LogReducerClass.class);

		// mapper输出格式
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(LongWritable.class);

		// 输出格式
		job.setOutputFormatClass(DBOutputFormat.class);

		// 输出到哪些表、字段
		DBOutputFormat.setOutput(job, "logInfoNum", "logType", "logDate", "logNum");

		// 提交任务
		return job.waitForCompletion(true) ? 0 : 1;
	}

	public static void main(String[] args) throws Exception {
		// 数据输入路径和输出路径
		System.setProperty("hadoop.home.dir", "E:\\hadjar\\hadoop-2.4.1-x64\\hadoop-2.4.1\\");

		int ec = ToolRunner.run(new Configuration(), new LogToMysql(), args);
		System.exit(ec);
	}

}
