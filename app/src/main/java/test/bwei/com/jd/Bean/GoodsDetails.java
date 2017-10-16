package test.bwei.com.jd.Bean;

/**
 * Created by C on 2017/10/13.
 */

public class GoodsDetails {

    /**
     * msg :
     * seller : {"description":"我是商家6","icon":"http://120.27.23.105/images/icon.png","name":"商家6","productNums":999,"score":5,"sellerid":6}
     * code : 0
     * data : {"bargainPrice":11800,"createtime":"2017-10-03T23:53:28","detailUrl":"https://mitem.jd.hk/ware/view.action?wareId=1988853309&cachekey=1acb07a701ece8d2434a6ae7fa6870a1","images":"https://m.360buyimg.com/n0/jfs/t6130/97/1370670410/180682/1109582a/593276b1Nd81fe723.jpg!q70.jpg|https://m.360buyimg.com/n0/jfs/t5698/110/2617517836/202970/c9388feb/593276b7Nbd94ef1f.jpg!q70.jpg|https://m.360buyimg.com/n0/jfs/t5698/110/2617517836/202970/c9388feb/593276b7Nbd94ef1f.jpg!q70.jpg|https://m.360buyimg.com/n0/jfs/t5815/178/2614671118/51656/7f52d137/593276c7N107b725a.jpg!q70.jpg|https://m.360buyimg.com/n0/jfs/t5878/60/2557817477/30873/4502b606/593276caN5a7d6357.jpg!q70.jpg","pid":62,"price":15999,"pscid":40,"salenum":43,"sellerid":6,"subhead":"购买电脑办公部分商品满1元返火车票5元优惠券（返完即止）","title":"全球购 新款Apple MacBook Pro 苹果笔记本电脑 银色VP2新13英寸Bar i5/8G/256G"}
     */

    public String msg;
    public SellerBean seller;
    public String code;
    public DataBean data;

    public static class SellerBean {
        /**
         * description : 我是商家6
         * icon : http://120.27.23.105/images/icon.png
         * name : 商家6
         * productNums : 999
         * score : 5.0
         * sellerid : 6
         */

        public String description;
        public String icon;
        public String name;
        public int productNums;
        public double score;
        public int sellerid;
    }

    public static class DataBean {
        /**
         * bargainPrice : 11800.0
         * createtime : 2017-10-03T23:53:28
         * detailUrl : https://mitem.jd.hk/ware/view.action?wareId=1988853309&cachekey=1acb07a701ece8d2434a6ae7fa6870a1
         * images : https://m.360buyimg.com/n0/jfs/t6130/97/1370670410/180682/1109582a/593276b1Nd81fe723.jpg!q70.jpg|https://m.360buyimg.com/n0/jfs/t5698/110/2617517836/202970/c9388feb/593276b7Nbd94ef1f.jpg!q70.jpg|https://m.360buyimg.com/n0/jfs/t5698/110/2617517836/202970/c9388feb/593276b7Nbd94ef1f.jpg!q70.jpg|https://m.360buyimg.com/n0/jfs/t5815/178/2614671118/51656/7f52d137/593276c7N107b725a.jpg!q70.jpg|https://m.360buyimg.com/n0/jfs/t5878/60/2557817477/30873/4502b606/593276caN5a7d6357.jpg!q70.jpg
         * pid : 62
         * price : 15999.0
         * pscid : 40
         * salenum : 43
         * sellerid : 6
         * subhead : 购买电脑办公部分商品满1元返火车票5元优惠券（返完即止）
         * title : 全球购 新款Apple MacBook Pro 苹果笔记本电脑 银色VP2新13英寸Bar i5/8G/256G
         */

        public double bargainPrice;
        public String createtime;
        public String detailUrl;
        public String images;
        public int pid;
        public double price;
        public int pscid;
        public int salenum;
        public int sellerid;
        public String subhead;
        public String title;
    }
}
