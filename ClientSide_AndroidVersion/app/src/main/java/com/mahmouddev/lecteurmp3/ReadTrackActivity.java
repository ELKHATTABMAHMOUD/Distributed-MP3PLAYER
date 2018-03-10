package com.mahmouddev.lecteurmp3;

import android.app.ActionBar;
import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;


public class ReadTrackActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_track);
        ImageView playButton = (ImageView) findViewById(R.id.playButton);
        playButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                String filePath = Environment.getExternalStorageDirectory()+"/myRecord.3gpp";
                MediaPlayer mediaPlayer = new MediaPlayer();
                try {

                    mediaPlayer.setDataSource(filePath);
                    mediaPlayer.prepare();
                    System.out.println("lectureeeeeeeeeeeeeeeeeee");
                    mediaPlayer.start();
                    return true ;
                }
                catch (IOException ex){
                    return false;
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_read_track, menu);
        getActionBar().setIcon(R.drawable.recording);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
