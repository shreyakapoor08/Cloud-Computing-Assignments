package com.example.container1.controller;

import com.example.container1.entity.Container1Request;
import com.example.container1.exception.InvalidInputException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/calculate")
public class Container1Controller {

    private static final String CONTAINER2_URL = "http://shreya-container2-test:8080/calculatesum";

    private final RestTemplate restTemplate;

    public Container1Controller(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping
    public ResponseEntity<Object> calculate(@RequestBody Container1Request request) {
        try {
            validateInput(request);
            String home = System.getProperty("user.home");
            System.out.println("Ghar - path --> " + home);
//            String filePath = home+ "\\Downloads\\Volume\\" + request.getFile();
            String filePath = "/home/volume/" + request.getFile();
            System.out.println("FilePath --> " + filePath);
            File file = new File(filePath);
            if (!file.exists()) {
                System.out.println("File not found: " + request.getFile());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createResult(request.getFile(), "File not found.", -9));
            }

            String fileContent = readFileContent(filePath);

            System.out.println("File content: " + fileContent);

            // Send request to Container 2 with file content
             ResponseEntity<Integer> responseEntity = sendRequestToContainer2(request.getProduct(), fileContent);

            return ResponseEntity.status(responseEntity.getStatusCode()).body(createResult(request.getFile(),"",responseEntity.getBody()));
        } catch (InvalidInputException | IOException e) {
            System.out.println("Bad request -->");
            e.printStackTrace();
            return ResponseEntity.badRequest().body(createResult(request.getFile(), e.getMessage(),-9));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    private void validateInput(Container1Request request) throws InvalidInputException {
        if (request.getFile() == null) {
            throw new InvalidInputException("Invalid JSON input.");
        }
    }

    private ResponseEntity<Integer> sendRequestToContainer2(String product, String fileContent) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("product", product);
            requestBody.put("fileContent", fileContent);

            HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

            return restTemplate.postForEntity(CONTAINER2_URL, entity, Integer.class);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(-2);
        }
    }

    private String readFileContent(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return new String(Files.readAllBytes(path));
    }

    private Map<String, Object> createResult(String fileName, String errorMessage, Integer sum) {
        Map<String, Object> result = new HashMap<>();
        result.put("file", fileName);
        if(sum == -9) {
            result.put("error", errorMessage);
        }
        else if(sum == -1){
            result.put("error", "Input file not in CSV format.");
        }
        else{
            result.put("sum", sum);
        }

        return result;
    }
}