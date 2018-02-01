package URLreq;

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
		
	}

}
