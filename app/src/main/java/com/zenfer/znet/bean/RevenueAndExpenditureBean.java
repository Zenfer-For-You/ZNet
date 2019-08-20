package com.zenfer.znet.bean;

/**
 * 收支明细 充值记录 提现记录
 *
 * @author Zenfer
 * @date 2019/7/9 16:17
 */
public class RevenueAndExpenditureBean {

    private String created_at;
    private String money;
    private String status;
    /**
     * 收支明细
     */
    private String type;
    private String flag;

    /**
     * 提现记录
     */
    private String order_no;
    private String total;
    private String credited;
    private String fee;
    private boolean is_fail;

    /**
     * 充值记录
     */
    private String id;
    private String pay_way;


    public boolean isIs_fail() {
        return is_fail;
    }

    public void setIs_fail(boolean is_fail) {
        this.is_fail = is_fail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPay_way() {
        return pay_way;
    }

    public void setPay_way(String pay_way) {
        this.pay_way = pay_way;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getCredited() {
        return credited;
    }

    public void setCredited(String credited) {
        this.credited = credited;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }
}
