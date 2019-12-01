import java.io.IOException;
import java.util.*;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class ComplaintTypeCountReducer extends Reducer<Text, Text, Text, NullWritable> {
  @Override
  public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
    HashMap<String, Long> map = new HashMap<String, Long>(); 

    for(Text text: values) {
      String type = text.toString();
      Long count = map.get(type);

      if (count == null) {
        map.put(type, 1L);
      } else {
        map.put(type, ++count);
      }
    }

    for (Map.Entry<String, Long> entry : map.entrySet()) {
      String k = entry.getKey();
      Long v = entry.getValue();

      String[] outputArray = {key.toString(), k, v.toString()};
      String output = String.join(",", outputArray);
      context.write(new Text(output), NullWritable.get());
    }
  }
}
