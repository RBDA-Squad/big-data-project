import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class YearStatDriver {
  public static void main(String[] args) throws Exception {
    if (args.length != 2) {
      System.err.println("Usage: YearStatDriver <input path> <output path>");
      System.exit(-1);
    }

    String input_path = args[0];
    String output_path = args[1];

    Job job = new Job();
    job.setJarByClass(YearStatDriver.class);
    job.setJobName("Year Stat");
    job.setNumReduceTasks(1);

    FileInputFormat.addInputPath(job, new Path(input_path));
    FileOutputFormat.setOutputPath(job, new Path(output_path));

    job.setMapperClass(YearStatMapper.class);
    job.setReducerClass(YearStatReducer.class);
    job.setMapOutputKeyClass(Text.class);
    job.setMapOutputValueClass(IntWritable.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(LongWritable.class);

    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}


