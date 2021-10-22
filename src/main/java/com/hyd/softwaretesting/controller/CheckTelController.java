package com.hyd.softwaretesting.controller;

import com.hyd.softwaretesting.util.JacksonUtil;
import com.hyd.softwaretesting.util.ResponseUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by : hayden
 * Date : 2021/10/22
 * Description :
 */
@RestController
public class CheckTelController {

    /**
     * 电话号码有效性检查功能:
     * 执行电话号码有效性检查功能，中国的固定电话号码由两部分组成。这两部分的名称和内容分别是：
     * 地区码(rc)：以0开头的三位或者四位数字（包括0）。
     * 电话号码(n)：以非0、非1开头的七位或者八位数字。
     */
    @PostMapping("/checktel")
    public Object checkTel(@RequestBody String body) {
        String rc = JacksonUtil.parseString(body, "rc");
        String n = JacksonUtil.parseString(body, "n");
        System.out.println("[rc]：" + rc + ",[n]：" + n);

        String pattRC = "^([0][0-9]{2,3})$";
        String pattN = "^([2-9][0-9]{6,7})$";

        Pattern patRC = Pattern.compile(pattRC);
        Pattern patN = Pattern.compile(pattN);
        assert rc != null;
        Matcher matRC = patRC.matcher(rc);
        assert n != null;
        Matcher matN = patN.matcher(n);

        if (matRC.matches()&&matN.matches()){
            System.out.println("您输入的电话号码有效！");
            return ResponseUtil.ok("您输入的电话号码有效！");
        }else {
            System.out.println("您输入的电话号码无效！");
            return ResponseUtil.fail(402, "您输入的电话号码无效！");
        }


    }
}
