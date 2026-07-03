package com.mednet.controller;

import com.mednet.entity.Prefix;
import com.mednet.service.PrefixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/prefix")
public class PrefixController {

    @Autowired
    private PrefixService prefixService;

    // Display Prefix List
    @GetMapping
    public String showPrefixPage(Model model) {

        model.addAttribute("prefixList", prefixService.getAllPrefixes());

        model.addAttribute("prefix", new Prefix());

        return "prefix";
    }

    // Save Prefix
    @PostMapping("/save")
    public String savePrefix(@ModelAttribute Prefix prefix) {

        prefixService.savePrefix(prefix);

        return "redirect:/";
    }

    // Delete Prefix
    @GetMapping("/delete/{id}")
    public String deletePrefix(@PathVariable int id) {

        prefixService.deletePrefix(id);

        return "redirect:/";
    }

}