package com.mednet.controller;

import com.mednet.entity.Staff;
import com.mednet.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class StaffController {

    @Autowired
    private StaffService staffService;

    @GetMapping("/staff")
    public String getAllStaff(Model model) {

        List<Staff> staffList = staffService.getAllStaff();

        model.addAttribute("staffList", staffList);

        return "home";
    }
}