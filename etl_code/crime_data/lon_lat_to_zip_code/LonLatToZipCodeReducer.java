import java.io.IOException;
import java.lang.Integer;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.util.List;
import java.util.ArrayList;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FSDataInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class LonLatToZipCodeReducer extends Reducer<Text, Text, Text, Text> {
  @Override
  public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
    String line = "";
    String[] items;
    String[] items2;
    double lat1;
    double lon1;
    double lat2;
    double lon2;
    String tmp_use = "";
    int count = 0;
    double s = 0.0;

    Path path = new Path("hdfs://dumbo/user/syc574/project/zct_output/part-r-00000");
    FileSystem fs = path.getFileSystem(context.getConfiguration()); // context of mapper or reducer
    FSDataInputStream zc_table = fs.open(path);

    for (Text value : values) {
      line = value.toString();
      items = line.split(",");
      lat1 = Double.parseDouble(items[4]);
      lon1 = Double.parseDouble(items[5]);

      double min_n = 100000.00;
      String min_zipcode = "";

      try {
        BufferedReader br = new BufferedReader(new InputStreamReader(zc_table));

        String line_tmp = "";
        count = 0;
        while ((line_tmp = br.readLine()) != null) {
          items2 = line_tmp.split(",");
          String zipcode = items2[0];
          lat2 = Double.parseDouble(items2[1]);
          lon2 = Double.parseDouble(items2[2]);

          double radLat1 = lat1 * Math.PI / 180.0;
          double radLon1 = lon1 * Math.PI / 180.0;
          double radLat2 = lat2 * Math.PI / 180.0;
          double radLon2 = lon2 * Math.PI / 180.0;

          double dlon = radLon2 - radLon1;
          double dlat = radLat2 - radLat1;

          s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(dlat / 2), 2)
              + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(dlon / 2), 2)));
          s = s * 6378.137;
          s = Math.round(s * 10000) / 10000;

          count += 1;

          if (s < min_n) {
            min_n = s;
            min_zipcode = zipcode;
            tmp_use = min_zipcode;
          }
        }

      } catch (Exception e) {
        e.printStackTrace();
      }

      String key2 = items[0] + ",";
      String value2 = items[1] + "," + items[2] + "," + items[3] + "," + items[4] + "," + items[5] + "," + tmp_use;
      context.write(new Text(key2), new Text(value2));
    }
  }
}
