import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Counter;

public class YearStatMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
  private Counter counter;
  public enum CustomCounter { NUM_INVALID_YEAR }

  @Override
  public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
    String[] valueSplit = value.toString().split(",", -1);

    Date date = new Date();
    String time = valueSplit[0];
    SimpleDateFormat parser = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a");
    try {
      date = parser.parse(time);
    } catch (Exception e) {
      counter = context.getCounter(CustomCounter.NUM_INVALID_YEAR);
      counter.increment(1L);
      return;
    }
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
    String year = formatter.format(date);

    context.write(new Text(year), new IntWritable(1));
  }
}


