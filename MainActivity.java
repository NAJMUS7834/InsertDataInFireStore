package getways.solution.authenticationapp;

import android.bluetooth.BluetoothGattDescriptor;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

   //Identifying views
   private static final String TAG = "MainActivity";
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //finding Views by id
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerView);



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog();
            }
        });
        //Cheking user is authorized or not

        if(FirebaseAuth.getInstance().getCurrentUser() == null){
           StartLogin();
        }
    }

    //for Alert Dialog

    private void showAlertDialog() {
        final EditText noteEditText = new EditText(this);
        new AlertDialog.Builder(this)
                .setTitle("Add Note")
                .setView(noteEditText)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d(TAG, "onClick: " + noteEditText.getText());
                        addNote(noteEditText.getText().toString());
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    /************for Adding data*************/

    private void addNote(String text) {

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Note note = new Note(text, false, new Timestamp(new Date()), userId);

        FirebaseFirestore.getInstance()
                .collection("notes")
                .add(note)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "onSuccess: Succesfully added the note...");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: " + e.getLocalizedMessage() );
                        Toast.makeText(MainActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
/************************************************************/
    
    private void StartLogin(){
        Intent intent = new Intent(this,LoginRegister.class);
        startActivity(intent);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case R.id.action_logout:
               AuthUI.getInstance().signOut(this)
                       .addOnCompleteListener(new OnCompleteListener<Void>() {
                           @Override
                           public void onComplete(@NonNull Task<Void> task) {
                             if (task.isSuccessful()) {
                                 StartLogin();
                             }
                             else 
                             {
                                 Log.e(TAG, "onComplete: ",task.getException() );
                             }
                           }
                       });
                return  true;
            case R.id.action_register:
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
