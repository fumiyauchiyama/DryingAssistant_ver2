package android.lifeistech.com.ioh;

/**
 * Created by fumiyauchiyama on 2017/07/16.
 */

public class WaterData {

    int number;
    int number1;

    //からのコンストラクタ
    public WaterData(){



    }

    public WaterData(int number, int number1){
        this.number = number;
        this.number1 = number1;
    }

    public int getnumber(){return number;}

    public void setnumber(int ch0){this.number = number;}

    public int getNumber1(){return number1;}

    public void setNumber1(int ch1){this.number1 = number1;}
}
