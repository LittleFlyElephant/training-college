<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<stripes:layout-render name="/WEB-INF/pages/layout/layout.jsp" page="${page}" pageTitle="${pageTitle}" act="${type}">
    <stripes:layout-component name="content">
        <c:if test="${msgType != null}">
            <div class="alert alert-${msgType} fade in" role="alert">
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4>${msg}</h4>
            </div>
        </c:if>
        <%--<img src="/img/back1.png" class="img-responsive" alt="background">--%>
        <c:if test="${page == \"org\"}">
            <c:set value="/organization" var="url_root" />
        </c:if>
        <c:if test="${page == \"std\"}">
            <c:set value="/student" var="url_root" />
        </c:if>
        <c:if test="${page == \"plat\"}">
            <c:set value="/platform" var="url_root" />
        </c:if>
        <div class="row">
            <h3>课程列表</h3>
            <hr>
            <c:forEach items="${items}" var="item">
                <div class="col-md-4">
                    <div class="thumbnail">
                        <img class="img-responsive" src="/img/course/${item.period}.jpg" alt="pic">
                        <div class="caption">
                            <h3>${item.title}</h3>
                            <p>${item.teacher}</p>
                            <c:choose>
                                <c:when test="${page == 'org'}">
                                    <a href="${url_root}/course/${item.id}/1" class="btn-block btn btn-primary" role="button">详情</a>
                                </c:when>
                                <c:otherwise>
                                    <a href="${url_root}/course/${item.id}" class="btn-block btn btn-primary" role="button">详情</a>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </stripes:layout-component>
    <stripes:layout-component name="scripts">
        <script>
            $(document).ready(function(){
                window.setTimeout(function(){
                    $('[data-dismiss="alert"]').alert('close');
                },2000);
            });
        </script>
    </stripes:layout-component>
</stripes:layout-render>
