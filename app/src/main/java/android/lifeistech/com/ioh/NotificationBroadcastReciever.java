package android.lifeistech.com.ioh;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by fumiyauchiyama on 2017/08/06.
 */

public class NotificationBroadcastReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("service", "BroadcastReceiver");

        Intent startServiceIntent = new Intent(context, NotificationService.class);
        startServiceIntent.putExtra(NotificationService.REQUEST_TYPE, NotificationService.REQUEST_PROCESS);
        context.startService(startServiceIntent);

        Bundle bundle = intent.getExtras();
        String message = bundle.getString("message");

        Toast.makeText(
                context,
                "onReceive! " + message,
                Toast.LENGTH_LONG).show();
    }

}
