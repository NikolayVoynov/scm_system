package com.example.scm_system.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/audits")
public class AuditsController {

    @GetMapping("/add")
    public String getAuditAddPage() {

        return "audit-add";
    }
}
