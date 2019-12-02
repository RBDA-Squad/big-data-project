import java.io.IOException;
import java.util.Arrays;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.Mapper;


public class CleaningMapper extends Mapper<LongWritable, Text, Text, NullWritable> {
  private Counter counter1, counter2;
  public enum CustomCounters { NUM_ROWS, NUM_INVALID_ROWS }

  @Override
  public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
    String[] valueSplit = value.toString().split(",", -1);
    if (valueSplit[0].equals("Unique Key")) {
      return;
    }
    // count # of lines
    counter1 = context.getCounter(CustomCounters.NUM_ROWS);
    counter1.increment(1L);

    String[] keySplit = {
      valueSplit[1], valueSplit[5], valueSplit[8], valueSplit[38], valueSplit[39]
    };

    // count invalid # of lines
    for (String el: keySplit){
      if (el.equals("")) {
        counter2 = context.getCounter(CustomCounters.NUM_INVALID_ROWS);
        counter2.increment(1L);
        return;
      }
    }

    context.write(new Text(String.join(",", keySplit)), NullWritable.get());
  }
}
