package com.example.aasxConv;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface aasxJsonObjectsRepository extends CrudRepository<aasxJsonObjects, Integer> {

    @Query(value = "SELECT t FROM aasxjsonobjects t where t.name=?1")
    aasxJsonObjects findJsonByName(String name);
}
