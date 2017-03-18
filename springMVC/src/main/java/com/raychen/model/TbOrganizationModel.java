package com.raychen.model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by raychen on 2017/3/11.
 */
@Entity
@Table(name = "tb_organization", schema = "TCS", catalog = "")
public class TbOrganizationModel {
    private int id;
    private String orgName;
    private String password;
    private String name;
    private String phone;
    private int cardId;
    private Double cardBalance;
    private Collection<TbCourseModel> courses;
    private Collection<TbOrgFinancialModel> financials;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "org_name", nullable = false, length = 255)
    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 255)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "phone", nullable = false, length = 255)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "card_id", nullable = false)
    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    @Basic
    @Column(name = "card_balance", nullable = true, precision = 0)
    public Double getCardBalance() {
        return cardBalance;
    }

    public void setCardBalance(Double cardBalance) {
        this.cardBalance = cardBalance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TbOrganizationModel that = (TbOrganizationModel) o;

        if (id != that.id) return false;
        if (cardId != that.cardId) return false;
        if (orgName != null ? !orgName.equals(that.orgName) : that.orgName != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        if (cardBalance != null ? !cardBalance.equals(that.cardBalance) : that.cardBalance != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (orgName != null ? orgName.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + cardId;
        result = 31 * result + (cardBalance != null ? cardBalance.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "org")
    public Collection<TbCourseModel> getCourses() {
        return courses;
    }

    public void setCourses(Collection<TbCourseModel> courses) {
        this.courses = courses;
    }

    @OneToMany(mappedBy = "org")
    public Collection<TbOrgFinancialModel> getFinancials() {
        return financials;
    }
    public void setFinancials(Collection<TbOrgFinancialModel> financials) {
        this.financials = financials;
    }

}
