package julioexample.wiisel;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * Created by mariapb on 19/02/17.
 */

public class loginPage extends Activity {
    boolean login = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final EditText setUser = (EditText)findViewById(R.id.doctorLogin);
        final EditText setPassword = (EditText)findViewById(R.id.doctorPassword);

        View.OnClickListener loginListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user = setUser.getText().toString();
                String password = setPassword.getText().toString();

                if(user.equals("admin")&&password.equals("admin"))
                    login = true;

            }
        };
    }
}
