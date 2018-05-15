package ceri.mahmoud.lecteurAudio;

import android.app.ProgressDialog;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import ceri.mahmoud.GUInterface.androidGUI.R;
import ceri.mahmoud.GUInterface.androidGUI.ReadTrackActivity;

public class MP3ReaderActivity extends Activity implements View.OnClickListener {
    private ProgressBar progressBar;
    private int progressStatus = 0;
    private TextView textView;
    private Handler handler = new Handler();
    private ImageView playButton ;
    private ImageView backButton ;
    private ImageView volume ;
    private AudioManager mAudioManager ;
    private MediaPlayer mPlayer ;
    private ProgressDialog pDialog ;
    private boolean initialStage =true ;
    private boolean playPause;
    private boolean isVolumeDown = false ;
    String videoURL = "http://192.168.43.122:8090/test.mp3";
//    String videoURL = "http://10.26.2.152:8090/test.mp3";
//    String videoURL = "http://192.168.1.12:8090/test.mp3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mp3_reader);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        playButton = (ImageButton) findViewById(R.id.playButton);
        backButton = (ImageButton) findViewById(R.id.backButton) ;
        volume     = (ImageButton) findViewById(R.id.volumeButton);
        mPlayer = new MediaPlayer();
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        pDialog = new ProgressDialog(this);

        playButton.setOnClickListener(this);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        /*
        volume.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isVolumeDown) {
                    volume.setImageResource(R.drawable.ic_volume_off);
                    mPlayer.setVolume(0, 0);
                    isVolumeDown =true;
                }
                else {
                    mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
                    int vol = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                    volume.setImageResource(R.drawable.ic_volume_up);

                    mPlayer.setVolume(vol, vol);
                    isVolumeDown=false;
                }
            }
        });
        /*******************  this will be used to call ice server ***********************
         playButton.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v) {
        try(com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize())
        {
        com.zeroc.Ice.ObjectPrx base1 = communicator.stringToProxy("LecteurMP3:tcp -h 192.168.0.12 -p 10000");
        ManagerPrx server1 = ManagerPrx.checkedCast(base1);

        com.zeroc.Ice.ObjectPrx base2 = communicator.stringToProxy("LecteurMP3:tcp -h 192.168.0.12 -p 20000");
        ManagerPrx server2 = ManagerPrx.checkedCast(base2);

        com.zeroc.Ice.ObjectPrx base3 = communicator.stringToProxy("LecteurMP3:tcp -h 192.168.0.12 -p 30000");
        ManagerPrx server3 = ManagerPrx.checkedCast(base3);
        if(server1 == null || server2 == null|| server3 == null )
        {
        throw new Error("Invalid server proxy");
        }
        // Add sounds to server 1
        server1.ajouterMorceau("test", "Rai", "Auteur 1", "F:\\sounds");
        // Add sounds to server 2
        server2.ajouterMorceau("test1", "RnB", "Auteur 2", "F:\\sounds");
        // Add sounds to server 3
        server3.ajouterMorceau("test2", "Rock", "Auteur 3", "F:\\sounds");
        int serverNumber = 0 ;
        // search a sound in all servers
        if(server1.rechMorceauParTitre("test")) {
        serverNumber = 1;
        }
        else {
        if(server2.rechMorceauParTitre("test")) {
        serverNumber = 2;
        }
        else {
        if(server3.rechMorceauParTitre("test")) {
        serverNumber = 2;
        }
        }
        }

        switch(serverNumber) {
        case 1:	System.out.println("server 1");
        server1.streamSound("test", 5);
        break;
        case 2:	System.out.println("server 2");
        server1.streamSound("test", 5);
        break;
        case 3:	System.out.println("server 3");
        server1.streamSound("test", 5);
        break;
        }
        }
        }
        });
         ************************************************************************/
    }
    @Override
    public void onClick(View v){
        /*
        String url = getEncodedUrl(videoURL);
        new Player().execute(url);
        */
        if(!playPause){
            playButton.setImageResource(R.drawable.ic_pause);
            if(initialStage){
                new Player().execute(videoURL);
            }
            else{
                if(!mPlayer.isPlaying()){
                    mPlayer.start();
                }
            }
            playPause=true;
        }
        else{
            playButton.setImageResource(R.drawable.ic_play);
            if(mPlayer.isPlaying()){
                mPlayer.pause();
            }
            playPause=false ;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mPlayer !=null){
            mPlayer.reset();
            mPlayer.release();
            mPlayer=null;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_read_track, menu);
        getActionBar().setIcon(R.drawable.ic_read_48);
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
    private String getEncodedUrl(String mediaUrl){
        URL url = null;
        String urlStr = null ;
        try {
            url = new URL(mediaUrl);
            URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
            urlStr = uri.toASCIIString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return urlStr ;
    }


    protected void  startProgressBar(){
        new Thread(new Runnable() {
            public void run() {
                while (progressStatus < 100) {
                    progressStatus += 1;
                    // Update the progress bar and display the
                    //current value in the text view
                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressStatus);
                            //   textView.setText(progressStatus+"/"+progressBar.getMax());
                        }
                    });
                    try {
                        // Sleep for 200 milliseconds.
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    class Player extends AsyncTask<String,Void,Boolean> {
        @Override
        protected Boolean doInBackground(String... strings) {
            Boolean prepared = false;
            try {
                mPlayer.setDataSource(strings[0]);
                mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        initialStage = true ;
                        playPause = false;
                        playButton.setImageResource(R.drawable.ic_play);
                        mPlayer.stop();
                        mPlayer.release();
                    }
                });
                mPlayer.prepare();
                prepared=true;
            }  catch (IOException e) {
                e.printStackTrace();
                prepared = false ;
            }
            return  prepared;
        }
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(pDialog.isShowing())
                pDialog.cancel();
            mPlayer.start();
            initialStage = false;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog.setMessage("Please wait ...");
            pDialog.show();
        }
    }
}
