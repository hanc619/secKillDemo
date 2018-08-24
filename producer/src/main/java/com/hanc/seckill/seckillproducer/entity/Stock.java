package com.hanc.seckill.seckillproducer.entity;

public class Stock {
    private Integer id;

    private String name;

    private Long count;

    private Long sale;

    private Long version;

    public Stock(Integer id, Long count, Long sale, Long version) {
        this.id = id;
        this.count = count;
        this.sale = sale;
        this.version = version;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Long getSale() {
        return sale;
    }

    public void setSale(Long sale) {
        this.sale = sale;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}