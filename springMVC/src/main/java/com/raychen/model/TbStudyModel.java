package com.raychen.model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by raychen on 2017/3/11.
 */
@Entity
@Table(name = "tb_study", schema = "TCS", catalog = "")
public class TbStudyModel {
    private int id;
    private Integer score;
    private byte state;
    private TbCourseModel course;
    private TbStudentModel std;
    private Collection<TbStudyPeriodModel> periods;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "score", nullable = true)
    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
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

        TbStudyModel that = (TbStudyModel) o;

        if (id != that.id) return false;
        if (state != that.state) return false;
        if (score != null ? !score.equals(that.score) : that.score != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (score != null ? score.hashCode() : 0);
        result = 31 * result + (int) state;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id", nullable = false)
    public TbCourseModel getCourse() {
        return course;
    }

    public void setCourse(TbCourseModel course) {
        this.course = course;
    }

    @ManyToOne
    @JoinColumn(name = "std_id", referencedColumnName = "id", nullable = false)
    public TbStudentModel getStd() {
        return std;
    }

    public void setStd(TbStudentModel std) {
        this.std = std;
    }

    @OneToMany(mappedBy = "study")
    public Collection<TbStudyPeriodModel> getPeriods() {
        return periods;
    }

    public void setPeriods(Collection<TbStudyPeriodModel> periods) {
        this.periods = periods;
    }
}
