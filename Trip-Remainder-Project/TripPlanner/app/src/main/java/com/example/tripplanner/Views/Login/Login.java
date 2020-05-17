package com.example.tripplanner.Views.Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tripplanner.Models.LoginModel.LoginContract;
import com.example.tripplanner.Presenters.LoginPresenter.LoginPresenter;
import com.example.tripplanner.R;
import com.example.tripplanner.Views.HomeView.MainActivity;
import com.example.tripplanner.Views.Register.Register;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity implements LoginContract.IView {
    EditText emailTxt;
    EditText passTxt;
    Button btnLogin;
    Button btnReg;
//    SignInButton signInButton ;
    FirebaseAuth firebaseAuth;
    FirebaseUser currentUser;
//    GoogleSignInClient mGoogleSignInClient;
    private LoginPresenter presenter;
    GoogleSignInAccount acc;

    GoogleSignInOptions gso;
    private GoogleSignInClient mGoogleSignInClient;
    SignInButton signInButton;

    boolean loginChk=false;

    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

         sharedPreferences = getSharedPreferences("MyLogin.txt", Context.MODE_PRIVATE);
         editor = sharedPreferences.edit();


        emailTxt = findViewById(R.id.emailTxt);
        passTxt = findViewById(R.id.passTxt);
        btnLogin = findViewById(R.id.btnLogin);
        btnReg = findViewById(R.id.btnReg);
        firebaseAuth = FirebaseAuth.getInstance();
        presenter = new LoginPresenter(firebaseAuth,this);

        signInButton=findViewById(R.id.btnGoogle);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, 1);
            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
                Login.this.finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailTxt.getText().toString().trim();
                String password = passTxt.getText().toString().trim();
                if(email.length()>0&&password.length()>0){
                    loginChk=presenter.performLogin(email, password);
//                    isLogin(loginChk);
//                presenter.checkLogin();
                }else{
                    Toast.makeText(Login.this,"Fill All Fields",Toast.LENGTH_SHORT).show();
                }

                //        presenter.FirebaseGoogleAuth(acc);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = firebaseAuth.getCurrentUser();
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }
    public void loginWithGoogle(View v){

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 1);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 1) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        Log.i("nasor","handle");
        try {
            Intent intent=new Intent(Login.this,MainActivity.class);
            intent.putExtra("user",currentUser.getEmail());
            intent.putExtra("firstLogin",true);          //newwwwwwwwwwww remeber to do this once [logout]
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(Login.this, "Authentication failed.",
                    Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void loginValidations() {
        Toast.makeText(this, "Please Fill ALL Field", Toast.LENGTH_LONG).show();

    }

    @Override
    public void loginSuccess() {
        Toast.makeText(this, "Login success", Toast.LENGTH_SHORT);
    }

    @Override
    public void loginError() {
        Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT);
    }


    @Override
    public void isLogin(boolean isLogin) {
        if (isLogin == true) {
            Intent i = new Intent(this,MainActivity.class);
            i.putExtra("user",emailTxt.getText().toString());
            i.putExtra("firstLogin",true);                  //newwwwwwwwwwww remeber to do this once [logout]
            startActivity(i);
            Login.this.finish();

            editor.putBoolean("FirstLogin", true);
            editor.putString("user",emailTxt.getText().toString());
            editor.commit();
        } else {
            Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();
            Log.d("sign","onAuthStateChanged:signed_out");
        }
    }

    @Override
    public void updateUI(FirebaseUser fUser) {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        String name = account.getDisplayName();
        String email = account.getEmail();
        emailTxt.setText(email);

    }


}
