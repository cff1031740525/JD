package test.bwei.com.jd.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import test.bwei.com.jd.AmountView;
import test.bwei.com.jd.Api;
import test.bwei.com.jd.Bean.CartBean;
import test.bwei.com.jd.R;
import test.bwei.com.okhttputils.OkCallback;
import test.bwei.com.okhttputils.OkHttpMethod;
import test.bwei.com.okhttputils.OkhttpUtils;

/**
 * Created by C on 2017/10/18.
 */

public class CartChildAdapter extends RecyclerView.Adapter<CartChildAdapter.myViewHolder> {
    List<CartBean.DataBean.ListBean> list;
    private Context context;
    private int s;
    private Map<String, Boolean> smap = new HashMap<>();
    private Map<String, String> fmap = new HashMap<>();


    public CartChildAdapter(List<CartBean.DataBean.ListBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cartchilditem, null);
        myViewHolder myViewHolder = new myViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final myViewHolder holder, final int position) {

        SharedPreferences sp = context.getSharedPreferences("user_uid", Context.MODE_PRIVATE);
        final String uid = sp.getString("uid", null);
        holder.price.setText(list.get(position).bargainPrice + "");
        holder.title.setText(list.get(position).title);
        holder.av.setGoods_storage(99);
        holder.av.setamount(list.get(position).num);
        String images = list.get(position).images;
        int selected = list.get(position).selected;
        String[] split = images.split("\\|");
        Glide.with(context).load(split[0]).into(holder.img);
        if ("1".equals(list.get(position).selected + "")) {
            priceListener.SumPrice();
            int iii = 0;
            holder.cb.setChecked(true);
            smap.put(list.get(position).pid + "", true);
            fmap.put(list.get(position).sellerid + "", list.get(position).selected + "");
            for (CartBean.DataBean.ListBean listBean : list) {

                if ("1".equals(listBean.selected + "")) {
                    iii++;

                }
                System.out.println(iii + "iiiiiiiiiii++++++++++" + list.size());
                if (iii == list.size()) {
                    changedLis.Changed("1");
                } else if (iii < list.size()) {
                    changedLis.Changed("0");
                }
            }
        } else {
            holder.cb.setChecked(false);
            smap.put(list.get(position).pid + "", false);
        }
        holder.cb.setOnCheckedChangeListener(null);
        holder.cb.setChecked(smap.get(list.get(position).pid + ""));
        holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                int iii = 0;
                if (isChecked) {
                    priceListener.SumPrice();
                    if (smap.containsKey(list.get(position).pid + "")) {
                        smap.put(list.get(position).pid + "", true);
                        list.get(position).selected = 1;
                    }
                    OkhttpUtils utils = OkhttpUtils.getOkhttpInstance(context);
                    Map<String, Object> parms = new HashMap<>();
                    parms.put("uid", uid);
                    parms.put("sellerid", list.get(position).sellerid + "");
                    parms.put("pid", list.get(position).pid + "");
                    parms.put("num", list.get(position).num + "");
                    parms.put("selected", 1 + "");
                    utils.call(OkHttpMethod.POST, Api.CHANGECART, parms, new OkCallback() {
                        @Override
                        public void onFailure(String e, String msg) {

                        }

                        @Override
                        public void onResponse(String result) {
                            Toast.makeText(context, result, Toast.LENGTH_SHORT).show();

                        }
                    });
                    for (CartBean.DataBean.ListBean listBean : list) {

                        if ("1".equals(listBean.selected + "")) {
                            iii++;

                        }
                        System.out.println(iii + "iiiiiiiiiii++++++++++" + list.size());
                        if (iii == list.size()) {
                            changedLis.Changed("1");
                        } else if (iii < list.size()) {
                            changedLis.Changed("0");
                        }
                    }

                } else {
                    priceListener.SumPrice();
                    if (smap.containsKey(list.get(position).pid + "")) {
                        smap.put(list.get(position).pid + "", false);
                        list.get(position).selected = 0;
                    }
                    OkhttpUtils utils = OkhttpUtils.getOkhttpInstance(context);
                    Map<String, Object> parms = new HashMap<>();
                    parms.put("uid", uid);
                    parms.put("sellerid", list.get(position).sellerid + "");
                    parms.put("pid", list.get(position).pid + "");
                    parms.put("num", list.get(position).num + "");
                    parms.put("selected", 0 + "");
                    utils.call(OkHttpMethod.POST, Api.CHANGECART, parms, new OkCallback() {
                        @Override
                        public void onFailure(String e, String msg) {

                        }

                        @Override
                        public void onResponse(String result) {
                            Toast.makeText(context, result, Toast.LENGTH_SHORT).show();

                        }
                    });
                    changedLis.Changed("0");

                }
            }
        });

        holder.av.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
            @Override
            public void onAmountChange(View view, int amount) {
                priceListener.SumPrice();
                OkhttpUtils utils = OkhttpUtils.getOkhttpInstance(context);
                Map<String, Object> parms = new HashMap<>();
                parms.put("uid", uid);
                parms.put("sellerid", list.get(position).sellerid + "");
                parms.put("pid", list.get(position).pid + "");
                System.out.println(amount + "-----amount");
                parms.put("num", amount + "");
                parms.put("selected", 1 + "");
                list.get(position).num = amount;
                utils.call(OkHttpMethod.POST, Api.CHANGECART, parms, new OkCallback() {
                    @Override
                    public void onFailure(String e, String msg) {

                    }

                    @Override
                    public void onResponse(String result) {
                        Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
                        holder.cb.setChecked(true);
                    }
                });
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        private final CheckBox cb;
        private final ImageView img;
        private final TextView price;
        private final TextView title;
        private final AmountView av;

        public myViewHolder(View itemView) {
            super(itemView);
            cb = itemView.findViewById(R.id.cccb);
            av = itemView.findViewById(R.id.av);
            img = itemView.findViewById(R.id.ccimg);
            price = itemView.findViewById(R.id.ccprice);
            title = itemView.findViewById(R.id.cctitle);

        }
    }

    private ChangedLis changedLis;

    public void setChangedLis(ChangedLis changedLis) {
        this.changedLis = changedLis;
    }

    public interface ChangedLis {
        void Changed(String flag);
    }
    private PriceListener priceListener;

    public void setPriceListener(PriceListener priceListener) {
        this.priceListener = priceListener;
    }

    public interface PriceListener{
        void SumPrice();
    }
}
