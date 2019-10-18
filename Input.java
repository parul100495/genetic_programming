import java.io.*;
import java.util.*;

public class Input
{
	ArrayList<String> attribute_name;
	ArrayList<String> class_name;
	//ArrayList<ArrayList<Double>> data;
	Double[][] data;
	public Input() {}

	public Input(ArrayList<String> attribute_name,ArrayList<String> class_name, Double[][] data)
	{
	    this.attribute_name = attribute_name;
	    this.class_name = class_name;
	    this.data = data;
	}
	  
	public static Input input_extract(String file_name)//THIS IS CALLLED
	{
	    ArrayList<String> attribute_name_local = new ArrayList<String>();
		ArrayList<String> class_name_local = new ArrayList<String>();
		Input input = null;
	    try
	    {
			int row=0;
	        BufferedReader reader = new BufferedReader(new FileReader(file_name));
	        String line = null;
	        ArrayList<String> lines = new ArrayList<String>(); 
	        while ((line = reader.readLine()) != null) 
	        {
				String[] parts = line.split("\\s");
	            switch(parts[0])
	            {
	                case "@attribute":
	                   	attribute_name_local.add(parts[1].trim()); //adds while omitting white spaces
	                   	break;

	                case "@class":
	                   	class_name_local.add(parts[1].trim());
	                   	break;

	                default:
	                   	lines.add(line);//add in the array the whole line (where 'lines' is the name of array) .... on 29
		                break;
	            } 
	        }
            System.out.println(lines.size()+ "    "+attribute_name_local.size());
	        Double[][] data = new Double[lines.size()][attribute_name_local.size()+1];
	        for(String dataLine:lines){                 //for each loop
				String parts[] = dataLine.split(",");
				int len = parts.length;
				for(int i=0;i<len-1;i++)
				{
				      if(parts[i].length()>0)
				        data[row][i] = Double.parseDouble(parts[i].trim());
				}
				for (int i = 0; i < class_name_local.size(); i++) 
				{
				    if(class_name_local.get(i).equals(parts[len-1].trim()))
				    {
				        data[row][len-1] = (double)i;
				        break;
				    }
				}
				row++;
	          }
	          input = new Input(attribute_name_local,class_name_local,data);
	          reader.close();
	    }
	    catch(Exception e)
	    {
	        e.printStackTrace();
	    }
	    return input;
	}
}