package com.multipz.clock24.Model;

/**
 * Created by Admin on 30-06-2017.
 */

public class NewsItem {

    String id, name, description, url, category, language, country, urlsToLogos, sortBysAvailable;
    boolean isshown;


    /*
    public NewsItem(String id, String name, String description, String url, String category, String language, String country, String urlsToLogos, String sortBysAvailable) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.url = url;
        this.category = category;
        this.language = language;
        this.country = country;
        this.urlsToLogos = urlsToLogos;
        this.sortBysAvailable = sortBysAvailable;
    }*/
    public boolean isshown() {
        return isshown;
    }

    public void setIsshown(boolean isshown) {
        this.isshown = isshown;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getUrlsToLogos() {
        return urlsToLogos;
    }

    public void setUrlsToLogos(String urlsToLogos) {
        this.urlsToLogos = urlsToLogos;
    }

    public String getSortBysAvailable() {
        return sortBysAvailable;
    }

    public void setSortBysAvailable(String sortBysAvailable) {
        this.sortBysAvailable = sortBysAvailable;
    }
}
