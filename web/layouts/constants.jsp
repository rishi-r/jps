<%@include file='objects.jsp' %>
<%
    Map<String, String> gv = (Map<String, String>) request.getAttribute("variables"); // global variables
%>
<script>
    SITE_NAME = "<% out.print(gv.get("SITE_NAME"));%>";
    HTTP_PATH = "${pageContext.request.contextPath}/";
    ASSETS_PATH = "${pageContext.request.contextPath}/assets/";
    IMG_PATH = ASSETS_PATH+"img/";
    ICONS_PATH = IMG_PATH+"icons/";
</script>