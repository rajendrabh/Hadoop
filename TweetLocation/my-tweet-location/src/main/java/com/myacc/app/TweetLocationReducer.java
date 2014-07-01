/*
*  Reducer code
*    Input is <distance, List(tweetIds);
*    Output is <tweetId<TAB>distance>
*
*/

import java.io.IOException;
import java.util.*;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class TweetLocationReducer extends Reducer<IntWritable, Text, Text, IntWritable> {

    @Override
    public void reduce(IntWritable key, Iterable<Text> values,
                 Context context) throws IOException, InterruptedException {

        for (Text value : values) {
            context.write(value, key);
        }
    }
}
