package net.dbans;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
public class DiscordBanAPI {

	private String apiKey = null;
	private final String URL = "https://bans.discord.id/api/check.php?user_id=%s";
	private boolean verbose = false;
	
	public DiscordBanAPI(String token) {
		this.apiKey = token;
	}
	public DiscordBanAPI(String token, boolean verbose) {
		this.apiKey = token;
		this.verbose = verbose;
	}
	public UserInfo checkUser(String user){
		try {
			URL url = new URL(String.format(URL, user));
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestProperty("Authorization", apiKey);
			conn.connect();
			Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
	        StringBuilder sb = new StringBuilder();
	        for (int c; (c = in.read()) >= 0;){
	            sb.append((char)c);
	        }
	        return new UserInfo(Long.parseLong(user), sb.toString(), sb.toString().toLowerCase().contains("\"banned\": \"1\""));
		}
		catch(Exception e) {
			if(verbose) {
				e.printStackTrace();
			}
		}
		return null;
	}
	public void setToken(String token) {
		this.apiKey = token;
	}
}
