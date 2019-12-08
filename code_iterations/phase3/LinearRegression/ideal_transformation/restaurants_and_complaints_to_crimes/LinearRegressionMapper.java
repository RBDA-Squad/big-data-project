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

	    // add log transformed restaurant features
	    for (int i = 1; i <= 38; ++i){
		double log_count = Math.log(Double.parseDouble(columns[i]) + 1);
		x = x + String.valueOf(log_count) + " ";
	    }

	    // add sqrt transformed complaint features
	    for (int i = 39; i <= 160; ++i){
		double sqrt_count = Math.sqrt(Double.parseDouble(columns[i]));
		x = x + String.valueOf(sqrt_count) + " ";
	    }
            context.write(new Text("Robbery"), new Text(x + "\t" + String.valueOf(Math.log(Double.parseDouble(Robbery) + 1))));
            context.write(new Text("Burglary"), new Text(x + "\t" + String.valueOf(Math.log(Double.parseDouble(Burglary) + 1))));
            context.write(new Text("Weapons"), new Text(x + "\t" + String.valueOf(Math.log(Double.parseDouble(Weapons) + 1))));
	    context.write(new Text("sexCrimes"), new Text(x + "\t" + String.valueOf(Math.log(Double.parseDouble(sexCrimes) + 1))));
	    context.write(new Text("Murder"), new Text(x + "\t" + String.valueOf(Math.log(Double.parseDouble(Murder) + 1))));
        }
}      
            
