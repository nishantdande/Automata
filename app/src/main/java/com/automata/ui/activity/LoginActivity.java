package com.automata.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.automata.R;
import com.automata.account.AccountManager;
import com.automata.preferences.Profile;
import com.automata.ui.view.ProgressView;
import com.automata.util.ConnectivityUtil;
import com.automata.util.DialogUtils;
import com.automata.util.Router;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by nishant on 15/03/16.
 */
public class LoginActivity extends AutomataActivity {

    @BindView(R.id.editTextUsername)
    EditText username;
    @BindView(R.id.editTextPassword)
    EditText password;

    @BindView(R.id.loginButton)
    Button mLogin;

    ProgressView mProgressView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProgressView = new ProgressView(this,getString(R.string.loading_text));
        if (!Profile.getInstance().isLoggedIn()){
            setContentView(R.layout.activity_login);
            ButterKnife.bind(this);
        }else{
            Router.startHomeScreenActivity(LoginActivity.this);
        }
    }

    @OnClick(R.id.loginButton)
    public void onLoginClick(){
        if (TextUtils.isEmpty(getUsername())){
            return;
        }

        if (TextUtils.isEmpty(getPassword())){
            return;
        }

        if (!ConnectivityUtil.isOnline()) {
            DialogUtils.showMessage(getString(R.string.no_internet_connection));
            return;
        }

        mProgressView.showDialog();
        AccountManager.getInstance().login(getUsername(), getPassword()).subscribe(login ->{
            mProgressView.closeDialog();
            if (login.getType().equalsIgnoreCase("success")) {
                Profile.getInstance().setUsername(getUsername());
                Profile.getInstance().setPassword(getPassword());
                Profile.getInstance().setIsLoggedIn();

                Router.startHomeScreenActivity(LoginActivity.this);
            }else{
                Exception throwable = new Exception(login.getMessage());
                apiErrorHandler.handleError(throwable);
            }
        }, throwable -> {
            mProgressView.closeDialog();
            apiErrorHandler.handleError(throwable);
        });
    }

    public String getUsername() {
        return username.getText().toString();
    }

    public String getPassword() {
        return password.getText().toString();
    }
}
