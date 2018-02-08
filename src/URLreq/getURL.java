package URLreq;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class getURL {

	public static void main(String[] args) {
		String json = new String(getJSON("https://www.timetablegenerator.io/api/v2/school/mcmaster/"));
		createFile(json);
		System.out.println("File created!");
	}
	
	
	public static String getJSON(String url) {
		
		try {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			
			// optional default is GET
			con.setRequestMethod("GET");
			
			//add request header
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + responseCode + "\n");
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			
			return in.readLine();
			
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
		}
		return;
	}

}
