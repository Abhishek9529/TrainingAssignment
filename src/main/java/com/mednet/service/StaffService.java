package com.mednet.service;

import com.mednet.dao.StaffDao;
import com.mednet.entity.Staff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffService {

    @Autowired
    private StaffDao staffDao;

    public List<Staff> getAllStaff() {
        return staffDao.getAllStaff();
    }
}