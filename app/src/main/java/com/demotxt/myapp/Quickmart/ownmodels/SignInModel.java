package com.demotxt.myapp.Quickmart.ownmodels;

import android.widget.EditText;

public class SignInModel {

    public SignInModel(String phonenumber, String password) {
        this.phone = phonenumber;
        this.password = password;
    }

    String phone , password;
    String contact;
    EditText pass;
    EditText pass1;
    EditText pass2;

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) { this.password = password; }


    public SignInModel(String contactnumber, EditText oldpassword, EditText newpassword, EditText confpassword) {
        this.contact = contactnumber;
        this.pass = oldpassword;
        this.pass1 = newpassword;
        this.pass2 = confpassword;
    }
    public String getContact() { return contact; }

    public void setContact(String cn) { this.contact = cn; }

    public EditText getPass() { return pass; }

    public void setPass(EditText pas) { this.pass = pas; }

    public EditText getPass1() { return pass1; }

    public void setPass1(EditText pas) { this.pass1 = pas; }

    public EditText getPass2() { return pass2; }

    public void setPass2(EditText pas) { this.pass2 = pas; }


}
