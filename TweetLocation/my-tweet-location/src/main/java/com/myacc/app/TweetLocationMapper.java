/*
*  Mapper code
*  Input is <Offset, userid:lat:longi:restoftweet:....>
*  Output is < distance, userIdofTweeter>
*
*/

import java.io.IOException;
import java.util.StringTokenizer;
import java.util.*;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class TweetLocationMapper
extends Mapper<LongWritable, Text, IntWritable, Text> {

    private static final String DELIMITER = ":";
    private static final int RADIUS_OF_EARTH_MILES = 3963;
    private static final double GOOGLE_HQ_LAT = 37.42;
    private static final double GOOGLE_HQ_LONG = -122.08;

    private Text tweetId = new Text();

    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        String tweetLine = value.toString();
        List<String> tweetList= new ArrayList<String>();
        tweetList = getTweetDetails(tweetLine);

        if (tweetList.size() >= 3) {
            double lat = Double.parseDouble(tweetList.get(1));
            double longi = Double.parseDouble(tweetList.get(2));

            int geoDistance;
            geoDistance = calculateDistance(lat, longi);

            tweetId.set(tweetList.get(0));
            context.write(new IntWritable(geoDistance), tweetId);
        }
        else {
            System.err.println("Bad Input: " + tweetLine);
        }
    }

    /*
     This function gets the string tokens assuming input file is parsed in such a way that the first token is the tweetId or userid of the tweeter, second token is the latitude, third token is longitude of the geotagged tweet. The rest of the tokens are ignored. It is also assume that the delimiter used in the input file is colon.
    */

    public List getTweetDetails(String record) {
        List<String> list = new ArrayList<String>();
        //System.out.println("Record: " + record);
        String[] words = record.split(":");
        for (int i = 0; i < words.length; i++) {
            list.add(words[i]);
            //System.out.println("words: " + words[i]);
        }

        return list;
    }


    /*
     * This function calculates the distance in miles between
	* GeoLocation (specified by latitude and longitude) of the tweeter
	* and Geo Location of Google's HQ.
    */

    public int calculateDistance(double lat, double longi) {

       double latDis = Math.toRadians(lat - GOOGLE_HQ_LAT);
       double longDis = Math.toRadians(longi - GOOGLE_HQ_LONG);

       double a = (Math.sin(latDis / 2) * Math.sin(latDis / 2)) + 
                  (Math.cos(Math.toRadians(lat))) *
                  (Math.cos(Math.toRadians(GOOGLE_HQ_LAT))) *
                  (Math.sin(longDis / 2)) * 
                  (Math.sin(longDis / 2));

       double angle = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

       return (int) (Math.round(RADIUS_OF_EARTH_MILES * angle));
    }

    /* This method is not needed because I have used a calculateDistance method that works with WGS84 lat longi co-ordinates.

    public String convertWGS84ToNAD27(Double lat, Double longi) {

        List<Double> tLatLong = new ArrayList<Double>();
        // Use Java Topology Suite for conversion.

        return tLatLong;
    }
    */
}
