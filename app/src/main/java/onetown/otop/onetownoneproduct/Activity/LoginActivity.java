package onetown.otop.onetownoneproduct.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import onetown.otop.onetownoneproduct.R;

public class LoginActivity extends AppCompatActivity {

    private Button loginButton;
    private EditText emailEditext;
    private EditText passwordEditext;
    private TextView redirectToRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        redirectToRegistration= (TextView)findViewById(R.id.redirecttoregistration_textview);
        redirectToRegistration.setOnClickListener(new LoginClickedListener());

        loginButton= (Button)findViewById(R.id.login_login_button);
        loginButton.setOnClickListener(new LoginClickedListener());
    }

    public class LoginClickedListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            startActivity(new Intent(LoginActivity.this,RegistrationActivity.class));
        }
    }
}
