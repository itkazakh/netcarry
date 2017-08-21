# netcarry
网页搬运工，用于特定页面的抓取解析，暂不支持深度抓取

##抓取程序执行demo
```java
public class CarrayMainDemo {
    public static void main(String[] args) throws IOException {
        // 结果保存路径
        String savePath = "";
        // 日志路径
        String logPath = "logDir";
        // 抓取页面的入口
        String[] carryUrls = {};
        // 抓取应用的相关配置，包括连接配置信息，线程数等等
        NetCarryConfig config = new NetCarryConfig();
        config.setFetchThreadNumber(5);
        //
        PageFetchExecutor<String> main = new PageFetchExecutor<String>();
        FetchCollector<String> collector = new FetchCollector<String>(1000, savePath);
        PageParserDemo pageParser = new PageParserDemo(collector);
        NextPageParserA nextPageParsers = new NextPageURLParserDemo(Integer.MAX_VALUE);
        main.execute(logPath, Arrays.asList(carryUrls), config, pageParser, new NextPageParserA[]{nextPageParsers});
    }
}
```

##待抓取页面解析demo

```java

public class NextPageURLParserDemo extends NextPageParserA {
    /**
     * deep参数定义的有些问题，目前不是当做深度再使用，作全局大抓取页面数
     * @param deep
     */
    public NextPageURLParserDemo(int maxPages) {
        super(maxPages);
    }

    /**
     * 该页面是否满足进一步抓取页面的要求
     */
    @Override
    public boolean needParserThisPage(String url) {
        return false;
    }

    /**
     * 解析出该页面中哪一些页面要作抓取
     */
    @Override
    protected List<PageMeta> parser(String url, Document document) {
        return null;
    }
}

```

##页面解析demo，即抓取者正真关心的内容
```java

public class PageParserDemo extends FetchParser<String> {
    /**
     * collector为收集器,负责页面解析结果的收集存储
     * 
     * @param collector
     */
    public PageParserDemo(FetchCollector<String> collector) {
        super(collector);
    }

    /**
     * 该URL是否满足解析规则
     */
    @Override
    public boolean needParser(String url) {
        return false;
    }

    /**
     * 页面解析
     * 
     * @param page 该页面的父页面的信息
     */
    @Override
    protected List<String> parser(PageMetas page, Document document) {
        return null;
    }

}
```

# about me
![](http://img1.ph.126.net/xTPOKnUu-Eao6HSK-e7AXQ==/6632583992235991768.jpg)


