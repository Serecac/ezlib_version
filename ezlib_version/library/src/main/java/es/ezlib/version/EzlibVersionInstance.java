package es.ezlib.version;

import java.util.List;

public class EzlibVersionInstance {

    private static EzlibVersionManager ezlibVersionManager = null;

    public static void init(EzlibVersionConfiguration configuration){
        ezlibVersionManager = new EzlibVersionManager(configuration);
    }

    public static void checkVersion(int versionCode, int typeUpdate){
        ezlibVersionManager.checkVersion(versionCode, typeUpdate);
    }

    public static void checkWithOnlineVersion(int typeUpdate){
        ezlibVersionManager.checkWithOnlineVersion(typeUpdate);
    }

    public void checkWithVersionList(List<EzlibVersionItem> versionItems){
        ezlibVersionManager.checkWithVersionList(versionItems);
    }
}
