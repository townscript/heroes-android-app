package com.example.barclaysfresh;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SignIn extends Activity{
	EditText username;
	EditText password;
	EditText email;
	EditText contact;
	Button signIn;
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_activity);	
		context = this;
		
		username = (EditText) findViewById(R.id.nameET);
		password = (EditText) findViewById(R.id.passwordET);
		email = (EditText) findViewById(R.id.emailET);
		contact = (EditText) findViewById(R.id.phoneET);
		signIn = (Button) findViewById(R.id.signUpBtn);
		
		signIn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				UserDTO userDTO = new UserDTO();
				userDTO.setUsername(username.getText().toString());
				userDTO.setPassword(password.getText().toString());
				userDTO.setEmail(email.getText().toString());
				userDTO.setContact(contact.getText().toString());

				Utils.setUserDtoSP(context,userDTO);
				Intent i = new Intent(context,HomePage.class);
				startActivity(i);
			}
		});
	}
}
