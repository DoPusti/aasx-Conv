package com.example.aasxConv;

import org.springframework.data.repository.CrudRepository;

public interface PackageRepository extends CrudRepository<Package, Integer> {
    // Ist für Verbindung mit der Datenbank für CRUD-Operations
    // Wird Vorgeschlagen von SPRING
}
