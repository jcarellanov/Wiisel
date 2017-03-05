package julioexample.wiisel;

import android.Manifest;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public Context context;
    int MY_PERMISSIONS_ACCESS_FINE = 1;
    int MY_PERMISSIONS_CALL_PHONE = 2;
    int MY_PERMISSIONS_CALL_PRIVILEGED = 3;
    int MY_PERMISSIONS_ACCESS_NETWORK = 4;
    private WifiManager mWifiManager;
    int REQUEST_ENABLE_BT = 2;
    private BluetoothAdapter mBluetoothAdapter;
    private String LOG_TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;
        ////permission requests
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                MY_PERMISSIONS_ACCESS_FINE);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CALL_PHONE},
                MY_PERMISSIONS_CALL_PHONE);

       /* ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CALL_PRIVILEGED},
                MY_PERMISSIONS_CALL_PRIVILEGED);*///needed?

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_NETWORK_STATE},
                MY_PERMISSIONS_ACCESS_NETWORK);
        ///end permission requests

///// activate necessary networks

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!mBluetoothAdapter.isEnabled())
            enableBluetoothOnDevice();

        mWifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        boolean isMobileEnabled = false;
        if (networkInfo != null) {
            isMobileEnabled = networkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
        }


        if (!mWifiManager.isWifiEnabled() && !isMobileEnabled)
          createNetErrorDialog();//crea una alerta para ir a los wifi settings



////////////////


        Button thisIsDoctor = (Button) findViewById(R.id.buttonDoctor);
        Button thisIsPatient = (Button) findViewById(R.id.buttonPatient);

        View.OnClickListener doctorLogin = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                Intent launchDoctorView = new Intent(MainActivity.this, loginPage.class);
                startActivity(launchDoctorView);


            }
        };

        thisIsDoctor.setOnClickListener(doctorLogin);

        View.OnClickListener patientLogin = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent launchPatient = new Intent(context, PatientHome.class);
                Intent launchPatientView = new Intent(MainActivity.this, PatientHome.class);
                //context.startActivity(launchPatient);
                startActivity(launchPatientView);
            }
        };

        thisIsPatient.setOnClickListener(patientLogin);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(this, "Aquí van los settings", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
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

    private void enableWifiOnDevice() {
        if (mWifiManager == null) {
            Log.e(LOG_TAG, "This device does not support wifi");
            finish();
            // If the android device does not have wifi, just return and get out.
        }

        mWifiManager.setWifiEnabled(true);
    }

    private void createNetErrorDialog() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("You need a network connection to use this application. Please turn on mobile network or Wi-Fi in Settings.")
                .setTitle(R.string.no_data)
                .setCancelable(false)
                .setNeutralButton(R.string.WIFI,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                enableWifiOnDevice();
                            }
                        }
                )
                .setPositiveButton(R.string.wireless_settings,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                enableWifiOnDevice();
                                Intent i = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                                startActivity(i);
                            }
                        }
                )
                .setNegativeButton(R.string.Cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                MainActivity.this.finish();
                            }
                        }
                );
        AlertDialog alert = builder.create();
        alert.show();
    }

}
