package com.example.lenovo.myapplication;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * created by Qin yiyi 16301087@bjtu.edu.cn
 */

public class MainActivity extends AppCompatActivity {

    Fragment currentFragment=null,moments_frg,find_frg,message_frg,me_frg;
    FragmentManager manager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        moments_frg = new Moments_Fragment();
        find_frg = new Find_Fragment();

        String username = getIntent().getStringExtra("username");
        me_frg = new Main_Fragment(username);

        currentFragment = moments_frg;

        // 获取对象
        LinearLayout moments=findViewById(R.id.moments);
        moments.setOnClickListener(l);
        LinearLayout find=findViewById(R.id.find);
        find.setOnClickListener(l);
        LinearLayout home=findViewById(R.id.home);
        home.setOnClickListener(l);
        LinearLayout message=findViewById(R.id.message);
        message.setOnClickListener(l);
    }

    private void showFragment(Fragment fragment) {
        if (currentFragment != fragment) {//  判断传入的fragment是不是当前的currentFragmentgit
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.hide(currentFragment);//  不是则隐藏
            currentFragment = fragment;  //  然后将传入的fragment赋值给currentFragment
            if (!fragment.isAdded()) { //  判断传入的fragment是否已经被add()过
                transaction.add(R.id.app_fragment, fragment).show(fragment).commit();
            } else {
                transaction.show(fragment).commit();
            }
        }
    }

    View.OnClickListener l=new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.moments:
                    showFragment(moments_frg);
                    break;
                case R.id.find:
                    showFragment(find_frg);
                    break;
                case R.id.home:
                    showFragment(me_frg);
                    break;
                case R.id.message:
                    Intent intent = new Intent(MainActivity.this, MapActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };
}
