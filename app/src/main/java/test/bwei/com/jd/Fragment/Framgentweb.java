package test.bwei.com.jd.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import test.bwei.com.jd.Bean.GoodsDetails;

/**
 * Created by C on 2017/10/13.
 */

public class Framgentweb extends Fragment {
    private Context context;
    private GoodsDetails details;

    public Framgentweb(Context context, GoodsDetails details) {
        this.context = context;
        this.details = details;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView tv=new TextView(getActivity());
        tv.setText("nihao");
        return tv;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
