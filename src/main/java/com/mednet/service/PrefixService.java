package com.mednet.service;

import com.mednet.dao.PrefixDao;
import com.mednet.entity.Prefix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrefixService {

    @Autowired
    private PrefixDao prefixDao;

    // Save Prefix
    public void savePrefix(Prefix prefix) {
        prefixDao.savePrefix(prefix);
    }

    // Get All Prefixes
    public List<Prefix> getAllPrefixes() {
        return prefixDao.getAllPrefixes();
    }

    // Delete Prefix
    public void deletePrefix(int id) {
        prefixDao.deletePrefix(id);
    }

}