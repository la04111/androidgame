package activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.daa.R;

import com.example.daa.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;

import fragment.ChooseTypeGameFragment;
import fragment.HomeFragment;
import fragment.ProfileFragment;
import fragment.RandomMathGameFragment;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
       setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());

        binding.amBottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bm_home:
                        replaceFragment(new HomeFragment());
                        break;
                    case R.id.bm_playGame:
                        replaceFragment(new ChooseTypeGameFragment());
                        break;
                    case R.id.bm_profile:
                        replaceFragment(new ProfileFragment());
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + item.getItemId());
                }
                return true;
            }
        });
        getSupportActionBar().hide();
    }

    private void replaceFragment (Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment1 = (Fragment) getSupportFragmentManager().findFragmentById(R.id.am_constraint1);
       if (fragment1 == null) {
           fragmentTransaction.replace(R.id.am_constraint1,fragment).commit();
       }
       else {
           fragmentTransaction.replace(R.id.am_constraint1,fragment).remove(fragment1).commit();
       }
    }

    public String getUserzName () {
        return getIntent().getStringExtra("userzName");
    }
    public String getUserzID () {
        return getIntent().getStringExtra("userzID");
    }
}