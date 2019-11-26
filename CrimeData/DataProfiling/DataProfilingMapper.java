import java.io.IOException;
import org.apache.hadoop.io.IntWritable; 
import org.apache.hadoop.io.LongWritable; 
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class DataProfilingMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
  private final static IntWritable one = new IntWritable(1);

  @Override
  public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
    String line = value.toString();

    String[] items1 = line.split("\"");
    String qt_len = Integer.toString(items1.length);
    String qt_key = "1,Quotation Mark," + qt_len;
    context.write(new Text(qt_key), one);

    if (items1.length == 3) {
      line = items1[0] + items1[2];
    }

    String[] items2 = line.split(",");
    String cm_len = Integer.toString(items2.length);
    String cm_key = "2,Comma," + cm_len;
    context.write(new Text(cm_key), one);

    if (items2.length == 18) {
      String dt_len = Integer.toString(items2[1].length());
      String dt_key = "3,Date," + dt_len;
      context.write(new Text(dt_key), one);

      String ct_len = Integer.toString(items2[5].length());
      String ct_key = "4,Crime Type";
      context.write(new Text(ct_key), new IntWritable(Integer.parseInt(ct_len)));

      String la_len = Integer.toString(items2[16].length());
      String la_key = "5,Latitude," + la_len;
      context.write(new Text(la_key), one);

      String lo_len = Integer.toString(items2[17].length());
      String lo_key = "6,Longitude," + lo_len;
      context.write(new Text(lo_key), one);
    }
  }
}
