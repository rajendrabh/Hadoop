package BulkImport;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.opencsv.CSVParser;

/**
 * Mapper Class
 */
public class HBaseMapper extends
		Mapper<LongWritable, Text, ImmutableBytesWritable, KeyValue> {
	// Set column family name
	final static byte[] MD_COL_FAM = "ctntElmtProp".getBytes();
	// Number of fields in text file
	final static int NUM_FIELDS = 7;

	CSVParser csvParser = new CSVParser();
	String tableName = "";

	ImmutableBytesWritable hKey = new ImmutableBytesWritable();
	KeyValue kv;

	/** {@inheritDoc} */
	@Override
	protected void setup(Context context) throws IOException,
			InterruptedException {
		Configuration c = context.getConfiguration();
		
		
		// Get parameters
		tableName = c.get("hbase.table.name");
		//String parameter2 = c.get("parameter2");
		// in parameter is now saved: "pass parameter example"
		// ( passed from Driver class )
	}

	/** {@inheritDoc} */
	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {

		String[] fields = null;

		// Failed to parse line
		try {
			fields = csvParser.parseLine(value.toString());
		} catch (Exception ex) {
			context.getCounter("HBaseMapper", "PARSE_ERRORS").increment(1);
			return;
		}

		// Wrong number of cols/fields in a line
		if (fields.length != NUM_FIELDS) {
			context.getCounter("HBaseMapper", "INVALID_FIELD_LEN").increment(
					1);
			return;
		}

		//Setting "DatabaseID_ID" as the rowkey for "md" col family
		String rowKey = fields[1].trim().toString() + "_" + fields[0].trim().toString();
		
		hKey.set(String.format("%s", rowKey).getBytes());

		// If field exists
		if (!fields[2].equals("")) {

			// Save KeyValue Pair
			kv = new KeyValue(hKey.get(), MD_COL_FAM,
					HColEnum.MD_COL_C.getColumnName(), fields[2].getBytes());
			// Write KV to HBase
			context.write(hKey, kv);
		}

		if (!fields[3].equals("")) {
			// Save KeyValue Pair
			kv = new KeyValue(hKey.get(), MD_COL_FAM,
					HColEnum.MD_COL_D.getColumnName(), fields[3].getBytes());
			// Write KV to HBase
			context.write(hKey, kv);
		}
		
		if (!fields[4].equals("")) {
			// Save KeyValue Pair
			kv = new KeyValue(hKey.get(), MD_COL_FAM,
					HColEnum.MD_COL_E.getColumnName(), fields[4].getBytes());
			// Write KV to HBase
			context.write(hKey, kv);
		}
		
		if (fields[5] != null && !fields[5].isEmpty()) {
			// Save KeyValue Pair
			kv = new KeyValue(hKey.get(), MD_COL_FAM,
					HColEnum.MD_COL_F.getColumnName(), fields[5].getBytes());
			// Write KV to HBase
			context.write(hKey, kv);
		}
		
		if (fields[6] != null && !fields[6].isEmpty()) {
			// Save KeyValue Pair
			kv = new KeyValue(hKey.get(), MD_COL_FAM,
					HColEnum.MD_COL_G.getColumnName(), fields[6].getBytes());
			// Write KV to HBase
			context.write(hKey, kv);
		}

		// counter to get cols from the col family
		int i = 2;
		// For each col, insert the corresponding value from the manifest file
		/*for ( HColEnum col : HColEnum.values())
		{
			if (fields[i] != null && !fields[i].isEmpty()) {
				kv = new KeyValue(hKey.get(), MD_COL_FAM, col.getColumnName(), fields[i].getBytes());
				// Write KV to HBase
				context.write(hKey, kv);
			}
		}*/
		
		context.getCounter("HBaseKVMapper", "NUM_MSGS").increment(1);

	}
}
