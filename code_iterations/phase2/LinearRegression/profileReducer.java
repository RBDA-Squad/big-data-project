import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.BooleanWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

public class LinearRegressionReducer
      extends Reducer<Text, Text, IntWritable, BooleanWritable> {

    @Override
    /*
    key = Text <category>: category of linear regression
    values = [Text(IntWritable <data_x>, IntWritable <data_y>)] 
    Context = (Text <category>, Double <slope>, Double <intercept>, Double <pValue>)
    */
    public void reduce(Text key, Iterable<Text> values, Context context)
          throws IOException, InterruptedException {
	/*
        OLSMultipleLinearRegression sr = new OLSMultipleLinearRegression();
        for (Text value: values){
            String data = value.toString();
            String[] fields = data.split("\t");
	    double[] x = Arrays.stream(fields[0].split(" ")).mapToDouble(Double::parseDouble).toArray();
            double y = Double.parseDouble(fields[1]);
            sr.addObservation(x, y);
        }
        String stat = String.join("\t", String.valueOf(sr.getSlope()), String.valueOf(sr.getIntercept()), String.valueOf(sr.getRSquare()), String.valueOf(sr.getSumSquaredErrors()), String.valueOf(sr.getSignificance()));
        context.write(key, new Text(stat));
	*/
	Iterator a = values.iterator();
	Iterator b = values.iterator();
	int count = 0;
	while (a.hasNext()){
		a.next();
		count++;
	}
	context.write(new IntWritable(count), new BooleanWritable(b.hasNext()));
    }
}
