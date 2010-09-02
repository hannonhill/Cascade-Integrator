
<%@ page import="com.hannonhill.integrator.Site" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'site.label', default: 'Site')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <g:each in="${siteInstanceList}" status="i" var="siteInstance">
                	<p><g:link action="show" id="${siteInstance.id}"><span style="font-size: 2em; text-decoration: underline">${fieldValue(bean: siteInstance, field: "name")}</span></g:link></p>
                </g:each>
            </div>
            <br/><br/>
            <!-- <div class="paginateButtons"> -->
            <div>
                <g:paginate total="${siteInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
