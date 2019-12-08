import java.io.IOException;

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
	    for (int i = 1; i < 5; ++i){
		x = x + columns[i] + " ";
	    }
	    x = x + columns[5];
            
	    context.write(new Text("Robbery"), new Text(x + "\t" + Robbery));
            context.write(new Text("Burglary"), new Text(x + "\t" + Burglary));
            context.write(new Text("Weapons"), new Text(x + "\t" + Weapons));
	    context.write(new Text("sexCrimes"), new Text(x + "\t" + sexCrimes));
	    context.write(new Text("Murder"), new Text(x + "\t" + Murder));
        }
}      
            
