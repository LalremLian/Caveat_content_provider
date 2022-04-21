package com.example.contentprovider2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.LinearLayout;

import com.example.contentprovider2.adapter.AdapterRecycler;
import com.example.contentprovider2.model.ModelClass;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class ContentProviderActivity extends AppCompatActivity {

    LinearLayout linearLayout;
    RecyclerView recyclerView;
    AdapterRecycler adapter;
    ArrayList<ModelClass> modelClassArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider);

        recyclerView = findViewById(R.id.recyclerView1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        linearLayout = findViewById(R.id.linearContent);

        getFetch();
    }

    private void getFetch() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},0);
        }
        Snackbar snackbar = Snackbar.make(linearLayout,"Developed by Amawng Calvert",Snackbar.LENGTH_LONG);
        snackbar.show();
        ContentResolver resolver = getContentResolver(); //creating an resolver object.............
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

        Cursor cursor = resolver.query(uri, null, null, null, null);

        modelClassArrayList = new ArrayList<>();
        modelClassArrayList.clear();
        int counter = 0;

        if (cursor.getCount()>0)
        {
            while (cursor.moveToNext())
            {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                @SuppressLint("Range") String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                ModelClass modelClass = new ModelClass(name, number);
                modelClassArrayList.add(counter, modelClass);

                counter++;
            }
        }
        //Log.e("ContactArrayList", modelClassArrayList.toString());

        adapter = new AdapterRecycler(ContentProviderActivity.this, modelClassArrayList);
        recyclerView.setAdapter(adapter);
    }
}