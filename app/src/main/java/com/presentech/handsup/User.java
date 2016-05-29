package com.presentech.handsup;


/**
 * Created by Lewis on 17/04/2016.
 */

//This class is used to create Users to add to userDB

public class User {
    private int _id;
    private String _email;
    private String _password;
    private String _name;
    private String _lockout;

    public User() {
    }

    public User(int id, String email, String password, String name, String lockout) {
        this._id = id;
        this._email = email;
        this._password = password;
        this._name = name;
        this._lockout = lockout;
    }

    public User(String email, String password, String name, String lockout) {
        this._email = email;
        this._password = password;
        this._name = name;
        this._lockout = lockout;
    }

    //Method to set User ID field value in UserDB
    public void setID(int id) {
        this._id = id;
    }

    //Method to get User ID field value from UserDB
    public int getID() {
        return this._id;
    }

    //Method to set User Email field value in UserDB
    public void setEmail(String email) {
        this._email = email;
    }

    //Method to get User Email field value from UserDB
    public String getEmail() {
        return this._email;
    }

    //Method to set User Password field value in UserDB
    public void setPassword(String password) {
        this._password = password;
    }

    //Method to get User Password field value from UserDB
    public String getPassword() {
        return this._password;
    }

    //Method to set User Name field value in UserDB
    public void setName(String name) {
        this._name = name;
    }

    //Method to get User Name field value from UserDB
    public String getName() {
        return this._name;
    }

    //Method to set User Lockout state field value in UserDB
    public void setLockout(String lockout) {
        this._lockout = lockout;
    }

    //Method to get User Lockout state field value from UserDB
    public String getLockout() {
        return this._lockout;
    }
}
