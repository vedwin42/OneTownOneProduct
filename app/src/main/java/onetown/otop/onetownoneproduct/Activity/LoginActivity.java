package onetown.otop.onetownoneproduct.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import onetown.otop.onetownoneproduct.Database.DBHelper;
import onetown.otop.onetownoneproduct.Objects.Credentials;
import onetown.otop.onetownoneproduct.R;

public class LoginActivity extends AppCompatActivity {

    private Button loginButton;
    private EditText emailEditext;
    private EditText passwordEditext;
    private TextView redirectToRegistration;
    DBHelper helper;
    ArrayList<Credentials> userDetails;
    String PREF_NAME="useridpref";

    String emailValue,passwordValue;
    Credentials credentials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        helper= new DBHelper(LoginActivity.this);

        emailEditext= (EditText)findViewById(R.id.loginemail_editext);
        passwordEditext= (EditText)findViewById(R.id.login_password_editext);

        redirectToRegistration= (TextView)findViewById(R.id.redirecttoregistration_textview);
        redirectToRegistration.setOnClickListener(new LoginClickedListener());

        loginButton= (Button)findViewById(R.id.login_login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailValue=emailEditext.getText().toString();
                passwordValue=passwordEditext.getText().toString();

                if (helper.checkIfAccountExist(emailValue,passwordValue)) {


                    credentials= new Credentials(emailValue,passwordValue);
                    helper.getSingleValue(credentials);

                    SharedPreferences userIdPref= getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor= userIdPref.edit();

                    editor.putInt("user_id",credentials.get_id());
                    editor.putString("user_email",credentials.getEmail());
                    Log.i("Credentials Value",String.valueOf(credentials));
                    editor.commit();

                   // startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    Intent redirectIntent=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(redirectIntent);


                    emailEditext.setText("");
                    passwordEditext.setText("");
                }else {
                    Toast.makeText(getApplicationContext(),"Invalid Login!",Toast.LENGTH_LONG).show();
                    emailEditext.setText("");
                    passwordEditext.setText("");
                }
            }
        });
    }

    public class LoginClickedListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            startActivity(new Intent(LoginActivity.this,RegistrationActivity.class));
        }
    }
}
