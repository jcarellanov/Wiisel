package julioexample.wiisel;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    public Context context;
    int MY_PERMISSIONS_ACCESS_FINE = 1;
    private WifiManager mWifiManager;
    int REQUEST_ENABLE_BT = 2;
    private BluetoothAdapter mBluetoothAdapter;
    private String LOG_TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                MY_PERMISSIONS_ACCESS_FINE);

        mWifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        mWifiManager.setWifiEnabled(true);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        enableBluetoothOnDevice();

        Button thisIsDoctor = (Button) findViewById(R.id.buttonDoctor);
        Button thisIsPatient = (Button) findViewById(R.id.buttonPatient);

        View.OnClickListener doctorLogin = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                setContentView(R.layout.doctor_login);


            }
        };

        thisIsDoctor.setOnClickListener(doctorLogin);

        View.OnClickListener patientLogin = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchPatient = new Intent(context, PatientHome.class);
                context.startActivity(launchPatient);
            }
        };

        thisIsPatient.setOnClickListener(patientLogin);
    }
    private void enableBluetoothOnDevice() {
        if (mBluetoothAdapter == null) {
            Log.e(LOG_TAG, "This device does not have a bluetooth adapter");
            finish();
            // If the android device does not have bluetooth, just return and get out.
            // There's nothing the app can do in this case. Closing app.
        }

        // Check to see if bluetooth is enabled. Prompt to enable it
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);

        }
    }

}
