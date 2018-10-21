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
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Stream;

import org.blockchain.project.models.Block;
import org.blockchain.project.models.Blockchain;
import org.blockchain.project.models.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class UtilImpl implements Util {
	
    private Environment environment;
    private Blockchain blockchain;
	
    @Autowired
	public UtilImpl(Environment environment, Blockchain blockchain) {
		this.environment = environment;
		this.blockchain = blockchain;
	}
    
	public String createSHA256(String textToHash) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        return Base64.getEncoder().encodeToString(messageDigest.digest(textToHash.getBytes(StandardCharsets.UTF_8)));
    }
    
    public Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    } 
    
    public String readFile(String property) throws IOException {
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

    public void appendDataToFile(byte[] content, String property) throws IOException {
        Path filePath = Paths.get(environment.getProperty(property));
        Files.write(filePath, content, StandardOpenOption.TRUNCATE_EXISTING);
    }
    
    public Block adjustNonce(Block block) throws NoSuchAlgorithmException {
    	 while(!hashedBlock(block).getHash().startsWith(blockchain.getDifficulty())) {
    		 block.setNonce(block.getNonce() + 1);
    	 }
    	 return block;
    }
    
    private Block hashedBlock(Block block) throws NoSuchAlgorithmException {
    	block.setHash(this.createSHA256(this.generateMerkelTree(block.getTransactions()) + block.getHeight() + block.getNonce() +
    			block.getTimestamp() + block.getPreviousBlock()));
    	return block;
    }
    
    private String generateMerkelTree(List<Transaction> transactions) throws NoSuchAlgorithmException {
    	if(transactions != null) {
	    	List<String> hashedTransactionList = createHashedTransactionList(transactions);
	    	return merkelTree(hashedTransactionList);
    	} else {
    		return "";
    	}
    }
    
    private String merkelTree(List<String> transactions) throws NoSuchAlgorithmException {
    	List<String> compressedHashedTransactionList = new ArrayList<>();
    	
    	int numberOfTransactions = transactions.size();
		if(numberOfTransactions % 2 != 0) {
			transactions.add(transactions.get(numberOfTransactions - 1));
		}
		for(int i = 0; i < transactions.size(); i+=2) {
			compressedHashedTransactionList.add(this.createSHA256(transactions.get(i) + transactions.get(i + 1)));
		}
		
		if(compressedHashedTransactionList.size() != 1) {
			return merkelTree(compressedHashedTransactionList);
		} else {
			return compressedHashedTransactionList.get(0);
		}
    }
    
    private List<String> createHashedTransactionList(List<Transaction> transactions) throws NoSuchAlgorithmException {
    	List<String> hashedTransactionList = new ArrayList<>();

    	for(Transaction transaction: transactions) {
    		hashedTransactionList.add(transaction.getTxHash());
    	}
    	
    	return hashedTransactionList;
    }
}
