import org.apache.hadoop.fs.FSDataInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;


public class DataCleaningReducer extends Reducer<Text, Text, Text, Text> {
  @Override
  public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
    String line = "";
    String[] items;
    String[] items2;
    double lat1;
    double lon1;
    double lat2;
    double lon2;


    Path path = new Path("hdfs://dumbo/user/syc574/FinalProject/output2/part-r-00000");
    FileSystem fs = path.getFileSystem(context.getConfiguration()); // context of mapper or reducer
    FSDataInputStream fdsis = fs.open(path);


    for (Text value : values) {
      line = value.toString();
      items = line.split(",");
      lat1 = Double.parseDouble(items[3]);
      lon1 = Double.parseDouble(items[4]);

      double min_n = Double.MAX_VALUE;
      String min_zipcode = "";

      try {
        BufferedReader br = new BufferedReader(new InputStreamReader(fdsis));

        String line_tmp = "";
        while ((line_tmp = br.readLine()) != null) {
          items2 = line_tmp.split(",");
          String zipcode = items2[0];
          lat2 = Double.parseDouble(items2[1]);
          lon2 = Double.parseDouble(items2[2]);

          lat1 = Math.toRadians(lat1);
          lon1 = Math.toRadians(lon1);
          lat2 = Math.toRadians(lat2);
          lon2 = Math.toRadians(lon2);

          double dlon = lon2 - lon1;
          double dlat = lat2 - lat1;

          double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2),2);
          double c = 2 * Math.asin(Math.sqrt(a));

          if (c < min_n) {
            min_n = c;
            min_zipcode = zipcode;
          }

          //if (Double.compare(c, min_n) < 0) { 
            //min_n = c;
            //min_zipcode = zipcode; 
            //context.write(key, new Text(zipcode));
          //} 

          //String ss = items[3] + "," + items[4] + "," + items2[1] + "," + items2[2] + "," + Double.toString(c) + "," + Double.toString(min_n);
          //context.write(key, new Text(ss));

        }

      } catch (Exception e) {
        e.printStackTrace();
      }

      String line2 = line + "," + min_zipcode;
      context.write(key, new Text(line2));
    }
  }
}
