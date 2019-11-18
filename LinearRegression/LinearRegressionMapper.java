import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class LinearRegressionMapper
    extends Mapper<LongWritable, Text, Text, IntWritable> {
        
        @Override
        public void map(LongWritable key, Text value, Context context) 
              throws IOException, InterruptedException {
            String line = value.toString();
            String columns = line.split("\t");
            String zip_code = columns[0];
            String rst_count = columns[1];
            String 311_count = columns[2];
            String arrest_count = columns[3];
            
            context.write(new Text("rst->311"), new Text(rst_count + "\t" + 311_count));
            context.write(new Text("rst->arr"), new Text(rst_count + "\t" + arrest_count));
            context.write(new Text("311->arr"), new Text(311_count + "\t" + arrest_count));
        }
}      
            