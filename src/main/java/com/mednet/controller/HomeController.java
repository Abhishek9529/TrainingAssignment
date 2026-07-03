package com.mednet.controller;

import com.mednet.entity.Staff;
import com.mednet.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import com.mednet.entity.Prefix;
import com.mednet.service.PrefixService;

@Controller
public class HomeController {

    @Autowired
    private StaffService staffService;

    @Autowired
    private PrefixService prefixService;

    @GetMapping("/")
    public String home(Model model) {

        List<Staff> staffList = staffService.getAllStaff();
//        System.out.println("Total Staff = " + staffList.size());
        model.addAttribute("staffList", staffList);
        model.addAttribute("prefixList", prefixService.getAllPrefixes());

        model.addAttribute("prefix", new Prefix());
        return "home";
    }


}