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
		String response = getURL.getJSON("https://www.timetablegenerator.io/api/v2/school/mcmaster/");
		JSONObject myresponse;
		
		try {
			myresponse = new JSONObject(response.toString());
			return myresponse;
		} catch (Exception e) {e.printStackTrace();}
		return null;
	}
	
	
	public static void freeTime(JSONObject timetable) {
		JSONObject courses = timetable.getJSONObject("timetables").getJSONObject("2017").getJSONObject("6").getJSONObject("courses");
		
		for (Object key : courses.keySet()) {
			String keyStr = (String)key;
			Object keyval = courses.get(keyStr);
			
			//System.out.println("key: " + keyStr + "\nvalue: " + keyval);
			
			if (keyval instanceof JSONObject) {
				
				JSONObject classes = ((JSONObject)keyval).getJSONObject("sections");
				
				if (classes.has("C")) {
					//System.out.println(lectures);
					JSONObject lectures = classes.getJSONObject("C");
					
					JSONObject section = lectures.getJSONObject("C01");
					
					JSONArray r_periods = section.getJSONArray("r_periods");
					
					//System.out.println(r_periods);
					
					for (int i = 0; i < r_periods.length(); i++) {
						JSONObject period = r_periods.getJSONObject(i);
						System.out.println(period);
					}
				}
				
				if (classes.has("L")) {
					//System.out.println(lectures);
					JSONObject lectures = classes.getJSONObject("L");
					
					JSONObject section = lectures.getJSONObject("L01");
					
					JSONArray r_periods = section.getJSONArray("r_periods");
					
					//System.out.println(r_periods);
					
					for (int i = 0; i < r_periods.length(); i++) {
						JSONObject period = r_periods.getJSONObject(i);
						System.out.println(period);
					}
				}
				
				if (classes.has("T")) {
					//System.out.println(lectures);
					JSONObject lectures = classes.getJSONObject("T");
					
					JSONObject section = lectures.getJSONObject("T01");
					
					JSONArray r_periods = section.getJSONArray("r_periods");
					
					//System.out.println(r_periods);
					
					for (int i = 0; i < r_periods.length(); i++) {
						JSONObject period = r_periods.getJSONObject(i);
						System.out.println(period);
					}
				}
				
			}
		}
	}

}
