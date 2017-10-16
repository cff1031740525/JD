package test.bwei.com.jd.Bean;

/**
 * Created by C on 2017/10/11.
 */

public class User {

    /**
     * msg : 获取用户信息成功
     * code : 0
     * data : {"age":null,"createtime":"2017-10-08T00:00:00","gender":0,"icon":null,"mobile":"13775854299","money":0,"nickname":"节能君","password":"123123","uid":100,"username":"13775854299"}
     */

    public String msg;
    public String code;
    public DataBean data;

    public static class DataBean {
        /**
         * age : null
         * createtime : 2017-10-08T00:00:00
         * gender : 0
         * icon : null
         * mobile : 13775854299
         * money : 0
         * nickname : 节能君
         * password : 123123
         * uid : 100
         * username : 13775854299
         */

        public Object age;
        public String createtime;
        public int gender;
        public Object icon;
        public String mobile;
        public int money;
        public String nickname;
        public String password;
        public int uid;
        public String username;
    }
}
