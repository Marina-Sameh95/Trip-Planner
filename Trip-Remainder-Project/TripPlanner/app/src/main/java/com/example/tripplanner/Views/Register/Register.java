package com.example.tripplanner.Views.Register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tripplanner.Models.RegisterModel.RegisterContract;
import com.example.tripplanner.Presenters.RegisterPresenter.RegisterPresenter;
import com.example.tripplanner.R;
import com.example.tripplanner.Views.Login.Login;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity implements RegisterContract.IView {

    EditText txtEmail;
    EditText txtPass;
    EditText txtPassw;
    Button btnReges;
    private FirebaseAuth firebaseAuth ;
    private RegisterPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtEmail = findViewById(R.id.txtEmail);
        txtPass = findViewById(R.id.txtPass);
        txtPassw = findViewById(R.id.txtPassw);
        firebaseAuth = FirebaseAuth.getInstance();
        presenter = new RegisterPresenter(firebaseAuth,this);
        btnReges = findViewById(R.id.btnReges);

        btnReges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtEmail.getText().toString().trim();
                String password = txtPass.getText().toString().trim();
                String confrimPassword = txtPassw.getText().toString().trim();
                presenter.performReg(email,password,confrimPassword);
            }
        });

    }

    @Override
    public void regValidations() {
        Toast.makeText(Register.this,"Please Fill ALL Field", Toast.LENGTH_LONG).show();
    }

    @Override
    public void regSuccess() {
        Intent i = new Intent(Register.this, Login.class);

        startActivity(i);
    }

    @Override
    public void regError() {
        Toast.makeText(Register.this , "Registeration Faild" , Toast.LENGTH_LONG).show();
    }


}
