/**
 * Copyright (c) 2016, yayunyin@126.com All Rights Reserved
 */
package org.yinyayun.netcarry.biz.common;

import java.io.IOException;
import java.util.Properties;

/**
 * SystemProperties.java
 *
 * @author yinyayun
 */
public class SystemProperties {
    private Properties properties = new Properties();
    private static SystemProperties systemProperties;

    public SystemProperties getSystemProperties() {
        if (systemProperties == null) {
            systemProperties = new SystemProperties();
        }
        return systemProperties;
    }

    private SystemProperties() {
        try {
            properties.load(SystemProperties.class.getResourceAsStream("system.properties"));
        }
        catch (IOException e) {
            throw new RuntimeException("load system.properties error!");
        }
    }

    public String getString(String key) {
        Object obj = properties.get(key);
        return obj == null ? null : String.valueOf(obj);
    }

    public Integer getInt(String key) {
        Object obj = properties.get(key);
        return obj == null ? null : Integer.valueOf(String.valueOf(obj));
    }
}
