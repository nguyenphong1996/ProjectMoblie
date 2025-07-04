package com.example.android_project.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.android_project.R;

import com.example.android_project.fragment.HomeFragment;
import com.example.android_project.fragment.CartFragment;
import com.example.android_project.fragment.NotificationFragment;
import com.example.android_project.fragment.ProfileFragment;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    // Khai báo các Fragment của bạn
    // Bạn nên khởi tạo chúng một lần và tái sử dụng để giữ trạng thái
    final Fragment fragmentHome = new HomeFragment(); // Thay thế bằng Fragment của bạn
    final Fragment fragmentDashboard = new CartFragment(); // Thay thế bằng Fragment của bạn
    final Fragment fragmentNotifications = new NotificationFragment(); // Thay thế bằng Fragment của bạn
    final Fragment fragmentProfile = new ProfileFragment();
    // ... Khai báo các fragment khác nếu có

    private Fragment activeFragment = fragmentHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bottomNavigationView = findViewById(R.id.bottom_navigation);


        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.fragment_container, fragmentNotifications, "3").hide(fragmentNotifications).commit();
        fragmentManager.beginTransaction().add(R.id.fragment_container, fragmentDashboard, "2").hide(fragmentDashboard).commit();

        fragmentManager.beginTransaction().add(R.id.fragment_container, fragmentHome, "1").commit();


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                String tag = "";

                int itemId = item.getItemId();
                if (itemId == R.id.bottom_navigation) {
                    selectedFragment = fragmentHome;
                    tag = "1";
                } else if (itemId == R.id.bottom_navigation) {
                    selectedFragment = fragmentDashboard;
                    tag = "2";
                } else if (itemId == R.id.bottom_navigation) {
                    selectedFragment = fragmentNotifications;
                    tag = "3";
                } else{
                    selectedFragment = fragmentProfile;
                    tag = "4";
                }

                if (selectedFragment != null && selectedFragment != activeFragment) {
                    // Ẩn fragment hiện tại, hiển thị fragment mới
                    fragmentManager.beginTransaction().hide(activeFragment).show(selectedFragment).commit();
                    activeFragment = selectedFragment;
                    return true;
                }
                return false;
            }
        });

        // Đặt mục được chọn mặc định (nếu cần)
        // bottomNavigationView.setSelectedItemId(R.id.navigation_home); // Ví dụ
    }

    // (Optional) Bạn có thể dùng phương thức này nếu muốn chuyển Fragment từ nơi khác
    private void loadFragment(Fragment fragment) {
        if (fragment != null && fragment != activeFragment) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .hide(activeFragment)
                    .show(fragment)
                    .commit();
            activeFragment = fragment;
        }
    }
}