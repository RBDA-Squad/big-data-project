import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class DataCleaningMapper
    extends Mapper<LongWritable, Text, Text, NullWritable> {
        
        @Override
        public void map(LongWritable key, Text value, Context context) 
              throws IOException, InterruptedException {
            String line = value.toString();
            String[] columns = line.split("\t", -1);
            String CAMIS = columns[0];
            String BORO = columns[2];
            String ZIPCODE = columns[5];
            String CUISINE = columns[7];
            String LATITUDE = columns[18];
            String LONGITUDE = columns[19];
            String newline;
            // skip header and bad records
            if (CAMIS.equals("CAMIS") || (CAMIS.length() != 8 || BORO.equals("0") || ZIPCODE????? ||CUISINE.equals("") || LATITUDE.length() <= 1 || LONGITUDE.length() <= 1));
            else{
                newline = String.join("\t", CAMIS, BORO, ZIPCODE, CUISINE, LATITUDE, LONGITUDE);
                context.write(new Text(newline), NullWritable.get());
            }
        }
}