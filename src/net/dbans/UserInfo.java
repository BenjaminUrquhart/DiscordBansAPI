package net.dbans;

import org.json.*;

public class UserInfo {
	
	private String id, caseId, reason, proof;
	private boolean banned = false;
	private JSONObject raw = null;
	
	public static String urlPrefix = "https://cdn.discordapp.com/attachments/437614369724039168/";
	
	protected UserInfo (JSONObject obj) {
		this.raw = obj;
		this.id = obj.getString("user_id");
		this.banned = obj.getString("banned").equals("1");
		if(this.banned) {
			this.reason = obj.getString("reason");
			this.proof = obj.getString("proof").replace("amp;", "");
			this.caseId = obj.getString("case_id");
		}
	}
	public boolean isBanned() {return this.banned;}
	//TODO: rename to getJSONObject(), add getJSONString() method
	public JSONObject getRaw() {
		return this.raw;
	}
	public String getReason() {
		if(!this.isBanned()) {
			throw new IllegalStateException("Cannot get ban reason of a non-banned user");
		}
		return reason;
	}
	public String getCaseId() {
		if(!this.isBanned()) {
			throw new IllegalStateException("Cannot get case ID of a non-banned user");
		}
		return caseId;
	}
	public long getCaseIdLong() {
		if(!this.isBanned()) {
			throw new IllegalStateException("Cannot get case ID of a non-banned user");
		}
		return Long.parseLong(this.caseId);
	}
	public String getProof() {
		return this.proof;
	}
	public String[] getDirectProofUrls() {
		String[] tmp = this.proof.split("\\&");
		String[] urls = new String[tmp.length - 1];
		for(int i = 1; i < tmp.length; i++) {
			urls[i - 1] = UserInfo.urlPrefix + tmp[i].split("=")[1];
		}
		return urls;
	}
	public long getUserIdLong() {return Long.parseLong(this.id);}
	public String getUserId() {return this.id;}
	public String toString() {
		String out = "User info:";
		out += "\nUser ID: " + this.getUserId();
		out += "\nBanned: " + (this.isBanned() ? "yes" : "no");
		if(this.isBanned()) {
			String[] urls = this.getDirectProofUrls();
			out += "\nCase ID: " + this.getCaseId();
			out += "\nReason: " + this.getReason();
			out += "\nProof: " + this.getProof();
			out += "\nDirect Links:";
			for(String link : urls) {
				out += "\n" + link;
			}
		}
		return out;
	}
	public boolean equals(Object obj) {
		//I forgot instanceof was a thing...
		if(!(obj instanceof UserInfo)) {
			return false;
		}
		UserInfo u = (UserInfo)obj;
		boolean isEqual = false;
		isEqual = this.getUserIdLong() == u.getUserIdLong();
		isEqual = this.isBanned() == u.isBanned();
		if(this.isBanned() && u.isBanned()) {
			isEqual = this.getCaseIdLong() == u.getCaseIdLong();
			isEqual = this.getProof().equals(u.getProof());
			isEqual = this.getReason().equals(u.getReason());
		}
		return isEqual;
	}
}
