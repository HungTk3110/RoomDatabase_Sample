package com.example.roomdatabase_sample;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roomdatabase_sample.database.userDataBase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText edt_name , edt_address ;
    private TextView tv_deleteAll ;
    private RecyclerView recyclerView;
    private Button btn_add;

    private recycviewAdapter recycviewAdapter;
    private static List<user> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edt_name = findViewById(R.id.edt_name);
        edt_address = findViewById(R.id.edt_Address);
        btn_add = findViewById(R.id.btn_add);
        recyclerView = findViewById(R.id.recycview);
        tv_deleteAll = findViewById(R.id.tv_deleteAll);

        recycviewAdapter = new recycviewAdapter(new recycviewAdapter.onClickItems() {
            @Override
            public void updateUser(user user) {
                clickUpdateUser(user);
                loadData();
            }

            @Override
            public void deleteUser(user user) {
                clickDeleteUser(user);
            }
        });
        recycviewAdapter.setData(list);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(recycviewAdapter);

        loadData();
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUser();
            }
        });
        tv_deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAll();
            }
        });


    }

    private void deleteAll() {
        new AlertDialog.Builder(this).setTitle("Confirm delete all user")
                .setMessage("Are you sure")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Delete user
                        userDataBase.getInstance(MainActivity.this).userDAO().deleteAllUser();
                        Toast.makeText(MainActivity.this,"Delete user successflly",Toast.LENGTH_SHORT).show();
                        loadData();
                    }
                })
                .setNegativeButton("no",null)
                .show();
    }

    private void clickDeleteUser(user user) {
        new AlertDialog.Builder(this).setTitle("Confirm delete user")
                .setMessage("Are you sure")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Delete user
                        userDataBase.getInstance(MainActivity.this).userDAO().deleteUser(user);
                        Toast.makeText(MainActivity.this,"Delete user successflly",Toast.LENGTH_SHORT).show();
                        loadData();
                    }
                })
                .setNegativeButton("no",null)
                .show();
    }

    private void clickUpdateUser(user user) {
        Intent intent = new Intent(this,UpdateActivity.class);
        intent.putExtra("object_user",user);
        startActivity(intent);
        //startActivityForResult(intent,);

    }

    private void addUser() {

        String str_name = edt_name.getText().toString().trim() ,
               str_address  = edt_address.getText().toString().trim();

        if (TextUtils.isEmpty(str_name) || TextUtils.isEmpty(str_address)){
            return;
        }

        user user = new user(str_name,str_address);

        if(isUserExist(user)) {
            Toast.makeText(this, "user exist", Toast.LENGTH_LONG).show();
            return;
        }

        userDataBase.getInstance(this).userDAO().insertUser(user);
        Toast.makeText(this ,"add usder",Toast.LENGTH_LONG).show();

        edt_name.setText("");
        edt_address.setText("");

        loadData();
    }

    public void loadData() {
        list = userDataBase.getInstance(this).userDAO().getListUser();
        recycviewAdapter.setData(list);
    }

    private boolean isUserExist(user user){
        List<user> list = userDataBase.getInstance(this).userDAO().checkUser(user.getName());
        return list != null && !list.isEmpty();
    }

    public static void updateList(user user){
        for(int i=0 ; i<list.size() ; i++){
            if(list.get(i).getId() == user.getId()){
                list.get(i).setName(user.getName());
                list.get(i).setAddress(user.getAddress());
            }
        }
    }
}