import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class TypeStatDriver {
  public static void main(String[] args) throws Exception {
    if (args.length != 2) {
      System.err.println("Usage: TypeStatDriver <input path> <output path>");
      System.exit(-1);
    }

    String input_path = args[0];
    String output_path = args[1];

    Job job = new Job();
    job.setJarByClass(TypeStatDriver.class);
    job.setJobName("Type Stat");
    job.setNumReduceTasks(4);

    FileInputFormat.addInputPath(job, new Path(input_path));
    FileOutputFormat.setOutputPath(job, new Path(output_path));

    job.setMapperClass(TypeStatMapper.class);
    job.setReducerClass(TypeStatReducer.class);
    job.setMapOutputKeyClass(Text.class);
    job.setMapOutputValueClass(IntWritable.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(LongWritable.class);

    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}

