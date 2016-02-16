package com.jhhc.baseframework.client;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 *
 * @author yecq
 */
@Component
public class Root {

    private static Root single = null;

    public static Root getInstance() {
        synchronized (Root.class) {
            if (single == null) {
                throw new IllegalArgumentException("Root对象没有被注入");
            }
        }
        return single;
    }

    private Root() {
    }

    @Autowired
    private ApplicationContext ctx;

    // 每次初始化后执行下面的操作
    @PostConstruct
    public void init() {
        single = this;
    }

    public ApplicationContext getApplicationContext() {
        return ctx;
    }

    public <T> T getBean(String name, Class<T> cls) {
        return ctx.getBean(name, cls);
    }

    public <T> T getBean(Class<T> cls) {
        return ctx.getBean(cls);
    }

    public Object getBean(String name, Object... os) {
        return ctx.getBean(name, os);
    }
}
