package es.ezlib.version;

import android.os.AsyncTask;

import org.jsoup.Jsoup;

class EzlibGetOnlineVersionAsyncTask extends AsyncTask<String, String, String> {

    private EzlibGetOnlineVersionListener listener;

    public EzlibGetOnlineVersionAsyncTask(EzlibGetOnlineVersionListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {

        String newVersion = null;
        try {
            newVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=" + params[0] + "&hl=it")
                    .timeout(30000)
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .referrer("http://www.google.com")
                    .get()
                    .select("div[itemprop=softwareVersion]")
                    .first()
                    .ownText();
            return newVersion;
        } catch (Exception e) {
            return newVersion;
        }
    }

    @Override
    protected void onPostExecute(String onlineVersionName) {
        super.onPostExecute(onlineVersionName);
        if (this.listener != null && onlineVersionName != null)
            listener.onOnlineVersionReady(onlineVersionName, versionCodeFromVersionName(onlineVersionName));
    }

    private int versionCodeFromVersionName(String versionString){
        return Integer.parseInt(versionString.replace(".","00"));
    }
}
