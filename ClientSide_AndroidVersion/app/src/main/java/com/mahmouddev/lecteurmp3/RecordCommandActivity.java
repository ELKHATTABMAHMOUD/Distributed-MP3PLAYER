package com.mahmouddev.lecteurmp3;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;


public class RecordCommandActivity extends Activity {
    private MediaRecorder mediaRecorder;
    private String FILE ;
    private Chronometer recordChrono ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_record_command);
        FILE = Environment.getExternalStorageDirectory()+"/myRecord.3gpp";
        ImageView recordButton = (ImageView) findViewById(R.id.recordButton);
        recordButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    System.out.println("recording ...");
                    try {
                        startRecording();
                    }
                    catch (Exception ex){
                        System.out.println("ERROR MESSAGE : erreur inconnu");
                    }
                } else if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
                    System.out.println("End of recording !");
                    stopRecordiing();
                    Intent readSoundIntent = new Intent(v.getContext(),ReadTrackActivity.class);
                    startActivityForResult(readSoundIntent, 0);
                }
                return true;
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_record_command, menu);
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

    /*--------------------- these are my functions associated to Media recording -----------------------*/

    protected void startRecording() throws Exception{
        File filout = new File(FILE);
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mediaRecorder.setOutputFile(FILE);
        mediaRecorder.prepare();
        recordChrono = (Chronometer) findViewById(R.id.recordChrono);
        recordChrono.setBase(SystemClock.elapsedRealtime());
        recordChrono.start();
        mediaRecorder.start();
    }

    protected void stopRecordiing(){
        mediaRecorder.stop();
        recordChrono.stop();
        recordChrono.setBase(SystemClock.elapsedRealtime());
        mediaRecorder.reset();
        mediaRecorder.release();
        mediaRecorder = null;
    }

}
