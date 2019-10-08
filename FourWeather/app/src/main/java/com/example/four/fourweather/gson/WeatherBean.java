package com.example.four.fourweather.gson;

import java.util.List;

/**
 * Created by Administrator on 2018/3/2 0002.
 */

public class WeatherBean {

    /**
     * basic : {"cid":"CN101040400","location":"南川","parent_city":"重庆","admin_area":"重庆","cnty":"中国","lat":"29.15664673","lon":"107.09815216","tz":"+8.00"}
     * update : {"loc":"2018-03-02 13:47","utc":"2018-03-02 05:47"}
     * status : ok
     * now : {"cloud":"70","cond_code":"104","cond_txt":"阴","fl":"13","hum":"86","pcpn":"0.0","pres":"1009","tmp":"15","vis":"14","wind_deg":"85","wind_dir":"东风","wind_sc":"微风","wind_spd":"5"}
     * daily_forecast : [{"cond_code_d":"300","cond_code_n":"101","cond_txt_d":"阵雨","cond_txt_n":"多云","date":"2018-03-02","hum":"75","mr":"19:14","ms":"07:26","pcpn":"4.2","pop":"97","pres":"1008","sr":"07:15","ss":"18:51","tmp_max":"17","tmp_min":"9","uv_index":"3","vis":"16","wind_deg":"0","wind_dir":"无持续风向","wind_sc":"微风","wind_spd":"6"},{"cond_code_d":"101","cond_code_n":"104","cond_txt_d":"多云","cond_txt_n":"阴","date":"2018-03-03","hum":"74","mr":"20:16","ms":"08:07","pcpn":"0.5","pop":"82","pres":"1002","sr":"07:14","ss":"18:52","tmp_max":"20","tmp_min":"13","uv_index":"6","vis":"18","wind_deg":"0","wind_dir":"无持续风向","wind_sc":"微风","wind_spd":"6"},{"cond_code_d":"104","cond_code_n":"104","cond_txt_d":"阴","cond_txt_n":"阴","date":"2018-03-04","hum":"79","mr":"21:17","ms":"08:46","pcpn":"3.3","pop":"87","pres":"1005","sr":"07:13","ss":"18:53","tmp_max":"18","tmp_min":"12","uv_index":"2","vis":"17","wind_deg":"0","wind_dir":"无持续风向","wind_sc":"微风","wind_spd":"6"},{"cond_code_d":"305","cond_code_n":"305","cond_txt_d":"小雨","cond_txt_n":"小雨","date":"2018-03-05","hum":"71","mr":"22:15","ms":"09:24","pcpn":"0.1","pop":"75","pres":"1016","sr":"07:12","ss":"18:53","tmp_max":"13","tmp_min":"10","uv_index":"2","vis":"19","wind_deg":"0","wind_dir":"无持续风向","wind_sc":"微风","wind_spd":"5"},{"cond_code_d":"305","cond_code_n":"104","cond_txt_d":"小雨","cond_txt_n":"阴","date":"2018-03-06","hum":"69","mr":"23:12","ms":"10:01","pcpn":"0.5","pop":"65","pres":"1016","sr":"07:11","ss":"18:54","tmp_max":"10","tmp_min":"7","uv_index":"3","vis":"18","wind_deg":"0","wind_dir":"无持续风向","wind_sc":"微风","wind_spd":"5"},{"cond_code_d":"305","cond_code_n":"104","cond_txt_d":"小雨","cond_txt_n":"阴","date":"2018-03-07","hum":"88","mr":"05:45","ms":"10:38","pcpn":"2.3","pop":"62","pres":"1019","sr":"07:10","ss":"18:54","tmp_max":"9","tmp_min":"5","uv_index":"2","vis":"17","wind_deg":"0","wind_dir":"无持续风向","wind_sc":"微风","wind_spd":"6"},{"cond_code_d":"101","cond_code_n":"100","cond_txt_d":"多云","cond_txt_n":"晴","date":"2018-03-08","hum":"75","mr":"00:08","ms":"11:18","pcpn":"1.6","pop":"79","pres":"1024","sr":"07:09","ss":"18:55","tmp_max":"14","tmp_min":"5","uv_index":"2","vis":"19","wind_deg":"0","wind_dir":"无持续风向","wind_sc":"微风","wind_spd":"7"}]
     * hourly : [{"cloud":"86","cond_code":"305","cond_txt":"小雨","dew":"11","hum":"64","pop":"7","pres":"1008","time":"2018-03-02 16:00","tmp":"16","wind_deg":"119","wind_dir":"无持续风向","wind_sc":"微风","wind_spd":"4"},{"cloud":"89","cond_code":"305","cond_txt":"小雨","dew":"11","hum":"77","pop":"0","pres":"1005","time":"2018-03-02 19:00","tmp":"14","wind_deg":"118","wind_dir":"无持续风向","wind_sc":"微风","wind_spd":"5"},{"cloud":"69","cond_code":"103","cond_txt":"晴间多云","dew":"10","hum":"77","pop":"0","pres":"1005","time":"2018-03-02 22:00","tmp":"13","wind_deg":"108","wind_dir":"无持续风向","wind_sc":"微风","wind_spd":"5"},{"cloud":"71","cond_code":"103","cond_txt":"晴间多云","dew":"9","hum":"79","pop":"7","pres":"1007","time":"2018-03-03 01:00","tmp":"11","wind_deg":"114","wind_dir":"无持续风向","wind_sc":"微风","wind_spd":"5"},{"cloud":"86","cond_code":"305","cond_txt":"小雨","dew":"9","hum":"85","pop":"7","pres":"1006","time":"2018-03-03 04:00","tmp":"11","wind_deg":"119","wind_dir":"无持续风向","wind_sc":"微风","wind_spd":"6"},{"cloud":"80","cond_code":"300","cond_txt":"阵雨","dew":"9","hum":"87","pop":"3","pres":"1004","time":"2018-03-03 07:00","tmp":"11","wind_deg":"122","wind_dir":"无持续风向","wind_sc":"微风","wind_spd":"5"},{"cloud":"66","cond_code":"103","cond_txt":"晴间多云","dew":"13","hum":"82","pop":"3","pres":"1004","time":"2018-03-03 10:00","tmp":"15","wind_deg":"125","wind_dir":"无持续风向","wind_sc":"微风","wind_spd":"3"},{"cloud":"72","cond_code":"101","cond_txt":"多云","dew":"14","hum":"70","pop":"5","pres":"1004","time":"2018-03-03 13:00","tmp":"16","wind_deg":"132","wind_dir":"无持续风向","wind_sc":"微风","wind_spd":"6"}]
     * lifestyle : [{"brf":"舒适","txt":"白天不太热也不太冷，风力不大，相信您在这样的天气条件下，应会感到比较清爽和舒适。","type":"comf"},{"brf":"较冷","txt":"建议着厚外套加毛衣等服装。年老体弱者宜着大衣、呢外套加羊毛衫。","type":"drsg"},{"brf":"较易发","txt":"昼夜温差较大，较易发生感冒，请适当增减衣服。体质较弱的朋友请注意防护。","type":"flu"},{"brf":"较不宜","txt":"有降水，推荐您在室内进行健身休闲运动；若坚持户外运动，须注意携带雨具并注意避雨防滑。","type":"sport"},{"brf":"适宜","txt":"有降水，温度适宜，在细雨中游玩别有一番情调，可不要错过机会呦！但记得出门要携带雨具。","type":"trav"},{"brf":"弱","txt":"紫外线强度较弱，建议出门前涂擦SPF在12-15之间、PA+的防晒护肤品。","type":"uv"},{"brf":"不宜","txt":"不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄脏您的爱车。","type":"cw"},{"brf":"中","txt":"气象条件对空气污染物稀释、扩散和清除无明显影响，易感人群应适当减少室外活动时间。","type":"air"}]
     */

