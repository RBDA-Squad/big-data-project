import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class DataProfiling2 {
  public static void main(String[] args) throws Exception { 
    if (args.length != 2) {
      System.err.println("Usage: Crime Data <input path> <output path>");
      System.exit(-1);
    }

    Job job = new Job(); 
    job.setJarByClass(DataProfiling2.class); 
    job.setJobName("Process Crime Data Profiling 2");
    job.setNumReduceTasks(1);

    FileInputFormat.addInputPath(job, new Path(args[0])); 
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
  
    job.setMapperClass(DataProfilingMapper2.class);
    job.setReducerClass(DataProfilingReducer2.class);
  
    job.setOutputKeyClass(Text.class); 
    job.setOutputValueClass(Text.class);

    System.exit(job.waitForCompletion(true) ? 0 : 1); 
  }
}
