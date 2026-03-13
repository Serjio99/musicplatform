
package com.musicplatform.song.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/song")
public class SongController {

@GetMapping
public String test(){
return "song endpoint";
}

}
