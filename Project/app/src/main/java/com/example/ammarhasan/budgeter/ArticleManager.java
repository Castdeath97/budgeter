package com.example.ammarhasan.budgeter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ammar Hasan 150454388 May 2018
 * Class Purpose: This class maintains a list of articles
 */
public class ArticleManager {
    private List<Article> articles;

    public ArticleManager(){
        List<Article> articles = new ArrayList<>();
    }

    // getters and setters

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    /**
     * Finds a given article by id
     * @param articleId id of article
     * @return Article article, null if not found
     */
    public Article findArticle(int articleId){
        for (Article a: articles) {
            if(a.getArticleId() == articleId){
                return a;
            }
        }
        return null;
    }
}
