package com.hannonhill.integrator

import com.hannonhill.www.ws.ns.AssetOperationService.PageConfiguration
import com.hannonhill.www.ws.ns.AssetOperationService.Page
import com.hannonhill.www.ws.ns.AssetOperationService.PageConfigurationSet
import com.hannonhill.www.ws.ns.AssetOperationService.CreateResult
import com.hannonhill.www.ws.ns.AssetOperationService.Asset
import com.hannonhill.www.ws.ns.AssetOperationService.AssetFactory
import com.hannonhill.www.ws.ns.AssetOperationService.AssetFactoryContainer
import com.hannonhill.www.ws.ns.AssetOperationService.AssetFactoryWorkflowMode
import com.hannonhill.www.ws.ns.AssetOperationService.SerializationType
import com.hannonhill.www.ws.ns.AssetOperationService.Read
import com.hannonhill.www.ws.ns.AssetOperationService.Template
import com.hannonhill.www.ws.ns.AssetOperationService.ReadResult
import com.hannonhill.www.ws.ns.AssetOperationService.Identifier
import com.hannonhill.www.ws.ns.AssetOperationService.EntityTypeString
import com.hannonhill.www.ws.ns.AssetOperationService.Site as CascadeSite
import com.hannonhill.www.ws.ns.AssetOperationService.ContentType as CascadeContentType
import com.hannonhill.www.ws.ns.AssetOperationService.Folder as CascadeFolder

class ContentTypeService {
	
	static transactional = true
	
	CascadeSite site
	ContentType ct
	AssetFactoryContainer assetFactoryContainer
	CascadeFolder[] folders
	
	void createRemoteContentType(CascadeSite site, ContentType ct, CascadeFolder[] folders) {
		this.ct = ct
		this.site = site
		this.folders = folders
		PageConfiguration[] pc
		
		Template t = this.createRemoteTemplate()
		Template tXml = this.createRemoteTemplate(true)
		pc = [
			this.createPageConfiguration(t, "html", true),
			this.createPageConfiguration(tXml, "xml", false)
		]
		PageConfigurationSet pcs = this.createPageConfigurationSet(this.ct.name, pc)
		CascadeContentType cct = this.createRemoteContentType(pcs)
		
		int index = cct.getName().toLowerCase().indexOf("homepage")
		
		if(index < 0) {
			Page p = this.createRemoteBaseAsset(cct)
			AssetFactory af = this.createRemoteAssetFactory(p)
		}
		else {
			Page p = this.createRemotePage(cct, "index", this.site.getRootFolderId())
		}
	}
	
	private Template prepareRemoteTemplate() {
		Template template = new Template()
		template.setSiteName(this.site.getName())
		template.setParentFolderId(this.folders[1].getId())
		
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
		template.setXml("<xml><system-region name=\"DEFAULT\"/></xml>")
		
		Asset asset = new Asset()
		asset.setTemplate(template)
		
		String wsId = this.ct.handler.create(this.ct.authorization, asset).getCreatedAssetId()
		
		Template remoteTemplate = this.readRemoteTemplate(wsId)
		
		return remoteTemplate
	}
	
	private Template readRemoteTemplate(String id) {
		
		Asset a = this.readRemoteAsset(id, EntityTypeString.template)
		
		Template t = a.getTemplate()
		
		return t
	}
	
	private PageConfigurationSet readRemotePageConfigurationSet(String id) {
		
		Asset a = this.readRemoteAsset(id, EntityTypeString.pageconfigurationset)
		
		PageConfigurationSet pcs = a.getPageConfigurationSet()
		
		return pcs
	}
	
	private CascadeContentType readRemoteContentType(String id) {
		
		Asset a = this.readRemoteAsset(id, EntityTypeString.contenttype)
		
		CascadeContentType cct = a.getContentType()
		
		return cct
	}
	
	private Page readRemotePage(String id) {
		
		Asset a = this.readRemoteAsset(id, EntityTypeString.page)
		
		Page p = a.getPage()
		
		return p
	}
	
	private AssetFactoryContainer readRemoteAssetFactoryContainer(String id) {
		
		Asset a = this.readRemoteAsset(id, EntityTypeString.assetfactorycontainer)
		
		AssetFactoryContainer afc = a.getAssetFactoryContainer()
		
		return afc
	}
	
