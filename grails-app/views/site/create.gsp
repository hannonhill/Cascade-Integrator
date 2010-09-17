

<%@ page import="com.hannonhill.integrator.Site" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'site.label', default: 'Site')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
         <script type="text/javascript">
			var i = 0;
			var j = 0;
        
        	function addFolder() {
				var input = new Element("input", {name : "folders[" + i + "].name", type : "text", style : "display: block"});
				$("newfolders").insert ({
					"bottom" : 	input
				} );
				i++;
        	}

        	function addContentType() {
				var input = new Element("input", {name : "contentTypes[" + j + "].name", type : "text", style : "display: block"});
				$("newct").insert ({
					"bottom" : 	input
				} );
				j++;
        	}
        	
        </script>
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
            <g:hasErrors bean="${siteInstance}">
            <div class="errors">
                <g:renderErrors bean="${siteInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>                            
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name"><g:message code="site.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: siteInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${siteInstance?.name}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                    <div id="newct">
                    	<input type="button" value="Add Content Type" onclick="addContentType();" />
                    </div>
                    <div id="newfolders">
                    	<input type="button" value="Add Folder" onclick="addFolder();" />
                    </div>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
