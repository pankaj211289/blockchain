package org.blockchain.project.services;

import java.io.IOException;

import org.blockchain.project.models.Transaction;

public interface BlockchainService {

    public String displayBlockchain() throws IOException;
    
    public void addTransaction(Transaction transaction);
}
