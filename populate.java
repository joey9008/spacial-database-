/*
NAME:	XIAOYANG JU
ID:	7342138151
*/
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
public class populate {

	public static void main(String arg[]) throws FileNotFoundException {
		BUILDINGSfunction(arg[0]);
		STUDENTSfunction(arg[1]);
		ANNOUNCEMENTSYSTEMSfunction(arg[2]);
	}
	
	public static ArrayList<String> readFile(String inputFileName) throws FileNotFoundException {
		File inputFile = new File(inputFileName);
		Scanner in = new Scanner(inputFile);
		ArrayList<String> data = new ArrayList<String>();
		while(in.hasNextLine()){
			data.add(in.nextLine());
		}
		return data;
	}


	public static void BUILDINGSfunction(String Buildings_file) throws FileNotFoundException {
		ArrayList<String> line_data = readFile(Buildings_file);
		DBexecute dbexecute = new DBexecute();
		String query = "";
		String left_ordinates = "";
		String area_ordinates = "";
		dbexecute.connect();
		dbexecute.delete("BUILDINGS");
		for(String element: line_data)
		{
			String components[] = element.split(", ");
			for(int i=3;i<components.length-1;i++)
			{
				left_ordinates += components[i] + ",";
				area_ordinates += components[i] + ",";
			}
			left_ordinates += components[components.length-1];
			area_ordinates += components[components.length-1] + ",";
			area_ordinates += components[3] + ",";
			area_ordinates += components[4];
			
			query = "INSERT INTO BUILDINGS VALUES ('" + components[0] + "','" + components[1] + "'," + components[2] + ",SDO_GEOMETRY(2003,NULL,NULL,SDO_ELEM_INFO_ARRAY(1,1003,1),SDO_ORDINATE_ARRAY(" + left_ordinates +"))"
				+ ",SDO_GEOMETRY(2003,NULL,NULL,SDO_ELEM_INFO_ARRAY(1,1003,1),SDO_ORDINATE_ARRAY(" + area_ordinates +")))";
			
			dbexecute.insert(query);
			left_ordinates = "";
			area_ordinates = "";
		}
		dbexecute.close();
	}

	public static void STUDENTSfunction(String Students_file) throws FileNotFoundException {
		ArrayList<String> line_data = readFile(Students_file);
		DBexecute dbexecute = new DBexecute();
		String query = "";
		dbexecute.connect();
		dbexecute.delete("STUDENTS");
		for(String element: line_data)
		{
			String components[] = element.split(", ");
			query = "INSERT INTO STUDENTS VALUES ('"+ components[0] + "',SDO_GEOMETRY(2001,NULL,MDSYS.SDO_POINT_TYPE(" + components[1] + "," + components[2] + ",NULL),NULL,NULL))";
			dbexecute.insert(query);
		}
		dbexecute.close();
	}

	public static void ANNOUNCEMENTSYSTEMSfunction(String Aps_file) throws FileNotFoundException {
		ArrayList<String> line_data = readFile(Aps_file);
		DBexecute dbexecute = new DBexecute();
		String query = "";
		dbexecute.connect();
		dbexecute.delete("ANNOUNCEMENTSYSTEMS");
		for(String element: line_data)
		{
			String components[] = element.split(", ");
			int ANNx=Integer.parseInt(components[1]);
			int ANNy=Integer.parseInt(components[2]);
			int ANNr=Integer.parseInt(components[3]);
			String comonents_x1 = String.valueOf(ANNx);
			String comonents_x2 = String.valueOf(ANNx+ANNr);
			String comonents_x3 = String.valueOf(ANNx);
			String comonents_y1 = String.valueOf(ANNy+ANNr);
			String comonents_y2 = String.valueOf(ANNy);
			String comonents_y3 = String.valueOf(ANNy-ANNr);
			query = "INSERT INTO ANNOUNCEMENTSYSTEMS VALUES ('"+ components[0] + "',SDO_GEOMETRY(2001,NULL,MDSYS.SDO_POINT_TYPE(" + components[1] + "," + components[2] + 
					",NULL),NULL,NULL)," + components[3] + ",SDO_GEOMETRY(2003,NULL,NULL,SDO_ELEM_INFO_ARRAY(1,1003,4),SDO_ORDINATE_ARRAY(" + comonents_x1 + "," + comonents_y1 + ","+ comonents_x2 +
					"," + comonents_y2 + "," + comonents_x3 + "," + comonents_y3 + ")))";
			dbexecute.insert(query);
		}
		dbexecute.close();
	}
}


