package es.ezlib.version_app;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import es.ezlib.version.EzlibVersionConfiguration;
import es.ezlib.version.EzlibVersionItem;
import es.ezlib.version.EzlibVersionManager;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        EzlibVersionConfiguration configuration = new EzlibVersionConfiguration();
        configuration.setAttempToAskForUpdate(1);
        configuration.setDialogMainColor(Color.BLUE);
        configuration.setDialogSecondColor(Color.BLUE);
        configuration.setDialogTextColor(Color.WHITE);
        configuration.setHasTypeface(true);
        configuration.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Regular.ttf"));
        configuration.setParentWidth(size.x);
        configuration.setParentHeight(size.y);
        configuration.setTextUpdate("Existe una nueva actualización disponible");
        configuration.setTitleUpdate("Nueva actualización");
        configuration.setContext(this);
        configuration.setPreferencesName("preferences_name");
        configuration.setIconLargeUpdate(R.drawable.ezlib);
        configuration.setIconSmallUpdate(R.drawable.ezlib);

        EzlibVersionManager ezlibVersionManager = new EzlibVersionManager(configuration);

        //ezlibVersionManager.checkVersion(3, EzlibVersionManager.TYPE_TOAST);

        ezlibVersionManager.checkVersion(3, EzlibVersionManager.TYPE_NOTIFICATION);

        //ezlibVersionManager.checkVersion(3, EzlibVersionManager.TYPE_DIALOG);

        /*List<EzlibVersionItem> items = new ArrayList<>();
        items.add(new EzlibVersionItem(1, EzlibVersionManager.TYPE_TOAST));
        items.add(new EzlibVersionItem(2, EzlibVersionManager.TYPE_FORCEDIALOG));
        ezlibVersionManager.checkWithVersionList(items);*/
    }
}
