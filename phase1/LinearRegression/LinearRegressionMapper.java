import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class LinearRegressionMapper
    extends Mapper<LongWritable, Text, Text, Text> {
        
        @Override
        public void map(LongWritable key, Text value, Context context) 
              throws IOException, InterruptedException {
            String line = value.toString();
            String[] columns = line.split("\t");
            String zipCode = columns[0];
            String rtsCount = columns[1];
            String cpsCount = columns[2];
            String cmsCount = columns[3];
            
            context.write(new Text("rts->cps"), new Text(rtsCount + "\t" + cpsCount));
            context.write(new Text("rts->cms"), new Text(rtsCount + "\t" + cmsCount));
            context.write(new Text("cps->cms"), new Text(cpsCount + "\t" + cmsCount));
        }
}      
            
