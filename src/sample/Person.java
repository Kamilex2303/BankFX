package sample;

public class Person {

    String name;
    String surename;
    String login;
    String password;
    int balance;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurename() {
        return surename;
    }

    public void setSurename(String surename) {
        this.surename = surename;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public Person(String name, String surename, String login, String password, int balance) {

        this.name = name;
        this.surename = surename;
        this.login = login;
        this.password = password;
        this.balance = balance;
    }
}
