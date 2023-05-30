package com.example.aasxConv;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


import java.util.List;

public interface aasxJsonUserRepository extends CrudRepository<aasxJsonUser,Long> {

    @Query(value = "SELECT t FROM aasxjsonuser t WHERE t.userID=?1 and t.name=?2")
    List<aasxJsonUser> findbyUseriD(String userid,String username);



}
