package org.blockchain.project.services;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.List;

import org.blockchain.project.models.Block;
import org.blockchain.project.models.Blockchain;
import org.blockchain.project.models.Transaction;
import org.blockchain.project.models.Wallet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlockchainServiceImpl implements BlockchainService {

    @Autowired
    private Blockchain blockchain;
    
    @Autowired
    private Util util;
    
    @Override
    public String displayBlockchain() throws IOException {
        return util.readFile("blockchain.file");
    }
    
    @Override
	public void addTransaction(Transaction transaction) throws JSONException, IOException, NoSuchAlgorithmException {
    	// verify transactions
    	// TODO implementation hold till wallet set up
    	
    	transaction.setTimestamp(util.getCurrentTimestamp().toString());
    	transaction.setTxHash(util.createSHA256(transaction.toString()));
    	blockchain.getOpenTransactions().add(transaction);
    	
    	JSONArray openTransactionsArray = new JSONArray();
    	for(Transaction t : blockchain.getOpenTransactions()) {
    		openTransactionsArray.put(t.toJSONObject());
    	}
    	util.appendDataToFile(openTransactionsArray.toString().getBytes(), "open.transactions.file");
	}
    
    @Override
    public void addTransactionsToBlockchain() throws NoSuchAlgorithmException, IOException, JSONException {
    	if(blockchain.getBlockchain() == null || blockchain.getBlockchain().size() == 0) {
    		blockchain.getBlockchain().add(createGenesisBlock());
    	}
    	
    	Block previousBlock = blockchain.getBlockchain().get(blockchain.getBlockchain().size() - 1);
    	
    	Block block = new Block();
    	block.setTransactions(blockchain.getOpenTransactions());
    	block.setPreviousBlock(previousBlock.getHash());
    	block.setHeight(previousBlock.getHeight() + 1);
    	block.setTimestamp(util.getCurrentTimestamp().toString());
    	util.adjustNonce(block);
    	
    	blockchain.getBlockchain().add(block);
    	
    	//Save blockchain and update open transaction
    	JSONArray blockArray = new JSONArray();
    	for(Block updatedBlock: blockchain.getBlockchain()) {
    		blockArray.put(updatedBlock.toJSONObject());
    	}
    	
    	util.appendDataToFile(blockArray.toString().getBytes(), "blockchain.file");
    	
    	// Clears open Transactions
    	blockchain.getOpenTransactions().clear();
    	util.appendDataToFile("".toString().getBytes(), "open.transactions.file");
    } 
    
    @Override
    public void populateOpenTransactions(JSONArray jsonArray) throws JSONException {
    	List<Transaction> openTransactions = blockchain.getOpenTransactions();
    	for(int i = 0; i < jsonArray.length(); i++) {
    		JSONObject transactionJSONObj = jsonArray.getJSONObject(i);
    		Transaction transaction = new Transaction();
    		transaction.setSender(transactionJSONObj.has("sender") ? transactionJSONObj.getString("sender") : null);
    		transaction.setRecipient(transactionJSONObj.has("recipient") ? transactionJSONObj.getString("recipient") : null);
    		transaction.setData(transactionJSONObj.has("data") ? transactionJSONObj.getString("data") : null);
    		transaction.setTimestamp(transactionJSONObj.has("timestamp") ? transactionJSONObj.getString("timestamp") : null);
    		transaction.setTxHash(transactionJSONObj.has("txHash") ? transactionJSONObj.getString("txHash") : null);
    		
    		openTransactions.add(transaction);
    	}
    }
    
    @Override
    public Wallet cerateWallet() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, UnsupportedEncodingException {
        return util.createWallet();
    }
    
    private Block createGenesisBlock() throws NoSuchAlgorithmException {
        Block genesisBlock = new Block();
        genesisBlock.setTransactions(null);
        genesisBlock.setPreviousBlock("00000000000000000000000000000000000000000000");
        genesisBlock.setTimestamp(util.getCurrentTimestamp().toString());
        genesisBlock.setHeight(0);
        util.adjustNonce(genesisBlock);
        
        return genesisBlock;
    }
}
