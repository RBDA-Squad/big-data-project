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
	    for (int i = 1; i <= 5; ++i){
		double log_count = Math.log(Double.parseDouble(columns[i] + 1));
		x = x + String.valueOf(log_count) + " ";
	    }
            context.write(new Text("Noise"), new Text(x + "\t" + String.valueOf(Math.sqrt(Double.parseDouble(Noise)))));
            context.write(new Text("Homeless"), new Text(x + "\t" + String.valueOf(Math.sqrt(Double.parseDouble(Homeless)))));
            context.write(new Text("animalAbuse"), new Text(x + "\t" + String.valueOf(Math.sqrt(Double.parseDouble(animalAbuse)))));
	    context.write(new Text("Safety"), new Text(x + "\t" + String.valueOf(Math.sqrt(Double.parseDouble(Safety)))));
	    context.write(new Text("drugActivity"), new Text(x + "\t" + String.valueOf(Math.sqrt(Double.parseDouble(drugActivity)))));
        }
}      
            
