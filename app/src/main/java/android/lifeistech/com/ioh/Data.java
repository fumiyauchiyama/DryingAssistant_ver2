package android.lifeistech.com.ioh;

/**
 * Created by fumiyauchiyama on 2017/07/23.
 */

public class Data {

    public int    year;
    public int    month;
    public int    day;
    public int    time;
    public int    tem;
    public int    hun;
    public String weather;
    public String memo;

    public Data(int month, int day, int time, int tem, int hun, String weather, String memo,int year){

        this.year    = year;
        this.month   = month;
        this.day     = day;
        this.time    = time;
        this.tem     = tem;
        this.hun     = hun;
        this.weather = weather;
        this.memo    = memo;

    }

}
