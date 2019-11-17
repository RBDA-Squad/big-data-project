import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class CleaningDriver {
  public static void main(String[] args) throws Exception {
    if (args.length != 2) {
      System.err.println("Usage: CleaningDriver <input path> <output path>");
      System.exit(-1);
    }

    String input_path = args[0];
    String output_path = args[1];

    Job job = new Job();
    job.setJarByClass(CleaningDriver.class);
    job.setJobName("cleaning data");
    job.setNumReduceTasks(0);

    FileInputFormat.addInputPath(job, new Path(input_path));
    FileOutputFormat.setOutputPath(job, new Path(output_path));

    job.setMapperClass(CleaningMapper.class);
    job.setMapOutputKeyClass(Text.class);
    job.setMapOutputValueClass(NullWritable.class);

    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}
