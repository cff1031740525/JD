package test.bwei.com.jd;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by C on 2017/10/17.
 */

public class NumView extends LinearLayout {
    private  Context context;
    private int num;

    public NumView(Context context, Context context1, int num) {
        super(context);
        this.context = context1;
        this.num = num;
    }



    public NumView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NumView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void initView() {
        View v=View.inflate(context,R.layout.randomview,null);
        ImageView jia=v.findViewById(R.id.jia);
        ImageView jian=v.findViewById(R.id.jian);
        TextView num=v.findViewById(R.id.num);
        jia.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                jiaClick.Jia();
            }
        });
        jian.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                jiaClick.Jian();
            }
        });

    }
    private JiaClick jiaClick;
    public void setJiaClick(JiaClick jiaClick) {
        this.jiaClick = jiaClick;
    }

    public interface JiaClick{
        void Jia();
        void Jian();
    }
}
