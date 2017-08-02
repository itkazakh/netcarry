/**
 * Copyright (c) 2016, yayunyin@126.com All Rights Reserved
 */
package org.yinyayun.netcarry.business;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * MachineTranslateTest.java
 *
 * @author yinyayun
 */
public class MachineTranslateTest {
    public static void main(String[] args) throws IOException {
        Connection connection = Jsoup
                .connect("https://zh.ifixit.com/Guide/iPhone+1st+Generation+Antenna+Cover+Replacement/441");
        connection.cookie("countryCode", "CN");// Accept-Language:zh-CN,zh;q=0.8
        connection.header("Accept-Language", "zh-CN,zh;q=0.8");
        connection.cookie("_gat_ifixit", "1");
        connection.cookie("session", "91c32f12575d1e1c0c430370b2955646");
        Document document = connection.get();
        document.getElementsByClass("steps-container");
    }
}
