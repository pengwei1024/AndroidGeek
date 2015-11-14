package com.apkfuns.androidgank.ui.base;


import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

/**
 * Created by pengwei08 on 15/11/14.
 */
public class BaseFragment extends Fragment implements BaseFunc {

    @Override
    public boolean notNull(Object object) {
        return object != null;
    }

    @Override
    public boolean notEmpty(String string) {
        return !TextUtils.isEmpty(string);
    }

    @Override
    public void toast(String msg, Object... args) {
        Toast.makeText(getActivity(), String.format(msg, args), Toast.LENGTH_LONG).show();
    }

    @Override
    public <T extends View> T inflateView(int layoutId) {
        return (T) LayoutInflater.from(getActivity()).inflate(layoutId, null);
    }

}
