package victorperales.servicetest2;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    //Boolean isPlaying = false;

    //Bundle recvBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        final Button playStream = (Button)  findViewById(R.id.start_service);
        final Button stopStream = (Button)  findViewById(R.id.stop_service);

        //starting service
        findViewById(R.id.start_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent intent = new Intent(MainActivity.this, ServiceTest.class);
                startService(intent);

                playStream.setVisibility(View.GONE);
                stopStream.setVisibility(View.VISIBLE);
            }
        });

        //service onDestroy callback method will be called
        findViewById(R.id.stop_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ServiceTest.class);
                stopService(intent);

                stopStream.setVisibility(View.GONE);
                playStream.setVisibility(View.VISIBLE);

            }
        });

    }

    @Override
    public void onResume() {

        //Log.e("DEBUG", "Called onResume");
        super.onResume();

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        super.onSaveInstanceState(savedInstanceState);

    }


        @Override
    protected void onStop() {

        super.onStop();

    }

}
