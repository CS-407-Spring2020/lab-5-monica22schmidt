package c.sakshi.lab5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class WelcomePage extends AppCompatActivity {

    TextView textView;
    SharedPreferences sharedPreferences;
    public static ArrayList<Note> notes;


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch(item.getItemId()){
            case R.id.logout:
                Intent intent = new Intent(this, MainActivity.class);
                sharedPreferences = getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);
                sharedPreferences.edit().remove("username").apply();
                startActivity(intent);
                return true;
            case R.id.aNote:
                Intent intent2 = new Intent(this, AddNote.class);
                startActivity(intent2);
                return true;
            default: return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        //1
        textView = findViewById(R.id.textView);
        Intent intent = getIntent();
        String str = intent.getStringExtra("message");
        textView.setText("Welcome " + str + "!");
        String userNameKey = "username";
        sharedPreferences =
                getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);
        if(!sharedPreferences.getString(userNameKey, "").equals("")) {
            String userName = sharedPreferences.getString(userNameKey, "");
            textView.setText("Welcome " + userName + "!");
        }
        //2
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes",
                Context.MODE_PRIVATE, null);

        //3
        DBHelper dbh = new DBHelper(sqLiteDatabase);
        notes = dbh.readNotes(str);

        //4
        ArrayList<String> displayNotes = new ArrayList<>();
        for(int i = 0; i < notes.size(); i++){
//            System.out.println(notes.get(i));
            displayNotes.add(String.format("Title:%s\nDate:%s", notes.get(i).gettitle(), notes.get(i).getDate()));
        }

        //5
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, displayNotes);
        ListView listView = findViewById(R.id.notesList);
        listView.setAdapter(adapter);

        //6
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), AddNote.class);
//                System.out.println(position);
                intent.putExtra("noteid", position);
                startActivity(intent);
            }
        });
    }
}
