package com.lawdogstudio.kyle.sharedpreferences;
/**
 * This is a template for the shared preferences way of persisting data. This method of persisting
 * data is meant only for primitive data types. More complex constructs, like classes and databases,
 * need to be saved either via a file or database.
 */

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Global variables
    SharedPreferences mSharedPreferences;
    EditText mEditText;
    Button deleteButton;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * This is where the shared preferences object is instantiated, giving it a file name
         * You can think of this as the file that holds all the variables being saved
         * The this.MODE_PRIVATE makes the preference file visible only to this app
         */
        mSharedPreferences = this.getSharedPreferences("EXAMPLE_SHARED_PREFERENCES", this.MODE_PRIVATE);

        mEditText = (EditText) findViewById(R.id.editText);

        //Instantiate the UI widgets
        deleteButton = (Button) findViewById(R.id.button2);
        //Delete the data from the shared preferences
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Make an editor so that the preferences can be edited
                SharedPreferences.Editor editor = mSharedPreferences.edit();

                //Remove the key from the file, since this is the key that the app checks for
                //When it checks if there is data, the app will default to its starting screen
                //Like before the user entered anything
                editor.remove("key");

                //This commits the action done with the editor to the file, apply() can be used instead
                //to run it in the background but because this is one key of a very small size
                //commit() is no big deal.
                editor.commit();

                //Clear the EditText field to show the user the data has been deleted
                mEditText.setText(null);

                //A notification saying the same. This and the .setText(null) are ways to make the
                //user experience better. Never let them guess at whether the app did something or not
                Toast.makeText(getApplicationContext(), "Shared Preferences Cleared", Toast.LENGTH_SHORT).show();
            }
        });


        saveButton = (Button) findViewById(R.id.button);
        //Take the text from the EditText field and save it
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Make an editor so that the preferences can be edited
                SharedPreferences.Editor editor = mSharedPreferences.edit();

                /**
                 * Take the text from the field and add it to the app's shared preferences file
                 * This puts a string variable but there is also putInt, putStringSet, putBoolean
                 * putLong, putFloat available. The first argument is naming the variable in the file
                 * the second is the data itself. editor.commit() saves the data given to the editor
                 * to the file so multiple instances of editor.putWhateverVariable can be called
                 * before commit. editor.apply() can also be used but is done in the background
                 * so this should be used for larger data sets.
                 */

                editor.putString("key", mEditText.getText().toString());

                editor.commit();
                Toast.makeText(getApplicationContext(), "Text Saved to Shared Preferences", Toast.LENGTH_SHORT).show();
            }
        });

        //We'll check for saved data here, because the EditText widget and Shared Preferences variables
        //are used in the method, we need them made then passed to the method
        checkForSavedData(mSharedPreferences, mEditText);

    }

    private void checkForSavedData(SharedPreferences passedSharedPreference, EditText editableText){

        //This checks for existing data in the file by checking for the variable named key
        if(passedSharedPreference.contains("key")){

            /**
             * If true, change the text field to the text that was saved, the getString must have
             * the name of the key in order to pull its value and a default string if the value is
             * null
             */
            editableText.setText(passedSharedPreference.getString("key", "Default String"));
        }
    }
}