	private Asset readRemoteAsset(String id, EntityTypeString type) {
		Identifier toRead = new Identifier();
		toRead.setId(id);
		toRead.setType(type);
		
		Read read = new Read();
		read.setIdentifier(toRead);
		
		ReadResult result = this.ct.handler.read(this.ct.authorization, toRead);
		Asset a = result.getAsset()
		
		return a
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
	
	private PageConfigurationSet createPageConfigurationSet(String name, PageConfiguration[] configs) {
		PageConfigurationSet configSet = new PageConfigurationSet()
		configSet.setName(name)
		configSet.setSiteId(this.site.getId())
		configSet.setParentContainerPath("/")
		configSet.setPageConfigurations(configs)
		
		Asset asset = new Asset()
		asset.setPageConfigurationSet(configSet)
		
		String wsId = this.ct.handler.create(this.ct.authorization, asset).getCreatedAssetId()
		
		PageConfigurationSet remotePageConfigurationSet = this.readRemotePageConfigurationSet(wsId)
		
		return remotePageConfigurationSet
	}
	
	private CascadeContentType createRemoteContentType(PageConfigurationSet pcs) {
		CascadeContentType cct = new CascadeContentType()
		cct.setName(this.ct.name)
		cct.setPageConfigurationSetId(pcs.getId())
		cct.setSiteId(site.getId())
		cct.setMetadataSetId(this.site.getDefaultMetadataSetId())
		cct.setParentContainerPath("/")
		
		Asset asset = new Asset()
		asset.setContentType(cct)
		
		String wsId = this.ct.handler.create(this.ct.authorization, asset).getCreatedAssetId()
		
		CascadeContentType remoteContentType = this.readRemoteContentType(wsId)
		
		return remoteContentType
	}
	
	private Page createRemoteBaseAsset(CascadeContentType cct) {
		Page page = new Page()
		page.setName(this.ct.name)
		page.setParentFolderId(this.folders[2].getId())
		page.setSiteId(this.site.getId())
		page.setContentTypeId(cct.getId())
		page.setXhtml("My default page content.")
		
		Asset asset = new Asset()
		asset.setPage(page)
		
		def wsId = this.ct.handler.create(this.ct.authorization, asset).getCreatedAssetId()
		
		Page remotePage = this.readRemotePage(wsId)
		
		return remotePage
	}
	
	private Page createRemotePage(CascadeContentType cct, String name, String parentFolderId) {
		Page page = new Page()
		page.setName(name)
		page.setParentFolderId(parentFolderId)
		page.setSiteId(this.site.getId())
		page.setContentTypeId(cct.getId())
		page.setXhtml("My default page content.")
		
		Asset asset = new Asset()
		asset.setPage(page)
		
		def wsId = this.ct.handler.create(this.ct.authorization, asset).getCreatedAssetId()
		
		Page remotePage = this.readRemotePage(wsId)
		
		return remotePage
	} 
	
	private void createAssetFactoryContainer() {
		AssetFactoryContainer afc = new AssetFactoryContainer()
		afc.setName(this.site.getName())
		afc.setSiteId(this.site.getId())
		afc.setParentContainerPath("/")
		afc.setApplicableGroups("")
		
		Asset asset = new Asset()
		asset.setAssetFactoryContainer(afc)
		
		def wsId = this.ct.handler.create(this.ct.authorization, asset).getCreatedAssetId()
		
		AssetFactoryContainer remoteAfc = this.readRemoteAssetFactoryContainer(wsId)
		
		this.assetFactoryContainer = remoteAfc
	}
	
	private AssetFactory createRemoteAssetFactory(Page p) {
		AssetFactory af = new AssetFactory()
		
		af.setName(this.ct.name)
		
		if(this.assetFactoryContainer != null) {
			af.setParentContainerPath(this.assetFactoryContainer.getPath())
		}
		else {
			createAssetFactoryContainer()
			af.setParentContainerPath(this.assetFactoryContainer.getPath())
		}
		
		
		af.setAssetType("page")
		af.setWorkflowMode(new AssetFactoryWorkflowMode().value1)
		af.setSiteId(this.site.getId())
		af.setBaseAssetId(p.getId())
		af.setApplicableGroups("")
		
		Asset asset = new Asset()
		asset.setAssetFactory(af)
		
		def wsId = this.ct.handler.create(this.ct.authorization, asset).getCreatedAssetId()		
		
		//returns the local AssetFactory object instead of a remote one
		return af
	}
}
