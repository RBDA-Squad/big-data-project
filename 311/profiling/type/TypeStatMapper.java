import java.io.IOException;
import java.util.*;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class TypeStatMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
  @Override
  public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
    String[] valueSplit = value.toString().split(",", -1);

    String type = valueSplit[1];
    context.write(new Text(type), new IntWritable(1));
  }
}

