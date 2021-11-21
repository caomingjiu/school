package com.example.zijin.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Tao
 * @version 1.0
 * @ClassName StringUtil
 * @Description TODO
 * @date 2021-04-04 12:06
 **/
public class StringUtil {
    private final static int LENGTH = 6;
    private final static int LENGTH1 = 7;

    public static void main(String[] args) throws ParseException {
       /* ArrayList<Group> list = new ArrayList<>();
        Group group1 = Group.builder().sortId(15).build();
        list.add(group1);
        Group group2 = Group.builder().sortId(19).build();
        list.add(group2);
        Group group3 = Group.builder().sortId(4).build();
        list.add(group3);
        DateComparator d =new DateComparator();
        System.out.println("最大值"+ Collections.max(list,d).getSortId());
        System.out.println("最小值"+ Collections.min(list,d).getSortId());*/
//        String startTime = "2019-12-29 11:00";
//        String endTime = "2019-12-30 10:30";
//        Calendar startCal = Calendar.getInstance();
//        Calendar endCal = Calendar.getInstance();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
//
//        Date startDate =  sdf.parse(startTime);
//        Date endDate = sdf.parse(endTime);
//        startCal.setTime(startDate);
//        endCal.setTime(endDate);
//        List<String> list = new ArrayList<>();
//
//        int days = ((int)(endCal.getTime().getTime()/1000)-(int)(startCal.getTime().getTime()/1000))/3600/24;
//        System.out.println(startCal.getTime());
//        System.out.println(endCal.getTime());
//
//        System.out.println(startTime.substring(11,13));
//        System.out.println("相差天数："+days);
        System.out.println(isMobile("1999999341"));


    }
    /**
     * startTime  起始日期
     * endTime   截止日期
     * dateType  日期类型 HOUR/DAY/MONTH/YEAR
     */
    public Integer dateTimeNums(String startTime,String endTime,String dateType) throws ParseException{
        String fmtStr = null;
        int timeNum = 3600 * 24;
        switch(dateType){
            case "HOUR" :
                fmtStr = "yyyy-MM-dd HH";
                timeNum = 3600;
                break;
            case "DAY" :
                fmtStr = "yyyy-MM-dd";
                timeNum = 3600 * 24 ;
                break;
            case "MONTH" :
                fmtStr = "yyyy-MM";
                timeNum = 3600 * 24 * 30;
                break;
            case "YEAR" :
                fmtStr = "yyyy";
                timeNum = 3600 * 24 * 365 ;
                break;
            default :
                fmtStr = "yyyy-MM-dd";
                break;
        }

        Calendar startCal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(fmtStr);//此处修改日期格式
        Date startDate =  sdf.parse(startTime);
        Date endDate = sdf.parse(endTime);
        startCal.setTime(startDate);
        endCal.setTime(endDate);
        startCal.compareTo(endCal);
        //得到两个日期相差的天数
        int nums = ((int)(endCal.getTime().getTime()/1000)-(int)(startCal.getTime().getTime()/1000))/timeNum;//此处修改日期单位
        return nums;
    }
//    public static class DateComparator implements Comparator<Group>{
//
//        @Override
//        public int compare(Group o1, Group o2) {
//            return (o1.getSortId()<o2.getSortId()?-1:(o1.getSortId()==o2.getSortId()?0:1));
//        }
//    }
    /**
     * 获取六位随机短信验证码
     * @return
     */
    public static String getVerifyCode(){
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0;i<LENGTH;i++){
            stringBuffer.append(random.nextInt(10));
        }
        return stringBuffer.toString();
    }

    /**
     * 随机生成7位邀请码
     * @return
     */
    public static String getInvitationCode(){
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(random.nextInt(9)+1);
        for (int i = 1;i<LENGTH1;i++){
            stringBuffer.append(random.nextInt(10));
        }
        return stringBuffer.toString();
    }
    public static String getRandomString(){
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        int index;
        for (int i=0 ; i< LENGTH ; i++){
            //随机生成0、1、2三个整数，代表数字字符、大写字母、小写字母，保证验证码的组成比较正态随机
            index = random.nextInt(3);
            char result = getChar(index);
            //追加到可变长字符串
            stringBuilder.append(result);
        }
        return stringBuilder.toString();
    }

    private static char getChar(int item) {
        //数字字符范围
        int digitalBound = 10 ;
        //字符返回
        int charBound = 26 ;
        Random random = new Random();
        int index;
        char c;
        //根据调用时候的三个选项，生成数字、大写字母、小写字母三种不同的字符
        if(item == 0){
            index = random.nextInt(digitalBound);
            c = (char) ('0' + index);
        }else if (item == 1 ){
            index = random.nextInt(charBound);
            c = (char)('A' + index);
        } else {
            index = random.nextInt(charBound);
            c = (char)('a' + index);
        }
        return c;
    }

    /**
     * 正则判断手机号码
     *
     * @param str
     * @return
     */
    public static boolean isMobile(final String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,6,9,5,7,8][0-9]{9}$");
        m = p.matcher(str);
        b = m.matches();
        return b;
    }
}
