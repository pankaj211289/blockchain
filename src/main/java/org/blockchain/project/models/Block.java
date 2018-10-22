package org.blockchain.project.models;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Block {

    private List<Transaction> transactions;
    private int height;
    private int nonce;
    private String timestamp;
    private String previousBlock;
    private String hash;
    
    public List<Transaction> getTransactions() {
        return transactions;
    }
    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
    
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    
    public int getNonce() {
        return nonce;
    }
    public void setNonce(int nonce) {
        this.nonce = nonce;
    }
    
    public String getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    
    public String getPreviousBlock() {
        return previousBlock;
    }
    public void setPreviousBlock(String previousBlock) {
        this.previousBlock = previousBlock;
    }
    
    public String getHash() {
        return hash;
    }
    public void setHash(String hash) {
        this.hash = hash;
    }
    
    public JSONObject toJSONObject() throws JSONException {
    	JSONObject block = new JSONObject();
    	
    	JSONArray transactions = new JSONArray();
    	if(getTransactions() != null) {
        	for(Transaction transaction: getTransactions()) {
        		transactions.put(transaction.toJSONObject());
        	}
    	}
    	block.put("transactions", transactions.length() == 0? null: transactions);
    	block.put("height", getHeight());
    	block.put("nonce", getNonce());
    	block.put("timestamp", getTimestamp());
    	block.put("previousBlock", getPreviousBlock());
    	block.put("hash", getHash());
    	
    	return block;
    }
}
