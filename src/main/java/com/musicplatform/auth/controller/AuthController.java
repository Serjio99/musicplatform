
package com.musicplatform.auth.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

@GetMapping
public String test(){
return "auth endpoint";
}

}
