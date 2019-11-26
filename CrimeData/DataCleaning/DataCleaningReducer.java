import java.io.IOException;
import java.lang.Integer;
import org.apache.hadoop.io.IntWritable; 
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.util.List;
import java.util.ArrayList;

public class DataCleaningReducer extends Reducer<Text, Text, Text, Text> {
  @Override
  public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
    String tmpStr = "";

    for (Text value : values) {
      tmpStr = value.toString();
    }

    context.write(key, new Text(tmpStr));
  }
}
