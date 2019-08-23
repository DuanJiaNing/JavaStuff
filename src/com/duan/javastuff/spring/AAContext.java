package com.duan.javastuff.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created on 2019/8/21.
 *
 * @author DuanJiaNing
 */
public class AAContext extends AnnotationConfigApplicationContext {

    public AAContext(String... basePackages) {
        super(basePackages);
    }

    @Override
    protected void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        super.postProcessBeanFactory(beanFactory);

        beanFactory.addBeanPostProcessor(new BeanPostProcessor() {
            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof BeanA) {
                    ((BeanA) bean).name = "pr-" + beanName;
                }
                return null;
            }

            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof BeanA) {
                    ((BeanA) bean).name = beanName + "-sub";
                }
                return null;
            }
        });
    }
}
