package list;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.daa.R;

import java.util.ArrayList;

import activity.MainActivity;
import fragment.GameFragment;
import fragment.ListGameFragment;
import modelz.DAO;
import modelz.Exam;

public class CustomListGameAdapter  extends BaseAdapter {
    private ArrayList<Exam> listData;
    private LayoutInflater layoutInflater;
    Context context;

    public CustomListGameAdapter(Context aContext, ArrayList<Exam> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
        this.context = aContext;
    }
    @Override
    public int getCount() {
        return listData.size();
    }
    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    public View getView(int position, View v, ViewGroup vg) {
        CustomListGameAdapter.ViewHolder holder;
        if (v == null) {
            v = layoutInflater.inflate(R.layout.list_item_game, null);
            holder = new CustomListGameAdapter.ViewHolder();
            holder.uName = (TextView) v.findViewById(R.id.lig_gameName);
            holder.uScore = (TextView) v.findViewById(R.id.lig_gameScore);
            holder.button = (Button) v.findViewById(R.id.lig_playGame);
            v.setTag(holder);
        } else {
            holder = (CustomListGameAdapter.ViewHolder) v.getTag();
        }
        holder.uName.setText(listData.get(position).getExamName());
        MainActivity mainActivity = (MainActivity) context;
        int userzID =  Integer.parseInt(mainActivity.getUserzID());
        Cursor cursor = new DAO(context).findScoreBoard2(userzID, listData.get(position).getExamID());
        holder.uScore.setText(String.valueOf(0));
        while (cursor.moveToNext()) {
            if (String.valueOf(cursor.getInt(2)) != null) {
                holder.uScore.setText(String.valueOf(cursor.getInt(2)));
            }
        }
        Cursor cursor1 = new DAO(context).getAllQuestionByExamID(listData.get(position).getExamID());
        if (cursor1.getCount() <= 0) {
            holder.button.setVisibility(View.INVISIBLE);
        }
        holder.button.setContentDescription(String.valueOf(listData.get(position).getExamID()));
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = mainActivity.getSupportFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putInt("examID", Integer.parseInt(String.valueOf(holder.button.getContentDescription())));
                GameFragment gameFragment = new GameFragment();
                gameFragment.setArguments(bundle);
                Fragment fragment = (Fragment) mainActivity.getSupportFragmentManager().findFragmentById(R.id.am_constraint1);
                fragmentTransaction.replace(R.id.am_constraint1,gameFragment).remove(fragment).commit();
            }
        });
        return v;
    }

    static class ViewHolder {
        TextView uName;
        TextView uScore;
        Button button;
    }

}