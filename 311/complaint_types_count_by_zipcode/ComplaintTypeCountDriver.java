import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class ComplaintTypeCountDriver {
  public static void main(String[] args) throws Exception {
    if (args.length != 2) {
      System.err.println("Usage: ComplaintTypeCountDriver <input path> <output path>");
      System.exit(-1);
    }

    String input_path = args[0];
    String output_path = args[1];

    Job job = new Job();
    job.setJarByClass(ComplaintTypeCountDriver.class);
    job.setJobName("complaint type by zipcode");
    job.setNumReduceTasks(32);

    FileInputFormat.addInputPath(job, new Path(input_path));
    FileOutputFormat.setOutputPath(job, new Path(output_path));

    job.setMapperClass(ComplaintTypeCountMapper.class);
    job.setReducerClass(ComplaintTypeCountReducer.class);
    job.setMapOutputKeyClass(Text.class);
    job.setMapOutputValueClass(Text.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(NullWritable.class);

    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}
