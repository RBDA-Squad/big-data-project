import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class DataProfilingMapper
    extends Mapper<LongWritable, Text, Text, IntWritable> {
        
        @Override
        public void map(LongWritable key, Text value, Context context) 
              throws IOException, InterruptedException {
            String line = value.toString();
            String[] columns = line.split("\t", -1);
            String CAMIS = columns[0];
            String BORO = columns[2];
            String CUISINE = columns[7];
            String LATITUDE = columns[18];
            String LONGITUDE = columns[19];
            String newline;
            // check the line is not header
            if (!CAMIS.equals("CAMIS")){
                // profile camis_length
                int camis_length = CAMIS.length();
                String camis_key = "camis length: " + camis_length;
                context.write(new Text(camis_key), new IntWritable(1));
                // profile BORO
                String boro_key = "boro: " + BORO;
                context.write(new Text(boro_key), new IntWritable(1));
                // profile CUISINE
                String cuisine_key = "cuisine: " + CUISINE;
                context.write(new Text(cuisine_key), new IntWritable(1));
                // profile latitude_length, longitude_length
                int latitude_length = LATITUDE.length();
                int longitude_length = LONGITUDE.length();
                String latitude_key = "latitude length: " + latitude_length;
                String longitude_key = "longitude_length: " + longitude_length;
                context.write(new Text(latitude_key), new IntWritable(1));
                context.write(new Text(longitude_key), new IntWritable(1));
                }
        }
}      
            