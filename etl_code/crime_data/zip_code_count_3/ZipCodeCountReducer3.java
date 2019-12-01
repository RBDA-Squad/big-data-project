import java.io.IOException;
import java.lang.Integer;
import org.apache.hadoop.io.IntWritable; 
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class ZipCodeCountReducer3 extends Reducer<Text, Text, Text, Text> {
  @Override
  public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
    int[] items = new int[50]; 
    String crime_type;
   
    for (Text value : values) {
      crime_type = value.toString();  

      switch (crime_type) {
        case "ADMINISTRATIVE CODE":
          items[0] += 1;
          break;
        case "ANTICIPATORY OFFENSES":
          items[1] += 1;
          break;
        case "ARSON":
          items[2] += 1;
          break;
        case "ASSAULT 3 & RELATED OFFENSES":
          items[3] += 1;
          break;
        case "BURGLARY":
          items[4] += 1;
          break;
        case "CRIMINAL MISCHIEF & RELATED OFFENSES":
          items[5] += 1;
          break;
        case "CRIMINAL TRESPASS":
          items[6] += 1;
          break;
        case "DANGEROUS DRUGS":
          items[7] += 1;
          break;
        case "DANGEROUS WEAPONS":
          items[8] += 1;
          break;
        case "DISORDERLY CONDUCT":
          items[9] += 1;
          break;
        case "DISRUPTION OF A RELIGIOUS SERVICE":
          items[10] += 1;
          break;
        case "ENDAN WELFARE INCOMP":
          items[11] += 1;
          break;
        case "F.C.A. P.I.N.O.S.":
          items[12] += 1;
          break;
        case "FELONY ASSAULT":
          items[13] += 1;
          break;
        case "FORCIBLE TOUCHING":
          items[14] += 1;
          break;
        case "FORGERY":
          items[15] += 1;
          break;
        case "FRAUDS":
          items[16] += 1;
          break;
        case "FRAUDULENT ACCOSTING":
          items[17] += 1;
          break;
        case "GAMBLING":
          items[18] += 1;
          break;
        case "GRAND LARCENY":
          items[19] += 1;
          break;
        case "GRAND LARCENY OF MOTOR VEHICLE":
          items[20] += 1;
          break;
        case "HARRASSMENT 2":
          items[21] += 1;
          break;
        case "HOMICIDE-NEGLIGENT-VEHICLE":
          items[22] += 1;
          break;
        case "INTOXICATED & IMPAIRED DRIVING":
          items[23] += 1;
          break;
        case "KIDNAPPING & RELATED OFFENSES":
          items[24] += 1;
          break;
        case "LOITERING":
          items[25] += 1;
          break;
        case "MISCELLANEOUS PENAL LAW":
          items[26] += 1;
          break;
        case "MOVING INFRACTIONS":
          items[27] += 1;
          break;
        case "MURDER & NON-NEGL. MANSLAUGHTER":
          items[28] += 1;
          break;
        case "NEW YORK CITY HEALTH CODE":
          items[29] += 1;
          break;
        case "NYS LAWS-UNCLASSIFIED FELONY":
          items[30] += 1;
          break;
        case "OFF. AGNST PUB ORD SENSBLTY & RGHTS TO PRIV":
          items[31] += 1;
          break;
        case "OFFENSES AGAINST MARRIAGE UNCLASSIFIED":
          items[32] += 1;
          break;
        case "OFFENSES AGAINST PUBLIC ADMINISTRATION":
          items[33] += 1;
          break;
        case "OFFENSES AGAINST PUBLIC SAFETY":
          items[34] += 1;
          break;
        case "OFFENSES AGAINST THE PERSON":
          items[35] += 1;
          break;
        case "OFFENSES INVOLVING FRAUD":
          items[36] += 1;
          break;
        case "OFFENSES RELATED TO CHILDREN":
          items[37] += 1;
          break;
        case "OTHER OFFENSES RELATED TO THEFT":
          items[38] += 1;
          break;
        case "OTHER STATE LAWS":
          items[39] += 1;
          break;
        case "OTHER STATE LAWS (NON PENAL LAW)":
          items[40] += 1;
          break;
        case "OTHER TRAFFIC INFRACTION":
          items[41] += 1;
          break;
        case "PARKING OFFENSES":
          items[42] += 1;
          break;
        case "PETIT LARCENY":
          items[43] += 1;
          break;
        case "POSSESSION OF STOLEN PROPERTY 5":
          items[44] += 1;
          break;
        case "PROSTITUTION & RELATED OFFENSES":
          items[45] += 1;
          break;
        case "ROBBERY":
          items[46] += 1;
          break;
        case "SEX CRIMES":
          items[47] += 1;
          break;
        case "THEFT-FRAUD":
          items[48] += 1;
          break;
        case "VEHICLE AND TRAFFIC LAWS":
          items[49] += 1;
          break;
      }      
    }

    String new_key = key.toString() + ",";

    String result = Integer.toString(items[0]);
    for (int i = 1; i < items.length; i++) {
      result = result + "," + Integer.toString(items[i]);
    }

    context.write(new Text(new_key), new Text(result));
  }
}
