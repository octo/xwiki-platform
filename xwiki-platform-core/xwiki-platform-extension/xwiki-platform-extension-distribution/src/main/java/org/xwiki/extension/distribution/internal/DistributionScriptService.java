/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.xwiki.extension.distribution.internal;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.xwiki.bridge.DocumentAccessBridge;
import org.xwiki.component.annotation.Component;
import org.xwiki.context.Execution;
import org.xwiki.extension.CoreExtension;
import org.xwiki.extension.ExtensionId;
import org.xwiki.extension.ExtensionManager;
import org.xwiki.extension.distribution.internal.DistributionManager.DistributionState;
import org.xwiki.extension.distribution.internal.job.DistributionJob;
import org.xwiki.extension.distribution.internal.job.DistributionJobStatus;
import org.xwiki.extension.internal.safe.ScriptSafeProvider;
import org.xwiki.extension.repository.CoreExtensionRepository;
import org.xwiki.extension.repository.ExtensionRepositoryManager;
import org.xwiki.extension.repository.InstalledExtensionRepository;
import org.xwiki.extension.repository.LocalExtensionRepository;
import org.xwiki.job.JobManager;
import org.xwiki.script.service.ScriptService;

/**
 * Provide helpers to manage running distribution.
 * <p>
 * Note: this script service is strictly internal and intended to be used only from templates for now.
 * 
 * @version $Id$
 */
@Component
@Named("distribution")
@Singleton
public class DistributionScriptService implements ScriptService
{
    /**
     * The key under which the last encountered error is stored in the current execution context.
     */
    public static final String EXTENSIONERROR_KEY = "scriptservice.distribution.error";

    /**
     * The real extension manager bridged by this script service.
     */
    @Inject
    private ExtensionManager extensionManager;

    /**
     * Needed for checking programming rights.
     */
    @Inject
    private DocumentAccessBridge documentAccessBridge;

    /**
     * The repository containing installed extensions.
     */
    @Inject
    private InstalledExtensionRepository installedExtensionRepository;

    /**
     * The repository containing local extensions.
     */
    @Inject
    private LocalExtensionRepository localExtensionRepository;

    /**
     * The repository with core modules provided by the platform.
     */
    @Inject
    private CoreExtensionRepository coreExtensionRepository;

    /**
     * Repository manager, needed for cross-repository operations.
     */
    @Inject
    private ExtensionRepositoryManager repositoryManager;

    /**
     * Handles and provides status feedback on extension operations (installation, upgrade, removal).
     */
    @Inject
    private JobManager jobManager;

    /**
     * Provides access to the current context.
     */
    @Inject
    private Execution execution;

    /**
     * 
     */
    @Inject
    @SuppressWarnings("rawtypes")
    private ScriptSafeProvider scriptProvider;

    private DistributionManager distributionManager;

    /**
     * @param <T> the type of the object
     * @param unsafe the unsafe object
     * @return the safe version of the passed object
     */
    @SuppressWarnings("unchecked")
    private <T> T safe(T unsafe)
    {
        return (T) this.scriptProvider.get(unsafe);
    }

    // Distribution

    public DistributionState getState()
    {
        return this.distributionManager.getDistributionState();
    }

    public CoreExtension getDistributionExtension()
    {
        return this.distributionManager.getDistributionExtension();
    }

    public ExtensionId getUIExtensionId()
    {
        return this.distributionManager.getUIExtensionId();
    }

    public DistributionJobStatus getPreviousJobStatus()
    {
        return this.distributionManager.getPreviousJobStatus();
    }

    public DistributionJobStatus getJobStatus()
    {
        DistributionJob job = this.distributionManager.getJob();

        return job != null ? (DistributionJobStatus) job.getStatus() : null;
    }
}