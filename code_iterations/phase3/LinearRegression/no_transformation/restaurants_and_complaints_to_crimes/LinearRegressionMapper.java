import java.io.IOException;
import java.lang.Math;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class LinearRegressionMapper
    extends Mapper<LongWritable, Text, Text, Text> {
        
	/*
 	num_of_rts = 38
	num_of_cps = 122
	num_of_cms = 43
	Index:
		zipcode: 0
		restaurants: 1 - 38
		complaints: 39 - 160
		crimes: 161 - 203
	*/
        @Override
        public void map(LongWritable key, Text value, Context context) 
              throws IOException, InterruptedException {
            String line = value.toString();
            String[] columns = line.split("\t");
	    String Robbery = columns[168];
	    String Burglary = columns[174];
	    String Weapons = columns[172];
	    String sexCrimes = columns[178];
	    String Murder = columns[186];
	    String x = "";

	    for (int i = 1; i <= 160; ++i){
		x = x + columns[i] + " ";
	    }

            context.write(new Text("Robbery"), new Text(x + "\t" + Robbery));
            context.write(new Text("Burglary"), new Text(x + "\t" + Burglary));
            context.write(new Text("Weapons"), new Text(x + "\t" + Weapons));
	    context.write(new Text("sexCrimes"), new Text(x + "\t" + sexCrimes));
	    context.write(new Text("Murder"), new Text(x + "\t" + Murder));
        }
}      
            
