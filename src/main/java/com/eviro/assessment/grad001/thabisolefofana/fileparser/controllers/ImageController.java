package com.eviro.assessment.grad001.thabisolefofana.fileparser.controllers;

import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("v1/api/image")
public class ImageController {

    @GetMapping(value = "/{name}/{surname}/{fileName}")
    public URI gethttpImageLink(@PathVariable String name, @PathVariable String surname, @PathVariable String fileName) throws IOException {

        return new FileSystemResource("src/main/resources/static/"+fileName).getURI();
    }
}
