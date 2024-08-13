## nacos 问题,下面的方式一种就可以
1. main 方法设置 System.setProperty("nacos.logging.default.config.enabled", "false");
2. -Dnacos.logging.default.config.enabled=false
3. resources 目录创建空的 nacos-logback.xml

## logstash

pipelines.yml 配置
```text
- pipeline.id: all.log
  pipeline.workers: 1
  #queue.type: persisted
  path.config: "D:\\develop\\elk\\logstash-7.17.20\\config\\conf.d\\*.conf"
```

注意用户名、密码的配置，需使用 elastic 用户


```text
YYYY-MM-DD HH:mm:ss.SSS

新版本则在kibana侧边栏Management-->Stack Management-->kibana-->Advanced Settings

在Advanced Settings界面找到Date format或dateFormat进行设置
这里可以点format来跳转时间格式转换的界面，和Java中的时间定义格式不太一样
我这里可以使用Java相同的格式
```


## elastic index policy
```text
PUT _ilm/policy/5minute_delete_policy
{
  "policy": {
    "phases": {
      "delete": {
        "min_age": "1m",
        "actions": {
          "delete": {}
        }
      }
    }
  }
}

PUT _index_template/5minute_delete_template
{
  "index_patterns": [
    "logstash_*"
  ],
  "template": {
    "settings": {
      "number_of_shards": 1,
      "number_of_replicas": 0,
      "index.lifecycle.name": "5minute_delete_policy",
      "index.lifecycle.rollover_alias": "logs-alias"
    },
    "aliases": {
      "logs-alias": {
        "is_write_index": true
      }
    }
  }
}

GET logstash_spring-boot-logstash_2024-08-13/_ilm/explain
```