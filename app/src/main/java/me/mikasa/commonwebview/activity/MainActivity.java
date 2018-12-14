package me.mikasa.commonwebview.activity;

import android.Manifest;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import me.mikasa.commonwebview.R;
import me.mikasa.commonwebview.base.BaseActivity;
import me.mikasa.commonwebview.base.BaseToolbarActivity;
import me.mikasa.commonwebview.fragment.GuoKeFragment;
import me.mikasa.commonwebview.listener.PermissionListener;

public class MainActivity extends BaseActivity implements PermissionListener{
    @BindView(R.id.toolbar_tv)
    TextView title;
    @BindView(R.id.toolbar_iv)
    ImageView iv_search;
    @BindView(R.id.toolbar_switch)
    ImageView iv_switch;
    @BindView(R.id.toolbar_include)
    Toolbar toolbar;

    private static long fistTime=0;
    @Override
    protected int setLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        String[] s=new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA};
        requestRuntimePermission(s,this);
    }

    @Override
    protected void initView() {
        setSupportActionBar(toolbar);
        title.setText("果壳");
        iv_search.setImageResource(R.mipmap.search_white);
        iv_switch.setImageResource(R.mipmap.icon_switch);
    }
    private void initFragment(){
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        GuoKeFragment guoKeFragment=GuoKeFragment.newInstance();
        ft.replace(R.id.container,guoKeFragment);
        ft.commit();
    }

    @Override
    protected void initListener() {
        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("待开发");
            }
        });
        iv_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenuPopup();
            }
        });
    }
    private void showMenuPopup(){
        View menu=LayoutInflater.from(this).inflate(R.layout.popup_main,null);
        PopupWindow popupWindow=new PopupWindow(menu,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,true);
        //popupWindow.setAnimationStyle(R.style.popupAnim);
        LinearLayout menuSearch=(LinearLayout)menu.findViewById(R.id.menu_share);
        menuSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("待开发");
            }
        });
        popupWindow.showAsDropDown(iv_switch);
    }

    @Override
    public void onGranted() {
        initFragment();
    }

    @Override
    public void onDenied(List<String> deniedPermission) {
        for (String s:deniedPermission){
            showToast("你拒绝了"+s);
        }
    }

    @Override
    public void onBackPressed() {
        long secondTime=new Date().getTime();
        if (secondTime-fistTime>2000){
            showToast("再按一次退出程序");
        }else {
            fistTime=secondTime;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
