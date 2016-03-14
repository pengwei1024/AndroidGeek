package com.apkfuns.androidgank.ui;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewDebug;

import com.apkfuns.androidgank.R;
import com.apkfuns.androidgank.ui.base.BaseActivity;
import com.apkfuns.androidgank.ui.fragments.CodeBlogFragment;
import com.apkfuns.androidgank.ui.fragments.GankIoFragment;
import com.apkfuns.androidgank.ui.fragments.GitHubFragment;
import com.apkfuns.androidgank.ui.fragments.WebBrowserFragment;
import com.apkfuns.logutils.LogUtils;
import com.umeng.comm.core.CommunitySDK;
import com.umeng.comm.core.impl.CommunityFactory;
import com.umeng.comm.ui.fragments.CommunityMainFragment;

import java.util.Map;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, getToolBar(),
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_gank);
        setFragment(GankIoFragment.getInstance());
        CommunitySDK mCommSDK = CommunityFactory.getCommSDK(this);
        mCommSDK.initSDK(this);
    }

    private long mExitTime;

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                toast("再按一次退出%s", getString(R.string.app_name));
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
        }
    }

    // 当前显示的fragment
    private Fragment currentFragment;

    /**
     * 切换默认视图
     *
     * @param fragment
     */
    private void setFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (notNull(currentFragment)) {
            if (!fragment.equals(currentFragment)) {
                if (!fragment.isAdded()) {
                    transaction.hide(currentFragment).add(R.id.container, fragment).commit();
                } else {
                    transaction.hide(currentFragment).show(fragment).commit();
                }
            }
        } else {
            transaction.add(R.id.container, fragment).commit();
        }
        currentFragment = fragment;
    }

    @Override
    protected boolean showBackIcon() {
        return false;
    }

    private CommunityMainFragment mFeedsFragment;

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_community:
                if (!notNull(mFeedsFragment)) {
                    mFeedsFragment = new CommunityMainFragment();
                    mFeedsFragment.setBackButtonVisibility(View.INVISIBLE);
                }
                setFragment(mFeedsFragment);
                break;
            case R.id.nav_gank:
                setFragment(GankIoFragment.getInstance());
                break;
            case R.id.nav_github_trending:
                setFragment(WebBrowserFragment.getSingleInstance("https://github.com/trending?l=java",
                        item.getTitle().toString()));
                break;
            case R.id.nav_blog:
                setFragment(CodeBlogFragment.getInstance());
                break;
            case R.id.nav_source:
                setFragment(GitHubFragment.getInstance());
                break;
            default:
                break;
        }
        if (notEmpty(item.getTitle().toString())) {
            setTitle(item.getTitle());
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
