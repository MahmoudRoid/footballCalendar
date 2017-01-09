package mahmoud.footballcalendar.AsynTask;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;

import com.orm.query.Condition;
import com.orm.query.Select;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import mahmoud.footballcalendar.Classes.API;
import mahmoud.footballcalendar.Classes.Team;
import mahmoud.footballcalendar.Classes.URLS;
import mahmoud.footballcalendar.Database.db_Team;
import mahmoud.footballcalendar.Interface.IWebservice;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Mahmoud on 0019 8/19/2016.
 */
public class GetData extends AsyncTask<Void,Void,String> {

    public ArrayList<Team> teamArrayList;
    public Context context;
    private IWebservice delegate = null;
    public String MyTeamName;
    SweetAlertDialog pDialog ;
    public String Url;


    public GetData(Context context, IWebservice delegate,String team_name) {
        this.context = context;
        this.delegate = delegate;
        this.MyTeamName=team_name;
        pDialog= new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        getUrl(team_name);
    }// end GetData()

    public void getUrl(String faction){

        switch (faction){
            case "chelsea":
                this.Url= URLS.Chelsea;
                break;
            case "arsenal":
                this.Url= URLS.Arsenal;
                break;
            case "manutd":
                this.Url=URLS.ManUtd;
                break;
            case "barcelona":
                this.Url=URLS.Barcelona;
                break;
            case "realmadrid":
                this.Url=URLS.RealMadrid;
                break;
            case "bayern":
                this.Url=URLS.Bayern;
                break;

        }

    }

    public String get_api(){
        API api = new API();
        return api.random_api();
    }


    @Override
    protected void onPreExecute() {
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("در حال دریافت اطلاعات");
        pDialog.setCancelable(true);
        pDialog.show();
    }


    @Override
    protected String doInBackground(Void... params) {
        // TODO add header
        Response response = null;
        String strResponse = "nothing_got";
        for(int i=0;i<=9;i++){
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .addHeader("X-Auth-Token",get_api())
                        .addHeader("X-Response-Control","minified")
                        .url(this.Url)
                        .build();

                response = client.newCall(request).execute();
                strResponse= response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(response!=null) break;
        }
        return strResponse;
    }

    @Override
    protected void onPostExecute(String result) {
        pDialog.dismiss();

        if (result.equals("nothing_got")) {
            try {
                delegate.getError("NoData");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(!result.startsWith("{")){
            // moshkel dare kollan
            try {
                delegate.getError("problem");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        else {
            // delete database and fill it
            try {
                List<db_Team> list =  Select.from(db_Team.class)
                         .where(Condition.prop("MyTeamName").eq(MyTeamName))
                         .list();
                if(list.size()>0){
                    db_Team.deleteAll(db_Team.class, "MyTeamName = ?",MyTeamName);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                JSONObject mainObject = new JSONObject(result);
                if(mainObject.getInt("count")!=0){
                    // edame majara
                    JSONArray jsonArray = mainObject.getJSONArray("fixtures");
                    for (int i= 0;i<jsonArray.length();i++){
                        JSONObject object =  jsonArray.getJSONObject(i);
//                        public String TeamHomeName,TeamAwayname ,date ,time ,status;
//                        public int MatchDay,goalsHomeTeam,goalsAwayTeam;
                        String TeamHomeName = object.getString("homeTeamName");
                        String TeamAwayname = object.getString("awayTeamName");
                        String status = object.getString("status");
                        String date_time=object.getString("date");
                        int MatchDay=object.getInt("matchday");

                        JSONObject resultObject = object.getJSONObject("result");
                        int goalsHomeTeam= resultObject.getInt("goalsHomeTeam");
                        int goalsAwayTeam= resultObject.getInt("goalsAwayTeam");

                        Team team = new Team(MyTeamName,TeamHomeName,TeamAwayname,date_time.substring(0,9)
                                ,date_time.substring(11,15),status,MatchDay,goalsHomeTeam,
                                goalsAwayTeam);
                        teamArrayList.add(team);

                        // todo : add to db
                        db_Team dbTeam = new db_Team(MyTeamName,TeamHomeName,TeamAwayname,date_time.substring(0,9),
                                date_time.substring(11,15),status,MatchDay,goalsHomeTeam,goalsAwayTeam);
                        dbTeam.save();
                    }

                    if (teamArrayList.size()>0){
                        delegate.getResult(teamArrayList,this.MyTeamName);
                    }
                    else {
                        delegate.getError("error");
                    }
                }
                else {
                    delegate.getError("No-Game");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
