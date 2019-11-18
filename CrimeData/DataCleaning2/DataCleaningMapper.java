import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import java.io.FileReader;


public class DataCleaningMapper extends Mapper<LongWritable, Text, Text, Text> {
  @Override
  public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
    String line = value.toString();
    String[] items = line.split(",");

      //URL url = new URL("https://maps.googleapis.com/maps/api/geocode/json?latlng=40.7221992040001,-73.9771217049999&key=AIzaSyAMYXrUpsrTEsMc34ccjV5Ub2aoN6cdZ-Y");
      //HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      //conn.setRequestMethod("GET");
      //conn.setRequestProperty("Accept", "application/json");
      //if (conn.getResponseCode() != 200) {
          //throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
      //}
      //BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
      //StringBuilder sb = new StringBuilder();
      //String line2;
      //while ((line2 = br.readLine()) != null) {
          //sb.append(line2);
      //}

      //Gson gson = new Gson();
      //JsonParser parser = new JsonParser();
      //JsonObject object1 = (JsonObject) parser.parse(sb.toString());

      //JsonArray array1 = object1.getAsJsonArray("results");
      //boolean flag = false;
      //for (JsonElement item1 : array1) {
          //if (flag) {break;}

          //JsonObject object2 = item1.getAsJsonObject();
          //JsonArray array2 = object2.getAsJsonArray("address_components");

          //if (array2 == null) {
              //continue;
          //}

          //for (JsonElement item2 : array2) {
              //JsonObject object3 = item2.getAsJsonObject();
              //JsonArray array3 = object3.getAsJsonArray("types");

              //for (JsonElement item3 : array3) {
                  //String target = item3.toString();
                  //String target2 = target.substring(1, target.length()-1);

                  //if (target2.equals("postal_code")) {
                      //String target3 = object3.get("long_name").toString();
                      //String target4 = target3.substring(1, target3.length()-1);
                      //target5 = target4;
                      //flag = !flag;
                  //}
              //}
          //}
      //}


    String tmp_use = items[1] + ',';
    context.write(new Text(tmp_use), value);
  }
}
