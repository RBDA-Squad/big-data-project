import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class ZipCodeCount {
  public static void main(String[] args) throws Exception { 
    if (args.length != 2) {
      System.err.println("Usage: Crime Data <input path> <output path>");
      System.exit(-1);
    }

    Job job = new Job(); 
    job.setJarByClass(ZipCodeCount.class); 
    job.setJobName("Process Crime Data Zip Code Count");
    job.setNumReduceTasks(1);

    FileInputFormat.addInputPath(job, new Path(args[0])); 
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
  
    job.setMapperClass(ZipCodeCountMapper.class);
    job.setReducerClass(ZipCodeCountReducer.class);
  
    job.setOutputKeyClass(Text.class); 
    job.setOutputValueClass(Text.class);

    System.exit(job.waitForCompletion(true) ? 0 : 1); 
  }
}
