
package com.musicplatform.comment.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

@GetMapping
public String test(){
return "comment endpoint";
}

}
