package com.duan.javastuff.spring;

import com.duan.javastuff.P;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * Created on 2019/8/21.
 *
 * @author DuanJiaNing
 */
@Component
public class BeanFactoryProcessor1 implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        P.out("postProcessBeanFactory in bean");
    }

}
