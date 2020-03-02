package c.sakshi.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.Integer.parseInt;

public class AddNote extends AppCompatActivity {

    private EditText editView;

    private int noteid = -1;

    public AddNote(){

    }


    public void saveMethod(View view){
        //1
        String userName = "";
        EditText note = findViewById(R.id.note);
        String noteContent = note.getText().toString();


        //2
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes",
                Context.MODE_PRIVATE, null);

        //3
        DBHelper dbh = new DBHelper(sqLiteDatabase);

        //4
        String userNameKey = "username";
        SharedPreferences sharedPreferences =
                getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);
        if(!sharedPreferences.getString(userNameKey, "").equals("")) {
            userName = sharedPreferences.getString(userNameKey, "");
        }

        //5
        String title;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date= dateFormat.format(new Date());

        if (noteid == -1) { //Add Note
            title = "NOTE_" + (WelcomePage.notes.size() + 1);
            dbh.saveNotes(userName, title, noteContent,date);
        } else { //Update Note
            title = "NOTE_" + (noteid + 1);
            System.out.println(noteid+1);
            dbh.updateNote(title, date, noteContent, userName);

        }

        Intent intent = new Intent(this, WelcomePage.class);
        intent.putExtra("message", userName);
        startActivity(intent);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        editView = findViewById(R.id.note);
        Intent intent = getIntent();
        noteid = intent.getIntExtra("noteid", -1);
//        System.out.println(noteid);
        if(noteid != -1){
            Note note = WelcomePage.notes.get(noteid);
            String noteContent = note.getContent();
            editView = findViewById(R.id.note);
            editView.setText("" + noteContent);
        }

    }
}
