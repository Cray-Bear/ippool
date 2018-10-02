package com.fty1.ippool;

import com.alibaba.fastjson.JSON;
import com.fty1.ippool.entity.IpInfo;

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
        String address = numberToIp(System.nanoTime());
        String port = address.substring(address.lastIndexOf(".")+1);
        IpInfo ipInfo = IpInfo.builder().address(address).port(Integer.valueOf(port)).build();
        return ipInfo;
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