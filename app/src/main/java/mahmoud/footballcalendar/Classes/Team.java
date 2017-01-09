package mahmoud.footballcalendar.Classes;

/**
 * Created by Mahmoud on 0025 12/25/2015.
 */
public class Team {
    public String MyTeamName,TeamHomeName,TeamAwayname ,date ,time ,status;
    public int MatchDay,goalsHomeTeam,goalsAwayTeam;
    public Team(String MyTeamName,String TeamHomeName,String TeamAwayname, String date, String time,
                String status,int MatchDay,int goalsHomeTeam,int goalsAwayTeam){
        this.MyTeamName=MyTeamName;
        this.TeamHomeName=TeamHomeName;
        this.TeamAwayname=TeamAwayname;
        this.date=date;
        this.time=time;
        this.status=status;
        this.MatchDay=MatchDay;
        this.goalsHomeTeam=goalsHomeTeam;
        this.goalsAwayTeam=goalsAwayTeam;
    }

    public String getMyTeamName(){return MyTeamName;}
    public String getTeamHomeName(){return TeamHomeName;}
    public String getTeamAwayname(){return TeamAwayname;}
    public String getDate(){return date;}
    public String getTime(){return time;}
    public String getStatus(){return this.status;}
    public int getMatchDay(){return MatchDay;}
    public int getGoalsHomeTeam(){return goalsHomeTeam;}
    public int getGoalsAwayTeam(){return goalsAwayTeam;}
}
