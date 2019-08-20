package com.zenfer.znet.bean;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * 列表数据通用实体类
 *
 * @author Zenfer
 * @date 2019/7/9 16:30
 */
public class Pages<T> {

    public int current_page;
    private List<T> data;
    private int from;
    private int to;
    private int last_page;
    private int per_page;
    private int total;

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public int getLast_page() {
        return last_page;
    }

    public void setLast_page(int last_page) {
        this.last_page = last_page;
    }

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public static <T> Pages<T> fromJson(Object data, Class<T> clazz) {
        Gson gson = new Gson();
        Type objectType = type(Pages.class, clazz);
        return gson.fromJson(gson.toJson(data), objectType);
    }

    static ParameterizedType type(final Class raw, final Type... args) {
        return new ParameterizedType() {
            @Override
            public Type getRawType() {
                return raw;
            }

            @Override
            public Type[] getActualTypeArguments() {
                return args;
            }

            @Override
            public Type getOwnerType() {
                return null;
            }
        };
    }

}
