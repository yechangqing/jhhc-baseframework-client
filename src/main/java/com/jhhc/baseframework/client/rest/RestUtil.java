package com.jhhc.baseframework.client.rest;

import com.google.gson.Gson;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author yecq
 */
@Component
public class RestUtil {

    @Autowired
    private RestTemplate rest;

//    public RestUtil() {
//        this.rest = new RestTemplate(new HttpComponentsClientHttpRequestFactory());     // 这样功能更强
//        System.out.println("初始化完毕...");
//    }
    // get all
    public Sret getList4Json(String url, Map<String, Object> condition) {
        if (condition != null && !condition.isEmpty()) {
            url += "?" + condition2Param(condition);
        }
        ResponseEntity entity = this.rest.getForEntity(url, String.class);
        HttpHeaders headers = entity.getHeaders();
        Sret sr = new Sret();
        String status = headers.get("status").get(0);
        String message = headers.get("message").get(0);
        if (status.equalsIgnoreCase("ok")) {
            sr.setOk(message);
        } else if (status.equalsIgnoreCase("fail")) {
            sr.setFail(message);
        } else if (status.equalsIgnoreCase("error")) {
            sr.setError(message);
        }

        sr.setData(entity.getBody());

        return sr;
    }

    public Sret getList4Json(String url) {
        return getList4Json(url, null);
    }

    public Sret getList4Object(String url, Map<String, Object> condition, Class cls) {
        Sret sr = getList4Json(url, condition);
        String json = (String) sr.getData();
        List li1 = new Gson().fromJson(json, List.class);
        List ret = new LinkedList();
        Iterator ite = li1.iterator();
        while (ite.hasNext()) {
            String tmp = new Gson().toJson(ite.next());
            ret.add(new Gson().fromJson(tmp, cls));
        }
        sr.setData(ret);
        return sr;
    }

    public <T> Sret getList4Object(String url, Class<T> cls) {
        return getList4Object(url, null, cls);
    }

    private String condition2Param(Map<String, Object> condition) {
        String ret = "";
        Iterator<Entry<String, Object>> ite = condition.entrySet().iterator();
        while (ite.hasNext()) {
            Entry<String, Object> ent = ite.next();
            ret += ent.getKey().trim() + "=" + ent.getValue() + "&";
        }
        return ret.substring(0, ret.length() - 1);
    }

    // get single
    public Sret get4Json(String url, Object... v) {
        ResponseEntity entity = this.rest.getForEntity(url, String.class, v);
        HttpHeaders headers = entity.getHeaders();
        String status = headers.get("status").get(0);
        String message = headers.get("message").get(0);
        Sret sr = new Sret();
        if (status.equalsIgnoreCase("ok")) {
            sr.setOk(message);
        } else if (status.equalsIgnoreCase("fail")) {
            sr.setFail(message);
        } else if (status.equalsIgnoreCase("error")) {
            sr.setError(message);
        }

        sr.setData(entity.getBody());

        return sr;
    }

    public Sret get4Object(String url, Class cls, Object... v) {
        Sret sr = get4Json(url, v);
        String json = (String) sr.getData();
        sr.setData(new Gson().fromJson(json, cls));
        return sr;
    }

    // post，返回的数据是String，由用户指定
    public Sret post(String url, Map<String, Object> param) {
        MultiValueMap<String, String> map = getMultiValueFromMap(param);
        ResponseEntity entity = this.rest.postForEntity(url, map, String.class);
        HttpHeaders headers = entity.getHeaders();
        String status = headers.get("status").get(0);
        String message = headers.get("message").get(0);
        Sret sr = new Sret();
        if (status.equalsIgnoreCase("ok")) {
            sr.setOk(message);
        } else if (status.equalsIgnoreCase("fail")) {
            sr.setFail(message);
        } else if (status.equalsIgnoreCase("error")) {
            sr.setError(message);
        }

        sr.setData(entity.getBody());
        return sr;
    }

    public Sret post(String url) {
        return post(url, null);
    }

    private MultiValueMap<String, String> getMultiValueFromMap(Map<String, Object> param) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap();
        if (param == null) {
            return map;
        }

        Iterator<Entry<String, Object>> ite = param.entrySet().iterator();
        while (ite.hasNext()) {
            Entry<String, Object> ent = ite.next();
            map.add(ent.getKey(), ent.getValue() + "");
        }
        return map;
    }

    // put，暂由post来实现，不返回数据，只返回状态
    public Sret put(String url, Map<String, Object> param, Object... v) {
        MultiValueMap<String, String> map = getMultiValueFromMap4PD(param, "put");
        ResponseEntity entity = this.rest.postForEntity(url, map, String.class, v);
        HttpHeaders headers = entity.getHeaders();
        String status = headers.get("status").get(0);
        String message = headers.get("message").get(0);
        Sret sr = new Sret();
        if (status.equalsIgnoreCase("ok")) {
            sr.setOk(message);
        } else if (status.equalsIgnoreCase("fail")) {
            sr.setFail(message);
        } else if (status.equalsIgnoreCase("error")) {
            sr.setError(message);
        }

        return sr;
    }

    private MultiValueMap<String, String> getMultiValueFromMap4PD(Map<String, Object> param, String method) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap();
        map.add("_method", method);
        if (param == null) {
            return map;
        }

        Iterator<Entry<String, Object>> ite = param.entrySet().iterator();
        while (ite.hasNext()) {
            Entry<String, Object> ent = ite.next();
            map.add(ent.getKey(), ent.getValue() + "");
        }
        return map;
    }

    // delete，也由post来实现
    public Sret delete(String url, Object... v) {
        MultiValueMap<String, String> map = getMultiValueFromMap4PD(null, "delete");
        ResponseEntity entity = this.rest.postForEntity(url, map, String.class, v);
        HttpHeaders headers = entity.getHeaders();
        String status = headers.get("status").get(0);
        String message = headers.get("message").get(0);
        Sret sr = new Sret();
        if (status.equalsIgnoreCase("ok")) {
            sr.setOk(message);
        } else if (status.equalsIgnoreCase("fail")) {
            sr.setFail(message);
        } else if (status.equalsIgnoreCase("error")) {
            sr.setError(message);
        }

        return sr;
    }
}
