

<%@ page import="com.hannonhill.integrator.ContentType" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'contentType.label', default: 'ContentType')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${contentTypeInstance}">
            <div class="errors">
                <g:renderErrors bean="${contentTypeInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="wsId"><g:message code="contentType.wsId.label" default="Ws Id" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: contentTypeInstance, field: 'wsId', 'errors')}">
                                    <g:textField name="wsId" value="${contentTypeInstance?.wsId}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name"><g:message code="contentType.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: contentTypeInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${contentTypeInstance?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="site"><g:message code="contentType.site.label" default="Site" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: contentTypeInstance, field: 'site', 'errors')}">
                                    <g:select name="site.id" from="${com.hannonhill.integrator.Site.list()}" optionKey="id" value="${contentTypeInstance?.site?.id}"  />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
