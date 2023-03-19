package fragment;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.daa.R;

import modelz.DAO;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChooseTypeGameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChooseTypeGameFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChooseTypeGameFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChooseTypeGameFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChooseTypeGameFragment newInstance(String param1, String param2) {
        ChooseTypeGameFragment fragment = new ChooseTypeGameFragment();
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
        return inflater.inflate(R.layout.fragment_choose_type_game, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        LinearLayout linearLayout = (LinearLayout) getActivity().findViewById(R.id.fctg_mainLayout);
        Cursor cursor = new DAO(getActivity()).getAllSubject();
        while (cursor.moveToNext()) {
            Button button = new Button(getActivity());
            button.setText(cursor.getString(1));
            button.setId(cursor.getInt(0));
            if (Integer.parseInt(String.valueOf(button.getId())) == 1) {
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        RandomMathGameFragment randomMathGameFragment = new RandomMathGameFragment();
                       Fragment fragment = (Fragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.am_constraint1);
                       fragmentTransaction.replace(R.id.am_constraint1,randomMathGameFragment).remove(fragment).commit();
                    }
                });
            }
            else  {
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("subjectID", Integer.parseInt(String.valueOf(button.getId())));
                        ListGameFragment listGameFragment = new ListGameFragment();
                        listGameFragment.setArguments(bundle);
                        Fragment fragment = (Fragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.am_constraint1);
                        fragmentTransaction.replace(R.id.am_constraint1,listGameFragment).remove(fragment).commit();
                    }
                });
            }
            linearLayout.addView(button);
        }
    }
}