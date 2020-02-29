package c.sakshi.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {


    SharedPreferences sharedPreferences;
    public  String userNameKey;

    public void clickFunction(View view){
        EditText userName = findViewById(R.id.userName);
        String usestr = userName.getText().toString();
        EditText password = findViewById(R.id.password);
        String passstr = password.getText().toString();

        sharedPreferences =
                getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("username", usestr).apply();

        goToWelcome(usestr, passstr);
    }

    public void goToWelcome(String user, String pass) {
        Intent intent = new Intent(this, WelcomePage.class);
        intent.putExtra("message", user);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userNameKey = "username";

        sharedPreferences =
                getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);
        if(!sharedPreferences.getString(userNameKey, "").equals("")){
            String userName = sharedPreferences.getString(userNameKey, "");
            Intent intent = new Intent(this, WelcomePage.class);
            intent.putExtra("message", userName);
            startActivity(intent);
        }else{
            setContentView(R.layout.activity_main);
        }
    }
}
