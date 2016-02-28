package com.jhhc.baseframework.client.listener;

import java.lang.reflect.Method;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author yecq
 */
@Component("notify_advice")
public class NotifyAdvice {

    @Autowired
    private CoreChangeNotifier notifier;

    public void notifyChange(JoinPoint p) {
        MethodSignature sign = (MethodSignature) p.getSignature();
        Method m = sign.getMethod();
        if (m.isAnnotationPresent(Notify.class)) {
            // 获取注解的参数
            Notify noti = m.getAnnotation(Notify.class);
            String[] names = noti.value();
            notifier.fireCoreChange(names);
        }
    }
}
