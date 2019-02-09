package br.ufg.inf.pos.fund_android;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.NoSubscriberEvent;
import org.greenrobot.eventbus.Subscribe;

import br.ufg.inf.pos.fund_android.services.WebTask;

public class LoginActivity extends AppCompatActivity {

    private WebTask webTask = null;

    private EditText usernameInput;
    private EditText passwordInput;
    private View progressView;
    private View loginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameInput = (EditText) findViewById(R.id.loginText);
        passwordInput = (EditText) findViewById(R.id.passwordText);

        passwordInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin(null);
                    return true;
                }
                return false;
            }
        });

    }

    public void login(View view){

        String user = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();

        if(user.equals("admin") && password.equals("12345")){
            Toast.makeText(getApplicationContext(), "Acesso permitido", Toast.LENGTH_SHORT);
            Intent intent = new Intent(this, TelaPrincipalActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Acesso negado", Toast.LENGTH_LONG);
        }
    }

    public void attemptLogin(View v) {
        if (webTask != null) {
            return;
        }

        // Reset errors.
        usernameInput.setError(null);
        passwordInput.setError(null);

        // Store values at the time of the login attempt.
        String user = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            passwordInput.setError(getString(R.string.error_invalid_password));
            focusView = passwordInput;
            cancel = true;
        }

        // Check for a valid user address.
        if (TextUtils.isEmpty(user)) {
            usernameInput.setError(getString(R.string.error_field_required));
            focusView = usernameInput;
            cancel = true;
        } else if (!isUsernameValid(user)) {
            usernameInput.setError(getString(R.string.error_invalid_email));
            focusView = usernameInput;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            webTask = new WebTask(this,user, password);
            webTask.execute();
        }
    }

    private boolean isUsernameValid(String user) {
        return user.equals("admin");
    }

    private boolean isPasswordValid(String password) {
        return password.equals("1234");
    }

    @Subscribe
    public void onEvent(User user){
        webTask = null;
        showProgress(false);

        Intent activity = new Intent(this, MainActivity.class);
        activity.putExtra("message","olá");
        startActivity(activity);

    }

    @Subscribe
    public void onEvent(Error error){
        webTask = null;
        showProgress(false);

        Snackbar snackbar = Snackbar
                .make(mLoginFormView, error.getMessage(), Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Subscribe
    public void onAnything(NoSubscriberEvent randomEvent){
        webTask = null;
        showProgress(false);
    }
}
