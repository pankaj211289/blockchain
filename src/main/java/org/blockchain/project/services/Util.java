package org.blockchain.project.services;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;

import org.blockchain.project.models.Block;

public interface Util {

	public String createSHA256(String textToHash) throws NoSuchAlgorithmException;
	
	public Timestamp getCurrentTimestamp();
	
	public String readFile(String property) throws IOException;
	
	public void appendDataToFile(byte[] content, String property) throws IOException;
	
	public Block adjustNonce(Block block) throws NoSuchAlgorithmException;
}
