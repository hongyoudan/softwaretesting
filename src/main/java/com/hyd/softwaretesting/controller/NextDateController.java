package com.hyd.softwaretesting.controller;

import com.hyd.softwaretesting.util.JacksonUtil;
import com.hyd.softwaretesting.util.ResponseUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by : hayden
 * Date : 2021/10/22
 * Description :
 */
@RestController
public class NextDateController {

    /**
     * 日期下一天：
     * 输入三个整数：month、day和year，函数的输出为输入日期后一天的日期。
     * 例如，输入为2021年10月22日，则函数的输出为2021年10月23日，year满足1920≤year≤2050。
     */
    @PostMapping("/nextdate")
    public Object nextDate(@RequestBody String body) {
        String year = JacksonUtil.parseString(body, "year");
        String month = JacksonUtil.parseString(body, "month");
        String day = JacksonUtil.parseString(body, "day");
        System.out.println("[year]：" + year + ",[month]：" + month + ",[day]：" + day);

        assert year != null;
        assert month != null;
        assert day != null;

        try {
            if (year.isEmpty() || month.isEmpty() || day.isEmpty()
                    || Integer.parseInt(year) <= 0 || Integer.parseInt(month) <= 0 || Integer.parseInt(day) <= 0) {
                System.out.println("您输入的日期错误！");
                return ResponseUtil.fail(402, "您输入的日期错误！");
            }
            if (Integer.parseInt(year)<1920||Integer.parseInt(year)>2050){
                System.out.println("年份必须在1920-2050之间！");
                return ResponseUtil.fail(402, "年份必须在1920-2050之间！");
            }
            if ((Integer.parseInt(month) == 1 && Integer.parseInt(day) > 31) || (Integer.parseInt(month) == 3 && Integer.parseInt(day) > 31)
                    || (Integer.parseInt(month) == 4 && Integer.parseInt(day) > 30) || (Integer.parseInt(month) == 5 && Integer.parseInt(day) > 31)
                    || (Integer.parseInt(month) == 6 && Integer.parseInt(day) > 30) || (Integer.parseInt(month) == 7 && Integer.parseInt(day) > 31)
                    || (Integer.parseInt(month) == 8 && Integer.parseInt(day) > 31) || (Integer.parseInt(month) == 9 && Integer.parseInt(day) > 30)
                    || (Integer.parseInt(month) == 10 && Integer.parseInt(day) > 31) || (Integer.parseInt(month) == 11 && Integer.parseInt(day) > 30)
                    || (Integer.parseInt(month) == 12 && Integer.parseInt(day) > 31)) {
                System.out.println("月份天数不对！");
                return ResponseUtil.fail(402, "月份天数不对！");
            }

            if (Integer.parseInt(month) == 2) {
                if (Integer.parseInt(year) % 4 == 0 && Integer.parseInt(year) % 100 != 0 || Integer.parseInt(year) % 400 == 0) {
                    if (Integer.parseInt(day) < 1 || Integer.parseInt(day) > 29) {
                        System.out.println("闰年2月要1-29天！");
                        return ResponseUtil.fail(402, "闰年2月要1-29天！");
                    } else {
                        return getObject(year, month, day);
                    }

                } else {
                    if (Integer.parseInt(day) < 1 || Integer.parseInt(day) > 28) {
                        System.out.println("平年2月要1-28天！");
                        return ResponseUtil.fail(402, "平年2月要1-28天！");
                    } else {
                        return getObject(year, month, day);
                    }
                }

            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return ResponseUtil.fail(402, "您输入的日期错误！");
        }

        return getObject(year, month, day);

    }

    private Object getObject(String year, String month, String day) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            //取控制台输入
            String sDate = year + "-" + month + "-" + day;
            //解析日期
            Date date = simpleDateFormat.parse(sDate);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            //用Calendar工具类给日期加一天
            c.add(Calendar.DATE, 1);
            System.out.println("[下一天]：" + simpleDateFormat.format(c.getTime()));
            return ResponseUtil.ok(simpleDateFormat.format(c.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
            System.err.println("不能解析的日期格式");
            return ResponseUtil.fail(402, "您输入的日期错误！");
        }
    }

}
