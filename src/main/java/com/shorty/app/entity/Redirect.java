package com.shorty.app.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Document(collection = "redirect")
public class Redirect {

    @Id
    @GeneratedValue
    private String id;

    @Column(unique = true, nullable = false)
    private String alias;

    @Column(nullable = false)
    private String url;

    @Column
    private int numberOfClicks;

    @Column
    private String userID;

    public Redirect(final String alias, final String url, final int numberOfClicks, final String userID) {
        this.alias = alias;
        this.url = url;
        this.numberOfClicks = numberOfClicks;
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "Redirect{" + "id=" + id + ", alias='" + alias + '\'' + ", url='" + url + '\'' + '}';
    }

    public String getId() {
        return id;
    }

    public String getAlias() {
        return alias;
    }

    public String getUrl() {
        return url;
    }

    public int getNumberOfClicks() {
        return numberOfClicks;
    }

    public String getUserID() {
        return userID;
    }

    public void setNumberOfClicks(int numberOfClicks) {
        this.numberOfClicks = numberOfClicks;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public static String generateRandomAlias() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 6) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

}
