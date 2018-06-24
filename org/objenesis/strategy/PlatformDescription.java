package org.objenesis.strategy;

import org.objenesis.ObjenesisException;

public final class PlatformDescription {
    public static final int ANDROID_VERSION = getAndroidVersion();
    public static final String DALVIK = "Dalvik";
    public static final String GAE_VERSION = getGaeRuntimeVersion();
    public static final String GNU = "GNU libgcj";
    public static final String HOTSPOT = "Java HotSpot";
    public static final String JROCKIT = "BEA";
    public static final String JVM_NAME = System.getProperty("java.vm.name");
    public static final String OPENJDK = "OpenJDK";
    public static final String PERC = "PERC";
    public static final String SPECIFICATION_VERSION = System.getProperty("java.specification.version");
    @Deprecated
    public static final String SUN = "Java HotSpot";
    public static final String VENDOR = System.getProperty("java.vm.vendor");
    public static final String VENDOR_VERSION = System.getProperty("java.vm.version");
    public static final String VM_INFO = System.getProperty("java.vm.info");
    public static final String VM_VERSION = System.getProperty("java.runtime.version");

    public static String describePlatform() {
        String desc = "Java " + SPECIFICATION_VERSION + " (" + "VM vendor name=\"" + VENDOR + "\", " + "VM vendor version=" + VENDOR_VERSION + ", " + "JVM name=\"" + JVM_NAME + "\", " + "JVM version=" + VM_VERSION + ", " + "JVM info=" + VM_INFO;
        if (ANDROID_VERSION != 0) {
            desc = desc + ", API level=" + ANDROID_VERSION;
        }
        return desc + ")";
    }

    public static boolean isThisJVM(String name) {
        return JVM_NAME.startsWith(name);
    }

    public static boolean isGoogleAppEngine() {
        return getGaeRuntimeVersion() != null;
    }

    private static String getGaeRuntimeVersion() {
        return System.getProperty("com.google.appengine.runtime.version");
    }

    private static int getAndroidVersion() {
        if (isThisJVM(DALVIK)) {
            return getAndroidVersion0();
        }
        return 0;
    }

    private static int getAndroidVersion0() {
        try {
            Class<?> clazz = Class.forName("android.os.Build$VERSION");
            try {
                try {
                    return ((Integer) clazz.getField("SDK_INT").get(null)).intValue();
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            } catch (NoSuchFieldException e2) {
                return getOldAndroidVersion(clazz);
            }
        } catch (Throwable e3) {
            throw new ObjenesisException(e3);
        }
    }

    private static int getOldAndroidVersion(Class<?> versionClass) {
        try {
            try {
                return Integer.parseInt((String) versionClass.getField("SDK").get(null));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        } catch (Throwable e2) {
            throw new ObjenesisException(e2);
        }
    }

    private PlatformDescription() {
    }
}
