package es.ezlib.version;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import es.ezlib.logs.EzlibLogBuilder;
import es.ezlib.logs.EzlibLogManager;
import es.ezlib.preferences.EzlibPreferencesManager;

public class EzlibVersionManager {

    private final String PREFERENCE_ATTEMPTS_ASKUPDATE = "attemps_askupdate";
    private final String CHANEL_ID = "version_chanel_id";
    private final String CHANEL_NAME = "version_chanel";

    private final int NOTIFICATION_ID = 123;

    public static final int TYPE_NOTIFICATION = 1;
    public static final int TYPE_TOAST = 2;
    public static final int TYPE_DIALOG = 3;
    public static final int TYPE_FORCEDIALOG = 4;

    private EzlibVersionConfiguration configuration;
    private EzlibLogManager logManager;
    private EzlibPreferencesManager preferencesManager;

    private EzlibUpdateDialog updateDialog;
    private EzlibForceUpdateDialog forceUpdateDialog;

    public EzlibVersionManager(EzlibVersionConfiguration configuration) {
        this.configuration = configuration;
        this.logManager = new EzlibLogManager("EzlibVersionManager", EzlibLogManager.LOG_DEBUG);
        this.preferencesManager = new EzlibPreferencesManager(configuration.getContext(), configuration.getPreferencesName());
    }

    public void checkVersion(int versionCode, int typeUpdate) {
        int currentVersion = getCurrentVersion();
        if (currentVersion != -1 && currentVersion < versionCode)
            checkAttemps(typeUpdate);
    }

    public void checkWithOnlineVersion(int typeUpdate) {
        EzlibGetOnlineVersionListener listener = (versionName, versionCode) -> checkVersion(versionCode, typeUpdate);
        new EzlibGetOnlineVersionAsyncTask(listener).execute(configuration.getContext().getPackageName());
    }

    public void checkWithVersionList(List<EzlibVersionItem> versionItems) {
        int currentVersion = getCurrentVersion();
        if (currentVersion != -1) {
            for (EzlibVersionItem versionItem : versionItems) {
                if (versionItem.getVersionCode() == currentVersion)
                    checkAttemps(versionItem.getTypeUpdate());
            }
        }
    }

    private int getCurrentVersion() {
        try {
            PackageInfo packageInfo = configuration.getContext().getPackageManager().getPackageInfo(configuration.getContext().getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException exception) {
            if (logManager != null)
                logManager.error(new EzlibLogBuilder().addMessage("EzlibVersionManager:getCurrentVersion").addThrowable(exception));
            else
                exception.printStackTrace();
        }
        return -1;
    }

    private void checkAttemps(int typeUpdate) {

        int attempts = preferencesManager.getInt(PREFERENCE_ATTEMPTS_ASKUPDATE);
        if (attempts == -1)
            attempts = configuration.getAttempToAskForUpdate();

        attempts--;
        if (attempts == 0 || typeUpdate == TYPE_FORCEDIALOG) {
            showNeedUpdate(typeUpdate);
            attempts = configuration.getAttempToAskForUpdate();
        }

        preferencesManager.setInt(PREFERENCE_ATTEMPTS_ASKUPDATE, attempts);
    }

    private void showNeedUpdate(int typeUpdate) {
        switch (typeUpdate) {
            case TYPE_NOTIFICATION:
                showNotification();
                break;
            case TYPE_TOAST:
                showCustomToastMessage();
                break;
            case TYPE_DIALOG:
                showUpdateDialog();
                break;
            case TYPE_FORCEDIALOG:
                showForceUpdateDialog();
                break;
        }
    }

    private void showCustomToastMessage() {
        Toast toast = Toast.makeText(configuration.getContext(), "  " + configuration.getTextUpdate().replace("\n", "  \n  ") + "  ", Toast.LENGTH_LONG);
        View view = toast.getView();
        view.setBackgroundColor(configuration.getDialogMainColor());
        TextView text = toast.getView().findViewById(android.R.id.message);
        if (text != null) {
            text.setGravity(Gravity.CENTER);
            text.setTextColor(configuration.getDialogTextColor());
            text.setBackgroundColor(configuration.getDialogMainColor());
        }
        toast.show();
    }

    private void showUpdateDialog() {

        if (updateDialog == null) {
            updateDialog = new EzlibUpdateDialog(configuration, () -> launchMarket());
            updateDialog.show();
        }
    }

    private void showForceUpdateDialog() {

        if (forceUpdateDialog == null) {
            forceUpdateDialog = new EzlibForceUpdateDialog(configuration, () -> launchMarket());
            forceUpdateDialog.show();
        }
    }

    private void showNotification() {

        createNotificationChannel();

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(configuration.getContext(), CHANEL_ID);
        mBuilder.setLargeIcon(BitmapFactory.decodeResource(configuration.getContext().getResources(), configuration.getIconLargeUpdate()));
        mBuilder.setSmallIcon(configuration.getIconSmallUpdate());
        mBuilder.setContentTitle(configuration.getTitleUpdate());
        mBuilder.setContentText(configuration.getTextUpdate());
        mBuilder.setContentIntent(PendingIntent.getActivity(configuration.getContext(), 0, intentMarket(), 0));
        mBuilder.setAutoCancel(true);

        NotificationManager mNotificationManager = (NotificationManager) configuration.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

    private Intent intentMarket() {

        String appPackageName = configuration.getContext().getPackageName();
        Intent marketIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName));
        marketIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET | Intent.FLAG_ACTIVITY_MULTIPLE_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        return marketIntent;
    }

    private void launchMarket() {
        try {
            configuration.getContext().startActivity(intentMarket());
        } catch (ActivityNotFoundException exception) {
            logManager.error(new EzlibLogBuilder()
                    .addMessage("App not found on market")
                    .addThrowable(exception));
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT < 26)
            return;
        NotificationManager notificationManager = (NotificationManager) configuration.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel(CHANEL_ID, CHANEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
        notificationManager.createNotificationChannel(channel);
    }
}
