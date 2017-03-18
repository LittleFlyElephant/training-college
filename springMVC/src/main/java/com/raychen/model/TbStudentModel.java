package com.raychen.model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by raychen on 2017/3/11.
 */
@Entity
@Table(name = "tb_student", schema = "TCS", catalog = "")
public class TbStudentModel {
    private int id;
    private String userName;
    private String password;
    private String name;
    private String phone;
    private String email;
    private int cardId;
    private Byte cardState;
    private Double cardBalance;
    private Double cardConsume;
    private Integer cardScore;
    private Integer cardLevel;
    private Collection<TbStdChargeModel> charges;
    private Collection<TbStudyModel> studies;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_name", nullable = false, length = 255)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
    @Column(name = "email", nullable = true, length = 255)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
    @Column(name = "card_state", nullable = true)
    public Byte getCardState() {
        return cardState;
    }

    public void setCardState(Byte cardState) {
        this.cardState = cardState;
    }

    @Basic
    @Column(name = "card_balance", nullable = true, precision = 0)
    public Double getCardBalance() {
        return cardBalance;
    }

    public void setCardBalance(Double cardBalance) {
        this.cardBalance = cardBalance;
    }

    @Basic
    @Column(name = "card_consume", nullable = true, precision = 0)
    public Double getCardConsume() {
        return cardConsume;
    }

    public void setCardConsume(Double cardConsume) {
        this.cardConsume = cardConsume;
    }

    @Basic
    @Column(name = "card_score", nullable = true)
    public Integer getCardScore() {
        return cardScore;
    }

    public void setCardScore(Integer cardScore) {
        this.cardScore = cardScore;
    }

    @Basic
    @Column(name = "card_level", nullable = true)
    public Integer getCardLevel() {
        return cardLevel;
    }

    public void setCardLevel(Integer cardLevel) {
        this.cardLevel = cardLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TbStudentModel that = (TbStudentModel) o;

        if (id != that.id) return false;
        if (cardId != that.cardId) return false;
        if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (cardState != null ? !cardState.equals(that.cardState) : that.cardState != null) return false;
        if (cardBalance != null ? !cardBalance.equals(that.cardBalance) : that.cardBalance != null) return false;
        if (cardConsume != null ? !cardConsume.equals(that.cardConsume) : that.cardConsume != null) return false;
        if (cardScore != null ? !cardScore.equals(that.cardScore) : that.cardScore != null) return false;
        if (cardLevel != null ? !cardLevel.equals(that.cardLevel) : that.cardLevel != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + cardId;
        result = 31 * result + (cardState != null ? cardState.hashCode() : 0);
        result = 31 * result + (cardBalance != null ? cardBalance.hashCode() : 0);
        result = 31 * result + (cardConsume != null ? cardConsume.hashCode() : 0);
        result = 31 * result + (cardScore != null ? cardScore.hashCode() : 0);
        result = 31 * result + (cardLevel != null ? cardLevel.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "std")
    public Collection<TbStdChargeModel> getCharges() {
        return charges;
    }

    public void setCharges(Collection<TbStdChargeModel> charges) {
        this.charges = charges;
    }

    @OneToMany(mappedBy = "std")
    public Collection<TbStudyModel> getStudies() {
        return studies;
    }

    public void setStudies(Collection<TbStudyModel> studies) {
        this.studies = studies;
    }
}
