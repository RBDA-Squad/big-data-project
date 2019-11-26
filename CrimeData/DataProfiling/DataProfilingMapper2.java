import java.io.IOException;
import org.apache.hadoop.io.IntWritable; 
import org.apache.hadoop.io.LongWritable; 
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class DataProfilingMapper2 extends Mapper<LongWritable, Text, Text, Text> {
  @Override
  public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
    String line = value.toString();

    String[] items1 = line.split("\"");
    if (items1.length == 3) {
      line = items1[0] + items1[2];
    }

    String[] items2 = line.split(",");
    if (items2.length == 18) {
      line = items2[1] + "," + items2[5] + "," + items2[16] + "," + items2[17];
    }

    context.write(new Text(items2[5]), new Text(line));
  }
}
