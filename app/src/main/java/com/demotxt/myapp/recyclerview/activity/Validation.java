
package com.demotxt.myapp.recyclerview.activity;

public class Validation {
    public boolean fullname(String name) {

        String namepattern = "^[\\p{L} .'-]+$";
        if (name.matches(namepattern)) {
            return true;
        } else {
            return false;
        }

    }

    public boolean email(String email) {

        // String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";
        String emailpattern = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        if (email.matches(emailpattern)) {
            return true;
        } else {
            return false;
        }
    }
}