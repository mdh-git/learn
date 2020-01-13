package com.mdh.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Data
@Slf4j
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TopicInfo {

    private static final String[] TOPIC_REG_LIST ={ "/(ext/session)/([^/]+)/([^/]+)/(.+)",
            "/(sys)/([^/]+)/([^/]+)/(.+)" };
    private String topic;
    // 不包含 '/'
    private String topicPrefix;

    private String topicSuffix;

    private String productId;

    private String sn;

    private String deviceId;

    public TopicInfo(String topic){
        this.topic = topic;
        parseTopic(topic);
    }

    public  boolean equalSubffix(final String topic){
        return topic.endsWith(topicSuffix);
    }

    public static void main(String[] args) {
        TopicInfo topicInfo = new TopicInfo("/sys/abc/abc/thing/enable_reply");
        Boolean flag = null;
        boolean r = Optional.ofNullable(flag).orElse(false);
        if(r){
            System.out.println(flag);
        }

    }
    private void parseTopic(String topic){
        //  /(ext/session)/([^/]+)/([^/]+)/(.+) 或  /(sys)/([^/]+)/([^/]+)/
        for ( String topicRegular: TOPIC_REG_LIST ) {
            Pattern pattern = Pattern.compile(topicRegular);
            Matcher matcher = pattern.matcher(topic);

            if (matcher.find( ) && matcher.groupCount() == 4 ) {
                topicPrefix = matcher.group(1);
                productId = matcher.group(2);
                sn = matcher.group(3);
                topicSuffix = matcher.group(4);
                deviceId = productId + "." + sn;
                log.debug("match: " + topic);
                break;
            } else {
                log.debug(topicRegular + ":no match:" + topic);
            }
        }
    }

}
