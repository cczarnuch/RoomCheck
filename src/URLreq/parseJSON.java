package URLreq;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

import org.json.JSONException;
import org.json.JSONObject;

public class parseJSON {
    public static void main(String[] args) throws JSONException, IOException {
    	if (args.length != 5) {
    		System.out.println("Correct use: \"HH:mm\" day(int) term(int) \"room\" building(boolean)\n");
    		return;
    	}
        JSONObject timetable = JSONtoObj(false);
        //String time = args[0];
        String time = "15:30";
        Integer hour = 0;
        Boolean building = Boolean.parseBoolean(args[4]);
        
        if (!building) {
        	//while (check.freeTime(timetable, time, 3, 1, "MDCL 1305") && hour < 24) {
        	while (check.freeTime(timetable, time, Integer.parseInt(args[1]), Integer.parseInt(args[2]), args[3]) && hour < 24) {
        		String[] time2 = time.split(":");
        		hour = Integer.parseInt(time2[0]);
        		hour++;
        		time = hour.toString() + ":" + time2[1];
        	}
        	System.out.println("Free until: " + time);
        } else {
	        //@building
	        LinkedList<String> rooms = check.freeTime2(timetable, time, Integer.parseInt(args[1]), Integer.parseInt(args[2]), args[3].toUpperCase());
        	//LinkedList<String> rooms = check.freeTime2(timetable, time, 3, 1, "HH");
	        
	        Iterator<String> itr = rooms.iterator();
	        while(itr.hasNext()){
	        	System.out.println(itr.next());
	        }
        }
    }


    private static JSONObject JSONtoObj(Boolean Online) throws JSONException, IOException {

        if (Online) {
            //Goes to URL and gets the json file
            String response = getURL.getJSON("https://www.timetablegenerator.io/api/v2/school/mcmaster/");
            try {
                //converts the response from the URL to JSONObject type; else return null
                return new JSONObject(response.toString());
            } catch (Exception e) {
            	e.printStackTrace();
            }
            return null;
        } else {
            BufferedReader in = new BufferedReader(new FileReader("timetables.json"));
            JSONObject myresp = new JSONObject(in.readLine());
            in.close();
            return myresp;
        }
    }
}