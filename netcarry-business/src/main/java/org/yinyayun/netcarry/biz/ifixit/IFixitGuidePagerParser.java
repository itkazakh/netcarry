/**
 * Copyright (c) 2016, yayunyin@126.com All Rights Reserved
 */
package org.yinyayun.netcarry.biz.ifixit;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.yinyayun.netcarry.biz.ifixit.GuideContent.CateGory;
import org.yinyayun.netcarry.biz.ifixit.GuideContent.StepInfo;
import org.yinyayun.netcarry.biz.ifixit.GuideContent.ToolEntity;
import org.yinyayun.netcarry.core.collect.FetchCollector;
import org.yinyayun.netcarry.core.dao.PageMetas;
import org.yinyayun.netcarry.core.parser.FetchParser;

/**
 * IFixitPagerParser.java 教程页面抓取
 * 
 * @author yinyayun
 */
public class IFixitGuidePagerParser extends FetchParser<GuideContent> {
    public IFixitGuidePagerParser(FetchCollector<GuideContent> collector) {
        super(collector);
    }

    @Override
    public boolean needParser(String url) {
        return (url.startsWith("https://zh.ifixit.com/Guide") && !url.equals("https://zh.ifixit.com/Guide"))
                || (url.startsWith("https://zh.ifixit.com/Teardown") && !url.equals("https://zh.ifixit.com/Teardown"));
    }

    // https://zh.ifixit.com/Guide/iPhone+1st+Generation+Antenna+Cover+Replacement/441
    // 对于拆机教程：工具的class为：attachment-container -> a -> href和p
    // 工具:fa-ul
    // 工具连接:fa-ul -> a -> href和p
    // 工具描述:fa-ul -> a -> span
    // 简介：introduction-container
    // 步骤：steps-container
    // 步骤标题：stepValue
    // 步骤内容：step-container
    // 步骤图片:stepImage -> img.src
    // 步骤文字:step-lines-container -> step-lines -> p
    @Override
    protected List<GuideContent> parser(PageMetas metas, Document document) {
        int type = metas.getCurrentUrl().startsWith("https://zh.ifixit.com/Teardown") ? 1 : 0;
        List<GuideContent> guides = new ArrayList<GuideContent>();
        GuideContent guideContent = new GuideContent(type);
        parserForTitle(guideContent, document);
        parserForDes(guideContent, document);
        parserForTools(guideContent, document);
        parserForSteps(guideContent, document);
        // 目录
        List<String> cats = metas.getMetas().get("category");
        List<String> imgs = metas.getMetas().get("img");
        for (int i = 0; i < cats.size(); i++) {
            guideContent.addCateGory(new CateGory(cats.get(i), imgs.get(i)));
        }
        if (guideContent.getSteps() != null && guideContent.getSteps().size() > 0) {
            guides.add(guideContent);
        }
        return guides;
    }

    private void parserForDes(GuideContent guideContent, Document document) {
        Elements elements = document.getElementsByAttributeValue("itemprop", "description");
        if (elements.size() > 0) {
            guideContent.setDes(elements.get(0).getElementsByTag("p").get(0).text());
        }
    }

    private void parserForSteps(GuideContent guideContent, Document document) {
        Elements stepsContainer = document.getElementsByClass("steps-container");
        Elements stepTitles = stepsContainer.get(0).getElementsByClass("step-title");
        Elements stepContainers = stepsContainer.get(0).getElementsByClass("step-container");
        if (stepTitles.size() == stepContainers.size()) {
            for (int i = 0; i < stepTitles.size(); i++) {
                StepInfo stepInfo = new StepInfo();
                String stepValue = stepTitles.get(i).getElementsByClass("stepValue").get(0).text();
                Elements stepTitleTitle = stepTitles.get(i).getElementsByClass("stepTitleTitle");
                String titleDes = stepTitleTitle.size() > 0 ? stepTitleTitle.get(0).text() : null;
                Elements passages = stepContainers.get(i).getElementsByTag("p");
                // 多图的情况，不含有img标签，而是多个div.data-src
                Elements stepImages = stepContainers.get(i).getElementsByAttributeValue("class",
                        "column stepMedia step-main-media js-step-main-media stepImage");
                if (stepImages.size() > 0) {
                    Elements imgs = stepImages.get(0).getElementsByTag("img");
                    if (imgs.size() > 0) {
                        for (Element img : imgs) {
                            stepInfo.addImg(img.attr("src"));
                        }
                    }
                    else {
                        Elements imgDivs = stepImages.get(0).getElementsByClass("div");
                        for (Element imgDiv : imgDivs) {
                            stepInfo.addImg(imgDiv.attr("data-src"));
                        }
                    }
                    for (Element passage : passages) {
                        stepInfo.addPassage(passage.text());
                    }
                    stepInfo.setpTiltle = stepValue;
                    stepInfo.titleDes = titleDes;
                    //
                    guideContent.addStep(stepInfo);
                }
            }
        }
    }

    /**
     * 标题解析
     * 
     * @param document
     * @return
     */
    private void parserForTitle(GuideContent guideContent, Document document) {
        Element titleElement = document.getElementById("guide-intro-title");
        if (titleElement != null) {
            guideContent.setTitle(titleElement.text());
        }
    }

    /**
     * 工具解析
     * 
     * @param document
     * @return
     */
    private void parserForTools(GuideContent guideContent, Document document) {
        Elements uls = document.getElementsByTag("ul");
        for (Element ul : uls) {
            Elements items = ul.getElementsByClass("item-list-item");
            if (items.size() > 0) {
                for (Element item : items) {
                    ToolEntity entity = new ToolEntity();
                    Element href = item.getElementsByTag("a").get(0);
                    entity.href = href.attr("href");
                    entity.des = href.attr("data-text");
                    entity.name = href.getElementsByTag("span").get(0).text();
                    entity.price = href.attr("data-price");
                    guideContent.addTool(entity);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Document document = Jsoup.parse(FileUtils.readFileToString(new File("data/termdown-example.txt")));
        new IFixitGuidePagerParser(null).fetchPaser(new PageMetas("", ""), document);
    }
}
