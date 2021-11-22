package com.cs4374.finalproject.repositories;

import com.cs4374.finalproject.siteElements.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article,Long> {
}
