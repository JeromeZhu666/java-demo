package xin.jerome.java.java8.time;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

/**
 * 新时间api
 *
 * @author Jerome Zhu
 * @since 2018.08.24 10:07
 */
public class TestTime {

    // ZoneDate
    @Test
    public void testZoneDate() {
        /*Set<String> set = ZoneId.getAvailableZoneIds();
        set.forEach(System.out::println);*/
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
        System.out.println(now.atZone(ZoneId.of("Asia/Shanghai")));


    }

    // DatetimeFormatter 格式化日期
    @Test
    public void testDateTimeFormatter() {
        DateTimeFormatter dtf = DateTimeFormatter.ISO_DATE;
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime.format(dtf));

        DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
        System.out.println(dtf1.format(localDateTime));

        System.out.println(LocalDateTime.parse((dtf1.format(localDateTime)), dtf1));
    }

    // TemporalAdjuster 时间校正器
    @Test
    public void testTemporalAdjuster() {
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);

        LocalDateTime localDateTime1 = localDateTime.withDayOfMonth(10);
        System.out.println(localDateTime1);

        System.out.println(localDateTime.with(TemporalAdjusters.next(DayOfWeek.SUNDAY)));
    }

    // Period 计算两个日期之间的间隔
    @Test
    public void testPeriod() {
        LocalDate localDate = LocalDate.of(2015,9,1);
        LocalDate localDate1 = LocalDate.now();
        System.out.println(Period.between(localDate, localDate1).toTotalMonths());
    }

    // Duration 计算两个时间之间的间隔
    @Test
    public void testDuration() throws InterruptedException {
        Instant i1 = Instant.now();
        Thread.sleep(1000);
        Instant i2 = Instant.now();
        Duration duration = Duration.between(i1, i2);
        System.out.println(duration.toMillis());

        LocalTime localTime = LocalTime.now();
        Thread.sleep(2000);
        LocalTime localTime1 = LocalTime.now();
        System.out.println(Duration.between(localTime, localTime1).toMillis());

    }

    // Instant 时间戳(以Unix元年：1970.1.1 0:0:0  到现在的毫秒值)
    @Test
    public void testInstant() {
        Instant instant = Instant.now(); //默认获取 UTC 时区
        System.out.println(instant);

        OffsetDateTime offsetDateTime = instant.atOffset(ZoneOffset.ofHours(8));
        System.out.println(offsetDateTime);

        System.out.println(Instant.now().toEpochMilli());

        Instant instant1 = Instant.ofEpochSecond(60);
        System.out.println(instant1);
    }

    // LocalDateTime   用于人读取的
    @Test
    public void testLocalDateTime() {
        LocalDateTime ldt = LocalDateTime.now();
        System.out.println(ldt);

        ldt = LocalDateTime.of(2018,8,4,10,13);
        System.out.println(ldt);

        ldt = ldt.plusHours(24);
        System.out.println(ldt);

        ldt = ldt.minusWeeks(2);
        System.out.println(ldt);

        System.out.println(ldt.getYear() +"-" +ldt.getMonthValue()+"-"+ ldt.getDayOfMonth());

    }


}
