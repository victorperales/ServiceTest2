package victorperales.servicetest2;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
//import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;


public class ServiceTest extends Service {

    private static final String TAG = "HelloService";

    private boolean isRunning = false;

    private MediaPlayer mediaPlayer;

    private  String StreamURL = "http://media.radiofrance-podcast.net/podcast09/13915-06.05.2015-ITEMA_20749422-4.mp3";

    WifiManager.WifiLock wifiLock;

    @Override
    public void onCreate() {
        Log.i(TAG, "Service onCreate");

        Toast.makeText(this, "The new Service was Created", Toast.LENGTH_LONG).show();

        isRunning = true;

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i(TAG, "Service onStartCommand");

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);

        //Uri file = Uri.parse("http://media.radiofrance-podcast.net/podcast09/13915-06.05.2015-ITEMA_20749422-4.mp3");

        //mediaPlayer = MediaPlayer.create(this, file);

        wifiLock = ((WifiManager) getSystemService(Context.WIFI_SERVICE)).createWifiLock(WifiManager.WIFI_MODE_FULL, "mylock");
        wifiLock.acquire();

        //Creating new thread for my service
        //Always write your long running tasks in a separate thread, to avoid ANR
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    mediaPlayer.setDataSource(StreamURL);

                    mediaPlayer.prepareAsync();

                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){

                        @Override
                        public void onPrepared(MediaPlayer streamPlayer){

                            streamPlayer.start();

                        }

                    });

                    //mediaPlayer.start();

                }

                catch (Exception e) {

                    Log.e(TAG, "Player failed", e);

                }

                if(isRunning){
                    Log.i(TAG, "Service running");
                }

            }
        }).start();

        Toast.makeText(this, "Service started", Toast.LENGTH_LONG).show();

        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent arg0) {
        Log.i(TAG, "Service onBind");

        // TODO: Return the communication channel to the service.

        throw new UnsupportedOperationException("Not yet implemented");

        //return null;
    }

    @Override
    public void onDestroy() {

        if (mediaPlayer != null){

            mediaPlayer.stop();
            mediaPlayer.release();

        }

        wifiLock.release();

        isRunning = false;

        Log.i(TAG, "Service onDestroy");

        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();

        super.onDestroy();

    }

}
