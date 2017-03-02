package julioexample.wiisel;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

/**
 * Created by mariapb on 21/02/17.
 */

public class PatientHome extends Activity {

    int MY_PERMISSIONS_CALL_PHONE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_home);
        Button emergencyButton = (Button) findViewById(R.id.emergencyButton);


        View.OnClickListener emergency = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri callUri = Uri.parse("tel://112");
                Intent callIntent = new Intent(Intent.ACTION_CALL, callUri);
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_USER_ACTION);
                try {
                    startActivity(callIntent);
                } catch (Exception e) {
                    ActivityCompat.requestPermissions(PatientHome.this,
                            new String[]{Manifest.permission.CALL_PHONE},
                            MY_PERMISSIONS_CALL_PHONE);
                    return;

                }
            }
        };

        emergencyButton.setOnClickListener(emergency);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
