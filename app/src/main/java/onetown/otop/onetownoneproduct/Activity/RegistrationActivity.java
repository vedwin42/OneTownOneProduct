package onetown.otop.onetownoneproduct.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import onetown.otop.onetownoneproduct.Database.DBHelper;
import onetown.otop.onetownoneproduct.Objects.Credentials;
import onetown.otop.onetownoneproduct.R;

public class RegistrationActivity extends AppCompatActivity {

    private EditText emailEditext,passwordEditext,confirmPassword;
    private Button registerButton;

    String email,password,confirmPass;
    DBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        helper= new DBHelper(RegistrationActivity.this);

        emailEditext= (EditText)findViewById(R.id.email_editext);
        passwordEditext= (EditText)findViewById(R.id.password_editext);
        confirmPassword= (EditText)findViewById(R.id.password_confirm_editext);


        registerButton= (Button)findViewById(R.id.register_button);
        registerButton.setOnClickListener(new RegisterButtonListener());

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public class RegisterButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            email= emailEditext.getText().toString();
            password= passwordEditext.getText().toString();
            confirmPass= confirmPassword.getText().toString();

            Credentials cred= new Credentials(password,email);

            if ((password != confirmPass && password.isEmpty() && confirmPass.isEmpty()) || (!password.trim().equals(confirmPass))) {
                Toast.makeText(getApplicationContext(),"Please check your password/email !",Toast.LENGTH_LONG).show();
            }else {

                if (helper.checkIfEmailExist(email)) {
                    Toast.makeText(getApplicationContext(),"An Existing Email found. Please use other email",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(),"Credentials Successfully added",Toast.LENGTH_LONG).show();
                    helper.addCredentialsToDb(cred);
                    startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
                }


            }


        }
    }
}
