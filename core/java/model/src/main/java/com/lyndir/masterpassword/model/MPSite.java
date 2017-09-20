//==============================================================================
// This file is part of Master Password.
// Copyright (c) 2011-2017, Maarten Billemont.
//
// Master Password is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// Master Password is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You can find a copy of the GNU General Public License in the
// LICENSE file.  Alternatively, see <http://www.gnu.org/licenses/>.
//==============================================================================

package com.lyndir.masterpassword.model;

import static com.lyndir.lhunath.opal.system.util.StringUtils.strf;

import com.google.common.primitives.UnsignedInteger;
import com.lyndir.masterpassword.*;
import java.util.Objects;
import javax.annotation.Nullable;
import org.joda.time.Instant;


/**
 * @author lhunath, 14-12-05
 */
public class MPSite {

    public static final UnsignedInteger DEFAULT_COUNTER = UnsignedInteger.valueOf( 1 );

    private final MPUser            user;
    private       MasterKey.Version algorithmVersion;
    private       Instant           lastUsed;
    private       String            siteName;
    private       MPResultType      resultType;
    private       UnsignedInteger   siteCounter;
    private       int               uses;
    private       String            loginName;

    public MPSite(final MPUser user, final String siteName) {
        this( user, siteName, DEFAULT_COUNTER, MPResultType.DEFAULT );
    }

    public MPSite(final MPUser user, final String siteName, final UnsignedInteger siteCounter, final MPResultType resultType) {
        this.user = user;
        this.algorithmVersion = MasterKey.Version.CURRENT;
        this.lastUsed = new Instant();
        this.siteName = siteName;
        this.resultType = resultType;
        this.siteCounter = siteCounter;
    }

    protected MPSite(final MPUser user, final MasterKey.Version algorithmVersion, final Instant lastUsed, final String siteName,
                     final MPResultType resultType, final UnsignedInteger siteCounter, final int uses, @Nullable final String loginName,
                     @Nullable final String importContent) {
        this.user = user;
        this.algorithmVersion = algorithmVersion;
        this.lastUsed = lastUsed;
        this.siteName = siteName;
        this.resultType = resultType;
        this.siteCounter = siteCounter;
        this.uses = uses;
        this.loginName = loginName;
    }

    public String resultFor(final MasterKey masterKey) {
        return resultFor( masterKey, MPKeyPurpose.Authentication, null );
    }

    public String resultFor(final MasterKey masterKey, final MPKeyPurpose purpose, @Nullable final String context) {
        return masterKey.siteResult( siteName, siteCounter, purpose, context, resultType, null );
    }

    public MPUser getUser() {
        return user;
    }

    @Nullable
    protected String exportContent() {
        return null;
    }

    public MasterKey.Version getAlgorithmVersion() {
        return algorithmVersion;
    }

    public void setAlgorithmVersion(final MasterKey.Version mpVersion) {
        this.algorithmVersion = mpVersion;
    }

    public Instant getLastUsed() {
        return lastUsed;
    }

    public void updateLastUsed() {
        lastUsed = new Instant();
        user.updateLastUsed();
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(final String siteName) {
        this.siteName = siteName;
    }

    public MPResultType getResultType() {
        return resultType;
    }

    public void setResultType(final MPResultType resultType) {
        this.resultType = resultType;
    }

    public UnsignedInteger getSiteCounter() {
        return siteCounter;
    }

    public void setSiteCounter(final UnsignedInteger siteCounter) {
        this.siteCounter = siteCounter;
    }

    public int getUses() {
        return uses;
    }

    public void setUses(final int uses) {
        this.uses = uses;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(final String loginName) {
        this.loginName = loginName;
    }

    @Override
    public boolean equals(final Object obj) {
        return (this == obj) || ((obj instanceof MPSite) && Objects.equals( siteName, ((MPSite) obj).siteName ));
    }

    @Override
    public int hashCode() {
        return Objects.hashCode( siteName );
    }

    @Override
    public String toString() {
        return strf( "{MPSite: %s}", siteName );
    }
}
