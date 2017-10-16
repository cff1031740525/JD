package test.bwei.com.jd;

import android.app.Application;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by C on 2017/10/12.
 */

public class application extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        DisplayImageOptions options=new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheInMemory(true)
                .build();
        ImageLoaderConfiguration config=new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(options)
                .build();
        ImageLoader.getInstance().init(config);
    }
}
