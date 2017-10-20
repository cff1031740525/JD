package test.bwei.com.jd.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import test.bwei.com.jd.Bean.Kbean1;
import test.bwei.com.jd.GoodInfoActivity;
import test.bwei.com.jd.R;

/**
 * Created by C on 2017/10/10.
 */

public class FatherAdapter extends RecyclerView.Adapter<FatherAdapter.ViewHolder>{
    private Context context;
    private List<Kbean1> list;

    public FatherAdapter(Context context, List<Kbean1> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.fatheradapter,parent,false);
        ViewHolder viewHolder=new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.ptv.setText(list.get(position).name);
        ChildAdapter childAdapter=new ChildAdapter(list.get(position).list,context);
        holder.rlv.setLayoutManager(new GridLayoutManager(context,3));
        holder.rlv.setAdapter(childAdapter);
        childAdapter.setItemOnclick(new ChildAdapter.ItemOnclick() {
            @Override
            public void ItemClick(View v, int pos) {
                Toast.makeText(context,pos+"",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(context, GoodInfoActivity.class);
                intent.putExtra("pscid",pos+"");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView ptv;
        private final RecyclerView rlv;

        public ViewHolder(View itemView) {
            super(itemView);
            ptv = itemView.findViewById(R.id.ptv);
            rlv = itemView.findViewById(R.id.child);
        }
    }
}
