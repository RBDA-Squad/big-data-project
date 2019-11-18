import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class AreaAccountMapper extends Mapper<LongWritable, Text, Text, Text> {
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

    boolean drop = false;
    if (items2[8].equals("S")) {
      items2[8] = "Staten Island";
    } else if (items2[8].equals("Q")) {
      items2[8] = "Queens";
    } else if (items2[8].equals("M")) {
      items2[8] = "Manhattan";
    } else if (items2[8].equals("K")) {
      items2[8] = "Brooklyn";
    } else if (items2[8].equals("B")) {
      items2[8] = "Bronx";
    } else {
      drop = true;
    }

    if (drop == false) {
      context.write(new Text(items2[8]), new Text(line));
    }
  }
}
