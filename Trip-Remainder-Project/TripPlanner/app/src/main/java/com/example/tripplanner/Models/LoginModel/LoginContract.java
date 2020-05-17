package com.example.tripplanner.Models.LoginModel;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseUser;

public class LoginContract {

    public  interface IView{
        void loginValidations();
        void loginSuccess();
        void loginError();
        void isLogin(boolean isLogin);
//        void onActivityResult(int requestCode, int resultCode, Intent data);
//        void handleSignInResult(Task<GoogleSignInAccount> task);
        void updateUI(FirebaseUser fUser);
    }

    public interface IPresenter{
        boolean performLogin(String email,String password);
        void checkLogin();
        void FirebaseGoogleAuth(GoogleSignInAccount acc);
    }

    public interface IModel {

    }
}
