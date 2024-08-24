package com.example.container2.controller;
import com.opencsv.CSVReader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.StringReader;

import java.util.Map;

@RestController
public class Container2Controller {

    @PostMapping("/calculatesum")
    public ResponseEntity<Integer> calculateSum(@RequestBody Map<String, String> request) {
        try {
            String product = request.get("product");
            String fileContent = request.get("fileContent");

            if (fileContent == null || fileContent.isEmpty()) {
                return ResponseEntity.ok(-1);
            }

            int sum = calculateSumFromCSV(fileContent, product);

            if(sum < 0){
                return ResponseEntity.ok(-1);
            }

            return ResponseEntity.ok(sum);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(-2);
        }
    }
    private int calculateSumFromCSV(String fileContent, String product) throws Exception {
        try (CSVReader reader = new CSVReader(new StringReader(fileContent))) {
            String[] nextLine;
            int sum = 0;

            String[] headers = reader.readNext();

            if(headers.length > 2){
                return -1;
            }

            while ((nextLine = reader.readNext()) != null) {

                if(nextLine.length > 2) {
                    return -1;
                }
                String rowProduct = nextLine[0];
                if (rowProduct.equals(product)) {
                    sum += Integer.parseInt(nextLine[1]);
                }
            }

            return sum;
        }
    }
}
