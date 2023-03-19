package activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.daa.R;
import com.google.android.material.navigation.NavigationView;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        getSupportActionBar().hide();

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.activity_admin);
        ImageView imageView = (ImageView) findViewById(R.id.am_imageMenu);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        NavigationView navigationView = (NavigationView) findViewById(R.id.am_navigationView);
        navigationView.setItemIconTintList(null);

        NavController navController = Navigation.findNavController(this, R.id.am_navHostFragment);
        NavigationUI.setupWithNavController(navigationView, navController);

        TextView textView = (TextView) findViewById(R.id.am_textTitle);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
                textView.setText(navDestination.getLabel());
            }
        });
    }

    public String getUserzName () {
        return getIntent().getStringExtra("userzName");
    }
    public String getUserzID () {
        return getIntent().getStringExtra("userzID");
    }

    public void logout() {
        startActivity(new Intent(AdminActivity.this, LoginActivity.class));
    }
}