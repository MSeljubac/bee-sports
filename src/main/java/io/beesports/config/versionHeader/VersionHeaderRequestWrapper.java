package io.beesports.config.versionHeader;

import io.beesports.config.ConfigConsts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

public class VersionHeaderRequestWrapper extends HttpServletRequestWrapper {
    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public VersionHeaderRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getHeader(String name) {
        if (name.equalsIgnoreCase("Accept-version") && super.getHeader(name) == null) {
            return "v0.1.0";
        } else if (name.equalsIgnoreCase("Accept-version") && ConfigConsts.isOfHigherAcceptVersionValue(super.getHeader(name))) {
            return ConfigConsts.LATEST_ACCEPT_VERSION;
        } else {
            return super.getHeader(name);
        }
    }
}
