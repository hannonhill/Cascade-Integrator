
<%@ page import="com.hannonhill.integrator.ContentType" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'contentType.label', default: 'ContentType')}" />
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
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'contentType.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="wsId" title="${message(code: 'contentType.wsId.label', default: 'Ws Id')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'contentType.name.label', default: 'Name')}" />
                        
                            <th><g:message code="contentType.site.label" default="Site" /></th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${contentTypeInstanceList}" status="i" var="contentTypeInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${contentTypeInstance.id}">${fieldValue(bean: contentTypeInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: contentTypeInstance, field: "wsId")}</td>
                        
                            <td>${fieldValue(bean: contentTypeInstance, field: "name")}</td>
                        
                            <td>${fieldValue(bean: contentTypeInstance, field: "site")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${contentTypeInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
