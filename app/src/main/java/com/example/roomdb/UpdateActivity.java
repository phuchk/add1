package com.example.roomdb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {
    Button btn;
    EditText edName, edPass;
    User u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        btn = findViewById(R.id.edt_btn);
        edName = findViewById(R.id.edt_name);
        edPass = findViewById(R.id.edt_pass);

        u = (User) getIntent().getExtras().get("user");
        if (u != null) {
            edName.setText(u.getName());
            edPass.setText(u.getPass());
        }
        btn.setOnClickListener(view -> {
            updateUser();
        });


    }

    private void updateUser() {
        String name = edName.getText().toString().trim();
        String pass = edPass.getText().toString().trim();
        u.setName(name);
        u.setPass(pass);
        Db.getInstance(this).dao().updateUser(u);
        Toast.makeText(this, "update ok", Toast.LENGTH_SHORT).show();

        Intent intent=new Intent(UpdateActivity.this,MainActivity.class);
        setResult(RESULT_OK,intent);
        finish();
    }
}