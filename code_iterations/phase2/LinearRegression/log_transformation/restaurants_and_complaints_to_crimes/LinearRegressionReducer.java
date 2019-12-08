import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

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
        OLSMultipleLinearRegression sr = new OLSMultipleLinearRegression();
	int r = 163;
	int c = 10;
	double[][] x = new double[r][c];
	double[] y = new double[r];
	int i = 0;
        for (Text value: values){
            String data = value.toString();
            String[] fields = data.split("\t");
	    x[i] = Arrays.stream(fields[0].split(" ")).mapToDouble(Double::parseDouble).toArray();
            y[i] = Double.parseDouble(fields[1]);
	    ++i;
        }
	sr.newSampleData(y, x);
	double adjustedRSquared = sr.calculateAdjustedRSquared();
	double RSquared = sr.calculateRSquared();
	double[] beta = sr.estimateRegressionParameters();
	String coef = "coef:";
	for (double b: beta){
		coef = coef + " " + String.valueOf(b);
	}
	coef += "\n";
        String stat = "adjustedRSquared: " + String.valueOf(adjustedRSquared) + "\n" + "RSquared: " + String.valueOf(RSquared) + "\n" + coef;
	context.write(key, new Text(stat));
    }
}
