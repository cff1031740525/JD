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

import test.bwei.com.jd.Bean.Sybean;
import test.bwei.com.jd.R;

/**
 * Created by C on 2017/10/8.
 */

public class SYAdapter extends RecyclerView.Adapter<SYAdapter.myViewHolder> {
    private Context context;
    private List<Sybean> list;

    public SYAdapter(Context context, List<Sybean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public SYAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.tuijian,parent,false);
        myViewHolder holder=new myViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, final int position) {
        holder.price.setText(list.get(position).price+"");
        holder.title.setText(list.get(position).title);
        Glide.with(context).load(list.get(position).img).into(holder.img);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    itemClick.ItemOnclick(view,position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
    class myViewHolder extends RecyclerView.ViewHolder{

        private final ImageView img;
        private final TextView price;
        private final TextView title;

        public myViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            price = itemView.findViewById(R.id.price);
            title = itemView.findViewById(R.id.title);
        }
    }



    private itemClick itemClick;
    public void setOnItemClick(itemClick itemClick){
        this.itemClick=itemClick;
    }
    public interface itemClick{
        void ItemOnclick(View v,int postion);
    }
}
