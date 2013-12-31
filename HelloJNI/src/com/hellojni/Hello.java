package com.hellojni;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class Hello extends Activity {
	TextView tView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       tView  =new TextView(this);
       tView.setText(getMessage());
       setContentView(tView);
        
        
    }
    public native String getMessage();
    /** Load the native library where the native method
    * is stored.
    */
    static {
          System.loadLibrary("myjni");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.hello, menu);
        return true;
    }
    
}
