package URLreq;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//import org.json.JSONObject;

public class getURL {

	public static void main(String[] args) {
		String json = new String(getJSON("https://www.timetablegenerator.io/api/v2/school/mcmaster/"));
		createFile(json);
	}
	
	
	public static String getJSON(String url) {
		
		try {
			//String url = "https://www.timetablegenerator.io/api/v2/school/mcmaster/";
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			
			// optional default is GET
			con.setRequestMethod("GET");
			
			//add request header
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);
			
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String json = in.readLine();
			
			/*
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			*/
			
			//JSONObject myresponse = new JSONObject(response.toString());
			
			return json;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	private static void createFile(String json) {
		File newFile = new File("timetables.json");
		try {
			FileWriter fw = new FileWriter(newFile);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(json);
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
			
			newFile = null;
		}
		return;
	}

}
