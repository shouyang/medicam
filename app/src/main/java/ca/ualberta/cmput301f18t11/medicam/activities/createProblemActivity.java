package ca.ualberta.cmput301f18t11.medicam.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

import ca.ualberta.cmput301f18t11.medicam.R;
import ca.ualberta.cmput301f18t11.medicam.controllers.ElasticSearchController;
import ca.ualberta.cmput301f18t11.medicam.controllers.abstracts.PersistenceController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.ProblemPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.models.Problem;

public class createProblemActivity extends AppCompatActivity {

    private EditText problemTitle;
    private EditText problemDescription;
    private PersistenceController<Problem> problemControler = new ProblemPersistenceController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_problem);
        ElasticSearchController.setIndex_url("cmput301f18t11test");

        problemTitle = (EditText) findViewById(R.id.editText2);
        problemDescription = (EditText) findViewById(R.id.probDescription);
    }

    //Creates a new problem object with the filled in user-data
    //Todo: Save the problem to file and elasticsearch
    public void createProblem(View view){
        if (problemTitle.getText().toString().equals("")){Toast.makeText(createProblemActivity.this,"Please Enter a title",Toast.LENGTH_SHORT).show();
        }
        else if (problemDescription.getText().toString().equals("")){Toast.makeText(createProblemActivity.this,"Please Enter a Description",Toast.LENGTH_SHORT).show();

        } else {
            Intent intent = new Intent();
            Problem newProblem = new Problem(problemTitle.getText().toString(), new Date(), problemDescription.getText().toString());
            problemControler.save(newProblem,this);
            intent.putExtra("newProblem", newProblem);
            setResult(RESULT_OK, intent);
            finish();
        }
    }


}