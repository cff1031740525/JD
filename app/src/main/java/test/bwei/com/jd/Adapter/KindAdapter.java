package test.bwei.com.jd.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by C on 2017/10/9.
 */

public class KindAdapter extends RecyclerView.Adapter<KindAdapter.myViewholder>{
    @Override
    public myViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(myViewholder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class myViewholder extends RecyclerView.ViewHolder{

        public myViewholder(View itemView) {
            super(itemView);
        }
    }
}
