package test.bwei.com.jd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import test.bwei.com.jd.Fragment.Fragment1;
import test.bwei.com.jd.Fragment.Fragment2;
import test.bwei.com.jd.Fragment.Fragment3;
import test.bwei.com.jd.Fragment.Fragment4;
import test.bwei.com.jd.Fragment.Fragment5;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout l1;
    private LinearLayout l2;
    private LinearLayout l3;
    private LinearLayout l4;
    private LinearLayout l5;
    private ImageView sy;
    private ImageView fl;
    private ImageView fx;
    private ImageView gwc;
    private ImageView wd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        initView();
    }

    private void initView() {
        l1 = (LinearLayout) findViewById(R.id.shouye);
        l2 = (LinearLayout) findViewById(R.id.type);
        l3 = (LinearLayout) findViewById(R.id.faxian);
        l4 = (LinearLayout) findViewById(R.id.gouwuche);
        l5 = (LinearLayout) findViewById(R.id.wode);
        sy = (ImageView) findViewById(R.id.psy);
        fl = (ImageView) findViewById(R.id.pfl);
        fx = (ImageView) findViewById(R.id.pfx);
        gwc = (ImageView) findViewById(R.id.pgwc);
        wd = (ImageView) findViewById(R.id.pwd);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame,new Fragment1()).commit();
        l1.setOnClickListener(this);
        l2.setOnClickListener(this);
        l3.setOnClickListener(this);
        l4.setOnClickListener(this);
        l5.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.shouye:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame,new Fragment1()).commit();
                sy.setImageResource(R.mipmap.shou2);
                fl.setImageResource(R.mipmap.fen1);
                fx.setImageResource(R.mipmap.fa1);
                gwc.setImageResource(R.mipmap.che1);
                wd.setImageResource(R.mipmap.wo1);
                break;
            case R.id.type:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame,new Fragment2()).commit();
                sy.setImageResource(R.mipmap.shou1);
                fl.setImageResource(R.mipmap.fen2);
                fx.setImageResource(R.mipmap.fa1);
                gwc.setImageResource(R.mipmap.che1);
                wd.setImageResource(R.mipmap.wo1);
                break;
            case R.id.faxian:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame,new Fragment3()).commit();
                sy.setImageResource(R.mipmap.shou1);
                fl.setImageResource(R.mipmap.fen1);
                fx.setImageResource(R.mipmap.fa2);
                gwc.setImageResource(R.mipmap.che1);
                wd.setImageResource(R.mipmap.wo1);
                break;
            case R.id.gouwuche:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame,new Fragment4()).commit();
                sy.setImageResource(R.mipmap.shou1);
                fl.setImageResource(R.mipmap.fen1);
                fx.setImageResource(R.mipmap.fa1);
                gwc.setImageResource(R.mipmap.che2);
                wd.setImageResource(R.mipmap.wo1);
                break;
            case R.id.wode:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame,new Fragment5()).commit();
                sy.setImageResource(R.mipmap.shou1);
                fl.setImageResource(R.mipmap.fen1);
                fx.setImageResource(R.mipmap.fa1);
                gwc.setImageResource(R.mipmap.che1);
                wd.setImageResource(R.mipmap.wo2);
                break;
        }
    }
}
