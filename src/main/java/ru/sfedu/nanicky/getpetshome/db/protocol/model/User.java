package ru.sfedu.nanicky.getpetshome.db.protocol.model;

import java.util.List;

public class User extends IdEntity {
    private String name;
    private List<String> numbers;
    private List<String> emails;
    private List<String> networksUrls;

    public User(long id, String name, List<String> numbers, List<String> emails, List<String> networksUrls) {
        this.id = id;
        this.name = name;
        this.numbers = numbers;
        this.emails = emails;
        this.networksUrls = networksUrls;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<String> numbers) {
        this.numbers = numbers;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public List<String> getNetworksUrls() {
        return networksUrls;
    }

    public void setNetworksUrls(List<String> networksUrls) {
        this.networksUrls = networksUrls;
    }
}
