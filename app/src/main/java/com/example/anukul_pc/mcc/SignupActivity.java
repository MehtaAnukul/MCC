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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yarolegovich.lovelydialog.LovelyProgressDialog;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private Button regBtn;
    private EditText emailEd;
    private EditText passwordEd;
    private EditText rePasswordEd;
    private TextView gotoLoginTv;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private LovelyProgressDialog waitingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        waitingDialog = new LovelyProgressDialog(SignupActivity.this);

        regBtn = findViewById(R.id.reg_btn);
        emailEd = findViewById(R.id.reg_email);
        passwordEd = findViewById(R.id.reg_password);
        rePasswordEd = findViewById(R.id.reg_repassword);
        gotoLoginTv = findViewById(R.id.reg_gotologin);


        gotoLoginTv.setOnClickListener(this);
        regBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.reg_btn:
                userRegistration();
                break;
            case R.id.reg_gotologin:
                final Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
                break;

        }

    }

    private void userRegistration() {


        final String email = emailEd.getText().toString().trim();
        final String password = passwordEd.getText().toString().trim();
        final String repassword = rePasswordEd.getText().toString().trim();


        if (email.isEmpty() || password.isEmpty() || repassword.isEmpty()) {
            Toast.makeText(this, "Please fill all details ", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(repassword)) {
            Toast.makeText(this, "Both password Not match ! try again", Toast.LENGTH_SHORT).show();
            return;
        }

        waitingDialog.setTitle("Signup account");
        waitingDialog.setMessage("Creating your accout...");
        waitingDialog.setTopColorRes(R.color.colorPrimary);
        waitingDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()) {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                waitingDialog.dismiss();
                                Toast.makeText(SignupActivity.this, "User with this email already exist.", Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(SignupActivity.this, task.getException() + "", Toast.LENGTH_SHORT).show();
                            Log.e("TAG", task.getException() + "");

                        } else {
                            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            String userId = user.getUid();

                            databaseReference.child(AppConstance.FIREBASE_TABLE_USER).child(userId)
                                    .setValue(new UserModel(email, password),
                                            new DatabaseReference.CompletionListener() {
                                                @Override
                                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                                                    if (databaseError != null) {
                                                        waitingDialog.dismiss();
                                                        Toast.makeText(SignupActivity.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        waitingDialog.dismiss();
                                                        Toast.makeText(SignupActivity.this, "Success ! Faculty will Verify your account !", Toast.LENGTH_SHORT).show();
                                                        finish();
                                                    }
                                                }
                                            });
                        }
                    }
                });


    }
}
