/*
 * Copyright (c) 2016.
 */

package org.llaith.onyx.formkit.dto;

import org.junit.Before;
import org.junit.Test;
import org.llaith.onyx.formkit.dto.session.DtoSession;
import org.llaith.onyx.formkit.dto.session.impl.DtoBusImpl;

/**
 *
 */
public class DtoSessionTest extends AbstractDtoSessionTest {

    @Before
    public void setUpSession() throws Exception {
        this.sessions = new DtoSession<>(DtoBusImpl::new, "TEST");
    }

    @Override
    @Test
    public void simplePropertyShouldSyncThroughSession() throws Exception {
        super.simplePropertyShouldSyncThroughSession();
    }

    @Override
    @Test
    public void nestedPropertyShouldSyncThroughSession() throws Exception {
        super.nestedPropertyShouldSyncThroughSession();
    }

    @Override
    @Test
    public void nestedSetsShouldSyncThroughSession() throws Exception {
        super.nestedSetsShouldSyncThroughSession();
    }

    @Override
    @Test
    public void nestedSetsShouldSyncThroughSessionWithProperties() throws Exception {
        super.nestedSetsShouldSyncThroughSessionWithProperties();
    }

    @Override
    @Test
    public void builderInstancesAreAutoAcceptedWhenAddedToSessions() throws Exception {
        super.builderInstancesAreAutoAcceptedWhenAddedToSessions();
    }

}
