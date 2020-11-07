package com.example.newshub;

public class News {

    String title;
    String content;
    String description;
    String date;
    String url;
    String web;
    int totalResults;

    public News(String title, String content, String description, String date, String  url, String web,int totalResults) {
        this.title = title;
        this.url=url;
        this.web=web;
        this.totalResults=totalResults;
        this.content = content;

        this.description = description;
        this.date = date;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public String getTitle() {
        return title;
    }
    public String getUrl() {
        return url;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
