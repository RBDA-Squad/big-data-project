import java.io.IOException;
import org.apache.hadoop.io.IntWritable; 
import org.apache.hadoop.io.LongWritable; 
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FSDataInputStream;

import java.lang.Integer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import java.io.FileReader;


public class DataCleaningMapper extends Mapper<LongWritable, Text, Text, Text> {
  @Override
  public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
    boolean isDrop = false;
    String line = value.toString();

    String[] items1 = line.split("\"");
    if (items1.length == 3) {
      line = items1[0] + items1[2];
    } else {
      isDrop = true;
    }

    String[] items2 = line.split(",");
    if (items2.length == 18) {
      switch (items2[5]) {
        case "ADMINISTRATIVE CODES":
          items2[5] = "ADMINISTRATIVE CODE";
          break;
        case "CRIMINAL MISCHIEF & RELATED OF":
          items2[5] = "CRIMINAL MISCHIEF & RELATED OFFENSES";
          break;
        case "BURGLAR'S TOOLS":
          items2[5] = "BURGLARY";
          break;
        case "INTOXICATED/IMPAIRED DRIVING":
          items2[5] = "INTOXICATED & IMPAIRED DRIVING";
          break;
        case "MURDER & NON-NEGL. MANSLAUGHTE":
          items2[5] = "MURDER & NON-NEGL. MANSLAUGHTER";
          break;
        case "OFF. AGNST PUB ORD SENSBLTY &":
          items2[5] = "OFF. AGNST PUB ORD SENSBLTY & RGHTS TO PRIV";
          break;
        case "OFFENSES AGAINST PUBLIC ADMINI":
          items2[5] = "OFFENSES AGAINST PUBLIC ADMINISTRATION";
          break;
        case "OTHER STATE LAWS (NON PENAL LA":
          items2[5] = "OTHER STATE LAWS (NON PENAL LAW)";
          break;
        case "POSSESSION OF STOLEN PROPERTY":
          items2[5] = "POSSESSION OF STOLEN PROPERTY 5";
          break;
        case "CHILD ABANDONMENT/NON SUPPORT":
          items2[5] = "CHILD ABANDONMENT/NON SUPPORT 1";
          break;
      }
       
      if (items2[1].length() == 11) {
        isDrop = true;
      }
      if (items2[5].length() == 0) {
        isDrop = true;
      }
      if (items2[16].length() == 8) {
        isDrop = true;
      }
      if (items2[17].length() == 9) {
        isDrop = true;
      }
      
    } else {
      isDrop = true;
    }

    if (isDrop == false) {
      String key_tmp = items2[0] + ","; 
      line = items2[1] + "," + items2[5] + "," + items2[8]  + "," + items2[16] + "," + items2[17];
      context.write(new Text(key_tmp), new Text(line));
    }
  }
}
