package fragment;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.daa.R;

import java.util.ArrayList;

import activity.MainActivity;
import list.CustomListGameAdapter;
import list.CustomListSubjectAdapter;
import modelz.DAO;
import modelz.Exam;
import modelz.Subject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListGameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListGameFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListGameFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListGameFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListGameFragment newInstance(String param1, String param2) {
        ListGameFragment fragment = new ListGameFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_game, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        refreshFragment();
    }

    public void refreshFragment(){
        ArrayList<Exam> arrayList = new ArrayList<>();
        int subectID = getArguments().getInt("subjectID");
        Cursor cursor = new DAO(getActivity()).getAllExamBySubjectID(subectID);
        while (cursor.moveToNext()) {
            Exam exam = new Exam(cursor.getInt(0),cursor.getString(1), cursor.getInt(2), cursor.getInt(3));
            arrayList.add(exam);
        }
        final ListView lv = (ListView) getActivity().findViewById(R.id.flg_listView);
        lv.setAdapter(new CustomListGameAdapter(getActivity(), arrayList));
    }
}