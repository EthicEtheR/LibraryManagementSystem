package com.aether.LibraryManagementSystem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstController {
    @GetMapping("/")
    public String show(){

       return "Welcome to LibraryManagementSystem by Aether. ";
    }
}
