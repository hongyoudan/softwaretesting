package com.hyd.softwaretesting.controller;

import com.hyd.softwaretesting.util.JacksonUtil;
import com.hyd.softwaretesting.util.ResponseUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by : hayden
 * Date : 2021/10/22
 * Description :
 */
@RestController
public class TriangleController {

    /**
     * 三角形问题：
     * 规定输入三个整数a、b、c分别作为三边的边长构成三角形。
     * 通过程序判定所构成的三角形的类型（等边三角形、等腰三角形、一般三角形、构不成三角形），
     * 并在屏幕上输出。
     */
    @PostMapping("/triangle")
    public Object triangle(@RequestBody String body) {
        String a = JacksonUtil.parseString(body, "a");
        String b = JacksonUtil.parseString(body, "b");
        String c = JacksonUtil.parseString(body, "c");
        System.out.println("[a]：" + a + ",[b]：" + b + ",[c]：" + c);

        String pattern = "^([1-9]|[1-9]\\d|1\\d{2}|200)$";
        Pattern patt = Pattern.compile(pattern);
        assert a != null;
        Matcher matA = patt.matcher(a);
        assert b != null;
        Matcher matB = patt.matcher(b);
        assert c != null;
        Matcher matC = patt.matcher(c);

        if (a.isEmpty() || b.isEmpty() || c.isEmpty()) {
            System.out.println("请输入三角形的边长！");
            return ResponseUtil.fail(402, "请输入三角形的边长！");
        } else if (!matA.matches() || !matB.matches() || !matC.matches()) {
            System.out.println("您输入的值无效！");
            return ResponseUtil.fail(402, "您输入的值无效！");
        } else if (Integer.parseInt(a) + Integer.parseInt(b) > Integer.parseInt(c) && Integer.parseInt(b) + Integer.parseInt(c) > Integer.parseInt(a) && Integer.parseInt(a) + Integer.parseInt(c) > Integer.parseInt(b)) {
            if (a.equals(b) && a.equals(c)) {
                System.out.println("等边三角形");
                return ResponseUtil.ok("等边三角形");
            } else if (a.equals(b) || a.equals(c) || b.equals(c)) {
                System.out.println("等腰三角形");
                return ResponseUtil.ok("等腰三角形");
            } else {
                System.out.println("一般三角形");
                return ResponseUtil.ok("一般三角形");
            }

        } else {
            System.out.println("构不成三角形");
            return ResponseUtil.ok("构不成三角形");
        }

    }
}
