package com.example.anukul_pc.mcc;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yarolegovich.lovelydialog.LovelyProgressDialog;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button logbtn;
    private EditText loginEmailEd;
    private EditText loginPasswordEd;
    private TextView gotoRegistrationTv;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private LovelyProgressDialog waitingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        waitingDialog = new LovelyProgressDialog(LoginActivity.this);

        logbtn = findViewById(R.id.login_btn);
        loginEmailEd = findViewById(R.id.login_email);
        loginPasswordEd = findViewById(R.id.login_password);
        gotoRegistrationTv = findViewById(R.id.login_gotoreg);


        logbtn.setOnClickListener(this);
        gotoRegistrationTv.setOnClickListener(this);

        if (firebaseAuth.getCurrentUser() != null) {
            final Intent gotoDashBoard = new Intent(LoginActivity.this, Main4Activity.class);
            startActivity(gotoDashBoard);
            finish();
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                loginUser();
                break;
            case R.id.login_gotoreg:
                final Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);

                break;
        }
    }

    private void loginUser() {

        final String email = loginEmailEd.getText().toString();
        final String password = loginPasswordEd.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill details", Toast.LENGTH_SHORT).show();
            return;
        }
        waitingDialog.setTitle("Sign in account");
        waitingDialog.setMessage("checking your accout...");
        waitingDialog.setTopColorRes(R.color.colorPrimary);
        waitingDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.e("TAG", "signInWithEmail:success");
                            final Intent gotoDashBoard = new Intent(LoginActivity.this, Main4Activity.class);
                            startActivity(gotoDashBoard);
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.e("TAG", "signInWithEmail:failure", task.getException());
                            waitingDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Email and password Dosent match",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }
}
