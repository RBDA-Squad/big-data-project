import java.io.IOException;
import java.lang.Math;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class LinearRegressionMapper
    extends Mapper<LongWritable, Text, Text, Text> {
        
        @Override
        public void map(LongWritable key, Text value, Context context) 
              throws IOException, InterruptedException {
            String line = value.toString();
            String[] columns = line.split("\t");
	    int num_of_features = 10;
            String zipCode = columns[0];
            String American = columns[1];
            String Chinese = columns[2];
            String Mexican = columns[3];
            String Italian = columns[4];
	    String Japanese = columns[5];
	    String Noise = columns[6];
	    String Homeless = columns[7];
	    String animalAbuse = columns[8];
	    String Safety = columns[9];
	    String drugActivity = columns[10];
	    String Robbery = columns[11];
	    String Burglary = columns[12];
	    String Weapons = columns[13];
	    String sexCrimes = columns[14];
	    String Murder = columns[15];
	    String x = "";
	    for (int i = 1; i <= num_of_features; ++i){
		double log_count = Math.log(Double.parseDouble(columns[i]) + 1);
		x = x + String.valueOf(log_count) + " ";
	    }
            context.write(new Text("Robbery"), new Text(x + "\t" + String.valueOf(Math.log(Double.parseDouble(Robbery) + 1))));
            context.write(new Text("Burglary"), new Text(x + "\t" + String.valueOf(Math.log(Double.parseDouble(Burglary) + 1))));
            context.write(new Text("Weapons"), new Text(x + "\t" + String.valueOf(Math.log(Double.parseDouble(Weapons) + 1))));
	    context.write(new Text("sexCrimes"), new Text(x + "\t" + String.valueOf(Math.log(Double.parseDouble(sexCrimes) + 1))));
	    context.write(new Text("Murder"), new Text(x + "\t" + String.valueOf(Math.log(Double.parseDouble(Murder) + 1))));
        }
}      
            
