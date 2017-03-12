package com.raychen.model;

import javax.persistence.*;

/**
 * Created by raychen on 2017/3/12.
 */
@Entity
@Table(name = "tb_study_period", schema = "TCS", catalog = "")
public class TbStudyPeriodModel {
    private int id;
    private int period;
    private Byte state;
    private String message;
    private TbStudyModel study;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "period", nullable = false)
    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    @Basic
    @Column(name = "state", nullable = true)
    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    @Basic
    @Column(name = "message", nullable = true, length = 255)
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TbStudyPeriodModel that = (TbStudyPeriodModel) o;

        if (id != that.id) return false;
        if (period != that.period) return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;
        if (message != null ? !message.equals(that.message) : that.message != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + period;
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "study_id", referencedColumnName = "id", nullable = false)
    public TbStudyModel getStudy() {
        return study;
    }

    public void setStudy(TbStudyModel study) {
        this.study = study;
    }
}
