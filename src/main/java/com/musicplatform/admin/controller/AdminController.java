
package com.musicplatform.admin.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

@GetMapping
public String test(){
return "admin endpoint";
}

}
