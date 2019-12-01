import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.MalformedJsonException;

public class Read_CSV {
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("rows.csv"));
            FileWriter csvWriter = new FileWriter("new.csv");
            String line;
            String new_line;
            String line2;
            String total_line = "";
            String zipcode;
            String url_str;
            int count = 0;
            HttpURLConnection conn = null;

            while ((line = br.readLine()) != null) {
                String[] items1 = line.split("\"");
                zipcode = "-1";

                // For header
                if (items1[0].equals("ARREST_KEY")) {
                    csvWriter.append(line);
                    csvWriter.append(",");
                    csvWriter.append("ZIP_CODE");
                    csvWriter.append("\n");
                }

                if (items1.length == 3) {
                    new_line = items1[0] + items1[2];
                    String[] items2 = new_line.split(",");

                    if (items2.length == 18) {
                        url_str = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + items2[16] + ","
                                + items2[17] + "&key=AIzaSyAMYXrUpsrTEsMc34ccjV5Ub2aoN6cdZ-Y";

                        URL url = new URL(url_str);
                        System.out.println(url_str);
                        // HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("GET");
                        conn.setRequestProperty("Accept", "application/json");
                        conn.setRequestProperty("Connection", "keep-alive");
                        conn.setRequestProperty("Keep-Alive", "header");

                        if (conn.getResponseCode() != 200) {
                            throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
                        }

                        BufferedReader br2 = new BufferedReader(new InputStreamReader((conn.getInputStream())));

                        zipcode = "99999";
                        while ((line2 = br2.readLine()) != null) {
                            total_line += line2;
                        }

                        // System.out.println(total_line);

                        Gson gson = new Gson();
                        // JsonReader reader = new JsonReader(new StringReader(total_line));
                        // reader.setLenient(true);
                        // Gson gson = gsonBuilder.create();
                        JsonParser parser = new JsonParser();

                        if (isJson(total_line) == true) {
                            JsonObject object1 = (JsonObject) parser.parse(total_line);
                            System.out.println(object1);

                            JsonArray array1 = object1.getAsJsonArray("results");
                            boolean flag = false;
                            for (JsonElement item1 : array1) {
                                if (flag) {
                                    break;
                                }

                                JsonObject object2 = item1.getAsJsonObject();
                                JsonArray array2 = object2.getAsJsonArray("address_components");

                                if (array2 == null) {
                                    continue;
                                }

                                for (JsonElement item2 : array2) {
                                    JsonObject object3 = item2.getAsJsonObject();
                                    JsonArray array3 = object3.getAsJsonArray("types");

                                    for (JsonElement item3 : array3) {
                                        String target = item3.toString();
                                        String target2 = target.substring(1, target.length() - 1);

                                        if (target2.equals("postal_code")) {
                                            System.out.println(target2);
                                            String target3 = object3.get("long_name").toString();
                                            String target4 = target3.substring(1, target3.length() - 1);
                                            System.out.println(target4);
                                            flag = !flag;
                                        }
                                    }
                                }
                            }

                            // conn.disconnect();
                        }

                        if (count == 5) {
                            break;
                        }
                        count += 1;
                    }
                }

                csvWriter.append(line);
                csvWriter.append(",");
                csvWriter.append(zipcode);
                csvWriter.append("\n");
            }
            conn.disconnect();
            csvWriter.flush();
            csvWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }

        // catch (MalformedJsonException e) {
        // e.printStackTrace();
        // }
    }

    public static boolean isJson(String Json) {
        Gson gson = new Gson();
        try {
            gson.fromJson(Json, Object.class);
            return true;
        } catch (JsonSyntaxException ex) {
            return false;
        }
    }
}
