package mahmoud.footballcalendar.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import mahmoud.footballcalendar.R;

public class TeamActivity extends AppCompatActivity {

    public String team_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getTeam();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void getTeam() {
        // TODO : set toolbar name
        switch (getIntent().getExtras().getString("team")){
            case "chelsea":
                this.team_name="chelsea";
                break;
            case "manutd":
                this.team_name="manutd";
                break;
            case "arsenal":
                this.team_name="arsenal";
                break;
            case "realmadrid":
                this.team_name="realmadrid";
                break;
            case "barcelona":
                this.team_name="barcelona";
                break;
            case "bayern":
                this.team_name="bayern";
                break;
        }
    }


}
