package com.hannonhill.integrator

import com.hannonhill.www.ws.ns.AssetOperationService.Site as CascadeSite

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
		def homepageInstance = new ContentType(name: "Homepage")
		def standardInstance = new ContentType(name: "Standard")
		siteInstance.addToContentTypes(homepageInstance)
		siteInstance.addToContentTypes(standardInstance)
		
		//create site and content type including all related content type objects
		//TODO Add try...catch block to handle timeouts and connection exceptions		
		CascadeSite site = siteInstance.createRemoteSite()
		homepageInstance.createRemoteContentType(site)
		standardInstance.createRemoteContentType(site)
		
		//		TODO create CMS Content Type objects in ContentTypeService
		//		TODO create _internal folder tree
		//		TODO create templates in _internal/templates folder
		//		TODO create Scaffold domain type for folders, index pages
		
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
		if (!siteInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'site.label', default: 'Site'), params.id])}"
			redirect(action: "list")
		}
		else {
			return [siteInstance: siteInstance]
		}
	}
	
	def update = {
		def siteInstance = Site.get(params.id)
		if (siteInstance) {
			if (params.version) {
				def version = params.version.toLong()
				if (siteInstance.version > version) {
					
					siteInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'site.label', default: 'Site')] as Object[], "Another user has updated this Site while you were editing")
					render(view: "edit", model: [siteInstance: siteInstance])
					return
				}
			}
			siteInstance.properties = params
			if (!siteInstance.hasErrors() && siteInstance.save(flush: true)) {
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'site.label', default: 'Site'), siteInstance.id])}"
				redirect(action: "show", id: siteInstance.id)
			}
			else {
				render(view: "edit", model: [siteInstance: siteInstance])
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'site.label', default: 'Site'), params.id])}"
			redirect(action: "list")
		}
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
