/**
 * Copyright (c) 2016, yayunyin@126.com All Rights Reserved
 */
package org.yinyayun.netcarry.business;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.yinyayun.netcarry.business.DBUtils.DRIVER_TYPE;

import com.google.gson.Gson;

/**
 * DataImport.java
 *
 * @author yinyayun
 */
public class DataImport {
    public static void main(String[] args) {
        new DataImport().importData();
    }

    /**
     * 数据导入
     */
    public void importData() {
        Connection connection = null;
        PreparedStatement statement = null;
        Gson gson = new Gson();
        try {
            connection = DBUtils.getConnection(DRIVER_TYPE.MYSQL,
                    "jdbc:mysql://116.62.214.186:3306/zijixiu?useUnicode=true&characterEncoding=UTF-8", "zijixiu",
                    "zijixiu@2017");
            statement = connection
                    .prepareStatement("insert into zijixiu_fetch_content(content,domain,type) value(?,'youku',0)");
            List<String> lines = Files.readAllLines(new File("C:/Users/yinyayun/Desktop/自己维修/手机维修相关.txt").toPath(),
                    Charset.forName("utf-8"));
            int count = 0;
            for (String line : lines) {
                try {
                    if (StringUtils.isNotEmpty(line)) {
                        Map<String, String> map = gson.fromJson(line, Map.class);
                        String href = map.get("href");
                        int index = href.indexOf("?from");
                        if (index > 0) {
                            map.put("href", href.substring(0, index));
                        }
                        statement.setString(1, gson.toJson(map));
                        statement.addBatch();
                    }
                    if ((++count) % 1 == 0) {
                        System.out.println("execute " + count);
                        statement.executeBatch();
                    }
                }
                catch (Exception e) {
                    System.out.println(line);
                }
            }
            System.out.println("execute " + count);
            statement.executeBatch();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            DBUtils.close(statement);
            DBUtils.close(connection);
        }
    }
}
