import java.io.IOException;
import java.util.*;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.Mapper;


public class CleaningMapper extends Mapper<LongWritable, Text, Text, NullWritable> {
  private Counter counter1, counter2;
  public enum CustomCounters { NUM_ROWS, NUM_INVALID_ROWS }
  public String[] types = {"Internal Code", "Sprinkler - Mechanical", "Ferry Complaint", "SG-99", "CST", "Foam Ban Enforcement", "Forensic Engineering", "Single Occupancy Bathroom", "Fire Alarm - Replacement", "DEP Street Condition", "Lost Property", "Overflowing Recycling Baskets", "Public Assembly - Temporary", "Calorie Labeling", "Collection Truck Noise", "LEAD", "Tanning", "Public Assembly", "Adopt-A-Basket", "Harboring Bees/Wasps", "Hazmat Storage/Use", "Appliance", "Outside Building", "Parent Leadership", "SRDE", "Legal Services Provider Complaint", "DOF Property - Reduction Issue", "Missed Collection", "Unsanitary Animal Facility", "Stalled Sites", "Animal Facility - No Permit", "Open Flame Permit", "Special Natural Area District (SNAD)", "Teaching/Learning/Instruction", "VACANT APARTMENT", "Public Toilet", "Taxi Compliment", "Lifeguard", "Fire Alarm - Modification", "Highway Sign - Missing", "Senior Center Complaint", "Unlicensed Dog", "Radioactive Material", "Request Changes - A.S.P.", "Illegal Animal - Sold/Kept", "Pet Shop", "Ferry Inquiry", "DEP Sidewalk Condition", "Health", "Building Condition", "Bottled Water", "FCST", "AGENCY", "Asbestos/Garbage Nuisance", "Fire Alarm - Reinspection", "Summer Camp", "MOLD", "LinkNYC", "Trans Fat", "FHE", "DOF Literature Request", "Hazardous Material", "Transportation Provider Complaint", "Tattooing", "Municipal Parking Facility", "DSNY Spillage", "Cooling Tower", "Special Operations", "Illegal Animal Sold", "X-Ray Machine/Equipment", "Rangehood", "Tunnel Condition", "Highway Sign - Damaged", "MSOTHER", "FATF", "Highway Sign - Dangling", "STRUCTURAL", "Dept of Investigations", "Air Quality", "Discipline and Suspension", "Water Maintenance", "Water Leak", "Gas Station Discharge Lines", "Electric", "ATF", "Standpipe - Mechanical", "Fire Alarm - New System", "Portable Toilet", "Registration and Transfers", "Employee Behavior", "Squeegee", "Trapping Pigeon", "Interior Demo", "Safety'"};
  public Set<String> typesSet = new HashSet<String>(Arrays.asList(types));

  @Override
  public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
    String[] valueSplit = value.toString().split(",", -1);
    if (valueSplit[0].equals("Unique Key")) {
      return;
    }

    // count # of lines
    counter1 = context.getCounter(CustomCounters.NUM_ROWS);
    counter1.increment(1L);

    String[] keySplit = {
      valueSplit[1], valueSplit[5], valueSplit[8], valueSplit[38], valueSplit[39]
    };

    // count invalid # of lines
    for (String el: keySplit){
      if (el.equals("")) {
        counter2 = context.getCounter(CustomCounters.NUM_INVALID_ROWS);
        counter2.increment(1L);
        return;
      }
    }

    String type = keySplit[1];

    // Change the form of type
    if (type.equals("Animal-Abuse")) {
      type = "Animal Abuse";
    } else if (type.equals("Broken Muni Meter") || type.equals("Broken Parking Meter")) {
      type = "Broken Meter";
    } else if (type.equals("Building Marshals office") || type.equals("Building/Use")) {
      type = "Building";
    } else if (type.equals("Bus Stop Shelter Placement")) {
      type = "Bus Stop Shelter Shelter";
    } else if (type.equals("Construction Safety Enforcement")) {
      type = "Construction";
    } else if (type.equals("Dead/Dying Tree")) {
      type = "Dead Tree";
    } else if (type.equals("Derelict Bicycle") || type.equals("Derelict Vehicles")) {
      type = "Derelict Vehicle";
    } else if (type.equals("Drinking Water")) {
      type = "Drinking";
    } else if (type.equals("Electrical")) {
      type = "ELECTRIC";
    } else if (type.equals("Electronics Waste Appointment")) {
      type = "Electronics Waste";
    } else if (type.equals("For Hire Vehicle Report")) {
      type = "For Hire Vehicle Complaint";
    } else if (type.equals("General Construction/Plumbing")) {
      type = "GENERAL CONSTRUCTION";
    } else if (type.equals("Homeless Person Assistance")) {
      type = "Homeless Encampment";
    } else if (type.equals("Noise Survey")) {
      type = "Noise";
    } else if (type.equals("PAINT - PLASTER") || type.equals("PAINT/PLASTER")) {
      type = "PAINT PLASTER";
    } else if (type.equals("Street Sign - Damaged") || type.equals("Street Sign - Dangling") || type.equals("Street Sign - Missing")) {
      type = "Street Sign";
    } else if (type.equals("Sweeping/Inadequate") || type.equals("Sweeping/Missed-Inadequate") || type.equals("Sweeping/Missed")) {
      type = "Sweeping Missed";
    } else if (type.equals("Taxi Report")) {
      type = "Taxi Complaint";
    } else if (type.equals("Traffic Signal Condition") || type.equals("Traffic/Illegal Parking")) {
      type = "Traffic";
    } else if (type.equals("Unsanitary Animal Pvt Property") || type.equals("Unsanitary Pigeon Condition") || type.equals("Urinating in Public")) {
      type = "Unsanitary Condition";
    } else if (type.equals("WATER LEAK")) {
      type = "Water Conservation";
    }

    // drop all types whose count is less than 1000 or type is miscellaneous
    if (typesSet.contains(type) || type.equals("Miscellaneous Categories")) {
      return;
    }

    // uppercase type
    keySplit[1] = type.toUpperCase();
    context.write(new Text(String.join(",", keySplit)), NullWritable.get());
  }
}
