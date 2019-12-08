import java.io.IOException;
import java.lang.Math;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class LinearRegressionMapper
    extends Mapper<LongWritable, Text, Text, Text> {
        
	/*
 	num_of_rts = 38
	num_of_cps = 122
	num_of_cms = 43
	Index:
		zipcode: 0
		restaurants: 1 - 38
		complaints: 39 - 160
		crimes: 161 - 203
	*/
        @Override
        public void map(LongWritable key, Text value, Context context) 
              throws IOException, InterruptedException {
            String line = value.toString();
            String[] columns = line.split("\t");
	    String Robbery = columns[168];
	    String Burglary = columns[174];
	    String Weapons = columns[172];
	    String sexCrimes = columns[178];
	    String Murder = columns[186];

	    String x1 = "";
	    String x2 = "";
	    String x3 = "";
	    String x4 = "";
	    String x5 = "";

	    // add only relevant log transformed restaurant features.
	    for (int i = 1; i <= 38; ++i){
		double log_count = Math.log(Double.parseDouble(columns[i]) + 1);
		// relevant features for Robbery
		if (i == 1 || i == 7 || i == 9 || i == 10 || i == 12 || i == 13 || i == 14 || i == 16 || i == 17 || i == 28 || i == 31 || i == 35)
			x1 = x1 + String.valueOf(log_count) + " ";
		// relevant features for Burglary
		if (i == 1 || i == 2 || i == 4 || i == 7 || i == 9 || i == 10 || i == 12 || i == 13 || i == 14 || i == 15 || i == 16 || i == 17 || i == 19 || i == 22 || i == 23 || i == 24 || i == 25 || i == 26 || i == 28 || i == 31 || i == 32 || i == 33 || i == 34 || i == 35)
			x2 = x2 + String.valueOf(log_count) + " ";
		// relevant features for Dangerous Weapons
		if (i == 1 || i == 9 || i == 16 || i == 31)
			x3 = x3 + String.valueOf(log_count) + " ";
		// relevant features for Sex Crimes
		if (i == 1 || i == 2 || i == 4 || i == 7 || i == 9 || i == 10 || i == 11 || i == 12 || i == 13 || i == 14 || i == 15 || i == 16 || i == 17 || i == 19 || i == 22 || i == 23 || i == 24 || i == 26 || i == 28 || i == 31 || i == 32 || i == 33 || i == 34 || i == 35 || i == 38)
			x4 = x4 + String.valueOf(log_count) + " ";
		// relevant features for Murder
		if (i == 1 || i == 7 || i == 9 || i == 10 || i == 12 || i == 13 || i == 16 || i == 17 || i == 31 || i == 32)
			x5 = x5 + String.valueOf(log_count) + " ";
	    }

	    // add only relevant sqrt transformed complaint features
	    for (int i = 39; i <= 160; ++i){
		double sqrt_count = Math.sqrt(Double.parseDouble(columns[i]));
		// relevant feature for Robbery
		int j = i - 38;
		if (j == 2 || j == 17 || j == 21 || j == 22 || j == 23 || j == 26 || j == 30 || j == 37 || j == 43 || j == 47 || j == 54 || j == 58 || j == 60 || j == 63 || j == 67 || j == 71 || j == 74 || j == 77 || j == 79 || j == 82 || j == 87 || j == 92 || j == 103 || j == 107 || j == 111 || j == 112 || j == 114 || j == 117 || j == 119)
		x1 = x1 + String.valueOf(sqrt_count) + " ";
		// relevant features for Burglary
		if (j == 1 || j == 2 || j == 5 || j == 7 || j == 13 || j == 17 || j == 21 || j == 22 || j == 23 || j == 24 || j == 26 || j == 29 || j == 30 || j == 35 || j == 37 || j == 42 || j == 43 || j == 47 || j == 51 || j == 52 || j == 54 || j == 55 || j == 56 || j == 58 || j == 59 || j == 60 || j == 63 || j == 67 || j == 71 || j == 74 || j == 77 || j == 78 || j == 79 || j == 81 || j == 82 || j == 83 || j == 84 || j == 87 || j == 89 || j == 92 || j == 99 || j == 103 || j == 104 || j == 105 || j == 107 || j == 108 || j == 110 || j == 111 || j == 112 || j == 113 || j == 114 || j == 115 || j == 117 || j == 119 || j == 120 || j == 122)
		x2 = x2 + String.valueOf(sqrt_count) + " ";
		// relevant features for Dangerous Weapons
		if (j == 21 || j == 23 || j == 37 || j == 63 || j == 77 || j == 82 || j == 87 || j == 103 || j == 111 || j == 112 || j == 119)
		x3 = x3 + String.valueOf(sqrt_count) + " ";
		// relevant features for Sex Crimes
		if (j == 1 || j == 2 || j == 4 || j == 5 || j == 7 || j == 8 || j == 11 || j == 13 || j == 15 || j == 17 || j == 19 || j == 21 || j == 22 || j == 23 || j == 24 || j == 26 || j == 30 || j == 32 || j == 34 || j == 35 || j == 36 || j == 37 || j == 39 || j == 42 || j == 43 || j == 45 || j == 46 || j == 47 || j == 48 || j == 50 || j == 51 || j == 54 || j == 55 || j == 56 || j == 58 || j == 60 || j == 61 || j == 62 || j == 63 || j == 64 || j == 67 || j == 68 || j == 71 || j == 74 || j == 77 || j == 79 || j == 82 || j == 83 || j == 84 || j == 87 || j == 92 || j == 98 || j == 99 || j == 103 || j == 104 || j == 105 || j == 107 || j == 108 || j == 111 || j == 112 || j == 113 || j == 114 || j == 115 || j == 117 || j == 119 || j == 120 || j == 122)
		x4 = x4 + String.valueOf(sqrt_count) + " ";
		// relevant features for Murder
		if (j == 1 || j == 2 || j == 21 || j == 22 || j == 23 || j == 24 || j == 26 || j == 30 || j == 37 || j == 43 || j == 46 || j == 47 || j == 51 || j == 52 || j == 54 || j == 58 || j == 60 || j == 63 || j == 64 || j == 67 || j == 71 || j == 74 || j == 77 || j == 79 || j == 82 || j == 84 || j == 87 || j == 103 || j == 105 || j == 108 || j == 111 || j == 112 || j == 117 || j == 119 || j == 122)
		x5 = x5 + String.valueOf(sqrt_count) + " ";
	    }
            context.write(new Text("Robbery"), new Text(x1 + "\t" + String.valueOf(Math.log(Double.parseDouble(Robbery) + 1))));
            context.write(new Text("Burglary"), new Text(x2 + "\t" + String.valueOf(Math.log(Double.parseDouble(Burglary) + 1))));
            context.write(new Text("Weapons"), new Text(x3 + "\t" + String.valueOf(Math.log(Double.parseDouble(Weapons) + 1))));
	    context.write(new Text("sexCrimes"), new Text(x4 + "\t" + String.valueOf(Math.log(Double.parseDouble(sexCrimes) + 1))));
	    context.write(new Text("Murder"), new Text(x5 + "\t" + String.valueOf(Math.log(Double.parseDouble(Murder) + 1))));
        }
}      
            
