package io.beesports.config;

public class ConfigConsts {

    public static final String LATEST_ACCEPT_VERSION = "v0.1.0";
    public static final String DEFAULT_ACCEPT_VERSION = "v0.1.0";

    public static Boolean isOfHigherAcceptVersionValue(String sentAcceptVersion) {
        sentAcceptVersion = sentAcceptVersion.replace("v", "").replace(".", "");
        String latestAcceptVersion = LATEST_ACCEPT_VERSION.replace("v", "").replace(".", "");
        return Integer.parseInt(sentAcceptVersion) > Integer.parseInt(latestAcceptVersion);
    }
}
