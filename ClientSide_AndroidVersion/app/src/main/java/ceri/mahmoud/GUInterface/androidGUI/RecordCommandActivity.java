package ceri.mahmoud.GUInterface.androidGUI;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import ceri.mahmoud.signalAcquisition.ISignalAcqusition;
import ceri.mahmoud.signalAcquisition.SignalAcqusitionImpl;


public class RecordCommandActivity extends Activity{

    private Chronometer recordChrono ;
    public Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_command);
        ImageView recordButton = findViewById(R.id.recordButton);
        recordButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ISignalAcqusition signalAcquier = new SignalAcqusitionImpl();
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    recordChrono = (Chronometer) findViewById(R.id.recordChrono);
                    recordChrono.setBase(SystemClock.elapsedRealtime());
                    recordChrono.start();
                    System.out.println("Call for signal recognition ...");
                    signalAcquier.getCommand(context);
                   // String transcriptedMessage = messageTranscriptor.getMessageCommand(context);
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    recordChrono.stop();
                    recordChrono.setBase(SystemClock.elapsedRealtime());
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


}
