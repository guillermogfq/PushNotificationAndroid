package cl.ubiobio.pushnotificationubb;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        counter = 0;
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:
                //crearNotificacion();
                crearNotificacionTouchable();
                break;
        }
    }

    public void crearNotificacion(){
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this ,
                NotificationCompat.CATEGORY_MESSAGE)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("titulo")
                .setContentText("mensaje largo ..... ..... ...")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this) ;
        notificationManager.notify(counter, mBuilder.build());
        counter++;
    }

    public void crearNotificacionTouchable(){
        Intent intent = new Intent(this, NotificationActivity.class);
        intent.putExtra("hola", "data");
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this , 0, intent , 0);
        NotificationChannel channel = null;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            channel =
                    new NotificationChannel("ubb.msg.noti","notificaiones",
                            NotificationManager.IMPORTANCE_DEFAULT);

        }

        @SuppressLint({"NewApi", "LocalSuppress"})
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this ,
                        (channel != null)? channel.getId():NotificationCompat.CATEGORY_MESSAGE)
                        .setSmallIcon(R.drawable.ic_notification)
                        .setContentTitle("titulo")
                        .setContentText("mensaje largo ..... ..... ...")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
            manager.notify(counter, mBuilder.build());
        }else{
            NotificationManagerCompat notificationManager =
                    NotificationManagerCompat.from(this) ;
            notificationManager.notify(counter, mBuilder.build());
        }

        counter++;
    }
}

