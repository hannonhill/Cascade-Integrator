package com.hannonhill.integrator

import com.hannonhill.www.ws.ns.AssetOperationService.Site as CascadeSite
import com.hannonhill.www.ws.ns.AssetOperationService.Folder as CascadeFolder
import com.hannonhill.www.ws.ns.AssetOperationService.ContentType as CascadeContentType

class SiteController {
	
	def scaffold = true
	
	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	
	def index = {
		redirect(action: "list", params: params)
	}
	
	def list = {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[siteInstanceList: Site.list(params), siteInstanceTotal: Site.count()]
	}
	
	def create = {
		def siteInstance = new Site()
		siteInstance.properties = params
		return [siteInstance: siteInstance]
	}
	
	def save = {
		def siteInstance = new Site(params)
		def internalFolderInstance = new InternalCmsAssets(name: "_internal")
		def homepageInstance = new ContentType(name: "Homepage")
		def standardInstance = new ContentType(name: "Standard")
		
		//create site and content type including all related content type objects
		//TODO Add try...catch block to handle timeouts and connection exceptions		
		CascadeSite site = siteInstance.createRemoteSite()
		CascadeFolder[] internalFolder = internalFolderInstance.createRemoteFolder(site, site.getRootFolderId())

		CascadeContentType homepage = homepageInstance.createRemoteContentType(site, internalFolder)
		CascadeContentType standard = standardInstance.createRemoteContentType(site, internalFolder)
		
		siteInstance.contentTypes.each() {
			def ctInstance = new ContentType(name: it.name)
			def ct = ctInstance.createRemoteContentType(site, internalFolder)
		}
		
		siteInstance.folders.each() {
			def folderInstance = new Folder(name: it.name)
			def folder = folderInstance.createRemoteFolder(site, site.getRootFolderId())
			standardInstance.createRemotePage(standard, 'index', folder[0].getId())
		}
		//adding this one since it's a special kind of folder
		siteInstance.addToFolders(internalFolderInstance)
		siteInstance.addToContentTypes(homepageInstance)
		siteInstance.addToContentTypes(standardInstance)
		
		//      TODO provide for .jar files that can be dropped in and consumed for creating default XSLT and Velocity formats
		
		if (siteInstance.save(flush: true)) {
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'site.label', default: 'Site'), siteInstance.id])}"
			redirect(action: "show", id: siteInstance.id)
		}
		else {
			render(view: "create", model: [siteInstance: siteInstance])
		}
	}
	
	def show = {
		def siteInstance = Site.get(params.id)
		if (!siteInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'site.label', default: 'Site'), params.id])}"
			redirect(action: "list")
		}
		else {
			[siteInstance: siteInstance]
		}
	}
	
	def edit = {
		def siteInstance = Site.get(params.id)
		redirect(action: "show", params:[id: siteInstance.id])
	}
	
	def delete = {
		def siteInstance = Site.get(params.id)
		if (siteInstance) {
			try {
				siteInstance.delete(flush: true)
				flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'site.label', default: 'Site'), params.id])}"
				redirect(action: "list")
			}
			catch (org.springframework.dao.DataIntegrityViolationException e) {
				flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'site.label', default: 'Site'), params.id])}"
				redirect(action: "show", id: params.id)
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'site.label', default: 'Site'), params.id])}"
			redirect(action: "list")
		}
	}
}
