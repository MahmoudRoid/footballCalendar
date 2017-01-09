package mahmoud.footballcalendar.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import mahmoud.footballcalendar.AsynTask.GetData;
import mahmoud.footballcalendar.Classes.AutoImageFlipper;
import mahmoud.footballcalendar.Classes.Internet;
import mahmoud.footballcalendar.Database.db_Team;
import mahmoud.footballcalendar.Interface.IWebservice;
import mahmoud.footballcalendar.R;

public class MainActivity extends AppCompatActivity implements IWebservice {

    @BindView(R.id.chelsea)
    ImageView chelsea_imgview;
    @BindView(R.id.manutd)
    ImageView manchester_imgview;
    @BindView(R.id.arsenal)
    ImageView arsenal_imgview;
    @BindView(R.id.barcelona)
    ImageView barcelona_imgview;
    @BindView(R.id.realmadrid)
    ImageView realmadrid_imgview;
    @BindView(R.id.bayern)
    ImageView bayern_imgview;
    @BindView(R.id.horizontalScrollView)
    HorizontalScrollView horizontalScrollView;
    @BindView(R.id.RelativeLayout)
    RelativeLayout relativeLayout;

    View snack_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();

        ArrayList<Drawable> drawables = new ArrayList<>();
        drawables.add(ContextCompat.getDrawable(this, R.drawable.hazard));
        drawables.add(ContextCompat.getDrawable(this, R.drawable.splash_2));

        AutoImageFlipper autoImageFlipper = (AutoImageFlipper) findViewById(R.id.autoImageFlipper);
        autoImageFlipper.setDrawables(drawables);
//        autoImageFlipper.setAspectRatio(0.75);
        // then, i'm going to add a new drawable to flipping list
        // this drawable can be something received from the internet
        drawables.add(ContextCompat.getDrawable(this, R.drawable.splash_3));
        // OR : autoImageFlipper.addDrawable(ContextCompat.getDrawable(this, R.drawable.sample3));

    }

    private void init() {
        horizontalScrollView.setVisibility(View.GONE);
    }

    @OnClick({R.id.chelsea, R.id.manutd, R.id.arsenal, R.id.barcelona, R.id.realmadrid, R.id.bayern, R.id.horizontalScrollView, R.id.RelativeLayout})
    public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, MatchResultActivity.class);
        switch (view.getId()) {
            case R.id.chelsea:
                goto_next_activity("chelsea");
                break;
            case R.id.manutd:
                goto_next_activity("manutd");
                break;
            case R.id.arsenal:
                goto_next_activity("arsenal");
                break;
            case R.id.barcelona:
                goto_next_activity("barcelona");;
                break;
            case R.id.realmadrid:
                goto_next_activity("realmadrid");
                break;
            case R.id.bayern:
                goto_next_activity("bayern");
                break;
            case R.id.horizontalScrollView:
                break;
            case R.id.RelativeLayout:
                if (horizontalScrollView.getVisibility() == View.VISIBLE) {
                    // gone shavad
                    horizontalScrollView.setVisibility(View.GONE);
                } else if (horizontalScrollView.getVisibility() == View.GONE) {
                    // visible
                    horizontalScrollView.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    public void goto_next_activity(String teamname){
        List<db_Team> list =  Select.from(db_Team.class)
                .where(Condition.prop("MyTeamName").eq(teamname))
                .list();
        if(list.size()>0){
            start_next_activity(teamname);
        }
        else {

            if(Internet.isNetworkAvailable(MainActivity.this)){
                GetData getData = new GetData(MainActivity.this, MainActivity.this,teamname);
                getData.execute();
            }
            else {
                Snackbar snackbar = Snackbar
                        .make(findViewById(R.id.RelativeLayout), "دسترسی اینترنت خود را بررسی نمایید", Snackbar.LENGTH_LONG);
                snack_view = snackbar.getView();
                snack_view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                TextView tv = (TextView) snack_view.findViewById(android.support.design.R.id.snackbar_text);
                tv.setTextColor(Color.WHITE);
                snackbar.show();
            }

        }
    }

    public void start_next_activity(String teamname)
    { Intent intent = new Intent(MainActivity.this, MatchResultActivity.class);
        switch (teamname){
            case "chelsea":
                intent.putExtra("team_name", "chelsea");
                startActivity(intent);
                break;
            case "manutd":
                intent.putExtra("team_name", "manutd");
                startActivity(intent);
                break;
            case "arsenal":
                intent.putExtra("team_name", "arsenal");
                startActivity(intent);
                break;
            case "barcelona":
                intent.putExtra("team_name", "barcelona");
                startActivity(intent);
                break;
            case "realmadrid":
                intent.putExtra("team_name", "realmadrid");
                startActivity(intent);
                break;
            case "bayern":
                intent.putExtra("team_name", "bayern");
                startActivity(intent);
                break;
        }
    }

    @Override
    public void getResult(Object result, String Tag) throws Exception {
        start_next_activity(Tag);
    }

    @Override
    public void getError(String ErrorCodeTitle) throws Exception {
        String error_kind="";
        if(ErrorCodeTitle.equals("No-Game")){
            error_kind="هیچ مسابقه ای برای نمایش وجود ندارد";
        }
        else error_kind="داده ای دریافت نشد ، مجددا تلاش نمایید";


        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.RelativeLayout),error_kind , Snackbar.LENGTH_LONG);
        snack_view = snackbar.getView();
        snack_view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        TextView tv = (TextView) snack_view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);
        snackbar.show();
    }

    @Override
    public void onBackPressed() {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("اخطار")
                .setContentText("مایل به خروج از برنامه هستید ؟")
                .setConfirmText("بله")
                .setCancelText("خیر")
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog dd) {
                        dd.dismiss();
                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        moveTaskToBack(true);
                        finish();
                        System.exit(0);
                    }
                })
                .show();
    }
}