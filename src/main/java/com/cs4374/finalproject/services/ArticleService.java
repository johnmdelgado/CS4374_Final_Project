package com.cs4374.finalproject.services;

import com.cs4374.finalproject.dataTransferObject.ArticleDataTransferObject;
import com.cs4374.finalproject.repositories.ArticleRepository;
import com.cs4374.finalproject.siteElements.Article;
import com.cs4374.finalproject.exception.ArticleNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    @Autowired
    private AuthService authService;
    @Autowired
    private ArticleRepository articleRepository;

    public void createArticle(ArticleDataTransferObject articleDataTransferObject){
        Article article = mapFromDataTransferObjectToArticle(articleDataTransferObject);
        articleRepository.save(article);

    }

    public List<ArticleDataTransferObject> getAllArticles() {
        List<Article> articles = articleRepository.findAll();
        return articles.stream().map(this::mapFromArticleDataTransferObject).collect(Collectors.toList());
    }

    private ArticleDataTransferObject mapFromArticleDataTransferObject(Article article) {
        ArticleDataTransferObject articleDataTransferObject = new ArticleDataTransferObject();
        articleDataTransferObject.setId(article.getId());
        articleDataTransferObject.setTitle(article.getTitle());
        articleDataTransferObject.setContent(article.getContent());
        articleDataTransferObject.setUsername(article.getUsername());

        return articleDataTransferObject;
    }

    private Article mapFromDataTransferObjectToArticle(ArticleDataTransferObject articleDataTransferObject){
        System.out.println("Here is our Username:" + authService.getCurrentUser());
        Article article = new Article();
        article.setTitle(articleDataTransferObject.getTitle());
        article.setContent(articleDataTransferObject.getContent());
        User loggedInUser = (authService.getCurrentUser()).orElseThrow(() ->
                new IllegalArgumentException("No User logged in"));
        article.setUsername(loggedInUser.getUsername());
        article.setCreatedOn(Instant.now());

        return article;
    }

    public ArticleDataTransferObject  getSingleArticle(Long id){
        Article article = articleRepository.findById(id).orElseThrow(() -> new ArticleNotFoundException("For id "+ id));

        return mapFromArticleDataTransferObject(article);
    }
}
