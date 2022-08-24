package com.example.roomdb;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    Adapter adapter;
    Button btn;
    TextView tv;
    EditText edName, edPass, edSearch, edYear;
    List<User> mlist = new ArrayList<>();
    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        loadData();
                    }
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new Adapter(new Adapter.IonClick() {
            @Override
            public void updateUser(User u) {
                clickUpdate(u);

            }

            @Override
            public void deleteUser(User u) {
                clickDelete(u);
            }
        });
        recyclerView = findViewById(R.id.rView);
        btn = findViewById(R.id.btn);
        edName = findViewById(R.id.name);
        edYear = findViewById(R.id.year);
        edPass = findViewById(R.id.pass);
        edSearch = findViewById(R.id.search);
        tv = findViewById(R.id.del_all);

        adapter.setData(mlist);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        btn.setOnClickListener(view -> {
            add();
        });
        loadData();
        tv.setOnClickListener(view -> {
            delAll();
        });
        edSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent event) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    handle();
                }
                return false;
            }
        });

    }

    private void handle() {
        String key = edSearch.getText().toString().trim();
        mlist = new ArrayList<>();
        mlist = Db.getInstance(this).dao().searchUser(key);
        adapter.setData(mlist);
    }

    private void delAll() {
        new AlertDialog.Builder(this)
                .setTitle("delete all user?")
                .setMessage("sure?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Db.getInstance(MainActivity.this).dao().deleteAll();
                        Toast.makeText(MainActivity.this, "delete all ok", Toast.LENGTH_SHORT).show();
                        loadData();
                    }
                })
                .setNegativeButton("no", null)
                .show();
    }

    private void clickDelete(User u) {
        new AlertDialog.Builder(this)
                .setTitle("delete user?")
                .setMessage("sure?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Db.getInstance(MainActivity.this).dao().deleteUser(u);
                        Toast.makeText(MainActivity.this, "delete ok", Toast.LENGTH_SHORT).show();
                        loadData();
                    }
                })
                .setNegativeButton("no", null)
                .show();

    }

    private void clickUpdate(User u) {
        Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", u);
        intent.putExtras(bundle);
        activityResultLauncher.launch(intent);
    }

    private void loadData() {
        mlist = Db.getInstance(this).dao().getListUser();
        adapter.setData(mlist);
    }

    private void add() {
        String name = edName.getText().toString().trim();
        String pass = edPass.getText().toString().trim();
        String year = edYear.getText().toString().trim();

        User u = new User(name, pass, year);
        if (isUserExist(u)) {
            Toast.makeText(this, "add exits", Toast.LENGTH_SHORT).show();
            return;
        }

        Db.getInstance(this).dao().insertUser(u);
        Toast.makeText(this, "add ok", Toast.LENGTH_SHORT).show();
        loadData();
    }

    private boolean isUserExist(User u) {
        List<User> list = Db.getInstance(this).dao().checkUser(u.getName());
        return list != null && !list.isEmpty();
    }
}