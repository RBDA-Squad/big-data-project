import java.io.IOException;
import java.lang.Integer;
import org.apache.hadoop.io.IntWritable; 
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.util.List;
import java.util.ArrayList;

public class DataProfilingReducer extends Reducer<Text, IntWritable, Text, Text> {
  @Override
  public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
    String line = key.toString();
    String[] items = line.split(",");
    String handle_case = items[1];
    String handle_case_len = "";
    int sum = 0;

    if (handle_case.indexOf("Crime Type") == -1) { 
      handle_case_len = items[2];

      for (IntWritable value : values) {
        sum += value.get();
      }
    }

    if (handle_case.indexOf("Quotation Mark") != -1) {
      int len = Integer.parseInt(handle_case_len);
      len -= 1;
      String qm_type = "The amount of the data when there is " + Integer.toString(len) + " quotation mark(s):";
      String qm_amount = Integer.toString(sum);
      context.write(new Text(qm_type), new Text(qm_amount));
    }

    if (handle_case.indexOf("Comma") != -1) {
      String cm_type = "The amount of the data when there is " + handle_case_len + " column(s) (splited by comma):";
      String cm_amount = Integer.toString(sum);
      context.write(new Text(cm_type), new Text(cm_amount));
    }
   
    if (handle_case.indexOf("Date") != -1) {
      String dt_type = "The amount of the data when the length of Date string is " + handle_case_len + ":";
      String dt_amount = Integer.toString(sum);
      context.write(new Text(dt_type), new Text(dt_amount));
    }

    if (handle_case.indexOf("Crime Type") != -1) {
      int max = 0;
      int min = 0;

      for (IntWritable value : values) {
        max = Math.max(max, value.get());
        min = Math.min(min, value.get());
      }

      String ct_type = "The maximum length of Crime Type string is: ";
      context.write(new Text(ct_type), new Text(Integer.toString(max)));

      ct_type = "The minimum length of Crime Type string is: ";
      context.write(new Text(ct_type), new Text(Integer.toString(min)));
    }

    if (handle_case.indexOf("Latitude") != -1) {
      String la_type = "The amount of the data when the length of Latitude string is " + handle_case_len + ":";
      String la_amount = Integer.toString(sum);
      context.write(new Text(la_type), new Text(la_amount));
    }

    if (handle_case.indexOf("Longitude") != -1) {
      String lo_type = "The amount of the data when the length of Longitude string is " + handle_case_len + ":";
      String lo_amount = Integer.toString(sum);
      context.write(new Text(lo_type), new Text(lo_amount));
    }
  }
}
