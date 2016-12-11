package onetown.otop.onetownoneproduct.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import onetown.otop.onetownoneproduct.Database.DBHelper;
import onetown.otop.onetownoneproduct.R;

public class LoginActivity extends AppCompatActivity {

    private Button loginButton;
    private EditText emailEditext;
    private EditText passwordEditext;
    private TextView redirectToRegistration;
    DBHelper helper;

    String emailValue,passwordValue;

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
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }else {
                    Toast.makeText(getApplicationContext(),"Invalid Login!",Toast.LENGTH_LONG).show();
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
