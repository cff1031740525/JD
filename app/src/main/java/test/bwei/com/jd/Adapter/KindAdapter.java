package test.bwei.com.jd.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.TimeoutException;

import test.bwei.com.jd.Bean.KBean;
import test.bwei.com.jd.R;

/**
 * Created by C on 2017/10/9.
 */

public class KindAdapter extends RecyclerView.Adapter<KindAdapter.myViewholder>{
    private List<KBean> list;
    private Context context;
    private int point;
    public KindAdapter(List<KBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public myViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.flitem,parent,false);
        myViewholder holder=new myViewholder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(myViewholder holder, final int position) {
        holder.tv.setText(list.get(position).name);
        if(position==point){
            holder.tv.setSelected(true);
        }else{
            holder.tv.setSelected(false);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kindOnclick.KinditemOnclick(view,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class myViewholder extends RecyclerView.ViewHolder{

        private final TextView tv;

        public myViewholder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.fltj);
        }
    }
    public KindOnclick kindOnclick;

    public void setKindOnclick(KindOnclick kindOnclick) {
        this.kindOnclick = kindOnclick;
    }

    public  interface KindOnclick{
        void KinditemOnclick(View v,int postion);
    }
    public void changerposion(int postion){
     this.point=postion;
        notifyDataSetChanged();
    }
}
