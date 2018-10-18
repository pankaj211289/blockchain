package org.blockchain.project.services;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.blockchain.project.models.Blockchain;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class BlockchainServiceImpl implements BlockchainService {

    private ObjectMapper objectMapper = new ObjectMapper();
    
    @Override
    public String displayBlockchain() throws IOException {
        return objectMapper.writeValueAsString(loadBlockchain());
    }
    
    private Blockchain loadBlockchain() throws IOException {
        Path filePath = Paths.get("src/main/resources/blockchain.txt");
        
        try (Stream<String> lines = Files.lines(filePath, StandardCharsets.UTF_8)) {        
            for(String line: (Iterable<String>) lines::iterator) {
                System.out.println(line);
            }
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }
}
