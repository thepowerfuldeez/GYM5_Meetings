package cf.thepowerfuldeez.gym5meetings;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    SharedPreferences sPref;
    Firebase ref;
    String SAVED_EMAIL = "saved_email";
    String SAVED_PASSWORD = "saved_password";

    @Bind(R.id.input_email) EditText _emailText;
    @Bind(R.id.input_password) EditText _passwordText;
    @Bind(R.id.btn_login) Button _loginButton;
    @Bind(R.id.link_signup) TextView _signupLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        Firebase.setAndroidContext(this);
        ref = new Firebase("https://gym5meetings.firebaseio.com");

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });

        load_email_password();
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Авторизация...");
        progressDialog.show();

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        final Firebase ref = new Firebase("https://gym5meetings.firebaseio.com");
        ref.authWithPassword(email, password, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                Intent intent2 = new Intent(getApplicationContext(), SelectSubjectsActivity.class);

                SharedPreferences sPref = getSharedPreferences("Credentials", MODE_PRIVATE);
                String uchplan = sPref.getString("uch_plan", "");
                save_email_password();

                if (uchplan.length() > 0)
                    startActivity(intent1);
                else
                    startActivity(intent2);

            }
            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                Toast.makeText(getBaseContext(), "Авторизация не удалась", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });


        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);


                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the SelectSubjectsActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Авторизация не удалась", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("введите правильный e-mail");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 12) {
            _passwordText.setError("от 4 до 12 символов");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    public void save_email_password() {
        sPref = getSharedPreferences("Credentials", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(SAVED_EMAIL, _emailText.getText().toString());
        ed.putString(SAVED_PASSWORD, _passwordText.getText().toString());
        ed.commit();
    }

    public void load_email_password() {
        sPref = getSharedPreferences("Credentials", MODE_PRIVATE);
        String loadedEmail = sPref.getString(SAVED_EMAIL, "");
        String loadedPassword = sPref.getString(SAVED_PASSWORD, "");
        _emailText.setText(loadedEmail);
        _passwordText.setText(loadedPassword);
    }
}