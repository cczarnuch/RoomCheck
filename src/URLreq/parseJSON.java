package URLreq;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class parseJSON {
    public static void main(String[] args) throws JSONException, IOException {
        JSONObject timetable = JSONtoObj(false);
        //freeTime(timetable, args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]), args[3]);
        String time = "19:30";//args[0];
        Integer hour = 0;
        while (freeTime(timetable, time, 4, 1, "BSB 147") && hour < 24) {
        //while (freeTime(timetable, time, Integer.parseInt(args[1]), Integer.parseInt(args[2]), args[3]) && hour < 24) {
        	String[] time2 = time.split(":");
        	hour = Integer.parseInt(time2[0]);
        	hour++;
        	time = hour.toString() + ":" + time2[1];
        }
        System.out.println("Free until: " + time);
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
    
    
    private static boolean checkFreeRoom(JSONObject period, String time, int day, int term, String room) {
        try {
            String start = period.getString("start");
            String[] start2 = start.split(":");
            String end = period.getString("end");
            String[] end2 = end.split(":");
            String[] time2 = time.split(":");

            double pstart = Double.parseDouble(start2[0]) + (Double.parseDouble(start2[1]) / 60);
            double pend = Double.parseDouble(end2[0]) + (Double.parseDouble(end2[1]) / 60);
            double ptime = Double.parseDouble(time2[0]) + (Double.parseDouble(time2[1]) / 60);

            int pterm = period.getInt("term");
            int pday = period.getInt("day");
            String proom = period.getString("room");
            
            //@ROOM System.out.println(proom);


            if (proom.equals(room) && pterm % 2 == term % 2 && pday == day && pstart <= ptime && ptime <= pend) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }


    //This method is used to find all of the periods of every class in every building that are stored in the JSON
    private static boolean freeTime(JSONObject timetable, String time, int day, int term, String room) throws JSONException, IOException {
        //Create an object to hold all of the unsorted data
        JSONObject courses = timetable.getJSONObject("timetables").getJSONObject("2017").getJSONObject("6").getJSONObject("courses");

        //@ROOM File newFile = new File("rooms.txt");
        //@ROOM FileWriter fw = new FileWriter(newFile);
		//@ROOM BufferedWriter bw = new BufferedWriter(fw);
        
        //Iterates through the previous object using a string for the names/keys of the nested objects
        Iterator<?> key = courses.keys();
        String keyStr = new String();
        while(key.hasNext()) {
            keyStr = (String)key.next();
            Object keyval = courses.get(keyStr);


            //checks for instance of JSONObject then prints/stores all lectures, tutorials, and labs
            if (keyval instanceof JSONObject) {

                JSONObject classes = ((JSONObject)keyval).getJSONObject("sections");

                //checks for core/lectures
                if (classes.has("C")) {
                    JSONObject lectures = classes.getJSONObject("C");
                    Iterator<?> sect1 = lectures.keys();
                    String keyStr1 = new String();
                    while(sect1.hasNext()) {
                        keyStr1 = (String) sect1.next();
                        Object keyval1 = lectures.get(keyStr1);


                        if (keyval1 instanceof JSONObject) {
                            JSONObject section = (JSONObject)keyval1;
                            JSONArray r_periods = section.getJSONArray("r_periods");

                            //iterates through all of the core times in a week
                            for (int i = 0; i < r_periods.length(); i++) {
                                JSONObject period = r_periods.getJSONObject(i);
                                //@ROOM bw.write(period.getString("room")+"\n");
                                if (checkFreeRoom(period, time, day, term, room)) {
                                	System.out.println(keyStr + ": " + keyStr1 + ":\n\t" + period);
                                    System.out.println("taken!");
                                    return false;
                                    
                                }
                            }
                            
                            
                        }
                    }
                }


                if (classes.has("T")) {
                    JSONObject tutorials = classes.getJSONObject("T");

                    Iterator<?> sect2 = tutorials.keys();
                    String keyStr2 = new String();
                    while(sect2.hasNext()) {
                        keyStr2 = (String) sect2.next();
                        Object keyval2 = tutorials.get(keyStr2);

                        
                        if (keyval2 instanceof JSONObject) {
                            JSONObject section = (JSONObject)keyval2;
                            JSONArray r_periods = section.getJSONArray("r_periods");

                            //iterates through all of the core times in a week
                            for (int i = 0; i < r_periods.length(); i++) {
                                JSONObject period = r_periods.getJSONObject(i);
                                //@ROOM bw.write(period.getString("room")+"\n");
                                if (checkFreeRoom(period, time, day, term, room)) {
                                    System.out.println(keyStr + ": " + keyStr2 + ":\n\t" + period);
                                    System.out.println("taken!");
                                    return false;
                                }
                            }
                        }
                    }
                }


                if (classes.has("L")) {
                    JSONObject labs = classes.getJSONObject("L");

                    Iterator<?> sect3 = labs.keys();
                    String keyStr3 = new String();
                    while(sect3.hasNext()) {
                        keyStr3 = (String) sect3.next();
                        Object keyval3 = labs.get(keyStr3);

                        if (keyval3 instanceof JSONObject) {
                            JSONObject section = (JSONObject)keyval3;
                            JSONArray r_periods = section.getJSONArray("r_periods");

                            //iterates through all of the core times in a week
                            for (int i = 0; i < r_periods.length(); i++) {
                                JSONObject period = r_periods.getJSONObject(i);
                                //@ROOM bw.write(period.getString("room")+"\n");
                                if (checkFreeRoom(period, time, day, term, room)) {
                                    System.out.println(keyStr + ": " + keyStr3 + ":\n\t" + period);
                                    System.out.println("taken!");
                                    return false;
                                }
                            }
                        }
                    }
                }

            }
        }
        //@ROOM bw.close();
        return true;
    }
}