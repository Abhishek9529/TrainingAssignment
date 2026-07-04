package com.mednet.controller;

import com.mednet.entity.Prefix;
import com.mednet.service.PrefixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prefix")
public class PrefixRestController {

    @Autowired
    private PrefixService prefixService;

    // Get All Prefix Records
    @GetMapping
    public List<Prefix> getAllPrefixes() {

        return prefixService.getAllPrefixes();

    }

    @PostMapping
    public ResponseEntity<?> savePrefix(@RequestBody Prefix prefix) {

        try {
            prefixService.savePrefix(prefix);
            return new ResponseEntity<>(prefix, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/{id}")
    public String deletePrefix(@PathVariable int id) {

        prefixService.deletePrefix(id);

        return "Record Deleted Successfully";

    }

}