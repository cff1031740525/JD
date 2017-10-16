package test.bwei.com.jd.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import test.bwei.com.jd.Bean.KindChileBean;
import test.bwei.com.jd.R;

/**
 * Created by C on 2017/10/10.
 */

public class ChildAdapter extends RecyclerView.Adapter<ChildAdapter.ChildViewHolder> {
    private List<KindChileBean.DataBean.ListBean> list;
    private Context context;

    public ChildAdapter(List<KindChileBean.DataBean.ListBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ChildViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.childadapter, parent, false);
        ChildViewHolder childViewHolder = new ChildViewHolder(v);
        return childViewHolder;
    }

    @Override
    public void onBindViewHolder(ChildViewHolder holder, final int position) {
        holder.ctv.setText(list.get(position).name);
        Glide.with(context).load(list.get(position).icon).into(holder.img);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemOnclick.ItemClick(v,list.get(position).pscid);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ChildViewHolder extends RecyclerView.ViewHolder {

        private final TextView ctv;
        private final ImageView img;

        public ChildViewHolder(View itemView) {
            super(itemView);
            ctv = itemView.findViewById(R.id.ctv);
            img = itemView.findViewById(R.id.cimg);
        }
    }
    private ItemOnclick itemOnclick;

    public void setItemOnclick(ItemOnclick itemOnclick) {
        this.itemOnclick = itemOnclick;
    }

    public interface ItemOnclick{
        void ItemClick(View v,int pos);
    }
}
