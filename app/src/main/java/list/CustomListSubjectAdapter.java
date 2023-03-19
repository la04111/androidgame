package list;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.daa.R;

import java.util.ArrayList;

import modelz.DAO;
import modelz.Exam;
import modelz.Subject;

public class CustomListSubjectAdapter extends BaseAdapter {
    private ArrayList<Subject> listData;
    private LayoutInflater layoutInflater;
    Context context;

    public CustomListSubjectAdapter(Context aContext, ArrayList<Subject> listData) {
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
        ViewHolder holder;
        if (v == null) {
            v = layoutInflater.inflate(R.layout.list_item_subject, null);
            holder = new ViewHolder();
            holder.uName = (TextView) v.findViewById(R.id.lis_subjectName);
           holder.button = (Button) v.findViewById(R.id.lis_updateSubject);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        holder.uName.setText(listData.get(position).getSubjectName());
        holder.button.setContentDescription(String.valueOf(listData.get(position).getSubjectID()));
        if (listData.get(position).getSubjectID() == 1 || listData.get(position).getSubjectID() == 2 || listData.get(position).getSubjectID() == 3) {
            holder.button.setVisibility(View.INVISIBLE);
        }
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setCancelable(true);
                LayoutInflater inflater = layoutInflater;
                View dialogView = inflater.inflate(R.layout.inflater_subject, null);
                dialog.setView(dialogView);
                TextView is_subjectName = (TextView) dialogView.findViewById(R.id.is_subjectName);
                TextView subjectID = (TextView) dialogView.findViewById(R.id.is_subjectID);
                Cursor cursor = new DAO(context).findSubject(Integer.parseInt(String.valueOf(holder.button.getContentDescription())));
                while (cursor.moveToNext()) {
                    is_subjectName.setText(cursor.getString(1));
                    subjectID.setText(String.valueOf(cursor.getInt(0)));
                }
                Button is_insertOrUpdate = (Button) dialogView.findViewById(R.id.is_insertOrUpdate);
                is_insertOrUpdate.setText("Cap Nhat");
                is_insertOrUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int i = new DAO(context).updateSubject(Integer.parseInt(subjectID.getText().toString()),is_subjectName.getText().toString());
                        if (i == 1) {
                            Toast.makeText(context, "Update successful", Toast.LENGTH_LONG).show();
                            refreshFragment();
                        }
                        else {
                            Toast.makeText(context, "Update failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                dialog.show();
            }
        });
        return v;
    }

    public void refreshFragment(){
        ArrayList<Subject> arrayList = new ArrayList<>();
        Cursor cursor = new DAO(context).getAllSubject();
        while (cursor.moveToNext()) {
            Subject subject = new Subject(cursor.getInt(0),cursor.getString(1));
            arrayList.add(subject);
        }
        Activity activity = (Activity) context;
        final ListView lv = (ListView) activity.findViewById(R.id.fs_listView);
        lv.setAdapter(new CustomListSubjectAdapter(context, arrayList));
    }

    static class ViewHolder {
        TextView uName;
        TextView uState;
        Button button;
    }

}
