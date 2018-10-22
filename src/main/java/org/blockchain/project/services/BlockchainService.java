package org.blockchain.project.services;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.blockchain.project.models.Transaction;
import org.json.JSONArray;
import org.json.JSONException;

public interface BlockchainService {

    public String displayBlockchain() throws IOException;
    
    public void addTransaction(Transaction transaction) throws JSONException, IOException, NoSuchAlgorithmException;
    
    public void addTransactionsToBlockchain() throws NoSuchAlgorithmException, IOException, JSONException;
    
    public void populateOpenTransactions(JSONArray jsonArray) throws JSONException;
}
