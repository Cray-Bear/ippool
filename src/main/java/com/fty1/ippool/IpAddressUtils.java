package com.fty1.ippool;

import com.alibaba.fastjson.JSON;
import com.fty1.ippool.entity.IpInfo;

import java.util.Random;

/**
 * IP地址操作工具类
 *
 * @author Crzy-Bear
 * @date 2018-10-02 4:22 PM
 **/
public class IpAddressUtils {

    /**
     * 构建IP地址
     *
     * @return
     */
    public static IpInfo generateIPfo() {
        String address = numberToIp(System.currentTimeMillis());
        String port = address.substring(address.lastIndexOf(".")+1);
        IpInfo ipInfo = IpInfo.builder().address(address).port(Integer.valueOf(port)).build();
        return ipInfo;
    }



    /**
     * 构建IP地址
     *
     * @return
     */
    public static IpInfo generateIPfo(int num) {
        String address = numberToIp(System.nanoTime());
        String port = address.substring(address.lastIndexOf(".")+1);
        IpInfo ipInfo = IpInfo.builder().address(address).port(Integer.valueOf(port)).build();
        return ipInfo;
    }


    public static String randomIP() {
        // ip范围
        int[][] range = {{607649792, 608174079},// 36.56.0.0-36.63.255.255
                {1038614528, 1039007743},// 61.232.0.0-61.237.255.255
                {1783627776, 1784676351},// 106.80.0.0-106.95.255.255
                {2035023872, 2035154943},// 121.76.0.0-121.77.255.255
                {2078801920, 2079064063},// 123.232.0.0-123.235.255.255
                {-1950089216, -1948778497},// 139.196.0.0-139.215.255.255
                {-1425539072, -1425014785},// 171.8.0.0-171.15.255.255
                {-1236271104, -1235419137},// 182.80.0.0-182.92.255.255
                {-770113536, -768606209},// 210.25.0.0-210.47.255.255
                {-569376768, -564133889}, // 222.16.0.0-222.95.255.255
        };

        Random rdint = new Random();
        int index = rdint.nextInt(10);
        String ip = numberToIp(range[index][0] + new Random().nextInt(range[index][1] - range[index][0]));
        return ip;
    }



    /**
     *  将十进制整数形式转换成127.0.0.1形式的ip地址
     *  将整数形式的IP地址转化成字符串的方法如下：
     *  1、将整数值进行右移位操作（>>>），右移24位，右移时高位补0，得到的数字即为第一段IP。
     *  2、通过与操作符（&）将整数值的高8位设为0，再右移16位，得到的数字即为第二段IP。
     *  3、通过与操作符吧整数值的高16位设为0，再右移8位，得到的数字即为第三段IP。
     *  4、通过与操作符吧整数值的高24位设为0，得到的数字即为第四段IP。
     *  @param longIp
     *  @return
     *  
     */
    public static String numberToIp(long ipLong) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append((ipLong >>> 24));
        stringBuffer.append(".");
        stringBuffer.append(((ipLong & 0x00FFFFFF) >>> 16));
        stringBuffer.append(".");
        stringBuffer.append(((ipLong & 0x0000FFFF) >>> 8));
        stringBuffer.append(".");
        stringBuffer.append((ipLong & 0x000000FF));
        return stringBuffer.toString();
    }


    public static void main(String[] args) throws InterruptedException {
        for (long i = 0; i < 100000L; i++) {
            System.out.println(numberToIp(System.currentTimeMillis()));
            System.out.println(JSON.toJSONString(generateIPfo()));
            Thread.sleep(1);
        }
    }

}