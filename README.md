# 《软件测试》实验程序

<p align="left" style="">
  <img src="https://img.shields.io/github/last-commit/hongyoudan/softwaretesting"></img>
	<img src="https://img.shields.io/github/languages/count/hongyoudan/softwaretesting"></img>
<img src="https://img.shields.io/github/stars/hongyoudan/softwaretesting?style=social"></img>
<img src="https://img.shields.io/github/watchers/hongyoudan/softwaretesting?style=social"></img>
</p>

**整理不易，欢迎 `Star` 和 `Fork` ^_^ ，谢谢~~**

## 1 技术栈

### 1.1 前端

开发工具：WebStorm

框架：Vue 3

Web UI：Element

请求：Axios

路由：Vue Router

### 1.2 后端

JDK 1.8

开发工具：IDEA 

框架： SpringBoot 2.x

### 1.3 测试

测试工具：Postman

## 2 项目简介

### 2.1 项目架构

前后端分离

**结构**

```java
.
├── software-testing-web // 前端项目
├── software-testing-springboot // 后台项目
   ├── src
       ├── main
          ├── java
          │   └── com
          │       └── hyd
          │           └── softwaretesting
          │               ├── SoftwareTestingApplication.java
          │               ├── config
          │               │   └── CorsConfig.java
          │               ├── controller
          │               │   ├── CheckTelController.java
          │               │   ├── NextDateController.java
          │               │   └── TriangleController.java
          │               └── util
          │                   ├── JacksonUtil.java
          │                   └── ResponseUtil.java
          └── resources
              ├── application.yml
              └── banner.txt
```

