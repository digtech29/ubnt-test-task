<%@page import="com.ubnt.testTask.entities.TimeRange"%>
<%@page import="com.ubnt.testTask.entities.EventType"%>
<%@page contentType="text/html;charset=UTF-8" language="java"%>
<ul>
	Rest api WADL (Web Application Description Language): 
	<li><a href="api/application.wadl">api/application.wadl</a></li>
</ul>
<ul>
	Rest calls examples: 
	<li><a href="api/top100?timeRange=<%=TimeRange.HOUR.name()%>">get last hour top100 subreddits</a></li>
	<li><a href="api/activity?timeRange=<%=TimeRange.HOUR.name()%>&eventType=<%=EventType.COMMENT.name()%>">get last hour comments activity</a></li>
	<li><a href="api/activity?timeRange=<%=TimeRange.HOUR.name()%>&eventType=<%=EventType.SUBMISSION.name()%>">get last hour submissions activity</a></li>
	<li><a href="api/mostActive?timeRange=<%=TimeRange.HOUR.name()%>&eventType=<%=EventType.COMMENT.name()%>">get last hour comments most active subreddits</a></li>
	<li><a href="api/mostActive?timeRange=<%=TimeRange.HOUR.name()%>&eventType=<%=EventType.SUBMISSION.name()%>">get last hour submissions most active subreddits</a></li>
</ul>
	


