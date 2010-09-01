package com.hannonhill.integrator

import com.hannonhill.www.ws.ns.AssetOperationService.CreateResult
import com.hannonhill.www.ws.ns.AssetOperationService.Asset
import com.hannonhill.www.ws.ns.AssetOperationService.PageConfiguration
import com.hannonhill.www.ws.ns.AssetOperationService.PageConfigurationSet
import com.hannonhill.www.ws.ns.AssetOperationService.SerializationType
import com.hannonhill.www.ws.ns.AssetOperationService.Read
import com.hannonhill.www.ws.ns.AssetOperationService.ReadResult
import com.hannonhill.www.ws.ns.AssetOperationService.Identifier
import com.hannonhill.www.ws.ns.AssetOperationService.EntityTypeString

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
		template.setParentFolderId(site.getRootFolderId())
		template.setXml(this.body)
				
		Asset asset = new Asset()
		asset.setTemplate(template)
		
		def authorization = authenticationService.getAuthentication()
		def handler = authenticationService.getHandler()
		this.wsId = handler.create(authorization, asset).getCreatedAssetId()
		
		com.hannonhill.www.ws.ns.AssetOperationService.Template remoteTemplate = this.readRemoteTemplate(this.wsId)
		
		return remoteTemplate
	}
	
	com.hannonhill.www.ws.ns.AssetOperationService.Template readRemoteTemplate(String id) {
		Identifier toRead = new Identifier();
		toRead.setId(id);
		toRead.setType(EntityTypeString.template);

		Read read = new Read();
		read.setIdentifier(toRead);

		def authorization = authenticationService.getAuthentication()
		def handler = authenticationService.getHandler()
		ReadResult result = handler.read(authorization, toRead);
		Asset a = result.getAsset()
		com.hannonhill.www.ws.ns.AssetOperationService.Template t = a.getTemplate()
		
		return t
	}
	
	PageConfiguration createPageConfiguration(com.hannonhill.www.ws.ns.AssetOperationService.Template template, String type, boolean defaultConfig) {
		PageConfiguration config = new PageConfiguration()
		config.setOutputExtension("." + type.toLowerCase())
		config.setName(type.toUpperCase())		
		config.setTemplatePath(template.getPath())
		
		if(defaultConfig == true) {
			config.setDefaultConfiguration(true)
		}
		
		if(type == "xml".toLowerCase()) {
			config.setSerializationType(new SerializationType().XML)
		}
		else {
			config.setSerializationType(new SerializationType().HTML)
		}
		
		return config		
	}
	
	PageConfigurationSet createPageConfigurationSet(String name, PageConfiguration[] configs, com.hannonhill.www.ws.ns.AssetOperationService.Site site) {
		PageConfigurationSet configSet = new PageConfigurationSet()
		configSet.setName(name)
		configSet.setSiteId(site.getId())
		configSet.setParentContainerPath("/")
		configSet.setPageConfigurations(configs)
		
		Asset asset = new Asset()
		asset.setPageConfigurationSet(configSet)
		
		def authorization = authenticationService.getAuthentication()
		def handler = authenticationService.getHandler()
		def configSetId = handler.create(authorization, asset)
		
		//this.wsId = configSetId
		
		return configSet
	}
	
}
