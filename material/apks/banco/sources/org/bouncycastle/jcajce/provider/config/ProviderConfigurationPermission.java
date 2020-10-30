package org.bouncycastle.jcajce.provider.config;

import java.security.BasicPermission;
import java.security.Permission;
import java.util.StringTokenizer;
import org.bouncycastle.util.Strings;

public class ProviderConfigurationPermission extends BasicPermission {
    private final String a;
    private final int b;

    public ProviderConfigurationPermission(String str) {
        super(str);
        this.a = "all";
        this.b = 15;
    }

    public ProviderConfigurationPermission(String str, String str2) {
        super(str, str2);
        this.a = str2;
        this.b = a(str2);
    }

    private int a(String str) {
        StringTokenizer stringTokenizer = new StringTokenizer(Strings.toLowerCase(str), " ,");
        int i = 0;
        while (stringTokenizer.hasMoreTokens()) {
            String nextToken = stringTokenizer.nextToken();
            if (nextToken.equals("threadlocalecimplicitlyca")) {
                i |= 1;
            } else if (nextToken.equals("ecimplicitlyca")) {
                i |= 2;
            } else if (nextToken.equals("threadlocaldhdefaultparams")) {
                i |= 4;
            } else if (nextToken.equals("dhdefaultparams")) {
                i |= 8;
            } else if (nextToken.equals("all")) {
                i |= 15;
            }
        }
        if (i != 0) {
            return i;
        }
        throw new IllegalArgumentException("unknown permissions passed to mask");
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ProviderConfigurationPermission)) {
            return false;
        }
        ProviderConfigurationPermission providerConfigurationPermission = (ProviderConfigurationPermission) obj;
        return this.b == providerConfigurationPermission.b && getName().equals(providerConfigurationPermission.getName());
    }

    public String getActions() {
        return this.a;
    }

    public int hashCode() {
        return getName().hashCode() + this.b;
    }

    public boolean implies(Permission permission) {
        boolean z = false;
        if (!(permission instanceof ProviderConfigurationPermission) || !getName().equals(permission.getName())) {
            return false;
        }
        ProviderConfigurationPermission providerConfigurationPermission = (ProviderConfigurationPermission) permission;
        if ((this.b & providerConfigurationPermission.b) == providerConfigurationPermission.b) {
            z = true;
        }
        return z;
    }
}
