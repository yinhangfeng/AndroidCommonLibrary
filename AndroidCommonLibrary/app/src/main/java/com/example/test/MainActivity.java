package com.example.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.library.androidcommonlibrary.R;
import com.example.library.util.L;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     *to
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id) {
        case R.id.logx:
            log();
            return true;
        case R.id.action_settings:
            setting();
            return true;
        case R.id.settings_all:
            settingAll();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void test(int a, String b, double c) {
        L.d(TAG, "test() called with a = [", a, "], b = [", b, "], c = [", c, "]");
        L.d(TAG, "test() returned ", 111);
    }

    private void test() {
        L.d(TAG, "test() called with ");
    }

    int level = -1;
    private void setting() {
        Log.e(TAG, "xxxxxxxxxxxx level=" + level);
        L.setMinimumLoggingLevel(level);
        if(++level > 8) {
            level = -1;
        }
    }

    boolean all = true;
    private void settingAll() {
        all = !all;
        Log.e(TAG, "xxxxxxxxxxxxxxxxxx all=" + all);
        L.setAllLevelLoggable(all);
    }

    private void log() {
        Exception e = new NullPointerException("xxx");

        test(3, "xxx", 3.3);
        test();

        L.v(TAG, "xxx", " a=", 1, " b=", "abc");
        L.fv(this, "xxx a=%d b=%s", 333, "abc");


        L.d(TAG, "xxx", " a=", 1, " b=", "abc");
        L.d(TAG, "xxx a=", 3, " xxx");
        L.d(this, "xxx a=", "saa", " b=", 3);
        L.fd(this, "xxx a=%d b=%s", 333, "abc");

        L.i(TAG, "xxx", " a=", 1, " b=", "abc");
        L.i(TAG, "xxx a=", 3, " xxx");
        L.i(this, "xxx a=", "saa", " b=", 3);
        L.fi(this, "xxx a=%d b=%s", 333, "abc");

        L.w(TAG, "xxx");
        L.w(TAG, "xxx", " a=", 1, " b=", "abc");
        L.w(TAG, "xxx a=", 3, " xxx");
        L.w(this, "xxx a=", "saa", " b=", 3);
        L.fw(this, e, "xxx a=%d b=%s", 333, "abc");

        L.e(TAG, "xxx", " a=", 1, " b=", "abc");
        L.e(TAG, "xxx a=", 3, " xxx");
        L.e(this, "xxx a=", "saa", " b=", 3);
        L.fe(this, e, "xxx a=%d b=%s", 333, "abc");

        L.wtf(TAG, "xxx", " a=", 1, " b=", "abc");
        L.wtf(TAG, "xxx a=", 3, " xxx");
        L.wtf(this, "xxx a=", "saa", " b=", 3);
        L.fwtf(this, "xxx a=%d b=%s", 333, "abc");

        Toast.makeText(this, "xxxx", Toast.LENGTH_SHORT).show();
    }
}
