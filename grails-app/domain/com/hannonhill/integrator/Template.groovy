package com.hannonhill.integrator

import com.hannonhill.www.ws.ns.AssetOperationService.CreateResult
import com.hannonhill.www.ws.ns.AssetOperationService.Asset
import com.hannonhill.www.ws.ns.AssetOperationService.PageConfiguration
import com.hannonhill.www.ws.ns.AssetOperationService.PageConfigurationSet
import com.hannonhill.www.ws.ns.AssetOperationService.SerializationType

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
	
	PageConfiguration createPageConfiguration(com.hannonhill.www.ws.ns.AssetOperationService.Template template, String type, boolean defaultConfig) {
		PageConfiguration config = new PageConfiguration()
		config.setOutputExtension("." + type.toLowerCase())
		config.setName(type.toUpperCase())		
		config.setTemplatePath("/" + template.getName()) //hardcoding this right now; not sure why getPath or getId will not work on template obj.
		
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
	
	PageConfigurationSet createPageConfigurationSet(String name, PageConfiguration[] configs) {
		PageConfigurationSet configSet = new PageConfigurationSet()
		configSet.setName(name)
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
