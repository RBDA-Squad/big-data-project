import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import org.apche.commons.math3.stat.regression.SimpleRegression

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
            int x = int(fields[0]);
            int y = int(fields[1]);
            sr.addData(x, y);
        }
        String stat = String.valueOf(sr.getSlope()) + "\t" + String.valueOf(sr.getIntercept()) + "\t" + String.valueOf(sr.getSignificance());
         context.write(key, new Text(stat));
    }
}