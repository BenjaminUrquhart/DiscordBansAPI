package net.dbans;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.*;
public class DiscordBanAPI {

	private String apiKey = null;
	private final String URL = "https://bans.discord.id/api/check.php?user_id=%s";
	private final String useragent = "Mozilla/5.0 (Windows NT 10.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36";
	private boolean verbose = false;
	
	public DiscordBanAPI(String token) {
		this.apiKey = token;
	}
	public DiscordBanAPI(String token, boolean verbose) {
		this.apiKey = token;
		this.verbose = verbose;
	}
	private List<UserInfo> removeDupes(List<UserInfo> list){
		List<UserInfo> out = new ArrayList<>();
		boolean isDupe = false;
		for(UserInfo u : list) {
			if(out.contains(u)) {
				continue;
			}
			out.add(u);
		}
		return out;
	}
	@SuppressWarnings("unchecked")
	public List<UserInfo> checkUsers(List<String> users){
		List<UserInfo> objects = new ArrayList<>();
		String args = "";
		try {
			if(users.size() == 0) {
				return objects;
			}
			if(users.size() == 1) {
				objects.add(checkUser(Long.parseLong(users.get(0)) + ""));
				return objects;
			}
			if(users.size() > 99) {
				List<String> tmp = new ArrayList<>();
				for(String id : users) {
					tmp.add(id);
					if(tmp.size() == 99) {
						objects.addAll(this.checkUsers(tmp));
						tmp = new ArrayList<>();
					}
				}
				if(tmp.size() > 0) {
					objects.addAll(this.checkUsers(tmp));
				}
				return removeDupes(objects);
			}
			for(String id : users) {
				args += Long.parseLong(id) + "&user_id=";
			}
			args = args.substring(0, args.length() - "&user_id=".length());
			URL url = new URL(String.format(URL, args));
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Authorization", apiKey);
			conn.setRequestProperty("User-Agent", useragent);
			conn.connect();
			Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
	        StringBuilder sb = new StringBuilder();
	        for (int c; (c = in.read()) >= 0;){
	            sb.append((char)c);
	        }
	        //Hacks - probably should use a variable but idk
	        try {
	        	if(new JSONObject(sb.toString()).has("error")) {
	        		throw new IOException((String) new JSONObject(sb.toString()).get("error"));
	        	}
	        }
	        catch(JSONException e) {} //We got an array, no error
	        JSONArray arr = new JSONArray(sb.toString());
	        List<Object> array = arr.toList();
	        for(Object obj : array) {
	        	objects.add(new UserInfo(new JSONObject((java.util.HashMap<String, Object>)obj)));
	        }
	        return removeDupes(objects);
		}
		catch(NumberFormatException e) {
			throw new IllegalArgumentException("User ID " + e.getMessage() + " is invalid: must be a long");
		}
		catch(Exception e) {
			if(verbose) {
				e.printStackTrace();
			}
		}
		return null;
	}
	public UserInfo checkUser(String user){
		try {
			Long.parseLong(user);
			URL url = new URL(String.format(URL, user));
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Authorization", apiKey);
			conn.setRequestProperty("User-Agent", useragent);
			conn.connect();
			Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
	        StringBuilder sb = new StringBuilder();
	        for (int c; (c = in.read()) >= 0;){
	            sb.append((char)c);
	        }
	        //System.out.println(sb);
	        try {
	        	if(new JSONObject(sb.toString()).has("error")) {
	        		throw new IOException((String) new JSONObject(sb.toString()).get("error"));
	        	}
	        }
	        catch(JSONException e) {} //We got an array, no error
	        JSONObject reason = (JSONObject) new JSONArray(sb.toString()).get(0);
	        return new UserInfo(reason);
		}
		catch(NumberFormatException e) {
			throw new IllegalArgumentException("User ID " + user + " is invalid: must be a long");
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
