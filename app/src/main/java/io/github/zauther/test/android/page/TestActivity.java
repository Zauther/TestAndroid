package io.github.zauther.test.android.page;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import io.github.zauther.test.android.MainActivity;
import io.github.zauther.test.android.R;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Log.d("TestActivity==", "onCreate: "+this.toString()+", taskId="+this.getTaskId());
       findViewById(R.id.root_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestActivity.this, MainActivity.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
}