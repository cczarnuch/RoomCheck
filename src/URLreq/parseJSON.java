package URLreq;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class parseJSON {

	public static void main(String[] args) throws JSONException {
		JSONObject timetable = JSONtoObj();
		freeTime(timetable);

	}
	
	
	public static JSONObject JSONtoObj() {
		//Goes to URL and gets the json file
		String response = getURL.getJSON("https://www.timetablegenerator.io/api/v2/school/mcmaster/");
		//file is stored in a JSONObject type
		JSONObject myresponse;
		
		try {
			//converts the response from the URL to JSONObject type; else return null
			myresponse = new JSONObject(response.toString());
			return myresponse;
		} catch (Exception e) {e.printStackTrace();}
		return null;
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
					
					//checks for all sections
					if (lectures.has("C01")) {
						JSONObject section = lectures.getJSONObject("C01");
						JSONArray r_periods = section.getJSONArray("r_periods");
						
						//iterates through all of the core times in a week
						for (int i = 0; i < r_periods.length(); i++) {
							JSONObject period = r_periods.getJSONObject(i);
							System.out.println(keyStr + ": C01:" + period);
						}
					} if (lectures.has("C02")) {
						JSONObject section = lectures.getJSONObject("C02");
						JSONArray r_periods = section.getJSONArray("r_periods");
						
						for (int i = 0; i < r_periods.length(); i++) {
							JSONObject period = r_periods.getJSONObject(i);
							System.out.println(keyStr + ": C02:" + period);
						}
					} if (lectures.has("C03")) {
						JSONObject section = lectures.getJSONObject("C03");
						JSONArray r_periods = section.getJSONArray("r_periods");
						
						for (int i = 0; i < r_periods.length(); i++) {
							JSONObject period = r_periods.getJSONObject(i);
							System.out.println(keyStr + ": C03:" + period);
						}
					}
				}
				
				
				if (classes.has("T")) {
					JSONObject tutorials = classes.getJSONObject("T");
					
					if (tutorials.has("T01")) {
						JSONObject section = tutorials.getJSONObject("T01");
						JSONArray r_periods = section.getJSONArray("r_periods");
						
						for (int i = 0; i < r_periods.length(); i++) {
							JSONObject period = r_periods.getJSONObject(i);
							System.out.println(keyStr + ": T01:" + period);
						}
					} if (tutorials.has("T02")) {
						JSONObject section = tutorials.getJSONObject("T02");
						JSONArray r_periods = section.getJSONArray("r_periods");
						
						for (int i = 0; i < r_periods.length(); i++) {
							JSONObject period = r_periods.getJSONObject(i);
							System.out.println(keyStr + ": T02:" + period);
						}
					}
				}
				
				
				if (classes.has("L")) {
					JSONObject labs = classes.getJSONObject("L");
					
					if (labs.has("L01")) {
						JSONObject section = labs.getJSONObject("L01");
						JSONArray r_periods = section.getJSONArray("r_periods");
						
						for (int i = 0; i < r_periods.length(); i++) {
							JSONObject period = r_periods.getJSONObject(i);
							System.out.println(keyStr + ": L01:" + period);
						}
					} if (labs.has("L02")) {
						JSONObject section = labs.getJSONObject("L02");
						JSONArray r_periods = section.getJSONArray("r_periods");
						
						for (int i = 0; i < r_periods.length(); i++) {
							JSONObject period = r_periods.getJSONObject(i);
							System.out.println(keyStr + ": L02:" + period);
						}
					}
				}
				
			}
		}
	}

	
}