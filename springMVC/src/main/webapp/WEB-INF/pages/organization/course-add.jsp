<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<stripes:layout-render name="/WEB-INF/pages/layout/layout.jsp" pageTitle="机构-添加课程" act="10" page="org">
    <stripes:layout-component name="content">
        <div class="col-md-8">
            <h3>添加课程: </h3>
            <hr>
            <form:form cssClass="form-horizontal" action="/organization/add" method="post" commandName="course" role="form">
                <div class="form-group">
                    <label for="title" class="col-sm-2 control-label">课程名</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="title" name="title" placeholder="输入课程名：" required/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="teacher" class="col-sm-2 control-label">教师</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="teacher" name="teacher" placeholder="输入教师名：" required/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="price" class="col-sm-2 control-label">价格</label>
                    <div class="col-sm-10">
                        <input type="number" class="form-control" id="price" name="price" placeholder="输入价格：" required/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="time" class="col-sm-2 control-label">时间</label>
                    <div class="col-sm-10">
                        <input type="date" class="form-control" id="time" name="time" required/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="period" class="col-sm-2 control-label">课时</label>
                    <div class="col-sm-10">
                        <input type="number" class="form-control" id="period" name="period" required/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="content" class="col-sm-2 control-label">简介</label>
                    <div class="col-sm-10">
                        <textarea class="form-control" id="content" name="content" rows="3" placeholder="输入简介" required></textarea>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-sm btn-success form-control">发布课程</button>
                    </div>
                </div>
            </form:form>
        </div>
    </stripes:layout-component>
</stripes:layout-render>
