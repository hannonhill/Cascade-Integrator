package com.hannonhill.integrator

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
		def template1 = new Template(name: "Homepage", body:"<html><head></head><body><system-region name=\"DEFAULT\"/></body></html>")
		def template2 = new Template(name: "Standard", body:"<html><head></head><body><system-region name=\"DEFAULT\"/></body></html>")
		siteInstance.addToTemplates(template1)
		siteInstance.addToTemplates(template2)
		
		//issue the web services calls
		//TODO Add try...catch block to handle timeouts and connection exceptions		
		com.hannonhill.www.ws.ns.AssetOperationService.Site site = siteInstance.createRemoteSite()
		def template1Id = template1.createRemoteTemplate(site)
		def template2Id = template2.createRemoteTemplate(site)
		
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
