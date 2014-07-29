<%-- 
    Document   : default
    Created on : 23 Jul, 2014, 2:46:06 PM
    Author     : cis
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file='objects.jsp' %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" media="screen">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/theme.css">
        <title><% out.println(data.get("title")); %></title>
    </head>
    <body>
        
        <jsp:include page="constants.jsp" flush="true"/>
        <jsp:include page="../elements/header.jsp" flush="true"/>
        <div class="container">
            <jsp:include page="<%=centerpage%>" flush="true"/>
            <jsp:include page="../elements/footer.jsp" flush="true"/>
        </div>
        <script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/bootswatch.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/custom/app.js"></script>
        <script>
           commonfn.doAjax(
                   {
                       url: "${pageContext.request.contextPath}/home",
                       data: ""
                   });
        </script>
    </body>
</html>