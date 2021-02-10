package org.alfresco.repo.security.authentication.identityservice;

import org.alfresco.repo.management.subsystems.ActivateableBean;
import org.alfresco.repo.security.authentication.external.DefaultRemoteUserMapper;
import org.alfresco.repo.security.authentication.external.RemoteUserMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Chain default remote user implementation with identity service user implementation
 *
 * @author Marcus Svartmark - Redpill Linpro AB
 */
public class CustomIdentityServiceRemoteUserMapper implements RemoteUserMapper, ActivateableBean {
  private static Log LOG = LogFactory.getLog(CustomIdentityServiceRemoteUserMapper.class);
  private IdentityServiceRemoteUserMapper remoteUserMapperIds;
  private DefaultRemoteUserMapper remoteUserMapperRu;

  private boolean active;


  public void setRemoteUserMapperIds(IdentityServiceRemoteUserMapper remoteUserMapperIds) {
    this.remoteUserMapperIds = remoteUserMapperIds;
  }

  public void setRemoteUserMapperRu(DefaultRemoteUserMapper remoteUserMapperRu) {
    this.remoteUserMapperRu = remoteUserMapperRu;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  @Override
  public boolean isActive() {
    return active;
  }

  @Override
  public String getRemoteUser(javax.servlet.http.HttpServletRequest request) {
    String remoteUser = null;
    if (remoteUserMapperRu.isActive()) {
      remoteUser = remoteUserMapperRu.getRemoteUser(request);
      LOG.trace("Got " + remoteUser + " from DefaultRemoteUserMapper");
    }

    if (remoteUserMapperIds.isActive() && remoteUser == null) {
      remoteUser = remoteUserMapperIds.getRemoteUser(request);
      LOG.trace("Got " + remoteUser + " from IdentityServiceRemoteUserMapper");
    }

    return remoteUser;
  }
}
