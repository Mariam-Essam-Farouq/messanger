package messenger.iti;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.os.Vibrator;
import android.telephony.SmsManager;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class Myservice extends Service {
    Broudcast broudcast;
    public static final String CHANNEL_ID = "ForegroundServiceChannel";


    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;
    public String number;
    public  String msg;
    @Override
    public void onCreate() {
        super.onCreate();
        registerScreenOffReceiver();

        mediaPlayer = MediaPlayer.create(this, R.raw.alarm);
        mediaPlayer.setLooping(true);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        number=  intent.getStringExtra("z");
        msg=       intent.getStringExtra("o");






        alarm();
        sendmesg();









        createNotificationChannel();
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Foreground Service")






                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);




        return START_STICKY;
    }


    @Override
    public void onDestroy() {

        super.onDestroy();














        mediaPlayer.stop();
        vibrator.cancel();



    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Intent restartServiceIntent = new Intent(getApplicationContext(), this.getClass());
        PendingIntent pendingIntent = PendingIntent.getService(getBaseContext(), 0, restartServiceIntent, 0);

        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),24*60*60*1000,pendingIntent);

        startService(restartServiceIntent);
        Intent intent = new Intent("com.android.ServiceStopped");
        sendBroadcast(intent);


        super.onTaskRemoved(rootIntent);

    }



    @Override


    public IBinder onBind(Intent intent) {
        return null;
    }



    public void registerScreenOffReceiver() {
        broudcast = new Broudcast() {



        };
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        registerReceiver(broudcast, filter);
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }
    public   void  sendmesg()
    {



        Timer timer=new Timer(true);
        final TimerTask mytask = new TimerTask() {
            public void run() {
                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(number, "", msg,
                        null, null);
            }
        };

        timer.schedule(mytask, 6000);


    }
    public void alarm()
    {
        Timer timer=new Timer(true);
        final TimerTask mytask = new TimerTask() {
            public void run() {
                mediaPlayer.start();


                long[] pattern = {0, 100, 1000};
                vibrator.vibrate(pattern, 0);

            }
        };


        timer.schedule(mytask, 6000);

    }













}
