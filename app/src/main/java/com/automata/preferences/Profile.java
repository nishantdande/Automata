package com.automata.preferences;

import com.pixplicity.easyprefs.library.Prefs;

/**
 * Created by nishant on 15/03/16.
 */
public class Profile {

    private final  static Profile profile  = new Profile();

    private Profile(){}

    public static Profile getInstance() {
        return profile;
    }

    public String getUsername() {
        return Prefs.getString("username",null);
    }

    public void setUsername(String username) {
        Prefs.putString("username", username);
    }

    public String getPassword() {
        return Prefs.getString("password",null);
    }

    public void setPassword(String password) {
        Prefs.putString("password",password);
    }

    public boolean isLoggedIn() {
        return Prefs.getBoolean("isLoggedIn", false);
    }

    public void setIsLoggedIn() {
        Prefs.putBoolean("isLoggedIn", true);
    }

    public void dologout() {
        Prefs.putBoolean("isLoggedIn",false);
    }
}
