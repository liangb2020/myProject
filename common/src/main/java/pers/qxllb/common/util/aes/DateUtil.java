package pers.qxllb.common.util.aes;

import org.bouncycastle.util.test.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * TODO
 *
 * @author liangb
 * @version 1.0
 * @date 2021/3/31 14:15
 */
public class DateUtil {

    public static Long getTimeInSecond(int offset) {
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, offset);
        return calendar.getTimeInMillis() / 1000;
    }

    /**
     * 获取当前月天：0407
     * @return
     */
    private static String getMonthDay(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMdd"));
    }

    /**
     * 获取当前到24点钱剩余的秒数
     * @return 返回秒数
     */
    public static Integer getDayRemainingTime() {

        Date currentDate=new Date();
        LocalDateTime midnight = LocalDateTime.ofInstant(currentDate.toInstant(),
                ZoneId.systemDefault()).plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);

        LocalDateTime currentDateTime = LocalDateTime.ofInstant(currentDate.toInstant(),ZoneId.systemDefault());
        long seconds = ChronoUnit.SECONDS.between(currentDateTime, midnight);
        return (int) seconds;
    }

    public static Integer getDayRemainingTime(Date currentDate) {
        LocalDateTime midnight = LocalDateTime.ofInstant(currentDate.toInstant(),
                ZoneId.systemDefault()).plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);

        LocalDateTime currentDateTime = LocalDateTime.ofInstant(currentDate.toInstant(),ZoneId.systemDefault());
        long seconds = ChronoUnit.SECONDS.between(currentDateTime, midnight);
        return (int) seconds;
    }

    public static void main(String[] args){

        System.out.println(getTimeInSecond(-10));
        System.out.println(getTimeInSecond(0));

        System.out.println(getMonthDay());

        //获取当天剩余秒数
        System.out.println(getDayRemainingTime(new Date()));
        //获取当天剩余秒数
        System.out.println(getDayRemainingTime());

        System.out.println(LocalDate.now().toString());

    }

}
