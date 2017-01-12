package mahmoud.footballcalendar.Database;

import com.orm.SugarRecord;


public class db_Team extends SugarRecord {
    public String myteamname,teamhomename,teamawayname ,date ,time ,status;
    public int matchday,goalshometeam,goalsawayteam;

    public db_Team(){}

    public db_Team(String myteamname,String teamhomename,String teamawayname, String date, String time,
                String status,int matchday,int goalshometeam,int goalsawayteam){
        this.myteamname=myteamname;
        this.teamhomename=teamhomename;
        this.teamawayname=teamawayname;
        this.date=date;
        this.time=time;
        this.status=status;
        this.matchday=matchday;
        this.goalshometeam=goalshometeam;
        this.goalsawayteam=goalsawayteam;
    }

    public String getMyTeamName(){return myteamname;}
    public String getTeamHomeName(){return teamhomename;}
    public String getTeamAwayname(){return teamawayname;}
    public String getDate(){return date;}
    public String getTime(){return time;}
    public String getStatus(){return this.status;}
    public int getMatchDay(){return matchday;}
    public int getGoalsHomeTeam(){return goalshometeam;}
    public int getGoalsAwayTeam(){return goalsawayteam;}
}
