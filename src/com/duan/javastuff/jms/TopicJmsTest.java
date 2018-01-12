package com.duan.javastuff.jms;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;

/**
 * Created on 2018/1/12.
 *
 * @author DuanJiaNing
 */
public class TopicJmsTest implements TopicConnectionFactory {

    @Override
    public TopicConnection createTopicConnection() throws JMSException {
        return null;
    }

    @Override
    public TopicConnection createTopicConnection(String s, String s1) throws JMSException {
        return null;
    }

    @Override
    public Connection createConnection() throws JMSException {
        return null;
    }

    @Override
    public Connection createConnection(String s, String s1) throws JMSException {
        return null;
    }

}
