package org.blockchain.project.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Transaction {

    private String sender;
    private String recipient;
    private String data;
    private String timestamp;
    
    public String getSender() {
        return sender;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }
    
    public String getRecipient() {
        return recipient;
    }
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
    
    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }
    
    public String getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    
    public JSONObject toJSONObject() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sender", getSender());
        jsonObject.put("recipient", getRecipient());
        jsonObject.put("data", getData());
        jsonObject.put("timestamp", getTimestamp());
        
        return jsonObject;
    }
}
