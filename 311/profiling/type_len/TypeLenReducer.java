import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class TypeLenReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
  @Override
  public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
    int max = 0;

    for(IntWritable val: values) {
    	int v = val.get();
        if (v > max) {
          max = v;
        }
    }

    context.write(key, new IntWritable(max));
  }
}

