package mahmoud.footballcalendar.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import mahmoud.footballcalendar.Classes.Team;
import mahmoud.footballcalendar.R;

/**
 * Created by Mahmoud on 0001 9/1/2016.
 */
public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.DataObjectHolder> {

    public ArrayList<Team> teamArrayList;

    public MatchAdapter(ArrayList<Team> arrayList){
        this.teamArrayList=arrayList;
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder{

        //  CustomTextView tv_title;
        TextView date,time,status,home_team,away_team;

        public DataObjectHolder(View itemView){
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.match_date);
            time = (TextView) itemView.findViewById(R.id.match_time);
            status = (TextView) itemView.findViewById(R.id.match_status);
            home_team = (TextView) itemView.findViewById(R.id.match_hometeam);
            away_team = (TextView) itemView.findViewById(R.id.match_awayteam);
        }

    }

    @Override
    public MatchAdapter.DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.match_item,parent,false);
        return new DataObjectHolder(view);
    }

    @Override
    public void onBindViewHolder(MatchAdapter.DataObjectHolder holder, int position) {
         holder.date.setText(teamArrayList.get(position).getDate());
        holder.time.setText(teamArrayList.get(position).getTime());
        holder.status.setText(teamArrayList.get(position).getMatchDay());
        holder.home_team.setText(teamArrayList.get(position).getTeamHomeName());
        holder.away_team.setText(teamArrayList.get(position).getTeamAwayname());
    }

    @Override
    public int getItemCount() {
        return teamArrayList.size();
    }

}
