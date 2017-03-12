<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<stripes:layout-render name="/WEB-INF/pages/layout/layout.jsp" pageTitle="机构-添加课程" act="3" page="org">
    <stripes:layout-component name="content">
        <h1>添加课程: </h1>
        <hr>
        <div class="col-md-6">
            <form:form action="/organization/add" method="post" commandName="course" role="form">
                <div class="form-group">
                    <label for="title">课程名</label>
                    <input type="text" class="form-control" id="title" name="title" placeholder="输入课程名："/>
                </div>
                <div class="form-group">
                    <label for="teacher">教师</label>
                    <input type="text" class="form-control" id="teacher" name="teacher" placeholder="输入教师名："/>
                </div>
                <div class="form-group">
                    <label for="price">价格</label>
                    <input type="text" class="form-control" id="price" name="price" placeholder="输入价格："/>
                </div>
                <div class="form-group">
                    <label for="time">时间</label>
                    <input type="date" class="form-control" id="time" name="time"/>
                </div>
                <div class="form-group">
                    <label for="period">课时</label>
                    <input type="text" class="form-control" id="period" name="period"/>
                </div>
                <div class="form-group">
                    <label for="content">简介</label>
                    <textarea class="form-control" id="content" name="content" rows="3" placeholder="输入简介"></textarea>
                </div>
                <div class="form-group">
                    <button type="submit" class="btn btn-sm btn-success form-control">发布课程</button>
                </div>
            </form:form>
        </div>
    </stripes:layout-component>
</stripes:layout-render>