![截屏2021-10-23 上午12.04.46](https://img-blog.csdnimg.cn/0e5384229489449795914da1073e5927.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBA57u_6Iy25ZOl5ZOl,size_20,color_FFFFFF,t_70,g_se,x_16#pic_center)

### 2.2 实现功能

#### 2.2.1 三角形问题

##### 模块需求

规定输入三个整数a、b、c分别作为三边的边长构成三角形。
通过程序判定所构成的三角形的类型（等边三角形、等腰三角形、一般三角形、构不成三角形），并在屏幕上输出。

##### 测试用例

|       模拟等价       |  a   |  b   |  c   |         输出         |
| :------------------: | :--: | :--: | :--: | :------------------: |
|       输入空值       | null |  1   |  1   | 请输入三角形的边长！ |
|         0值          |  0   |  0   |  0   |   您输入的值无效！   |
|  输入值小于1的整数   |  -1  |  -1  |  -1  |   您输入的值无效！   |
|     输入值为小数     | 1.1  | 1.1  | 1.1  |   您输入的值无效！   |
|      输入值为1       |  1   |  1   |  1   |      等边三角形      |
|     输入值为200      | 200  | 200  | 200  |      等边三角形      |
|     输入值为201      | 201  | 201  | 201  |   您输入的值无效！   |
|     输入值为字符     |  a   |  a   |  a   |   您输入的值无效！   |
| 输入值构成一般三角形 |  3   |  4   |  5   |      一般三角形      |
| 输入值构成等腰三角形 |  2   |  2   |  3   |      等腰三角形      |
|  输入值不构成三角形  |  1   |  1   |  2   |     构不成三角形     |

##### 相关代码

```java
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
        System.out.println("[a]=" + a + ",[b]=" + b + ",[c]=" + c);

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
```

#### 2.2.2 电话号码有效性检查功能

##### 模块需求

执行电话号码有效性检查功能。
中国的固定电话号码由两部分组成。这两部分的名称和内容分别是：
地区码：以0开头的三位或者四位数字（包括0）。
电话号码：以非0、非1开头的七位或者八位数字。

##### 测试用例

|           模拟等价           |  rc   |     n     |          输出          |
| :--------------------------: | :---: | :-------: | :--------------------: |
|           输入空值           | null  |   null    | 您输入的电话号码无效！ |
|             0值              |   0   |     0     | 您输入的电话号码无效！ |
|             负值             | -012  | -4567890  | 您输入的电话号码无效！ |
|            小数值            | 01.2  | 456789.0  | 您输入的电话号码无效！ |
|  rc以非0开头的三位有效数字   |  123  | 45678900  | 您输入的电话号码无效！ |
|   rc以0开头的二位有效数字    |  01   | 45678900  | 您输入的电话号码无效！ |
|   rc以0开头的三位有效数字    |  012  | 45678900  | 您输入的电话号码有效！ |
|   rc以0开头的四位有效数字    | 0123  | 45678900  | 您输入的电话号码有效！ |
|   rc以0开头的五位有效数字    | 01234 | 45678900  | 您输入的电话号码无效！ |
|    n以0开头的七位有效数字    |  012  |  0123456  | 您输入的电话号码无效！ |
|    n以1开头的七位有效数字    |  012  |  1234567  | 您输入的电话号码无效！ |
| n以非0/非1开头的六位有效数字 |  012  |  234567   | 您输入的电话号码无效！ |
| n以非0/非1开头的七位有效数字 |  012  |  2345678  | 您输入的电话号码有效！ |
| n以非0/非1开头的八位有效数字 |  012  | 23456789  | 您输入的电话号码有效！ |
| n以非0/非1开头的九位有效数字 |  012  | 234567890 | 您输入的电话号码无效！ |

##### 相关代码

```java
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
        System.out.println("[rc=]" + rc + ",[n=]" + n);

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
```

#### 2.2.3 日期下一天功能

##### 模块需求

输入三个整数：年、月和日，函数的输出为输入日期后一天的日期。
例如，输入为2021年10月22日，则函数的输出为2021年10月23日，
年满足[1920,2050]。

##### 测试用例

|           模拟等价            | year  | month | day  |           输出            |
| :---------------------------: | :---: | :---: | :--: | :-----------------------: |
|             空值              | null  | null  | null |    您输入的日期错误！     |
|              0值              |   0   |   0   |  0   |    您输入的日期错误！     |
|             负值              | -2021 |  -10  | -22  |    您输入的日期错误！     |
|            字符值             | 202a  |  1a   |  2a  |    您输入的日期错误！     |
|         年份小于1920          | 1919  |  10   |  22  | 年份必须在1920-2050之间！ |
|         年份大于2050          | 2051  |  10   |  22  | 年份必须在1920-2050之间！ |
| 年份为闰年，月份为2月，日为30 | 2008  |   2   |  30  |     闰年2月要1-29天！     |
| 年份为闰年，月份为2月，日为29 | 2008  |   2   |  29  |        2008-03-01         |
| 年份为平年，月份为2月，日为28 | 2021  |   2   |  28  |        2021-03-01         |
| 年份为平年，月份为2月，日为29 | 2021  |   2   |  29  |     平年2月要1-28天！     |
|       月份为1，日期为31       | 2021  |   1   |  31  |        2021-02-01         |
|       月份为1，日期为32       | 2021  |   1   |  32  |     该月份天数不对！      |
|       月份为3，日期为31       | 2021  |   3   |  31  |        2021-04-01         |
|       月份为3，日期为32       | 2021  |   3   |  32  |     该月份天数不对！      |
|       月份为4，日期为30       | 2021  |   4   |  30  |        2021-05-01         |
|       月份为4，日期为31       | 2021  |   4   |  31  |     该月份天数不对！      |
|       月份为5，日期为31       | 2021  |   5   |  31  |        2021-06-01         |
|       月份为5，日期为32       | 2021  |   5   |  32  |     该月份天数不对！      |
|       月份为6，日期为30       | 2021  |   6   |  30  |        2021-07-01         |
|       月份为6，日期为31       | 2021  |   6   |  31  |     该月份天数不对！      |
|       月份为7，日期为31       | 2021  |   7   |  31  |        2021-08-01         |
|       月份为7，日期为32       | 2021  |   7   |  32  |     该月份天数不对！      |
|       月份为8，日期为31       | 2021  |   8   |  31  |        2021-09-01         |
|       月份为8，日期为32       | 2021  |   8   |  32  |     该月份天数不对！      |
|       月份为9，日期为30       | 2021  |   9   |  30  |        2021-10-01         |
|       月份为9，日期为31       | 2021  |   9   |  31  |     该月份天数不对！      |
|      月份为10，日期为31       | 2021  |  10   |  31  |        2021-11-01         |
|      月份为10，日期为32       | 2021  |  10   |  32  |     该月份天数不对！      |
|      月份为11，日期为30       | 2021  |  11   |  30  |        2021-12-01         |
|      月份为11，日期为31       | 2021  |  11   |  31  |     该月份天数不对！      |
|      月份为12，日期为31       | 2021  |  12   |  31  |        2022-01-01         |
|      月份为12，日期为32       | 2021  |  12   |  32  |     该月份天数不对！      |

##### 相关代码

```java
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
```

## 附.常用的正则表达式

### 一、校验数字的表达式

数字：`^[0-9]*$`

n位的数字：`^\d{n}$`

至少n位的数字：`^\d{n,}$`

m-n位的数字：`^\d{m,n}$`

零和非零开头的数字：`^(0|[1-9][0-9]*)$`

非零开头的最多带两位小数的数字：`^([1-9][0-9]*)+(\.[0-9]{1,2})?$`

带1-2位小数的正数或负数：`^(\-)?\d+(\.\d{1,2})$`

正数、负数、和小数：`^(\-|\+)?\d+(\.\d+)?$`

有两位小数的正实数：`^[0-9]+(\.[0-9]{2})?$`

有1~3位小数的正实数：`^[0-9]+(\.[0-9]{1,3})?$`

非零的正整数：`^[1-9]\d$ 或 ^(1-9){1,3}$ 或 ^+?1-9$`

非零的负整数：`^-1-90-9"$ 或 ^-[1-9]\d$`

非负整数：`^\d+$ 或 ^[1-9]\d*|0$`

非正整数：`^-[1-9]\d|0$ 或 ^((-\d+)|(0+))$`

非负浮点数：`^\d+(.\d+)?$ 或 ^[1-9]\d.\d|0.\d[1-9]\d|0?.0+|0$`

非正浮点数：`^((-\d+(.\d+)?)|(0+(.0+)?))$ 或 ^(-([1-9]\d.\d|0.\d[1-9]\d))|0?.0+|0$`

正浮点数：`^[1-9]\d.\d|0.\d[1-9]\d$ 或 ^(([0-9]+.[0-9]1-9)|([0-9]1-9.[0-9]+)|([0-9]1-9))$`

负浮点数：`^-([1-9]\d.\d|0.\d[1-9]\d)$ 或 ^(-(([0-9]+.[0-9]1-9)|([0-9]1-9.[0-9]+)|([0-9]1-9)))$`

浮点数：`^(-?\d+)(.\d+)?$ 或 ^-?([1-9]\d.\d|0.\d[1-9]\d|0?.0+|0)$`

### 二、校验字符的表达式

汉字：`^[\u4e00-\u9fa5]{0,}$`

英文和数字：`^[A-Za-z0-9]+$ 或 ^[A-Za-z0-9]{4,40}$`

长度为3-20的所有字符：`^.{3,20}$`

由26个英文字母组成的字符串：`^[A-Za-z]+$`

由26个大写英文字母组成的字符串：`^[A-Z]+$`

由26个小写英文字母组成的字符串：`^[a-z]+$`

由数字和26个英文字母组成的字符串：`^[A-Za-z0-9]+$`

由数字、26个英文字母或者下划线组成的字符串：`^\w+$ 或 ^\w{3,20}$`

中文、英文、数字包括下划线：`^[\u4E00-\u9FA5A-Za-z0-9_]+$`

中文、英文、数字但不包括下划线等符号：`^[\u4E00-\u9FA5A-Za-z0-9]+$ 或 ^[\u4E00-\u9FA5A-Za-z0-9]{2,20}$`

可以输入含有^%&',;=?$\"等字符：`[^%&',;=?$\x22]+`

禁止输入含有~的字符：`[^~\x22]+`

### 三、特殊需求表达式

Email地址：`^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$`

域名：`[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+\.?`

InternetURL：`[a-zA-z]+://\s* 或 ^http://([\w-]+.)+[\w-]+(/[\w-./?%&=]*)?$`

手机号码：`^(13[0-9]|14[5|7]|15[0|1|2|3|4|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8}$`

电话号码：`("XXX-XXXXXXX"、"XXXX-XXXXXXXX"、"XXX-XXXXXXX"、"XXX-XXXXXXXX"、"XXXXXXX"和"XXXXXXXX)：^((\d{3,4}-)|\d{3.4}-)?\d{7,8}$`

国内电话号码：`(0511-4405222、021-87888822)：\d{3}-\d{8}|\d{4}-\d{7}`

电话号码正则表达式（支持手机号码，3-4位区号，7-8位直播号码，1－4位分机号）: `((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)`

身份证号(15位、18位数字)，最后一位是校验位，可能为数字或字符X：`(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)`

帐号是否合法(字母开头，允许5-16字节，允许字母数字下划线)：`^a-zA-Z{4,15}$`

密码(以字母开头，长度在6~18之间，只能包含字母、数字和下划线)：`^[a-zA-Z]\w{5,17}$`

强密码(必须包含大小写字母和数字的组合，不能使用特殊字符，长度在 8-10 之间)：`^(?=.\d)(?=.[a-z])(?=.[A-Z])[a-zA-Z0-9]{8,10}$`

强密码(必须包含大小写字母和数字的组合，可以使用特殊字符，长度在8-10之间)：`^(?=.\d)(?=.[a-z])(?=.[A-Z]).{8,10}$`

日期格式：`^\d{4}-\d{1,2}-\d{1,2}`

一年的12个月(01～09和1～12)：`^(0?[1-9]|1[0-2])$`

一个月的31天(01～09和1～31)：`^((0?[1-9])|((1|2)[0-9])|30|31)$`

**钱的输入格式：**
有四种钱的表示形式我们可以接受:"10000.00" 和 "10,000.00", 和没有 "分" 的 "10000" 和 "10,000"：`^1-9$`

这表示任意一个不以0开头的数字,但是,这也意味着一个字符"0"不通过,所以我们采用下面的形式：`^(0|1-9)$`

一个0或者一个不以0开头的数字.我们还可以允许开头有一个负号：`^(0|-?1-9)$`

这表示一个0或者一个可能为负的开头不为0的数字.让用户以0开头好了.把负号的也去掉,因为钱总不能是负的吧。下面我们要加的是说明可能的小数部分：`^[0-9]+(.[0-9]+)?$`

必须说明的是,小数点后面至少应该有1位数,所以"10."是不通过的,但是 "10" 和 "10.2" 是通过的：`^[0-9]+(.[0-9]{2})?$`

这样我们规定小数点后面必须有两位,如果你认为太苛刻了,可以这样：`^[0-9]+(.[0-9]{1,2})?$`

这样就允许用户只写一位小数.下面我们该考虑数字中的逗号了,我们可以这样：`^[0-9]{1,3}(,[0-9]{3})(.[0-9]{1,2})?$`

1到3个数字,后面跟着任意个 逗号+3个数字,逗号成为可选,而不是必须：`^([0-9]+|[0-9]{1,3}(,[0-9]{3}))(.[0-9]{1,2})?$`

备注：这就是最终结果了，别忘了"+"可以用""替代如果你觉得空字符串也可以接受的话(奇怪,为什么?)最后，别忘了在用函数时去掉去掉那个反斜杠，一般的错误都在这里。

xml文件：`^([a-zA-Z]+-?)+[a-zA-Z0-9]+\.x|X[l|L]$`

中文字符的正则表达式：`[\u4e00-\u9fa5]`

双字节字符：`\x00-\xff` (包括汉字在内，可以用来计算字符串的长度(一个双字节字符长度计2，ASCII字符计1))

空白行的正则表达式：`\n\s\r` (可以用来删除空白行)

HTML标记的正则表达式：`<(\S?)>>.?|<.? />` ( 首尾空白字符的正则表达式：^\s|\s$或(^\s)|(\s$) (可以用来删除行首行尾的空白字符(包括空格、制表符、换页符等等)，非常有用的表达式)

腾讯QQ号：`1-9{4,}` (腾讯QQ号从10000开始)

中国邮政编码：`[1-9]\d{5}(?!\d)` (中国邮政编码为6位数字)

IPv4地址：`((2(5[0-5]|[0-4]\d))|[0-1]?\d{1,2})(.((2(5[0-5]|[0-4]\d))|[0-1]?\d{1,2})){3}`







