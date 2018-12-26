package com.bloodgift.bloodgift.Vue;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bloodgift.bloodgift.Controller.SignUpController;
import com.bloodgift.bloodgift.R;

public class SignUpActivity extends AppCompatActivity {

    EditText userName;

    EditText password;

    EditText confirmPassword;

    SignUpController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        userName = findViewById(R.id.usrusr);
        password = findViewById(R.id.passwrd);
        confirmPassword = findViewById(R.id.confirmPasswrd);

        controller = new SignUpController(this);

        final TextView signUpButton = findViewById(R.id.sup);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userName.getText().toString().length() < 3 || password.getText().toString().length() <3){
                    displaySignUpError("Password ou userName trop court");
                }else{
//                    if (!controller.signUp(userName.getText().toString(), password.getText().toString(), confirmPassword.getText().toString())){
//                        displaySignUpError("Erreur au niveau du mot de passe");
//                    }else{
//                        controller.login("user created");
//                    }
                    controller.signUp(userName.getText().toString(), password.getText().toString(), confirmPassword.getText().toString());
                }
            }
        });

        TextView backLoginButton = findViewById(R.id.login);
        backLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.login("Login");
            }
        });
    }

    public void displaySignUpError(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void createValidationFragment(){
        ValidationFragment validationFragment = new ValidationFragment();
        validationFragment.show(getFragmentManager(), "dialog");
    }

    @SuppressLint("ValidFragment")
    public class ValidationFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.erase_sign_up)
                    .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            controller.createUser();
                            dismiss();
                            controller.login("Compte créé");
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                            dismiss();
                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }

    // Mettre une méthode pour la création de Toast pour affichage de message de retour de login
}
