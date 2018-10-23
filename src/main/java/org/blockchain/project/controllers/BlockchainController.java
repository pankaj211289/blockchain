package org.blockchain.project.controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import org.blockchain.project.models.Blockchain;
import org.blockchain.project.models.Transaction;
import org.blockchain.project.models.Wallet;
import org.blockchain.project.services.BlockchainService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlockchainController {

    @Autowired
    private BlockchainService blockchainService;
    
    @Autowired
    private Blockchain blockchain;
    
    @Autowired
    private Transaction transaction;
    
    @Autowired
    private Wallet wallet;
    
    @RequestMapping(value="/blockchain", method=RequestMethod.GET)
    public void displayBlockchain() throws IOException {
        blockchainService.displayBlockchain();
        
    }
    
    @RequestMapping(value="/addTransaction", method=RequestMethod.POST)
    public void addTransaction(@RequestBody Transaction transaction) throws JSONException, IOException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException, InvalidKeySpecException {
    	blockchainService.addTransaction(transaction);
    }
    
    @RequestMapping(value="/mine", method=RequestMethod.POST)
    public void addTransactionsToBlockchain() throws JSONException, IOException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException, InvalidKeySpecException {
    	blockchain.setDifficulty("00");
        blockchainService.addTransactionsToBlockchain();
    }
    
    @RequestMapping(value="/createWallet", method=RequestMethod.POST)
    public String createWallet() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, UnsupportedEncodingException, JSONException {
        Wallet wallet = blockchainService.createWallet();
        
        JSONObject walletJSONObj = new JSONObject();
        walletJSONObj.put("publicKey", wallet.getPublicKey());
        walletJSONObj.put("privateKey", wallet.getPrivateKey());
        return walletJSONObj.toString();
    }
    
    @RequestMapping(value="/loadWallet", method=RequestMethod.PUT)
    public void loadWallet(@RequestBody Wallet wallet) {
        // TODO: Test to match Public-Private Key values
        
        transaction.setSender(wallet.getPublicKey());
        this.wallet.setPublicKey(wallet.getPublicKey());
        this.wallet.setPrivateKey(wallet.getPrivateKey());
    }
}