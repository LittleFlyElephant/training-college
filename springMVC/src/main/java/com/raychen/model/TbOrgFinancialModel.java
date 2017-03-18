package com.raychen.model;

import javax.persistence.*;

/**
 * Created by raychen on 2017/3/12.
 */
@Entity
@Table(name = "tb_org_financial", schema = "TCS", catalog = "")
public class TbOrgFinancialModel {
    private int id;
    private Byte asd;
    private double money;
    private String time;
    private String op;
    private int opType;
    private byte state;
    private TbOrganizationModel org;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "asd", nullable = true)
    public Byte getAsd() {
        return asd;
    }

    public void setAsd(Byte asd) {
        this.asd = asd;
    }

    @Basic
    @Column(name = "money", nullable = false, precision = 0)
    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    @Basic
    @Column(name = "time", nullable = false, length = 255)
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Basic
    @Column(name = "op", nullable = false, length = 255)
    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    @Basic
    @Column(name = "op_type", nullable = false)
    public int getOpType() {
        return opType;
    }

    public void setOpType(int opType) {
        this.opType = opType;
    }

    @Basic
    @Column(name = "state", nullable = false)
    public byte getState() {
        return state;
    }

    public void setState(byte state) {
        this.state = state;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TbOrgFinancialModel that = (TbOrgFinancialModel) o;

        if (id != that.id) return false;
        if (state != that.state) return false;
        if (Double.compare(that.money, money) != 0) return false;
        if (opType != that.opType) return false;
        if (asd != null ? !asd.equals(that.asd) : that.asd != null) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;
        if (op != null ? !op.equals(that.op) : that.op != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (asd != null ? asd.hashCode() : 0);
        temp = Double.doubleToLongBits(money);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (op != null ? op.hashCode() : 0);
        result = 31 * result + opType;
        result = 31 * result + (int) state;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "org_id", referencedColumnName = "id", nullable = false)
    public TbOrganizationModel getOrg() {
        return org;
    }

    public void setOrg(TbOrganizationModel org) {
        this.org = org;
    }
}
