package org.blockchain.project.services;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.sql.Timestamp;

import org.blockchain.project.models.Block;
import org.blockchain.project.models.Wallet;

public interface Util {

	public String createSHA256(String textToHash) throws NoSuchAlgorithmException;
	
	public Timestamp getCurrentTimestamp();
	
	public String readFile(String property) throws IOException;
	
	public void appendDataToFile(byte[] content, String property) throws IOException;
	
	public Block adjustNonce(Block block) throws NoSuchAlgorithmException;
	
	public Wallet createWallet() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, UnsupportedEncodingException;
	
	public String signTransaction(PrivateKey privateKey, String data) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException;
	
	public boolean verifySignedTransaction(PublicKey publicKey, String data, String signedData) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException;
}
