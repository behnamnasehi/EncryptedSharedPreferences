package com.behnamnasehi.encryptedsharedpreferences;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EncryptedDataHolder encryptedDataHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            encryptedDataHolder = new EncryptedDataHolder(this);
        }
        encryptedDataHolder.setApiKey("this is test from Behnam Nasehi");

        TextView textView = findViewById(R.id.txt);
        textView.setText(encryptedDataHolder.getApiKey());
    }
}