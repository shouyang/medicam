package ca.ualberta.cmput301f18t11.medicam.activities;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import ca.ualberta.cmput301f18t11.medicam.R;
import ca.ualberta.cmput301f18t11.medicam.controllers.GeolocationController;
import ca.ualberta.cmput301f18t11.medicam.controllers.abstracts.PersistenceController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.PatientPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.ShortIDPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.models.Patient;
import ca.ualberta.cmput301f18t11.medicam.models.ShortID;

public class ProfileEditting extends AppCompatActivity {
    private static final int ADD_BODYLOCATION_REQUEST_CODE = 1;

    private TextView userId;
    private EditText enteredPhoneNumber;
    private EditText enteredEmail;
    private Patient patient;
    private PersistenceController<Patient> patientController = new PatientPersistenceController();
    private ShortIDPersistenceController shortIDController = new ShortIDPersistenceController();

    private Button bodyLocationButton;
    private ImageButton displayLocationsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_editting);

        //Toolbar Setup
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.profileEditing_toolbar);
        setSupportActionBar(toolbar);
        TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setText("Edit Profile");// Sets the title to be shown in the toolbar

        userId = findViewById(R.id.userIDTextView);
        enteredPhoneNumber = findViewById(R.id.editingPhoneNumberText);
        enteredEmail = findViewById(R.id.editingEmailText);
        bodyLocationButton = findViewById(R.id.profileEdittingBodyLocation);
        displayLocationsButton = findViewById(R.id.displayAllRecordLocationsButton);

        Intent intent = getIntent();
        String userUUID = intent.getStringExtra("USERUUID");
        patient = patientController.load(userUUID,this);

        userId.setText(patient.getUserID());
        enteredPhoneNumber.setText(patient.getPhoneNumber());
        enteredEmail.setText(patient.getEmail());

        bodyLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileEditting.this,
                        BodyLocationListActivity.class);
                intent.putExtra("patient", patient.getUuid());
                intent.putExtra("mode", "edit");
                startActivity(intent);
            }
        });

        displayLocationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = GeolocationController.viewLocations(ProfileEditting.this, patient);
                startActivity(intent);
            }
        });
    }

    public void finishEditing(View view){
        patient.setPhoneNumber(enteredPhoneNumber.getText().toString());
        patient.setEmail(enteredEmail.getText().toString());
        patientController.save(patient,this);
        finish();
    }

    public void generateShortID (View view){
        ShortID shortID = new ShortID(patient.getUuid());
        TextView shortIDView = findViewById(R.id.short_id_textView);
        shortIDView.setText(shortID.getUuid());
        shortIDController.save(shortID,this);

    }
}
