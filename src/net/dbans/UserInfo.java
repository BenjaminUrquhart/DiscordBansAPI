package net.dbans;

import org.json.*;

public class UserInfo {
	
	private String id = null;
	private String reason = null;
	private boolean banned = false;
	
	protected UserInfo(long id, String reason, boolean isBanned){
		if(id < 0) {
			throw new IllegalArgumentException("ID cannot be less than 0!");
		}
		this.id = Long.toString(id);
		this.reason = reason;
		this.banned = isBanned;
	}
	public boolean isBanned() {return this.banned;}
	public String getRaw() {
		if(!this.isBanned()) {
			throw new IllegalStateException("Cannot get ban reason of a non-banned user");
		}
		return this.reason;
	}
	public String getReason() {
		return new JSONObject(getRaw()).getString("reason");
	}
	public long getCaseId() {
		return new JSONObject(getRaw()).getLong("case_id");
	}
	public String getProof() {
		return new JSONObject(getRaw()).getString("proof");
	}
	public long getIdLong() {return Long.parseLong(this.id);}
	public String getId() {return this.id;}
	public String toString() {
		String out = "User info:";
		out += "\nBanned: " + this.isBanned();
		if(this.isBanned()) {
			out += "\nCase ID: " + this.getCaseId();
			out += "\nReason: " + this.getReason();
			out += "\nProof:" + this.getProof();
		}
		return out;
	}
}
