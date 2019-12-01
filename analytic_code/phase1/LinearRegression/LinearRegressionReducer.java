import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import org.apache.commons.math3.stat.regression.SimpleRegression;

public class LinearRegressionReducer
      extends Reducer<Text, Text, Text, Text> {

    @Override
    /*
    key = Text <category>: category of linear regression
    values = [Text(IntWritable <data_x>, IntWritable <data_y>)] 
    Context = (Text <category>, Double <slope>, Double <intercept>, Double <pValue>)
    */
    public void reduce(Text key, Iterable<Text> values, Context context)
          throws IOException, InterruptedException {
        SimpleRegression sr = new SimpleRegression();
        for (Text value: values){
            String data = value.toString();
            String[] fields = data.split("\t");
            int x = Integer.parseInt(fields[0]);
            int y = Integer.parseInt(fields[1]);
            sr.addData(x, y);
        }
        String stat = String.join("\t", String.valueOf(sr.getSlope()), String.valueOf(sr.getIntercept()), String.valueOf(sr.getRSquare()), String.valueOf(sr.getSumSquaredErrors()), String.valueOf(sr.getSignificance()));
         context.write(key, new Text(stat));
    }
}
