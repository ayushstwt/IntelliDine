package com.hunger.saviour.portal.apis;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestAPI {

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String admin(){
        return "Admin";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String user(){
        return "User";
    }

    @GetMapping("/public")
    public String globalApiAccess(){
        return "Public API";
    }
}