    private BasicBean basic;
    private UpdateBean update;
    private String status;
    private NowBean now;
    private List<DailyForecastBean> daily_forecast;
    private List<HourlyBean> hourly;
    private List<LifestyleBean> lifestyle;

    public BasicBean getBasic() {
        return basic;
    }

    public void setBasic(BasicBean basic) {
        this.basic = basic;
    }

    public UpdateBean getUpdate() {
        return update;
    }

    public void setUpdate(UpdateBean update) {
        this.update = update;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public NowBean getNow() {
        return now;
    }

    public void setNow(NowBean now) {
        this.now = now;
    }

    public List<DailyForecastBean> getDaily_forecast() {
        return daily_forecast;
    }

    public void setDaily_forecast(List<DailyForecastBean> daily_forecast) {
        this.daily_forecast = daily_forecast;
    }

    public List<HourlyBean> getHourly() {
        return hourly;
    }

    public void setHourly(List<HourlyBean> hourly) {
        this.hourly = hourly;
    }

    public List<LifestyleBean> getLifestyle() {
        return lifestyle;
    }

    public void setLifestyle(List<LifestyleBean> lifestyle) {
        this.lifestyle = lifestyle;
    }

    public static class BasicBean {
        /**
         * cid : CN101040400
         * location : 南川
         * parent_city : 重庆
         * admin_area : 重庆
         * cnty : 中国
         * lat : 29.15664673
         * lon : 107.09815216
         * tz : +8.00
         */

        private String cid;
        private String location;

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }
    }

