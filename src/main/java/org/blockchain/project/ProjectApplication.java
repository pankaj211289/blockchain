package org.blockchain.project;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;

import org.blockchain.project.services.BlockchainService;
import org.blockchain.project.services.Util;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ProjectApplication {

	public static void main(String[] args) throws BeansException, IOException, JSONException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchProviderException {
		ConfigurableApplicationContext context = SpringApplication.run(ProjectApplication.class, args);
		
		Security.addProvider(new BouncyCastleProvider());
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
