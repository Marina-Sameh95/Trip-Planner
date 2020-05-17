package com.example.tripplanner.Presenters.RegisterPresenter;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.example.tripplanner.Models.RegisterModel.RegisterContract;
import com.example.tripplanner.Views.Register.Register;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterPresenter implements RegisterContract.IPresenter{
    RegisterContract.IView regCont;
    private FirebaseAuth auth ;

    public RegisterPresenter(FirebaseAuth auth,RegisterContract.IView  view) {
        this.auth = auth;
        this.regCont=view;
    }

    @Override
    public void performReg(String email, String password, String confirmPassword) {
        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password) ||TextUtils.isEmpty(confirmPassword) ){
            regCont.regValidations();
        } else if(password.equals(confirmPassword)){

           auth.createUserWithEmailAndPassword(email, password)
                   .addOnCompleteListener((Register)regCont, new OnCompleteListener<AuthResult>() {


                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           if (task.isSuccessful()) {
                               regCont.regSuccess();
                           } else {
                                 regCont.regError();
                           }
                       }
                   });
    }
    }


}
