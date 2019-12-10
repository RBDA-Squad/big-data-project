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
	    String illegalParking = columns[41];
	    String unsanitaryCondition = columns[51];
	    String commercialNoise = columns[52];
	    String rodent = columns[58];
	    String homelessEncampment = columns[75];
	    String x = "";

	    // add log transformed restaurant features
	    for (int i = 1; i <= 38; ++i){
		double log_count = Math.log(Double.parseDouble(columns[i]) + 1);
		x = x + String.valueOf(log_count) + " ";
	    }

            context.write(new Text("illegalParking"), new Text(x + "\t" + String.valueOf(Math.sqrt(Double.parseDouble(illegalParking)))));
            context.write(new Text("unsanitaryCondition"), new Text(x + "\t" + String.valueOf(Math.sqrt(Double.parseDouble(unsanitaryCondition)))));
            context.write(new Text("commercialNoise"), new Text(x + "\t" + String.valueOf(Math.sqrt(Double.parseDouble(commercialNoise)))));
	    context.write(new Text("rodent"), new Text(x + "\t" + String.valueOf(Math.sqrt(Double.parseDouble(rodent)))));
	    context.write(new Text("homelessEncampment"), new Text(x + "\t" + String.valueOf(Math.sqrt(Double.parseDouble(homelessEncampment)))));
        }
}      
            
