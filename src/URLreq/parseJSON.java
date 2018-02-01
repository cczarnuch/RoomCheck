package URLreq;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class parseJSON {

	public static void main(String[] args) throws JSONException, IOException {
		JSONObject timetable = JSONtoObj(true);
		freeTime(timetable);

	}
	
	
	public static JSONObject JSONtoObj(Boolean Online) throws JSONException, IOException {
		
		if (Online) {
			//Goes to URL and gets the json file
			String response = getURL.getJSON("https://www.timetablegenerator.io/api/v2/school/mcmaster/");
			//file is stored in a JSONObject type
			JSONObject myresponse;
			
			try {
				//converts the response from the URL ttoo JSONObject type; else return null
				myresponse = new JSONObject(response.toString());
				return myresponse;
			} catch (Exception e) {e.printStackTrace();}
			return null;
		} else {
			BufferedReader in = new BufferedReader(new FileReader("timetables.json"));
			JSONObject myresponse = new JSONObject(in.readLine());
			in.close();
			return myresponse;
		}
	}
	
	
	//This method is used to find all of ther periods of every class in every building that are stored in the JSON
	public static void freeTime(JSONObject timetable) {
		//Create an object to hold all of the unsorted data
		JSONObject courses = timetable.getJSONObject("timetables").getJSONObject("2017").getJSONObject("6").getJSONObject("courses");
		
		//Iterates through the [revious object using a string for the names/keys of the nested objects
		for (Object key : courses.keySet()) {
			String keyStr = (String)key;
			Object keyval = courses.get(keyStr);
			
			//System.out.println("key: " + keyStr + "\nvalue: " + keyval);
			
			
			//checks for instance of JSONObject then prints/stores all lectures, tutorials, and labs
			if (keyval instanceof JSONObject) {
				
				JSONObject classes = ((JSONObject)keyval).getJSONObject("sections");
				
				//checks for core/lectures
				if (classes.has("C")) {
					JSONObject lectures = classes.getJSONObject("C");
					
					for (Object sect1 : lectures.keySet()) {
						String keyStr1 = (String)sect1;
						Object keyval1 = lectures.get(keyStr1);
						
						if (keyval1 instanceof JSONObject) {
							JSONObject section = (JSONObject)keyval1;
							JSONArray r_periods = section.getJSONArray("r_periods");
							
							//iterates through all of the core times in a week
							for (int i = 0; i < r_periods.length(); i++) {
								JSONObject period = r_periods.getJSONObject(i);
								System.out.println(keyStr + ": " + keyStr1 + ": " + period);
							}
						}
					}
				}
				
				
				if (classes.has("T")) {
					JSONObject tutorials = classes.getJSONObject("T");
					
					for (Object sect2 : tutorials.keySet()) {
						String keyStr2 = (String)sect2;
						Object keyval2 = tutorials.get(keyStr2);
						
						if (keyval2 instanceof JSONObject) {
							JSONObject section = (JSONObject)keyval2;
							JSONArray r_periods = section.getJSONArray("r_periods");
							
							//iterates through all of the core times in a week
							for (int i = 0; i < r_periods.length(); i++) {
								JSONObject period = r_periods.getJSONObject(i);
								System.out.println(keyStr + ": " + keyStr2 + ": " + period);
							}
						}
					}
				}
				
				
				if (classes.has("L")) {
					JSONObject labs = classes.getJSONObject("L");
					
					for (Object sect3 : labs.keySet()) {
						String keyStr3 = (String)sect3;
						Object keyval3 = labs.get(keyStr3);
						
						if (keyval3 instanceof JSONObject) {
							JSONObject section = (JSONObject)keyval3;
							JSONArray r_periods = section.getJSONArray("r_periods");
							
							//iterates through all of the core times in a week
							for (int i = 0; i < r_periods.length(); i++) {
								JSONObject period = r_periods.getJSONObject(i);
								System.out.println(keyStr + ": " + keyStr3 + ": " + period);
							}
						}
					}
				}
				
			}
		}
	}

	
}