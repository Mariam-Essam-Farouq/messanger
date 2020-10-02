package messenger.iti;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

public class Broudcast extends BroadcastReceiver {
    public static final String MONDAY = "MONDAY";
    public static final String TUESDAY = "TUESDAY";
    public static final String WEDNESDAY = "WEDNESDAY";
    public static final String THURSDAY = "THURSDAY";
    public static final String FRIDAY = "FRIDAY";
    public static final String SATURDAY = "SATURDAY";
    public static final String SUNDAY = "SUNDAY";
    public static final String RECURRING = "RECURRING";
    public static final String TITLE = "TITLE";

    @Override
    public void onReceive(Context context, Intent intent) {
      if  (intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
        {
            Toast.makeText(context, "Alarm is working", Toast.LENGTH_SHORT).show();
            intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
            startRescheduleAlarmsService(context);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(new Intent(context, Myservice.class));
        } else {
            context.startService(new Intent(context, Myservice.class));
        }
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
        {


            startRescheduleAlarmsService(context);


        }

      if  (intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
        {


            startRescheduleAlarmsService(context);


        }





            if (!intent.getBooleanExtra(RECURRING, false)) {


                startAlarmService(context, intent);
            }

        }







    private void startRescheduleAlarmsService(Context context) {

        Intent intentService = new Intent(context, Myservice.class);
        intentService.putExtra(TITLE, intentService.getStringExtra(TITLE));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intentService);
        } else {
            context.startService(intentService);
        }

    }




    public void startAlarmService(Context context, Intent intent) {
        Intent intentService = new Intent(context, Myservice.class);
        intentService.putExtra(TITLE, intent.getStringExtra(TITLE));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intentService);
        } else {
            context.startService(intentService);
        }
    }




}
