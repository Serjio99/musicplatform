
package com.musicplatform.user.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

@GetMapping
public String test(){
return "user endpoint";
}

}
