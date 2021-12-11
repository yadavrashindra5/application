package com.example.internproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewData extends AppCompatActivity {

    EditText firstname,lastname,email;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_data);
        getSupportActionBar().hide();


        firstname=findViewById(R.id.firstname);
        lastname=findViewById(R.id.lastname);
        email=findViewById(R.id.email);
        submit=findViewById(R.id.submit);
        



        //submit button is calling
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(firstname.getText().toString())&&!TextUtils.isEmpty(lastname.getText().toString())&&!TextUtils.isEmpty(email.getText().toString()))
                {
                    new bgthread().start();
                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please fill all the Field",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    class bgthread extends Thread
    {
        public void run()
        {
            super.run();
            AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "database-name").build();
            UserDao userDao = db.userDao();
            userDao.insertAll(new User(firstname.getText().toString(),lastname.getText().toString(),email.getText().toString()));
            email.setText("");
            firstname.setText("");
            lastname.setText("");
        }
    }
}