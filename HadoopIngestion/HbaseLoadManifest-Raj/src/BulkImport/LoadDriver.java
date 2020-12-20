package BulkImport;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.HFileOutputFormat2;
import org.apache.hadoop.hbase.mapreduce.LoadIncrementalHFiles;
//import org.apache.hadoop.hbase.regionserver.metrics.SchemaMetrics;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * HBase bulk import <br>
 * Data preparation MapReduce job driver
 * <ol>
 * <li>args[0]: HDFS input path
 * <li>args[1]: HDFS mapper output path
 * <li>args[2]: HBase table name (Argument Format 'ns:table_name')
 * </ol>
 */
public class LoadDriver {
	public static void main(String[] args) throws Exception {
		// HBase Configuration
		Configuration conf = new Configuration();
		
		// Pass parameters to Map Reduce
		conf.set("hbase.table.name", args[2]);
		conf.set("parameter2", "pass parameter example");
		
		// Workaround
		//SchemaMetrics.configureGlobally(conf);
		
		// Load hbase-site.xml
		HBaseConfiguration.addHbaseResources(conf);
		
		// Create the job
		Job job = Job.getInstance();
	    job.setJarByClass(LoadDriver.class);
	    
		job.setJobName("HBase Bulk Import of Lotus Notes - Manifest only");
		//job.setJarByClass(HBaseMapper.class);
		job.setMapperClass(HBaseMapper.class);
		job.setMapOutputKeyClass(ImmutableBytesWritable.class);
		job.setMapOutputValueClass(KeyValue.class);
		job.setInputFormatClass(TextInputFormat.class);
		
		// Get the table
		// TO DO - use connection pool here to get the handle
		HTable hTable = new HTable(conf, args[2]);
		
		// Auto configure partitioner and reducer
		HFileOutputFormat2.configureIncrementalLoad(job, hTable);
		
		// Save output path and input path
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		// Wait for HFiles creations
		job.waitForCompletion(true);
		
		// Load generated HFiles into Hbase table
		LoadIncrementalHFiles loader = new LoadIncrementalHFiles(conf);
		loader.doBulkLoad(new Path(args[1]), hTable);
	}
}
