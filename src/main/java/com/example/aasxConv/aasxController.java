package com.example.aasxConv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/* Nun kann man dann ein ObjectModell zurückgeben */

/* Modell erstellen -> Repository erstellen -> Controller erstellen */
@RestController
public class aasxController {

    @Autowired
    private PackageRepository packageRepository;

    /* Neuer Endpunkt wird mit Methode verbunden */
    @GetMapping("/package")
    public ResponseEntity getPackage (@RequestParam(value="id") int id){
        // get package from db by id
        Optional<Package> newPackage = packageRepository.findById(id);
        if(newPackage.isPresent()) {
            return new ResponseEntity<Package>(newPackage.get(), HttpStatus.OK);
        }

        return new ResponseEntity("No package found by id + " + id,HttpStatus.NOT_FOUND);
    }

    @GetMapping("/package/all")
    public ResponseEntity<Iterable<Package>> getAllPackages(){
        Iterable<Package> allPackagesInDB = packageRepository.findAll();
        return new ResponseEntity<Iterable<Package>>(allPackagesInDB,HttpStatus.FOUND);
    }

    /* RequestBody für BodyValue z.B. JSON. Param direkt über den Aufruf */
    @PostMapping("/package")
    public ResponseEntity<Package> createPackage(@RequestBody Package newPackage) {
        // save Package in DB
        // Repo stellt Verbindung zur Datenbank her
        packageRepository.save(newPackage);
        // Klasse wird entgegengenommen und direkt in die Klasse geparst
        return new ResponseEntity<Package>(newPackage, HttpStatus.OK);
    }

    @DeleteMapping("/package")
    public ResponseEntity deletePackage(@RequestParam(value = "id") int id) {

        Optional<Package> newPackage = packageRepository.findById(id);
        if(newPackage.isPresent()) {
            packageRepository.deleteById(id);
            return new ResponseEntity<>("Todo deleted",HttpStatus.OK);

        }
        return new ResponseEntity<>("Package not found",HttpStatus.NOT_FOUND);

    }

    //Patch : nur ein Bestandteil wird ausgetauscht
    //PUT   : alles wird ausgetauscht

    @PutMapping("/package")
    public ResponseEntity<Package> updatePackage(@RequestBody Package editedPackage) {

        Optional<Package> newPackage = packageRepository.findById(editedPackage.getId());
        if(newPackage.isPresent()) {
            Package updatedTodo = packageRepository.save(editedPackage);
            return new ResponseEntity<Package>(updatedTodo,HttpStatus.OK);

        }
        return new ResponseEntity("Package not found",HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/package")
    public ResponseEntity<Package> updatePackageDone(@RequestParam(value = "id") int id) {
        Optional<Package> newPackage = packageRepository.findById(id);
        if(newPackage.isPresent()) {
            newPackage.get().setDone(true);
            Package updatedTodo = packageRepository.save(newPackage.get());
            return new ResponseEntity<Package>(updatedTodo,HttpStatus.OK);

        }
        return new ResponseEntity("Package not found",HttpStatus.NOT_FOUND);
    }



}
