package URLreq;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class check {
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
    public static boolean freeTime(JSONObject timetable, String time, int day, int term, String room) throws JSONException, IOException {
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
    
    
    private static boolean checkFreeRoom2(JSONObject period, String time, int day, int term, String building) {
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
            String[] proom = period.getString("room").split(" ");
            
            //@ROOM System.out.println(proom);

            if (proom[0].equals(building) && pterm % 2 == term % 2 && pday == day && pstart <= ptime && ptime <= pend) {
            	System.out.println(ptime <= pend);
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
    
    
    public static LinkedList<String> getrooms(String building) throws IOException {
		LinkedList<String> rooms = new LinkedList<String>();
		BufferedReader in = new BufferedReader(new FileReader("rooms.txt"));
		String line;
		while ((line = in.readLine()) != null) {
			String code = line.split(" ")[0];
			if (code.equals(building)) {
				rooms.add(line);
			}
		}
		in.close();
    	return rooms;
    }
    
  //This method is used to find all of the periods of every class in every building that are stored in the JSON
    public static LinkedList<String> freeTime2(JSONObject timetable, String time, int day, int term, String building) throws JSONException, IOException {
    	//Create an object to hold all of the unsorted data
        JSONObject courses = timetable.getJSONObject("timetables").getJSONObject("2017").getJSONObject("6").getJSONObject("courses");

        //@ROOM File newFile = new File("rooms.txt");
        //@ROOM FileWriter fw = new FileWriter(newFile);
		//@ROOM BufferedWriter bw = new BufferedWriter(fw);
        
        LinkedList<String> rooms = getrooms(building);
        
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
                                if (checkFreeRoom2(period, time, day, term, building)) {
                                	System.out.println(keyStr + ": " + keyStr1 + ":\n\t" + period);
                                    rooms.remove(period.getString("room"));
                                    
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
                                if (checkFreeRoom2(period, time, day, term, building)) {
                                    System.out.println(keyStr + ": " + keyStr2 + ":\n\t" + period);
                                    rooms.remove(period.getString("room"));
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
                                if (checkFreeRoom2(period, time, day, term, building)) {
                                    System.out.println(keyStr + ": " + keyStr3 + ":\n\t" + period);
                                    rooms.remove(period.getString("room"));
                                }
                            }
                        }
                    }
                }

            }
        }
        //@ROOM bw.close();
        return rooms;
    }
}
