package com.example.ammarhasan.budgeter;

import java.time.ZonedDateTime;

/**
 * @author Ammar Hasan 150454388 May 2018
 * Class Purpose: This class represents articles posted in the
 * app
 */
public class Article {

    private String title;
    private String author;
    private int articleId;
    private String post;

    /**
     * Article default constructor
     */
    public Article(){

    }

    /**
     * Article constructor
     * @param title Title of the article
     * @param author The Author of the article
     * @param date Date at which the article was posted
     * @param post The post, containing the article text
     * @param articleId unique article id
     */
    public Article(int articleId, String title, String author, ZonedDateTime date, String post) {
        this.title = title;
        this.author = author;
     //   this.date = date;
        this.post = post;
        this.articleId = articleId;
    }


    // getters and setters


    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }
}
