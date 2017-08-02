/**
 * Copyright (c) 2016, yayunyin@126.com All Rights Reserved
 */
package org.yinyayun.netcarry.biz.ifixit;

import java.util.ArrayList;
import java.util.List;

/**
 * GuideContent.java 教程正文
 * 
 * @author yinyayun
 */
public class GuideContent {
    // 更换配件指南0,拆机指南：1
    private int type;
    // 教程的标题
    private String title;
    // 教程描述
    private String des;
    // 分类信息、分类对应的图片
    private List<CateGory> cateGorys = new ArrayList<GuideContent.CateGory>();;
    private List<ToolEntity> tools = new ArrayList<ToolEntity>();
    // 教程步骤
    private List<StepInfo> steps = new ArrayList<GuideContent.StepInfo>();

    public GuideContent(int type) {
        this.type = type;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public void setSteps(List<StepInfo> steps) {
        this.steps = steps;
    }

    public void addStep(StepInfo stepInfo) {
        steps.add(stepInfo);
    }

    public List<StepInfo> getSteps() {
        return steps;
    }

    public String getTitle() {
        return title;
    }

    public List<CateGory> getCateGory() {
        return cateGorys;
    }

    public void addCateGory(CateGory cateGory) {
        this.cateGorys.add(cateGory);
    }

    public List<ToolEntity> getTools() {
        return tools;
    }

    public void addTool(ToolEntity tool) {
        tools.add(tool);
    }

    public void setTool(List<ToolEntity> tools) {
        this.tools = tools;
    }

    public String getDes() {
        return des;
    }

    public int getType() {
        return type;
    }
    //
    /**
     * 工具实体
     * 
     * @author yinyayun
     */
    public static class ToolEntity {
        public String name;
        public String href;
        public String des;
        public String price;
    }
    /**
     * 目录结构
     * 
     * @author yinyayun
     */
    public static class CateGory {
        public String cat;
        public String img;

        public CateGory(String cat, String img) {
            this.cat = cat;
            this.img = img;
        }
    }
    /**
     * 步骤信息
     * 
     * @author yinyayun
     */
    public static class StepInfo {
        public String setpTiltle;
        public String titleDes;// 标题描述
        public List<String> imgs = new ArrayList<String>();
        public List<String> passages = new ArrayList<String>();

        public void addImg(String img) {
            imgs.add(img);
        }

        public void addPassage(String passage) {
            passages.add(passage);
        }
    }
}
