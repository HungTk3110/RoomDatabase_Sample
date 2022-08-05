package com.example.roomdatabase_sample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.roomdatabase_sample.database.userDataBase;

public class UpdateActivity extends AppCompatActivity {

    private EditText edt_nameUpdate , edt_addressUpdate ;
    private Button btn_Update ;
    private user muser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        edt_nameUpdate = findViewById(R.id.edt_name_update);
        edt_addressUpdate = findViewById(R.id.edt_Address_update);
        btn_Update = findViewById(R.id.btn_update);

        Intent intent = getIntent();
        muser = (user) intent.getSerializableExtra("object_user");
        if(muser != null){
            edt_nameUpdate.setText(muser.getName());
            edt_addressUpdate.setText(muser.getAddress());
        }

        btn_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUser();
            }
        });
    }

    private void updateUser() {
        String str_name = edt_nameUpdate.getText().toString().trim() ,
                str_address  = edt_addressUpdate.getText().toString().trim();

        if (TextUtils.isEmpty(str_name) || TextUtils.isEmpty(str_address)){
            return;
        }

        //Update user
        muser.setName(str_name);
        muser.setAddress(str_address);

        userDataBase.getInstance(this).userDAO().updateUser(muser);
        Toast.makeText(this,"Update user successfully",Toast.LENGTH_SHORT).show();
        MainActivity.updateList(muser);
        startActivity(new Intent(this , MainActivity.class));
    }
}