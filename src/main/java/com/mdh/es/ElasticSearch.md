# ElasticSearch
~~~
ElasticSearch (ES) 是当前最流行的搜索引擎，而 Lucene 是 ES 的底层核心库。


Lucene 不是一个服务，而是一个 Java 库，它解决了“如何在海量文本中快速找到关键词”的问题。
倒排索引（Inverted Index）：
原理：传统数据库是“文档 -> 关键词”（正排），Lucene 是“关键词 -> 文档 ID 列表”（倒排）。
结构：
词典（Term Dictionary）：存储所有分词后的词项。
倒排表（Posting List）：记录该词项出现在哪些文档中，以及出现的位置、频率。
优势：查询速度极快，与文档总数关系不大，只与词项数量有关。
分词与分析（Analyzer）：
搜索前必须经过“分词”。例如“ElasticSearch 原理”可能被分词为 ElasticSearch 和 原理。
关键点：索引时的分析器和搜索时的分析器必须保持一致，否则搜不到结果。
评分机制（TF-IDF / BM25）：
Lucene 根据词频（TF）（词在文档中出现越多越相关）和逆文档频率（IDF）（词在所有文档中越稀有越重要）来计算相关性得分。

~~~

## Lucene原理
~~~
倒排索引（Inverted Index）：
    原理：传统数据库是“文档 -> 关键词”（正排），Lucene 是“关键词 -> 文档 ID 列表”（倒排）。
    结构：
        词典（Term Dictionary）：存储所有分词后的词项。
        倒排表（Posting List）：记录该词项出现在哪些文档中，以及出现的位置、频率。
    优势：查询速度极快，与文档总数关系不大，只与词项数量有关。
    
分词与分析（Analyzer）：
    搜索前必须经过“分词”。例如“ElasticSearch 原理”可能被分词为 ElasticSearch 和 原理。
    关键点：索引时的分析器和搜索时的分析器必须保持一致，否则搜不到结果。
    
评分机制（TF-IDF / BM25）：
    Lucene 根据词频（TF）（词在文档中出现越多越相关）和逆文档频率（IDF）（词在所有文档中越稀有越重要）来计算相关性得分。
~~~

## ElasticSearch 实战
~~~
ES 在 Lucene 基础上增加了分布式、RESTful API 和聚合分析能力。

核心能力：
    全文检索：电商商品搜索、文章搜索（支持模糊、同义词、高亮显示）。
    日志分析（ELK 栈）：配合 Logstash/Beats 收集日志，ES 存储，Kibana 展示。
    聚合分析：类似于 SQL 的 GROUP BY，但性能更强。例如“统计过去一小时每个地区的订单总额”。
    
优化与调优：
    Mapping 设计：明确字段类型（Keyword vs Text）。Text 会分词用于全文搜索，Keyword 不分词用于精确过滤和聚合。
    深度分页问题：ES 默认的 from + size 分页在深度翻页时性能极差（因为要加载前 N 页数据）。需使用 search_after 或游标（Scroll）机制。
    写入性能：使用 Bulk 批量写入；调整 refresh_interval（默认 1s，写入量大可调大以减少 Segment 合并开销）。
    集群健康：理解分片（Shard）和副本（Replica）机制，确保集群状态为 Green。
~~~