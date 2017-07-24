/**
 * Copyright (c) 2016, yayunyin@126.com All Rights Reserved
 */
package org.yinyayun.netcarry.core.config;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.yinyayun.netcarry.core.util.CircularCounter;

/**
 * AgentFactory.java
 *
 * @author yinyayun
 */
public class DefaultAgentFactory extends AgentFactoryA {
    private CircularCounter counter;
    private List<String> agents = new ArrayList<String>();

    public DefaultAgentFactory() {
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            inputStream = AgentFactoryA.class.getClassLoader().getResourceAsStream("user-agent.txt");
            reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (line.length() > 0) {
                    agents.add(line);
                }
            }
            this.counter = new CircularCounter(0, agents.size());
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String takeAgent() {
        return agents.get(counter.count());
    }
}
