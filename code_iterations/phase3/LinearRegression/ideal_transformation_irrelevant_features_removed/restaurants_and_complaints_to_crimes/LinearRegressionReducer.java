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
	int c = 0;
	switch (String.valueOf(key)){
		case "Robbery":
			c = 41;
			break;
		case "Burglary":
			c = 80;
			break;
		case "Weapons":
			c = 15;
			break;
		case "sexCrimes":
			c = 92;
			break;
		case "Murder":
			c = 45;
			break;
	}

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
	double[] sde = sr.estimateRegressionParametersStandardErrors();

	String coef = "coef:";
	for (double b: beta){
		coef = coef + " " + String.valueOf(b);
	}

	String irrelevantFeatures = "irrelevant features:";
	for (int j = 0; j < c; ++j){
		double z_score = Math.abs(beta[j]) / sde[j];
		if (z_score < 1.96)
			if (j < 38)
				irrelevantFeatures = irrelevantFeatures + " " + "rt" + String.valueOf(j + 1);
			else
				irrelevantFeatures = irrelevantFeatures + " " + "cp" + String.valueOf(j + 1 - 38);
	}
		
        String stat = "adjustedRSquared: " + String.valueOf(adjustedRSquared) + "\n" + "RSquared: " + String.valueOf(RSquared) + "\n" + coef + "\n" + irrelevantFeatures + "\n";
	context.write(key, new Text(stat));
    }
}
