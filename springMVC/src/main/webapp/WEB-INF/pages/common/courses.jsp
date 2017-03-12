<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<stripes:layout-render name="/WEB-INF/pages/layout/layout.jsp" page="${page}" pageTitle="${pageTitle}" act="${type}">
    <stripes:layout-component name="content">
        <h1>课程列表: </h1>
        <c:if test="${page == \"org\"}">
            <a href="/organization/add" class="btn btn-primary" role="button">添加课程</a>
        </c:if>
        <hr>
        <c:if test="${page == \"org\"}">
            <c:set value="/organization" var="url_root" />
        </c:if>
        <c:if test="${page == \"std\"}">
            <c:set value="/student" var="url_root" />
        </c:if>
        <div class="row">
            <c:forEach items="${items}" var="item">
                <div class="col-sm-6 col-md-4">
                    <div class="thumbnail">
                        <img src="/img/1.jpg" alt="pic">
                        <div class="caption">
                            <h3>${item.title}</h3>
                            <p>${item.content}</p>
                            <p>
                                <a href="${url_root}/course/${item.id}" class="btn btn-primary" role="button">详情</a>
                            </p>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </stripes:layout-component>
</stripes:layout-render>
