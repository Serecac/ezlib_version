package es.ezlib.version;

public class EzlibVersionItem {

    private int versionCode;
    private int typeUpdate;

    public EzlibVersionItem(int versionCode, int typeUpdate) {
        this.versionCode = versionCode;
        this.typeUpdate = typeUpdate;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public int getTypeUpdate() {
        return typeUpdate;
    }

    public void setTypeUpdate(int typeUpdate) {
        this.typeUpdate = typeUpdate;
    }
}
