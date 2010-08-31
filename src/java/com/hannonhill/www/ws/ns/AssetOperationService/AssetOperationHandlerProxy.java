package com.hannonhill.www.ws.ns.AssetOperationService;

public class AssetOperationHandlerProxy implements com.hannonhill.www.ws.ns.AssetOperationService.AssetOperationHandler {
  private String _endpoint = null;
  private com.hannonhill.www.ws.ns.AssetOperationService.AssetOperationHandler assetOperationHandler = null;
  
  public AssetOperationHandlerProxy() {
    _initAssetOperationHandlerProxy();
  }
  
  public AssetOperationHandlerProxy(String endpoint) {
    _endpoint = endpoint;
    _initAssetOperationHandlerProxy();
  }
  
  private void _initAssetOperationHandlerProxy() {
    try {
      assetOperationHandler = (new com.hannonhill.www.ws.ns.AssetOperationService.AssetOperationHandlerServiceLocator()).getAssetOperationService();
      if (assetOperationHandler != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)assetOperationHandler)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)assetOperationHandler)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (assetOperationHandler != null)
      ((javax.xml.rpc.Stub)assetOperationHandler)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.hannonhill.www.ws.ns.AssetOperationService.AssetOperationHandler getAssetOperationHandler() {
    if (assetOperationHandler == null)
      _initAssetOperationHandlerProxy();
    return assetOperationHandler;
  }
  
  public com.hannonhill.www.ws.ns.AssetOperationService.OperationResult delete(com.hannonhill.www.ws.ns.AssetOperationService.Authentication authentication, com.hannonhill.www.ws.ns.AssetOperationService.Identifier identifier) throws java.rmi.RemoteException{
    if (assetOperationHandler == null)
      _initAssetOperationHandlerProxy();
    return assetOperationHandler.delete(authentication, identifier);
  }
  
  public com.hannonhill.www.ws.ns.AssetOperationService.CreateResult create(com.hannonhill.www.ws.ns.AssetOperationService.Authentication authentication, com.hannonhill.www.ws.ns.AssetOperationService.Asset asset) throws java.rmi.RemoteException{
    if (assetOperationHandler == null)
      _initAssetOperationHandlerProxy();
    return assetOperationHandler.create(authentication, asset);
  }
  
  public com.hannonhill.www.ws.ns.AssetOperationService.OperationResult publish(com.hannonhill.www.ws.ns.AssetOperationService.Authentication authentication, com.hannonhill.www.ws.ns.AssetOperationService.Identifier identifier) throws java.rmi.RemoteException{
    if (assetOperationHandler == null)
      _initAssetOperationHandlerProxy();
    return assetOperationHandler.publish(authentication, identifier);
  }
  
  public com.hannonhill.www.ws.ns.AssetOperationService.OperationResult edit(com.hannonhill.www.ws.ns.AssetOperationService.Authentication authentication, com.hannonhill.www.ws.ns.AssetOperationService.Asset asset) throws java.rmi.RemoteException{
    if (assetOperationHandler == null)
      _initAssetOperationHandlerProxy();
    return assetOperationHandler.edit(authentication, asset);
  }
  
  public com.hannonhill.www.ws.ns.AssetOperationService.BatchResult[] batch(com.hannonhill.www.ws.ns.AssetOperationService.Authentication authentication, com.hannonhill.www.ws.ns.AssetOperationService.Operation[] operation) throws java.rmi.RemoteException{
    if (assetOperationHandler == null)
      _initAssetOperationHandlerProxy();
    return assetOperationHandler.batch(authentication, operation);
  }
  
  public com.hannonhill.www.ws.ns.AssetOperationService.ReadResult read(com.hannonhill.www.ws.ns.AssetOperationService.Authentication authentication, com.hannonhill.www.ws.ns.AssetOperationService.Identifier identifier) throws java.rmi.RemoteException{
    if (assetOperationHandler == null)
      _initAssetOperationHandlerProxy();
    return assetOperationHandler.read(authentication, identifier);
  }
  
  public com.hannonhill.www.ws.ns.AssetOperationService.SearchResult search(com.hannonhill.www.ws.ns.AssetOperationService.Authentication authentication, com.hannonhill.www.ws.ns.AssetOperationService.SearchInformation searchInformation) throws java.rmi.RemoteException{
    if (assetOperationHandler == null)
      _initAssetOperationHandlerProxy();
    return assetOperationHandler.search(authentication, searchInformation);
  }
  
  public com.hannonhill.www.ws.ns.AssetOperationService.ReadAccessRightsResult readAccessRights(com.hannonhill.www.ws.ns.AssetOperationService.Authentication authentication, com.hannonhill.www.ws.ns.AssetOperationService.Identifier identifier) throws java.rmi.RemoteException{
    if (assetOperationHandler == null)
      _initAssetOperationHandlerProxy();
    return assetOperationHandler.readAccessRights(authentication, identifier);
  }
  
  public com.hannonhill.www.ws.ns.AssetOperationService.OperationResult editAccessRights(com.hannonhill.www.ws.ns.AssetOperationService.Authentication authentication, com.hannonhill.www.ws.ns.AssetOperationService.AccessRightsInformation accessRightsInformation, java.lang.Boolean applyToChildren) throws java.rmi.RemoteException{
    if (assetOperationHandler == null)
      _initAssetOperationHandlerProxy();
    return assetOperationHandler.editAccessRights(authentication, accessRightsInformation, applyToChildren);
  }
  
  public com.hannonhill.www.ws.ns.AssetOperationService.ListMessagesResult listMessages(com.hannonhill.www.ws.ns.AssetOperationService.Authentication authentication) throws java.rmi.RemoteException{
    if (assetOperationHandler == null)
      _initAssetOperationHandlerProxy();
    return assetOperationHandler.listMessages(authentication);
  }
  
  public com.hannonhill.www.ws.ns.AssetOperationService.OperationResult markMessage(com.hannonhill.www.ws.ns.AssetOperationService.Authentication authentication, com.hannonhill.www.ws.ns.AssetOperationService.Identifier identifier, com.hannonhill.www.ws.ns.AssetOperationService.MessageMarkType markType) throws java.rmi.RemoteException{
    if (assetOperationHandler == null)
      _initAssetOperationHandlerProxy();
    return assetOperationHandler.markMessage(authentication, identifier, markType);
  }
  
  public com.hannonhill.www.ws.ns.AssetOperationService.OperationResult deleteMessage(com.hannonhill.www.ws.ns.AssetOperationService.Authentication authentication, com.hannonhill.www.ws.ns.AssetOperationService.Identifier identifier) throws java.rmi.RemoteException{
    if (assetOperationHandler == null)
      _initAssetOperationHandlerProxy();
    return assetOperationHandler.deleteMessage(authentication, identifier);
  }
  
  public com.hannonhill.www.ws.ns.AssetOperationService.OperationResult sendMessage(com.hannonhill.www.ws.ns.AssetOperationService.Authentication authentication, com.hannonhill.www.ws.ns.AssetOperationService.Message message) throws java.rmi.RemoteException{
    if (assetOperationHandler == null)
      _initAssetOperationHandlerProxy();
    return assetOperationHandler.sendMessage(authentication, message);
  }
  
  public com.hannonhill.www.ws.ns.AssetOperationService.CheckOutResult checkOut(com.hannonhill.www.ws.ns.AssetOperationService.Authentication authentication, com.hannonhill.www.ws.ns.AssetOperationService.Identifier identifier) throws java.rmi.RemoteException{
    if (assetOperationHandler == null)
      _initAssetOperationHandlerProxy();
    return assetOperationHandler.checkOut(authentication, identifier);
  }
  
  public com.hannonhill.www.ws.ns.AssetOperationService.OperationResult checkIn(com.hannonhill.www.ws.ns.AssetOperationService.Authentication authentication, com.hannonhill.www.ws.ns.AssetOperationService.Identifier identifier, java.lang.String comments) throws java.rmi.RemoteException{
    if (assetOperationHandler == null)
      _initAssetOperationHandlerProxy();
    return assetOperationHandler.checkIn(authentication, identifier, comments);
  }
  
  public com.hannonhill.www.ws.ns.AssetOperationService.OperationResult copy(com.hannonhill.www.ws.ns.AssetOperationService.Authentication authentication, com.hannonhill.www.ws.ns.AssetOperationService.Identifier identifier, com.hannonhill.www.ws.ns.AssetOperationService.CopyParameters copyParameters, com.hannonhill.www.ws.ns.AssetOperationService.WorkflowConfiguration workflowConfiguration) throws java.rmi.RemoteException{
    if (assetOperationHandler == null)
      _initAssetOperationHandlerProxy();
    return assetOperationHandler.copy(authentication, identifier, copyParameters, workflowConfiguration);
  }
  
  public com.hannonhill.www.ws.ns.AssetOperationService.OperationResult move(com.hannonhill.www.ws.ns.AssetOperationService.Authentication authentication, com.hannonhill.www.ws.ns.AssetOperationService.Identifier identifier, com.hannonhill.www.ws.ns.AssetOperationService.MoveParameters moveParameters, com.hannonhill.www.ws.ns.AssetOperationService.WorkflowConfiguration workflowConfiguration) throws java.rmi.RemoteException{
    if (assetOperationHandler == null)
      _initAssetOperationHandlerProxy();
    return assetOperationHandler.move(authentication, identifier, moveParameters, workflowConfiguration);
  }
  
  public com.hannonhill.www.ws.ns.AssetOperationService.ReadWorkflowInformationResult readWorkflowInformation(com.hannonhill.www.ws.ns.AssetOperationService.Authentication authentication, com.hannonhill.www.ws.ns.AssetOperationService.Identifier identifier) throws java.rmi.RemoteException{
    if (assetOperationHandler == null)
      _initAssetOperationHandlerProxy();
    return assetOperationHandler.readWorkflowInformation(authentication, identifier);
  }
  
  public com.hannonhill.www.ws.ns.AssetOperationService.ReadAuditsResult readAudits(com.hannonhill.www.ws.ns.AssetOperationService.Authentication authentication, com.hannonhill.www.ws.ns.AssetOperationService.AuditParameters auditParameters) throws java.rmi.RemoteException{
    if (assetOperationHandler == null)
      _initAssetOperationHandlerProxy();
    return assetOperationHandler.readAudits(authentication, auditParameters);
  }
  
  public com.hannonhill.www.ws.ns.AssetOperationService.OperationResult performWorkflowTransition(com.hannonhill.www.ws.ns.AssetOperationService.Authentication authentication, com.hannonhill.www.ws.ns.AssetOperationService.WorkflowTransitionInformation workflowTransitionInformation) throws java.rmi.RemoteException{
    if (assetOperationHandler == null)
      _initAssetOperationHandlerProxy();
    return assetOperationHandler.performWorkflowTransition(authentication, workflowTransitionInformation);
  }
  
  
}