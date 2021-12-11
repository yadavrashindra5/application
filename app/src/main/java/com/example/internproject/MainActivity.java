package com.example.internproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Button remote,local;
    private FloatingActionButton submit;
    ArrayList<User>arrayList;
    RecyclerView recyclerView;
    private int page=1;
    private int limit=2;
    private LinearLayoutManager manager;
    private NestedScrollView nestedScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        remote=findViewById(R.id.remote);
        local=findViewById(R.id.local);
        submit=findViewById(R.id.submit);
        recyclerView=findViewById(R.id.recyclerview);
        arrayList=new ArrayList<>();
        manager=new LinearLayoutManager(getApplicationContext());
        nestedScrollView=findViewById(R.id.nestedScrollview);
        getLoadData(page,limit);


        //local button functionality
        local.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               submit.setVisibility(View.VISIBLE);
               remote.setBackgroundColor(getResources().getColor(R.color.white));
                local.setBackgroundColor(getResources().getColor(R.color.teal_200));


                //Featching data from room database
                AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "database-name").allowMainThreadQueries().build();
                UserDao     userDao=db.userDao();

                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                List<User> userList=userDao.getallusers();

                MyAdapter adapter=new MyAdapter(userList,getApplicationContext());
                recyclerView.setAdapter(adapter);

            }
        });



        //remote button functionality
        remote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit.setVisibility(View.GONE);
                local.setBackgroundColor(getResources().getColor(R.color.white));
                remote.setBackgroundColor(getResources().getColor(R.color.teal_700));
                page=1;
                getLoadData(page,limit);

            }
        });


        //float button functionality
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),NewData.class);
                startActivity(intent);
            }
        });

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    // in this method we are incrementing page number,
                    // making progress bar visible and calling get data method.
                    page++;
                    if(page>limit)
                    {
                        Toast.makeText(getApplicationContext(),"that is all data",Toast.LENGTH_LONG).show();
                        return;
                    }
                    Toast.makeText(getApplicationContext(),"Load more",Toast.LENGTH_SHORT).show();
                    getLoadData(page, limit);
                }

            }
        });
    }

    private void getLoadData(int page,int limit) {



        Retrofit retrofit=new Retrofit.Builder().baseUrl("https://reqres.in/").addConverterFactory(GsonConverterFactory.create()).build();
        MyApi myApi=retrofit.create(MyApi.class);
        Call<Class1> call=myApi.getData(page,6);
        call.enqueue(new Callback<Class1>() {
            @Override
            public void onResponse(Call<Class1> call, Response<Class1> response) {
                Class1 class1=response.body();
                List<Data>list=class1.getData();


                RemoteAdapter adapter=new RemoteAdapter(list,getApplicationContext());
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(adapter);


            }

            @Override
            public void onFailure(Call<Class1> call, Throwable t) {
                Toast.makeText(getApplicationContext(),""+t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });
    }
}