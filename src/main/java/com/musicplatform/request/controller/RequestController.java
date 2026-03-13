
package com.musicplatform.request.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/request")
public class RequestController {

@GetMapping
public String test(){
return "request endpoint";
}

}
