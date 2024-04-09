package edu.tcu.cs.peerevaluation.admin;

import edu.tcu.cs.peerevaluation.system.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    private final AdminService adminService ;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }
}