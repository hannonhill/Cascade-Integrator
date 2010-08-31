package com.hannonhill.integrator

import com.hannonhill.www.ws.ns.AssetOperationService.CreateResult;
import com.hannonhill.www.ws.ns.AssetOperationService.Asset;
import com.hannonhill.www.ws.ns.AssetOperationService.ReadResult;
import com.hannonhill.www.ws.ns.AssetOperationService.Read;
import com.hannonhill.www.ws.ns.AssetOperationService.Identifier;
import com.hannonhill.www.ws.ns.AssetOperationService.EntityTypeString;

class Template {
	
	def authenticationService
	
	static belongsTo = [site: Site]
	
	String name
	String wsId
	String body
	
	String toString() {
		"$name"
	}

    static constraints = {
		wsId(nullable: true)
    }
	
	com.hannonhill.www.ws.ns.AssetOperationService.Template createRemoteTemplate(com.hannonhill.www.ws.ns.AssetOperationService.Site site) {
		com.hannonhill.www.ws.ns.AssetOperationService.Template template = new com.hannonhill.www.ws.ns.AssetOperationService.Template()
		template.setName(this.name)
		template.setSiteName(site.getName())
		template.setParentFolderPath("/")
		template.setXml(this.body)
				
		Asset asset = new Asset()
		asset.setTemplate(template)
		
		def authorization = authenticationService.getAuthentication()
		def handler = authenticationService.getHandler()
		def templateId = handler.create(authorization, asset)
		
		this.wsId = templateId
		
		return template
	}
	
}
