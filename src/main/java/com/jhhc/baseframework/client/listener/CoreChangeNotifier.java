package com.jhhc.baseframework.client.listener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Component;

/**
 *
 * @author yecq
 */
@Component
final public class CoreChangeNotifier {

    Map<String, List<CoreChangeListener>> map;

    CoreChangeNotifier() {
        this.map = new HashMap();
    }

    public void addCoreChangeListener(CoreChangeListener cl) {
        if (cl == null || cl.getNotifyNames() == null) {
            return;
        }

        String[] names = cl.getNotifyNames();
        for (int i = 0; i < names.length; i++) {
            if (!this.map.containsKey(names[i])) {
                this.map.put(names[i], new LinkedList());
            }
            List<CoreChangeListener> list = map.get(names[i]);
            list.add(cl);
        }
    }

    public void removeAll() {
        // 此方法用于方便测试
        this.map.clear();
    }

    public void fireCoreChange(String[] names) {
        if (names == null) {
            return;
        }

        Set<CoreChangeListener> set = new HashSet();
        for (int i = 0; i < names.length; i++) {
            List<CoreChangeListener> list = this.map.get(names[i]);
            if (list != null) {
                set.addAll(list);
            }
        }

        Iterator<CoreChangeListener> ite = set.iterator();
        while (ite.hasNext()) {
            ite.next().action();
        }
    }
}
