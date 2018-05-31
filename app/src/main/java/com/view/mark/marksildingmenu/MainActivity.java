package com.view.mark.marksildingmenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.view.mark.marksildingmenu.view.MarkSlideMenu;

public class MainActivity extends AppCompatActivity {

    private MarkSlideMenu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏标题栏，设置全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        mMenu = findViewById(R.id.id_menu);
        mMenu.setLeftMenu(true);
        mMenu.setmMenuRightPadding(100);
        mMenu.setmMenuLeftPadding(100);
        mMenu.setRightMenu(true);
        findViewById(R.id.btn_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleLeftMenu();
            }
        });
    }

    public void toggleLeftMenu(){
        mMenu.toggleLeft();
    }

    public void openRghtMenu(View view){
        mMenu.toggleRight();
    }

}
