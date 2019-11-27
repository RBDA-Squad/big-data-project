import java.io.IOException;
import java.util.*;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Mapper;

public class ComplaintTypeCountMapper extends Mapper<LongWritable, Text, Text, Text> {
  @Override
  public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
    String[] valueSplit = value.toString().split(",", -1);

    String complaint_type = valueSplit[1];
    String zipcode = valueSplit[2];
    if (zipcode.matches("\\d{5}")) { 
      context.write(new Text(zipcode), new Text(complaint_type));
    }
  }
}
