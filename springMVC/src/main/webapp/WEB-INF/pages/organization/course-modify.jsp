<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<stripes:layout-render name="/WEB-INF/pages/organization/org-layout.jsp" pageTitle="机构-课程详情" act="3" username="${org.orgName}">
    <stripes:layout-component name="content">
        <h1>修改课程详情: </h1>
        <hr>
        <form:form action="/organization/${org.id}/course/${course.id}/detail" method="post" commandName="course" role="form">
            <div class="form-group">
                <label for="title">课程名:</label>
                <input type="text" class="form-control" id="title" name="title" placeholder="输入课程名:"
                       value="${course.title}" required/>
            </div>
            <div class="form-group">
                <label for="teacher">课程教师:</label>
                <input type="text" class="form-control" id="teacher" name="teacher" placeholder="输入课程教师:"
                       value="${course.teacher}"/>
            </div>
            <div class="form-group">
                <label for="time">课程时间:</label>
                <input type="date" class="form-control" id="time" name="time" value="${course.time}"/>
            </div>
            <div class="form-group">
                <label for="price">课程价格:</label>
                <input type="text" class="form-control" id="price" name="price" placeholder="输入课程价格:"
                       value="${course.price}"/>
            </div>
            <div class="form-group">
                <button type="submit" class="btn btn-sm btn-success">提交修改</button>
            </div>
        </form:form>
    </stripes:layout-component>
</stripes:layout-render>

