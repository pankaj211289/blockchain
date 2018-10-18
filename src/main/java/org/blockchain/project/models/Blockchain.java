package org.blockchain.project.models;

import java.util.List;

public class Blockchain {

    private List<Block> blockchain;
    private List<Transaction> openTransactions;
    
    public List<Block> getBlockchain() {
        return blockchain;
    }
    public void setBlockchain(List<Block> blockchain) {
        this.blockchain = blockchain;
    }
    
    public List<Transaction> getOpenTransactions() {
        return openTransactions;
    }
    public void setOpenTransactions(List<Transaction> openTransactions) {
        this.openTransactions = openTransactions;
    }
}
