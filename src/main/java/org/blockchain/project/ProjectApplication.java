package org.blockchain.project;

import java.io.IOException;

import org.blockchain.project.services.BlockchainService;
import org.blockchain.project.services.Util;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ProjectApplication {

	public static void main(String[] args) throws BeansException, IOException, JSONException {
		ConfigurableApplicationContext context = SpringApplication.run(ProjectApplication.class, args);
		
		// Read transactions
    	String existingOpenTransactions = context.getBean(Util.class).readFile("open.transactions.file");
    	
    	JSONArray openTransactionsArray;
    	if(existingOpenTransactions != null) {
    	    openTransactionsArray = new JSONArray(existingOpenTransactions);
    	} else {
    	    openTransactionsArray = new JSONArray();
    	}
    	
    	context.getBean(BlockchainService.class).populateOpenTransactions(openTransactionsArray);
	}
}
