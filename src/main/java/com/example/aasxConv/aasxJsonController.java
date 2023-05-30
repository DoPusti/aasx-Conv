package com.example.aasxConv;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@RestController
public class aasxJsonController {

    @Autowired
    private aasxJsonUserRepository aasxJsonUserRepository;

    @Autowired
    private aasxJsonObjectsRepository aasxJsonObjectsRepository;


    @GetMapping("/aasxpackage")
    public ResponseEntity<List<aasxJsonUser>> getPackage(@RequestParam(value = "name") String name,
                                                         @RequestParam(value = "user") String user) {

        // get package from db by user and name
        List<aasxJsonUser> packageName = aasxJsonUserRepository.findbyUseriD(user, name);

        if (packageName.isEmpty()) {

            aasxJsonObjects jsonObject = aasxJsonObjectsRepository.findJsonByName(name);
            String json = jsonObject.getJson_objects();
            aasxJsonUser newUserEntry = new aasxJsonUser();
            newUserEntry.setUserID(user);
            newUserEntry.setJson_object(json);
            newUserEntry.setVersion(1);
            newUserEntry.setName(name);
            aasxJsonUserRepository.save(newUserEntry);
            return new ResponseEntity("No package found by user " + user + ". New Entry set for user " + json, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(packageName, HttpStatus.OK);


    }

    @DeleteMapping("/aasxpackage")
    public ResponseEntity deletePackage(@RequestParam(value = "name") String name,
                                        @RequestParam(value = "user") String user) {
        List<aasxJsonUser> packageName = aasxJsonUserRepository.findbyUseriD(user, name);
        if (packageName.isEmpty()) {
            return new ResponseEntity("No package found by user " + user, HttpStatus.NOT_FOUND);
        }
        aasxJsonUserRepository.delete(packageName.get(0));
        return new ResponseEntity<>("Package deleted", HttpStatus.OK);
    }

    @PatchMapping("/aasxpackage")
    public ResponseEntity updatePackage(@RequestParam(value = "name") String name,
                                        @RequestParam(value = "user") String user,
                                        @RequestBody String newJson) throws JSONException, IOException {

        JSONObject jsonObject = new JSONObject(newJson);
        JSONArray keys = jsonObject.names();
        List<aasxJsonUser> packageName = aasxJsonUserRepository.findbyUseriD(user, name);
        if (packageName.isEmpty()) {

            aasxJsonObjects jsonObjects = aasxJsonObjectsRepository.findJsonByName(name);
            String json = jsonObjects.getJson_objects();
            aasxJsonUser newUserEntry = new aasxJsonUser();
            newUserEntry.setUserID(user);
            newUserEntry.setJson_object(json);
            newUserEntry.setVersion(1);
            newUserEntry.setName(name);
            aasxJsonUserRepository.save(newUserEntry);
            return new ResponseEntity("No package found by user " + user + ". New Entry set for user " + json, HttpStatus.NOT_FOUND);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree((JsonParser) packageName);


        String nameToSet = "";
        String unitToSet = "";
        String unitOfJson = "";
        String valueToSet = "";
        Boolean nameFound = false;

        for (int i = 0; i < keys.length(); i++) {

            String key = keys.getString(i);
            String value = jsonObject.getString(key);
            // Synonymliste erstellen
            List<PairSynonym> controllLists = new ArrayList<>();
            PairSynonym pairSynonym = new PairSynonym("Abmessungen", " Abmessungen", "mm");
            controllLists.add(pairSynonym);
            pairSynonym = new PairSynonym("Arbeitsbereich", " Abmessungen", "mm");
            controllLists.add(pairSynonym);
            pairSynonym = new PairSynonym("Zeichnung", " Abmessungen", "mm");
            controllLists.add(pairSynonym);

            // Synonymliste auswerten
            for (PairSynonym pairSynonyms : controllLists) {
                //System.out.println("Synonym =  " + pairSynonyms.getSynonym());
                //System.out.println("Value " + value);
                //System.out.println("Key " + key );
                if (value.equals(pairSynonyms.getSynonym()) && key.equals("Name")) {
                    nameToSet = pairSynonyms.getOriginName();
                    System.out.println("nameToSet set with " + nameToSet);
                    unitToSet = pairSynonyms.getUnit();
                    System.out.println("unitToSet set with " + unitToSet);
                    nameFound = true;
                }
            }
            if (key.equals("Unit")) {
                unitOfJson = value;
                System.out.println("unitOfJson gesetzt " + unitOfJson);
            }
            if (key.equals("Value") && nameFound) {
                // Unit auswerten
                switch (unitOfJson) {
                    case "mm":
                        valueToSet = value;
                        break;
                    case "cm":
                        valueToSet = String.valueOf(Integer.parseInt(value) * 100);
                        break;
                        //code
                    case "m":
                        valueToSet = String.valueOf(Integer.parseInt(value) * 1000);
                        //code
                    default:
                        //code
                }
            }


        }


        String[] fieldNames = packageName.toString().split("\\.");

        JsonNode currentNode = jsonNode;
        for (int i = 0; i < fieldNames.length - 1; i++) {
            currentNode = currentNode.get(fieldNames[i]);
            if (currentNode == null || !currentNode.isObject()) {
                throw new IllegalArgumentException("Invalid field path: " + packageName.toString());
            }else {
                break;
            }
        }

        // Updating the field value
        String fieldName = fieldNames[fieldNames.length - 1];
        ((com.fasterxml.jackson.databind.node.ObjectNode) currentNode).put(fieldName, valueToSet);

        System.out.println("Fertiges Set");
        System.out.println("Name :" + nameToSet);
        System.out.println("Unit :" + unitToSet);
        System.out.println("Value :" + valueToSet);

        return new ResponseEntity<>(newJson, HttpStatus.OK);
    }

}
