package com.gray.tools;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;
import org.apache.hadoop.util.Tool;

public class ConnectMysql extends Configured implements Tool {
	
	public static class TblsWritable implements Writable, DBWritable {

		@Override
		public void readFields(ResultSet arg0) throws SQLException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void write(PreparedStatement arg0) throws SQLException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void readFields(DataInput arg0) throws IOException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void write(DataOutput arg0) throws IOException {
			// TODO Auto-generated method stub
			
		}  
		
	}

	@Override
	public int run(String[] arg0) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

}