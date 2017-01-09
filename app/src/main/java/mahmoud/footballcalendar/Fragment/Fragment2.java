package mahmoud.footballcalendar.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mahmoud.footballcalendar.Activity.MainActivity;
import mahmoud.footballcalendar.Activity.MatchResultActivity;
import mahmoud.footballcalendar.AsynTask.GetData;
import mahmoud.footballcalendar.Classes.Internet;
import mahmoud.footballcalendar.Classes.Team;
import mahmoud.footballcalendar.Database.db_Team;
import mahmoud.footballcalendar.Interface.IWebservice;
import mahmoud.footballcalendar.R;

/**
 * Created by Mahmoud on 0001 9/1/2016.
 */
public class Fragment2 extends Fragment implements IWebservice {

    @BindView(R.id.match_recycler)
    RecyclerView matchRecycler;
    private ViewGroup layout;
    View snack_view;
//    private ImageCategoryAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public ArrayList<Team> teamArrayList;

    public Fragment2() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        layout = (ViewGroup) inflater.inflate(R.layout.fragment2, container, false);
        ButterKnife.bind(this, layout);
        return layout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            init();
    }

    public void init() {
        List<db_Team> list = Select.from(db_Team.class)
                .where(Condition.prop("MyTeamName").eq(MatchResultActivity.teamName_static)
                ,Condition.prop("status").notEq("FINISHED"))
                .list();
        if (list.size() > 0) {
            // show from db
            // todo : convert to arraylist and show

        }
        else {
            Snackbar snackbar = Snackbar
                    .make(getActivity().findViewById(R.id.RelativeLayout), "هیچ مسابقه ای وجود ندارد", Snackbar.LENGTH_LONG);
            snack_view = snackbar.getView();
            snack_view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            TextView tv = (TextView) snack_view.findViewById(android.support.design.R.id.snackbar_text);
            tv.setTextColor(Color.WHITE);
            snackbar.show();
        }
    }


    @Override
    public void getResult(Object result,String Tag) throws Exception {
        teamArrayList = (ArrayList<Team>)result;
        // todo check shavad oonhae k finished nashodand beravand
    }

    @Override
    public void getError(String ErrorCodeTitle) throws Exception {
        Snackbar snackbar = Snackbar
                .make(getActivity().findViewById(R.id.RelativeLayout), "داده ای دریافت نشد ، مجددا تلاش نمایید", Snackbar.LENGTH_LONG);
        snack_view = snackbar.getView();
        snack_view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        TextView tv = (TextView) snack_view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);
        snackbar.show();
    }


    public void showList(ArrayList<Team> arrayList) {

        mLayoutManager = new LinearLayoutManager(getActivity());
        matchRecycler.setLayoutManager(mLayoutManager);

//        mAdapter = new ImageCategoryAdapter(arrayList);
//        matchRecycler.setAdapter(mAdapter);

    }


}