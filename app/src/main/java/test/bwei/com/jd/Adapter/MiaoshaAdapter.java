package test.bwei.com.jd.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import test.bwei.com.jd.Bean.BannerBean;
import test.bwei.com.jd.R;

/**
 * Created by C on 2017/10/16.
 */

public class MiaoshaAdapter extends RecyclerView.Adapter<MiaoshaAdapter.myViewholder> {
    private List<BannerBean.MiaoshaBean.ListBeanX> list1;
    private Context context;

    public MiaoshaAdapter(List<BannerBean.MiaoshaBean.ListBeanX> list1, Context context) {
        this.list1 = list1;
        this.context = context;
    }

    @Override
    public myViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.miaosha, parent, false);
        myViewholder myViewholder = new myViewholder(view);

        return myViewholder;
    }

    @Override
    public void onBindViewHolder(myViewholder holder, int position) {
        holder.xianjia.setText(list1.get(position).bargainPrice + "");
        holder.yuanjia.setText(list1.get(position).price + "");
        String images = list1.get(position).images;
        String[] split = images.split("\\|");
        Glide.with(context).load(split[0]).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return list1.size();
    }

    class myViewholder extends RecyclerView.ViewHolder {

        private final TextView xianjia;
        private final TextView yuanjia;
        private final ImageView img;

        public myViewholder(View itemView) {
            super(itemView);
            xianjia = itemView.findViewById(R.id.xianjia);
            yuanjia = itemView.findViewById(R.id.yuanjia);
            img = itemView.findViewById(R.id.msimg);
            yuanjia.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
        }
    }
}