    public static class UpdateBean {
        /**
         * loc : 2018-03-02 13:47
         * utc : 2018-03-02 05:47
         */

        private String loc;

        public String getLoc() {
            return loc;
        }

        public void setLoc(String loc) {
            this.loc = loc;
        }
    }

    public static class NowBean {
        /**
         * cloud : 70
         * cond_code : 104
         * cond_txt : 阴
         * fl : 13
         * hum : 86
         * pcpn : 0.0
         * pres : 1009
         * tmp : 15
         * vis : 14
         * wind_deg : 85
         * wind_dir : 东风
         * wind_sc : 微风
         * wind_spd : 5
         */

        private String cloud;
        private String cond_code;
        private String cond_txt;
        private String fl;
        private String hum;
        private String pcpn;
        private String pres;
        private String tmp;
        private String vis;
        private String wind_dir;
        private String wind_sc;
        private String wind_spd;

        public String getCloud() {
            return cloud;
        }

        public void setCloud(String cloud) {
            this.cloud = cloud;
        }

        public String getCond_code() {
            return cond_code;
        }

        public void setCond_code(String cond_code) {
            this.cond_code = cond_code;
        }

        public String getCond_txt() {
            return cond_txt;
        }

        public void setCond_txt(String cond_txt) {
            this.cond_txt = cond_txt;
        }

        public String getFl() {
            return fl;
        }

        public void setFl(String fl) {
            this.fl = fl;
        }

        public String getHum() {
            return hum;
        }

        public void setHum(String hum) {
            this.hum = hum;
        }

        public String getPcpn() {
            return pcpn;
        }

        public void setPcpn(String pcpn) {
            this.pcpn = pcpn;
        }

        public String getPres() {
            return pres;
        }

        public void setPres(String pres) {
            this.pres = pres;
        }

        public String getTmp() {
            return tmp;
        }

        public void setTmp(String tmp) {
            this.tmp = tmp;
        }

        public String getVis() {
            return vis;
        }

        public void setVis(String vis) {
            this.vis = vis;
        }

        public String getWind_dir() {
            return wind_dir;
        }

        public void setWind_dir(String wind_dir) {
            this.wind_dir = wind_dir;
        }

        public String getWind_sc() {
            return wind_sc;
        }

        public void setWind_sc(String wind_sc) {
            this.wind_sc = wind_sc;
        }

        public String getWind_spd() {
            return wind_spd;
        }

        public void setWind_spd(String wind_spd) {
            this.wind_spd = wind_spd;
        }
    }

    public static class DailyForecastBean {
        /**
         * cond_code_d : 300
         * cond_code_n : 101
         * cond_txt_d : 阵雨
         * cond_txt_n : 多云
         * date : 2018-03-02
         * hum : 75
         * mr : 19:14
         * ms : 07:26
         * pcpn : 4.2
         * pop : 97
         * pres : 1008
         * sr : 07:15
         * ss : 18:51
         * tmp_max : 17
         * tmp_min : 9
         * uv_index : 3
         * vis : 16
         * wind_deg : 0
         * wind_dir : 无持续风向
         * wind_sc : 微风
         * wind_spd : 6
         */

