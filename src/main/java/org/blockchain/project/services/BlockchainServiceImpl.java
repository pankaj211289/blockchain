package org.blockchain.project.services;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.stream.Stream;

import org.blockchain.project.models.Block;
import org.blockchain.project.models.Blockchain;
import org.blockchain.project.models.Transaction;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class BlockchainServiceImpl implements BlockchainService {

    @Autowired
    private Environment environment;
    
    @Autowired
    private Blockchain blockchain;
    
    @Override
    public String displayBlockchain() throws IOException {
        return readFile("blockchain.file");
    }
    
    @Override
	public void addTransaction(Transaction transaction) throws JSONException, IOException {
    	blockchain.setOpenTransaction(transaction);
    	
    	// verify transactions
    	// TODO implementation hold till wallet set up
    	
    	// save transactions
    	String existingOpenTransactions = readFile("open.transactions.file");
    	
    	JSONArray openTransactionsArray;
    	if(existingOpenTransactions != null) {
    	    openTransactionsArray = new JSONArray(existingOpenTransactions);
    	} else {
    	    openTransactionsArray = new JSONArray();
    	}
    	
    	openTransactionsArray.put(transaction.toJSONObject());
    	appendDataToFile(openTransactionsArray.toString().getBytes(), "open.transactions.file");
	}
    
    @Override
    public void addTransactionsToBlockchain() {
        // TODO : Create Merkle tree for lists of transactions
        
        
    } 
    
    private Block createGenesisBlock() {
        // TODO : In-progress
        Block genesisBlock = new Block();
        genesisBlock.setTransactions(null);
        genesisBlock.setPreviousBlock("SHA256-00");
        genesisBlock.setTimestamp(getCurrentTimestamp().toString());
        genesisBlock.setHeight(0);
//        genesisBlock.setHash(hash);
//        genesisBlock.setNonce(nonce);
        
        return genesisBlock;
    }
    
    
    
    private String createSHA256(String textToHash) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA256");
        return Base64.getEncoder().encodeToString(messageDigest.digest(textToHash.getBytes(StandardCharsets.UTF_8)));
    }
    
    private Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    } 
    
    private String readFile(String property) throws IOException {
        Path filePath = Paths.get(environment.getProperty(property));
        
        try (Stream<String> lines = Files.lines(filePath, StandardCharsets.UTF_8)) {        
            for(String line: (Iterable<String>) lines::iterator) {
                return line;
            }
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }

    private void appendDataToFile(byte[] content, String property) throws IOException {
        Path filePath = Paths.get(environment.getProperty(property));
        Files.write(filePath, content, StandardOpenOption.TRUNCATE_EXISTING);
    }
}
