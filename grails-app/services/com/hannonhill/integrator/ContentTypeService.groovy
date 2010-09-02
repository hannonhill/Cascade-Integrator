package com.hannonhill.integrator

import com.hannonhill.www.ws.ns.AssetOperationService.PageConfiguration;
import com.hannonhill.www.ws.ns.AssetOperationService.PageConfigurationSet;
import com.hannonhill.www.ws.ns.AssetOperationService.CreateResult
import com.hannonhill.www.ws.ns.AssetOperationService.Asset
import com.hannonhill.www.ws.ns.AssetOperationService.SerializationType
import com.hannonhill.www.ws.ns.AssetOperationService.Read
import com.hannonhill.www.ws.ns.AssetOperationService.Template
import com.hannonhill.www.ws.ns.AssetOperationService.ReadResult
import com.hannonhill.www.ws.ns.AssetOperationService.Identifier
import com.hannonhill.www.ws.ns.AssetOperationService.EntityTypeString

class ContentTypeService {
	
	static transactional = true
	
	com.hannonhill.www.ws.ns.AssetOperationService.Site site
	ContentType ct
	
	void createRemoteContentType(com.hannonhill.www.ws.ns.AssetOperationService.Site site, ContentType ct) {
		this.ct = ct
		this.site = site
		PageConfiguration[] pc
		
		Template t = this.createRemoteTemplate()
		Template tXml = this.createRemoteTemplate(true)
		pc = [this.createPageConfiguration(t, "html", true), this.createPageConfiguration(t, "xml", true)]
		PageConfigurationSet pcs = this.createPageConfigurationSet(this.ct.name, pc, this.site) 
	}
	
	private Template prepareRemoteTemplate() {
		Template template = new Template()
		template.setSiteName(this.site.getName())
		template.setParentFolderId(this.site.getRootFolderId())
		
		return template
	}
	
	private Template createRemoteTemplate() {
		Template template = prepareRemoteTemplate()		
		template.setName(this.ct.name)		
		template.setXml(this.ct.body)
		
		Asset asset = new Asset()
		asset.setTemplate(template)		
		
		String wsId = this.ct.handler.create(this.ct.authorization, asset).getCreatedAssetId()
		
		Template remoteTemplate = this.readRemoteTemplate(wsId)
		
		return remoteTemplate
	}
	
	private Template createRemoteTemplate(boolean isXml) {
		Template template = prepareRemoteTemplate()
		template.setName(this.ct.name + "XML")
		template.setXml("<system-region name=\"DEFAULT\"/>")
		
		Asset asset = new Asset()
		asset.setTemplate(template)
		
		String wsId = this.ct.handler.create(this.ct.authorization, asset).getCreatedAssetId()
		
		Template remoteTemplate = this.readRemoteTemplate(wsId)
		
		return remoteTemplate
	}
	
	private Template readRemoteTemplate(String id) {
		Identifier toRead = new Identifier();
		toRead.setId(id);
		toRead.setType(EntityTypeString.template);
		
		Read read = new Read();
		read.setIdentifier(toRead);
		
		ReadResult result = this.ct.handler.read(this.ct.authorization, toRead);
		Asset a = result.getAsset()
		Template t = a.getTemplate()
		
		return t
	}
	
	private PageConfiguration createPageConfiguration(Template template, String type, boolean defaultConfig) {
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
	
	private PageConfigurationSet createPageConfigurationSet(String name, PageConfiguration[] configs, com.hannonhill.www.ws.ns.AssetOperationService.Site site) {
		PageConfigurationSet configSet = new PageConfigurationSet()
		configSet.setName(name)
		configSet.setSiteId(site.getId())
		configSet.setParentContainerPath("/")
		configSet.setPageConfigurations(configs)
		
		Asset asset = new Asset()
		asset.setPageConfigurationSet(configSet)
		
		def configSetId = this.ct.handler.create(this.ct.authorization, asset)
		
		return configSet
	}
}
