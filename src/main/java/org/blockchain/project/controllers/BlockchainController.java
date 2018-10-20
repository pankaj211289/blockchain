package org.blockchain.project.controllers;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.blockchain.project.models.Blockchain;
import org.blockchain.project.models.Transaction;
import org.blockchain.project.services.BlockchainService;
import org.json.JSONException;
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

    @RequestMapping(value="/blockchain", method=RequestMethod.GET)
    public void displayBlockchain() throws IOException {
        blockchainService.displayBlockchain();
    }
    
    @RequestMapping(value="/addTransaction", method=RequestMethod.POST)
    public void addTransaction(@RequestBody Transaction transaction) throws JSONException, IOException {
    	blockchainService.addTransaction(transaction);
    }
    
    @RequestMapping(value="/mine", method=RequestMethod.POST)
    public void addTransactionsToBlockchain() throws JSONException, IOException, NoSuchAlgorithmException {
    	blockchain.setDifficulty("00");
        blockchainService.addTransactionsToBlockchain();
    }
}
