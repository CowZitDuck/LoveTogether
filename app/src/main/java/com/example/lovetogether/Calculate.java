package com.example.lovetogether;

public class Calculate {
    private static final int n1 = 1;
    private static final int t1 = 1;
    private static final int nam1 = 1970;

    public static int convertTime(String type, String date) {
        int res = 0;
        int start = 0, end = 0;
        switch (type) {
            case "1": start = 0; end = 2; break;
            case "2": start = 3; end = 5; break;
            case "3": start = 6; end = 10; break;
        }
        for(int i = start; i < end; i++) {
            res *= 10;
            res += date.charAt(i) - '0';
        }
        return res;
    }

    public static long TimeToMillisSecond(int n2, int t2, int nam2) {
        int k,i,dem=0,t=0,c,b=0,d=0;
        int songay1 = 0,songay2 = 0,sonam;
        long songay;
        if(nam1%400==0 || (nam1%4==0 & nam1%100!=0)){
            switch(t1){
                case 1:
                {
                    songay1=n1;
                    break;}
                case 2: {
                    songay1=31+n1;
                    break;}
                case 3:
                {
                    songay1=60+n1;
                    break;
                }
                case 4:
                {
                    songay1=91+n1;
                    break;}
                case 5:
                {
                    songay1=121+n1;
                    break;}
                case 6:
                {
                    songay1=152+n1;
                    break;}
                case 7:
                {
                    songay1=182+n1;
                    break;}
                case 8:
                {
                    songay1=213+n1;
                    break;}
                case 9:
                {
                    songay1=244+n1;
                    break;}
                case 10:
                {
                    songay1=274+n1;
                    break;}
                case 11:
                {
                    songay1=305+n1;
                    break;}
                case 12:
                {
                    songay1=335+n1;
                    break;}
            }
        }
        else {
            switch(t1){
                case 1:
                {
                    songay1=n1;
                    break;}
                case 2: {
                    songay1=31+n1;
                    break;}
                case 3:
                {
                    songay1=59+n1;
                    break;
                }
                case 4:
                {
                    songay1=90+n1;
                    break;}
                case 5:
                {
                    songay1=120+n1;
                    break;}
                case 6:
                {
                    songay1=151+n1;
                    break;}
                case 7:
                {
                    songay1=181+n1;
                    break;}
                case 8:
                {
                    songay1=212+n1;
                    break;}
                case 9:
                {
                    songay1=243+n1;
                    break;}
                case 10:
                {
                    songay1=273+n1;
                    break;}
                case 11:
                {
                    songay1=304+n1;
                    break;}
                case 12:
                {
                    songay1=334+n1;
                    break;}
            }
        }
        if(nam2%400==0|( nam2%4==0 & nam2%100!=0)){
            switch(t2){
                case 1:
                {
                    songay2=n2;
                    break;}
                case 2: {
                    songay2=31+n2;
                    break;}
                case 3:
                {
                    songay2=60+n2;
                    break;
                }
                case 4:
                {
                    songay2=91+n2;
                    break;}
                case 5:
                {
                    songay2=121+n2;
                    break;}
                case 6:
                {
                    songay2=152+n2;
                    break;}
                case 7:
                {
                    songay2=182+n2;
                    break;}
                case 8:
                {
                    songay2=213+n2;
                    break;}
                case 9:
                {
                    songay2=244+n2;
                    break;}
                case 10:
                {
                    songay2=274+n2;
                    break;}
                case 11:
                {
                    songay2=305+n2;
                    break;}
                case 12:
                {
                    songay2=335+n2;
                    break;}
            }
        }
        else {
            switch(t2){
                case 1:
                {
                    songay2=n2;
                    break;}
                case 2: {
                    songay2=31+n2;
                    break;}
                case 3:
                {
                    songay2=59+n2;
                    break;
                }
                case 4:
                {
                    songay2=90+n2;
                    break;}
                case 5:
                {
                    songay2=120+n2;
                    break;}
                case 6:
                {
                    songay2=151+n2;
                    break;}
                case 7:
                {
                    songay2=181+n2;
                    break;}
                case 8:
                {
                    songay2=212+n2;
                    break;}
                case 9:
                {
                    songay2=243+n2;
                    break;}
                case 10:
                {
                    songay2=273+n2;
                    break;}
                case 11:
                {
                    songay2=304+n2;
                    break;}
                case 12:
                {
                    songay2=334+n2;
                    break;}
            }
        }
        if(nam1==nam2){
            songay=songay2-songay1;
            return songay * (1000 * 60 * 60 * 24);
        }
        else{
            if(nam1%4==0 & nam1%100!=0 & nam2%4==0 & nam2%100!=0|nam1%400==0 & nam2%4==0 & nam2%100!=0|nam1%4==0 & nam1%100!=0 & nam2%400==0 ){
                for(i=nam1+1;i<=nam2-1;i++){
                    if( i%100==0 & i%400!=0) dem=dem+1;
                }
                if (dem==0){
                    k=(nam2-nam1)/4;
                    if(k==1) {
                        songay=366-songay1+365*3+songay2;
                        return songay * (1000 * 60 * 60 * 24);
                    }
                    if(k>1){
                        if(n2==1 &t2==1)
                            songay=(nam2-nam1-k-1)*365+k*366+366-songay1+songay2-1;
                        else songay=(nam2-nam1-k-1)*365+k*366+366-songay1+songay2;
                        return songay * (1000 * 60 * 60 * 24);
                    }
                }
                if(dem>0){
                    k=(nam2-nam1)/4;
                    songay=(nam2-nam1-k+1)*365+(k-2)*366+366-songay1+songay2;
                    return songay * (1000 * 60 * 60 * 24);

                }
            }
            if (nam1%4==0 & nam1%100!=0 & nam2%4!=0|nam1%400==0 &nam2%4!=0){
                for(c=nam1+1;c<nam2;c++) {
                    if(c%4==0 & c%100!=0|c%400==0) b=b+1;
                }
                if(b==0){
                    songay=365*(nam2-nam1)+366-songay1+songay2;
                    return songay * (1000 * 60 * 60 * 24);

                }
                if(b>0){
                    songay=366-songay1+songay2+365*(nam2-nam1-b-1)+366*b;
                    return songay * (1000 * 60 * 60 * 24);

                }
            }
            if(nam2%4==0 & nam2%100!=0 & nam1%4!=0 |nam2%400==0 & nam1%4!=0){
                for(c=nam1+1;c<nam2;c++)
                    if(c%4==0 & c%100!=0|c%400==0) d=d+1;
                if(d==0){
                    songay=songay2+365-songay1+365*(nam2-nam1-1);
                    return songay * (1000 * 60 * 60 * 24);

                }
                if(d>0){
                    songay=songay2+365-songay1+366*d+365*(nam2-nam1-d-1);
                    return songay * (1000 * 60 * 60 * 24);

                }

            }
            if( nam1%4!=0  & nam2%4!=0 ){

                for(c=nam1;c<=nam2;c++){
                    if(c%4==0 & c%100!=0|c%400==0) t=t+1;
                }
                if(t==0) {
                    songay=365-songay1+songay2+365*(nam2-nam1-1);
                    return songay * (1000 * 60 * 60 * 24);

                }
                if(t>0) {
                    songay=songay2+365-songay1+366*t+365*(nam2-nam1-t-1);
                    return songay * (1000 * 60 * 60 * 24);

                }
            }
        }
        return 0;
    }
}
