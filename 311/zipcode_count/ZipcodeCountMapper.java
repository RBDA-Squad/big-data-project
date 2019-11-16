import java.io.IOException;
import java.util.*;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Mapper;

public class ZipcodeCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
  @Override
  public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
    String[] valueSplit = value.toString().split(",", -1);

    String zipcode = valueSplit[2];
    System.out.println(zipcode);
    context.write(new Text(zipcode), new IntWritable(1));
  }
}
