package net.dbans;

import org.json.*;

public class UserInfo {
	
	private String id = null;
	private String caseId = null;
	private String reason = null;
	private String proof = null;
	private boolean banned = false;
	private JSONObject raw = null;
	
	protected UserInfo (JSONObject obj) {
		this.raw = obj;
		this.id = obj.getString("user_id");
		this.banned = obj.getString("banned").equals("1");
		if(this.banned) {
			this.reason = obj.getString("reason");
			this.proof = obj.getString("proof");
			this.caseId = obj.getString("case_id");
		}
	}
	public boolean isBanned() {return this.banned;}
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
	public long getUserIdLong() {return Long.parseLong(this.id);}
	public String getUserId() {return this.id;}
	public String toString() {
		String out = "User info:";
		out += "\nUser ID: " + this.getUserId();
		out += "\nBanned: " + this.isBanned();
		if(this.isBanned()) {
			out += "\nCase ID: " + this.getCaseId();
			out += "\nReason: " + this.getReason();
			out += "\nProof: " + this.getProof();
		}
		return out;
	}
	public boolean equals(Object obj) {
		UserInfo u = null;
		try {
			u = (UserInfo)obj;
		}
		catch(Exception e) {
			return false;
		}
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
