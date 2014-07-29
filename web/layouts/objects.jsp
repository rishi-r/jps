<%@page import="java.util.Map"%>
<%@page import="utils.CommonHelper"%>
<%
CommonHelper ch = new CommonHelper();
Map<String, String> variables = ch.constants();
request.setAttribute("variables", variables);
String centerpage = "../"+(String) request.getAttribute("centerpage") + ".jsp";

Map<String, String> data = (Map)request.getAttribute("data");

//out.println(ch.path("images"));
%>