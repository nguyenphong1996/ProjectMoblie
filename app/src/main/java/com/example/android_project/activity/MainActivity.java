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


    final Fragment fragmentHome = new HomeFragment();
    final Fragment fragmentCart = new CartFragment();
    final Fragment fragmentNotifications = new NotificationFragment();
    final Fragment fragmentProfile = new ProfileFragment();

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
        fragmentManager.beginTransaction().add(R.id.fragment_container, fragmentProfile, "4").hide(fragmentProfile).commit();
        fragmentManager.beginTransaction().add(R.id.fragment_container, fragmentNotifications, "3").hide(fragmentNotifications).commit();
        fragmentManager.beginTransaction().add(R.id.fragment_container, fragmentCart, "2").hide(fragmentCart).commit();

        fragmentManager.beginTransaction().add(R.id.fragment_container, fragmentHome, "1").commit();


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                // String tag = ""; // Biến tag có vẻ không được sử dụng ở đây, bạn có thể bỏ đi nếu không cần thiết

                int itemId = item.getItemId();

                // Sử dụng switch-case để xử lý các mục menu
                // **QUAN TRỌNG:** Thay thế R.id.navigation_home, R.id.navigation_cart, v.v.
                // bằng các ID thực tế từ file menu XML của bạn.
                if (itemId == R.id.nav_home) { // Giả sử ID cho Home là navigation_home
                    selectedFragment = fragmentHome;
                    // tag = "1";
                } else if (itemId == R.id.nav_cart) { // Giả sử ID cho Cart là navigation_cart
                    selectedFragment = fragmentCart; // fragmentDashboard có lẽ nên là fragmentCart
                    // tag = "2";
                } else if (itemId == R.id.nav_notifications) { // Giả sử ID cho Notifications là navigation_notifications
                    selectedFragment = fragmentNotifications;
                    // tag = "3";
                } else if (itemId == R.id.nav_profile) { // Giả sử ID cho Profile là navigation_profile
                    selectedFragment = fragmentProfile;
                    // tag = "4";
                } else {
                    // Trường hợp không có ID nào khớp (có thể không cần thiết nếu tất cả các item đều được xử lý)
                    return false;
                }


                // Logic để hiển thị Fragment đã chọn
                if (selectedFragment != null && selectedFragment != activeFragment) {
                    // Bạn cần khai báo fragmentManager ở cấp độ class hoặc lấy lại ở đây
                    // FragmentManager fragmentManager = getSupportFragmentManager(); // Nếu chưa khai báo ở cấp class
                    getSupportFragmentManager().beginTransaction().hide(activeFragment).show(selectedFragment).commit();
                    activeFragment = selectedFragment;
                    return true;
                }
                return false; // Trả về false nếu Fragment đã được chọn hoặc không có Fragment nào được chọn
            }
        });

    }

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