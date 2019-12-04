
package info.camposha.firebasedatabasecrud.Views;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.appcompat.app.AppCompatActivity;

import info.camposha.firebasedatabasecrud.Helpers.Utils;
import info.camposha.firebasedatabasecrud.Login;
import info.camposha.firebasedatabasecrud.R;
import info.camposha.firebasedatabasecrud.Register;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class DashboardActivity extends AppCompatActivity {

    //We have 4 cards in the dashboard
    private LinearLayout viewScientistsCard;
    private LinearLayout addScientistCard;
    private LinearLayout third;
    private LinearLayout closeCard;

    /**
     * Let's initialize our cards  and listen to their click events
     */
    private void initializeWidgets(){
        viewScientistsCard = findViewById(R.id.viewScientistsCard);
        addScientistCard = findViewById(R.id.addScientistCard);
        third = findViewById(R.id.third);
        closeCard = findViewById(R.id.closeCard);

        viewScientistsCard.setOnClickListener(v -> Utils.openActivity(DashboardActivity.this,
        ScientistsActivity.class));
        addScientistCard.setOnClickListener(v -> Utils.openActivity(DashboardActivity.this,
        CRUDActivity.class));
        third.setOnClickListener(v -> Utils.openActivity(DashboardActivity.this,
                Register.class));
        closeCard.setOnClickListener(v -> finish());
    }
    /**
     * Let's override the attachBaseContext() method
     */
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    /**
     * When the back button is pressed finish this activity
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    /**
     * Let's override the onCreate() and call our initializeWidgets()
     */

    /*TextView fullName,email,phone;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);



        this.initializeWidgets();
    }


    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();//logout
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }

}
//end