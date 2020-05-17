package com.example.tripplanner.Presenters.LoginPresenter;

import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.tripplanner.Models.LoginModel.LoginContract;
import com.example.tripplanner.Views.Login.Login;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginPresenter implements LoginContract.IPresenter {
    LoginContract.IView loginCont;
    private FirebaseAuth auth ;

    public LoginPresenter(FirebaseAuth auth,LoginContract.IView view) {
        this.auth = auth;
        this.loginCont=view;
    }
    boolean chk=false;
    @Override
    public boolean performLogin(String email, String password) {

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            loginCont.loginValidations();
        } else {

            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener((Login) loginCont, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
//                                loginCont.loginSuccess();
                                chk=true;
                                loginCont.isLogin(true);
                            } else {
                                loginCont.isLogin(false);
//                                loginCont.loginError();

                            }
                        }
                    });
        }
        return chk;
    }

    @Override
    public void checkLogin() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null)
            loginCont.isLogin(true);
        else
            loginCont.isLogin(false);
    }
    @Override
    public void FirebaseGoogleAuth(GoogleSignInAccount acc) {
        AuthCredential authCredential = GoogleAuthProvider.getCredential(acc.getIdToken(), null);
        auth.signInWithCredential(authCredential).addOnCompleteListener((Login) loginCont , new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    //Toast.makeText((Login)loginCont , "Susseful", Toast.LENGTH_LONG).show();

                    FirebaseUser user = auth.getCurrentUser();
                    loginCont.updateUI(user);
                }
                else {

                    Toast.makeText((Login)loginCont , "Fail", Toast.LENGTH_LONG).show();
                    loginCont.updateUI(null);

                }

            }
        });
    }


}