        private String cond_code_d;
        private String cond_code_n;
        private String cond_txt_d;
        private String cond_txt_n;
        private String date;
        private String hum;
        private String mr;
        private String ms;
        private String pcpn;
        private String pop;
        private String pres;
        private String sr;
        private String ss;
        private String tmp_max;
        private String tmp_min;
        private String uv_index;
        private String vis;
        private String wind_dir;
        private String wind_sc;
        private String wind_spd;

        public String getCond_code_d() {
            return cond_code_d;
        }

        public void setCond_code_d(String cond_code_d) {
            this.cond_code_d = cond_code_d;
        }

        public String getCond_code_n() {
            return cond_code_n;
        }

        public void setCond_code_n(String cond_code_n) {
            this.cond_code_n = cond_code_n;
        }

        public String getCond_txt_d() {
            return cond_txt_d;
        }

        public void setCond_txt_d(String cond_txt_d) {
            this.cond_txt_d = cond_txt_d;
        }

        public String getCond_txt_n() {
            return cond_txt_n;
        }

        public void setCond_txt_n(String cond_txt_n) {
            this.cond_txt_n = cond_txt_n;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getHum() {
            return hum;
        }

        public void setHum(String hum) {
            this.hum = hum;
        }

        public String getMr() {
            return mr;
        }

        public void setMr(String mr) {
            this.mr = mr;
        }

        public String getMs() {
            return ms;
        }

        public void setMs(String ms) {
            this.ms = ms;
        }

        public String getPcpn() {
            return pcpn;
        }

        public void setPcpn(String pcpn) {
            this.pcpn = pcpn;
        }

        public String getPop() {
            return pop;
        }

        public void setPop(String pop) {
            this.pop = pop;
        }

        public String getPres() {
            return pres;
        }

        public void setPres(String pres) {
            this.pres = pres;
        }

        public String getSr() {
            return sr;
        }

        public void setSr(String sr) {
            this.sr = sr;
        }

        public String getSs() {
            return ss;
        }

        public void setSs(String ss) {
            this.ss = ss;
        }

        public String getTmp_max() {
            return tmp_max;
        }

        public void setTmp_max(String tmp_max) {
            this.tmp_max = tmp_max;
        }

        public String getTmp_min() {
            return tmp_min;
        }

        public void setTmp_min(String tmp_min) {
            this.tmp_min = tmp_min;
        }

        public String getUv_index() {
            return uv_index;
        }

        public void setUv_index(String uv_index) {
            this.uv_index = uv_index;
        }

        public String getVis() {
            return vis;
        }

        public void setVis(String vis) {
            this.vis = vis;
        }

        public String getWind_dir() {
            return wind_dir;
        }

        public void setWind_dir(String wind_dir) {
            this.wind_dir = wind_dir;
        }

        public String getWind_sc() {
            return wind_sc;
        }

        public void setWind_sc(String wind_sc) {
            this.wind_sc = wind_sc;
        }

        public String getWind_spd() {
            return wind_spd;
        }

        public void setWind_spd(String wind_spd) {
            this.wind_spd = wind_spd;
        }
    }

    public static class HourlyBean {
        /**
         * cloud : 86
         * cond_code : 305
         * cond_txt : 小雨
         * dew : 11
         * hum : 64
         * pop : 7
         * pres : 1008
         * time : 2018-03-02 16:00
         * tmp : 16
         * wind_deg : 119
         * wind_dir : 无持续风向
         * wind_sc : 微风
         * wind_spd : 4
         */

        private String cond_code;
        private String cond_txt;
        private String time;
        private String tmp;

        public String getCond_code() {
            return cond_code;
        }

        public void setCond_code(String cond_code) {
            this.cond_code = cond_code;
        }

        public String getCond_txt() {
            return cond_txt;
        }

        public void setCond_txt(String cond_txt) {
            this.cond_txt = cond_txt;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getTmp() {
            return tmp;
        }

        public void setTmp(String tmp) {
            this.tmp = tmp;
        }
    }

    public static class LifestyleBean {
        /**
         * brf : 舒适
         * txt : 白天不太热也不太冷，风力不大，相信您在这样的天气条件下，应会感到比较清爽和舒适。
         * type : comf
         */

        private String brf;
        private String txt;
        private String type;

        public String getBrf() {
            return brf;
        }

        public void setBrf(String brf) {
            this.brf = brf;
        }

        public String getTxt() {
            return txt;
        }

        public void setTxt(String txt) {
            this.txt = txt;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
