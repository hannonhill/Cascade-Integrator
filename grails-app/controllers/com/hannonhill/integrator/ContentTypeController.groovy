package com.hannonhill.integrator

class ContentTypeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [contentTypeInstanceList: ContentType.list(params), contentTypeInstanceTotal: ContentType.count()]
    }

    def create = {
        def contentTypeInstance = new ContentType()
        contentTypeInstance.properties = params
        return [contentTypeInstance: contentTypeInstance]
    }

    def save = {
        def contentTypeInstance = new ContentType(params)
        if (contentTypeInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'contentType.label', default: 'ContentType'), contentTypeInstance.id])}"
            redirect(action: "show", id: contentTypeInstance.id)
        }
        else {
            render(view: "create", model: [contentTypeInstance: contentTypeInstance])
        }
    }

    def show = {
        def contentTypeInstance = ContentType.get(params.id)
        if (!contentTypeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'contentType.label', default: 'ContentType'), params.id])}"
            redirect(action: "list")
        }
        else {
            [contentTypeInstance: contentTypeInstance]
        }
    }

    def edit = {
        def contentTypeInstance = ContentType.get(params.id)
        if (!contentTypeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'contentType.label', default: 'ContentType'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [contentTypeInstance: contentTypeInstance]
        }
    }

    def update = {
        def contentTypeInstance = ContentType.get(params.id)
        if (contentTypeInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (contentTypeInstance.version > version) {
                    
                    contentTypeInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'contentType.label', default: 'ContentType')] as Object[], "Another user has updated this ContentType while you were editing")
                    render(view: "edit", model: [contentTypeInstance: contentTypeInstance])
                    return
                }
            }
            contentTypeInstance.properties = params
            if (!contentTypeInstance.hasErrors() && contentTypeInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'contentType.label', default: 'ContentType'), contentTypeInstance.id])}"
                redirect(action: "show", id: contentTypeInstance.id)
            }
            else {
                render(view: "edit", model: [contentTypeInstance: contentTypeInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'contentType.label', default: 'ContentType'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def contentTypeInstance = ContentType.get(params.id)
        if (contentTypeInstance) {
            try {
                contentTypeInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'contentType.label', default: 'ContentType'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'contentType.label', default: 'ContentType'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'contentType.label', default: 'ContentType'), params.id])}"
            redirect(action: "list")
        }
    }
}
