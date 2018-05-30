package cl.ubiobio.pushnotificationubb;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage){
        crearNotificacionTouchable();
    }

    public void crearNotificacionTouchable(){
        Intent intent = new Intent(this, NotificationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
            manager.notify(0, mBuilder.build());
        }else{
            NotificationManagerCompat notificationManager =
                    NotificationManagerCompat.from(this) ;
            notificationManager.notify(0, mBuilder.build());
        }
    }
}
