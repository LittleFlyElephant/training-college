package com.raychen.model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by raychen on 2017/3/11.
 */
@Entity
@Table(name = "tb_course", schema = "TCS", catalog = "")
public class TbCourseModel {
    private int id;
    private String teacher;
    private String title;
    private String content;
    private String time;
    private double price;
    private byte state;
    private Integer period;
    private TbOrganizationModel org;
    private Collection<TbStudyModel> studies;
    private Collection<TbUserModel> users;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "teacher", nullable = false, length = 255)
    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    @Basic
    @Column(name = "title", nullable = false, length = 255)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "content", nullable = true, length = 255)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
    @Column(name = "price", nullable = false)
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Basic
    @Column(name = "state", nullable = false)
    public byte getState() {
        return state;
    }

    public void setState(byte state) {
        this.state = state;
    }

    @Basic
    @Column(name = "period", nullable = true)
    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TbCourseModel that = (TbCourseModel) o;

        if (id != that.id) return false;
        if (price != that.price) return false;
        if (state != that.state) return false;
        if (teacher != null ? !teacher.equals(that.teacher) : that.teacher != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;
        if (period != null ? !period.equals(that.period) : that.period != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        long temp;
        result = 31 * result + (teacher != null ? teacher.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (int) state;
        result = 31 * result + (period != null ? period.hashCode() : 0);
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

    @OneToMany(mappedBy = "course")
    public Collection<TbStudyModel> getStudies() {
        return studies;
    }

    public void setStudies(Collection<TbStudyModel> studies) {
        this.studies = studies;
    }

    @OneToMany(mappedBy = "cid")
    public Collection<TbUserModel> getUsers() {
        return users;
    }

    public void setUsers(Collection<TbUserModel> users) {
        this.users = users;
    }
}
