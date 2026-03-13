
package com.musicplatform.file.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/file")
public class FileController {

@GetMapping
public String test(){
return "file endpoint";
}

}
