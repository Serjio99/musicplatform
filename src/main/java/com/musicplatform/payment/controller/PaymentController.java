
package com.musicplatform.payment.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

@GetMapping
public String test(){
return "payment endpoint";
}

}
