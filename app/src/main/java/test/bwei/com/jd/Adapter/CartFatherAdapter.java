package test.bwei.com.jd.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import test.bwei.com.jd.Api;
import test.bwei.com.jd.Bean.CartBean;
import test.bwei.com.jd.R;
import test.bwei.com.okhttputils.OkCallback;
import test.bwei.com.okhttputils.OkHttpMethod;
import test.bwei.com.okhttputils.OkhttpUtils;

/**
 * Created by C on 2017/10/18.
 */

public class CartFatherAdapter extends RecyclerView.Adapter<CartFatherAdapter.myViewHolder> {
    private List<CartBean.DataBean> data;
    private Context context;
    private int flags;
    private Map<String,Boolean> smap=new HashMap<>();

    public CartFatherAdapter(List<CartBean.DataBean> data, Context context) {
        this.data = data;
        this.context = context;
        for(int i=0;i<data.size();i++){
            smap.put(i+"",false);
        }
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cartfatheritem, parent, false);
        myViewHolder myViewHolder = new myViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final myViewHolder holder, final int position) {

        holder.seller.setText(data.get(position).sellerName);
        final SharedPreferences sp = context.getSharedPreferences("user_uid", Context.MODE_PRIVATE);
        final List<CartBean.DataBean.ListBean> list = data.get(position).list;
        final CartChildAdapter cartChildAdapter = new CartChildAdapter(list, context);
        cartChildAdapter.setPriceListener(new CartChildAdapter.PriceListener() {
            @Override
            public void SumPrice() {
                fpriceListener.SumPrices();
            }
        });
        holder.clv.setLayoutManager(new LinearLayoutManager(context));
        holder.clv.setAdapter(cartChildAdapter);
        holder.fcb.setOnCheckedChangeListener(null);
        holder.fcb.setChecked(smap.get(position+""));
        cartChildAdapter.setChangedLis(new CartChildAdapter.ChangedLis() {
            @Override
            public void Changed(String flag) {
                if(flag.equals("0")){
                    holder.fcb.setChecked(false);
                    smap.put(position+"",false);
                    System.out.println(position+"postion++++++++++++");
                }else{
                    holder.fcb.setChecked(true);
                    smap.put(position+"",true);
                    System.out.println(position+"postion++++++++++++");
                }
            }
        });
        holder.fcb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OkhttpUtils okhttpUtils=OkhttpUtils.getOkhttpInstance(context);
                boolean checked = holder.fcb.isChecked();
                if(checked){
                    fpriceListener.SumPrices();
                    smap.put(position+"",true);

                    CartBean.DataBean dataBean = data.get(position);
                    System.out.println(position+"postion++++++++++++");
                    for (CartBean.DataBean.ListBean listBean : dataBean.list) {
                        listBean.selected=1;
                        Map<String, Object> parms = new HashMap<>();
                        parms.put("uid", sp.getString("uid",null));
                        parms.put("sellerid", listBean.sellerid + "");
                        parms.put("pid", listBean.pid + "");
                        parms.put("num", listBean.num + "");
                        parms.put("selected", 1 + "");
                        okhttpUtils.call(OkHttpMethod.POST, Api.CHANGECART, parms, new OkCallback() {
                            @Override
                            public void onFailure(String e, String msg) {

                            }

                            @Override
                            public void onResponse(String result) {
                                Toast.makeText(context, result, Toast.LENGTH_SHORT).show();

                            }
                        });
                    }

                    holder.clv.setAdapter(cartChildAdapter);
                }else{
                    fpriceListener.SumPrices();
                    smap.put(position+"",false);
                    CartBean.DataBean dataBean = data.get(position);
                    for (CartBean.DataBean.ListBean listBean : dataBean.list) {
                        listBean.selected=0;
                        Map<String, Object> parms = new HashMap<>();
                        parms.put("uid", sp.getString("uid",null));
                        parms.put("sellerid", listBean.sellerid + "");
                        parms.put("pid", listBean.pid + "");
                        parms.put("num", listBean.num + "");
                        parms.put("selected", 0 + "");
                        okhttpUtils.call(OkHttpMethod.POST, Api.CHANGECART, parms, new OkCallback() {
                            @Override
                            public void onFailure(String e, String msg) {

                            }

                            @Override
                            public void onResponse(String result) {
                                Toast.makeText(context, result, Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                    holder.clv.setAdapter(cartChildAdapter);

                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        private final TextView seller;
        private final CheckBox fcb;
        private final RecyclerView clv;

        public myViewHolder(View itemView) {
            super(itemView);
            seller = itemView.findViewById(R.id.seller);
            fcb = itemView.findViewById(R.id.fathercb);
            clv = itemView.findViewById(R.id.clv);
        }
    }
    private FPriceListener fpriceListener;

    public void setFpriceListener(FPriceListener fpriceListener) {
        this.fpriceListener = fpriceListener;
    }

    public interface FPriceListener{
        void SumPrices();
    }
}
