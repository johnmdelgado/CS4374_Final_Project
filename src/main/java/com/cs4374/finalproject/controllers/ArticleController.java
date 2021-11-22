package com.cs4374.finalproject.controllers;

import com.cs4374.finalproject.dataTransferObject.ArticleDataTransferObject;
import com.cs4374.finalproject.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @PostMapping
    public ResponseEntity createArticle(@RequestBody ArticleDataTransferObject articleDataTransferObject){
        articleService.createArticle(articleDataTransferObject);

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ArticleDataTransferObject>> getAllArticles(){
        return new ResponseEntity(articleService.getAllArticles(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ArticleDataTransferObject> getSingleArticle(@PathVariable @RequestBody Long id){
        return new ResponseEntity(articleService.getSingleArticle(id), HttpStatus.OK);
    }
}
