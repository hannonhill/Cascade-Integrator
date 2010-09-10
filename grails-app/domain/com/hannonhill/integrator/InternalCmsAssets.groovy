package com.hannonhill.integrator

import com.hannonhill.www.ws.ns.AssetOperationService.Site as CascadeSite
import com.hannonhill.www.ws.ns.AssetOperationService.Folder as CascadeFolder

class InternalCmsAssets extends Folder {	
		
	CascadeFolder[] createRemoteFolder(CascadeSite site, String parentFolderId) {

		Folder templates = new Folder(name: "templates")
		Folder boilerplates = new Folder(name: "boilerplates")
		Folder blocks = new Folder(name: "blocks")
		Folder formats = new Folder(name: "formats")
		
		this.setUpWS()
		templates.setUpWS()
		boilerplates.setUpWS()
		blocks.setUpWS()
		formats.setUpWS()
		
		CascadeFolder internalFolder = folderService.createRemoteFolder(site, (Folder) this, parentFolderId)
		CascadeFolder templatesFolder = folderService.createRemoteFolder(site, templates, internalFolder.getId())
		CascadeFolder boilerplatesFolder = folderService.createRemoteFolder(site, boilerplates, internalFolder.getId())
		CascadeFolder blocksFolder = folderService.createRemoteFolder(site, blocks, internalFolder.getId())
		CascadeFolder formatsFolder = folderService.createRemoteFolder(site, formats, internalFolder.getId())
		
		def folders = [internalFolder, templatesFolder, boilerplatesFolder, blocksFolder, formatsFolder]
		
		return folders
	}
}
