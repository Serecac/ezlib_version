package es.ezlib.version;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EzlibForceUpdateDialog extends Dialog {

    private final double PERCENTAGE_WIDH = 0.95;
    private final double PERCENTAGE_HEIGHT = 0.35;
    private final float PERCENTAGE_BUTTONTEXSIZE = 0.022f;
    private final float PERCENTAGE_TITLETEXSIZE = 0.026f;

    private EzlibVersionConfiguration configuration;
    private Listener listener;

    private TextView updateText;
    private TextView descriptionText;
    private TextView titleText;

    private LinearLayout updateButton;

    public EzlibForceUpdateDialog(EzlibVersionConfiguration configuration, Listener listener) {
        super(configuration.getContext());
        this.configuration = configuration;
        this.listener = listener;
        init();
        findViews();
        setDinamicSize();
        setColor();
        setTypeface();
        setText();
        prepareListeners();
    }

    private void init() {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.dialog_forceupdate);
        this.setCancelable(false);

        getWindow().setLayout((int) (configuration.getParentWidth() * PERCENTAGE_WIDH), (int) (configuration.getParentHeight() * PERCENTAGE_HEIGHT));
    }

    private void findViews() {
        updateText = findViewById(R.id.forceupdate_update_text);
        titleText = findViewById(R.id.forceupdate_title_text);
        descriptionText = findViewById(R.id.forceupdate_description_text);
        updateButton = findViewById(R.id.forceupdate_update_button);
    }

    private void setText(){
        if (descriptionText != null)
            descriptionText.setText(configuration.getTextUpdate());
        if (titleText != null)
            titleText.setText(configuration.getTitleUpdate());
    }

    public void setColor() {

        findViewById(R.id.forceupdate_mainLayout).setBackgroundColor(configuration.getDialogMainColor());
        findViewById(R.id.forceupdate_line_topseparator).setBackgroundColor(configuration.getDialogTextColor());

        this.titleText.setTextColor(configuration.getDialogTextColor());
        this.updateText.setTextColor(configuration.getDialogTextColor());
        this.descriptionText.setTextColor(configuration.getDialogTextColor());
    }

    private void setTypeface(){

        if (configuration.isHasTypeface() && configuration.getTypeface() != null){
            this.titleText.setTypeface(configuration.getTypeface());
            this.updateText.setTypeface(configuration.getTypeface());
            this.descriptionText.setTypeface(configuration.getTypeface());
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void prepareListeners() {

        this.updateButton.setOnTouchListener((view, event) -> {

            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                updateButton.setBackgroundColor(configuration.getDialogSecondColor());
                updateText.setTextColor(configuration.getDialogMainColor());
            }

            if (event.getAction() == MotionEvent.ACTION_UP) {
                updateButton.setBackgroundColor(configuration.getDialogMainColor());
                updateText.setTextColor(configuration.getDialogTextColor());
            }

            return false;
        });

        this.updateButton.setOnClickListener(v -> {
            dismiss();
            listener.onUpdate();
        });
    }

    private void setDinamicSize() {
        updateText.setTextSize(pixelsToSp(configuration.getParentHeight() * PERCENTAGE_BUTTONTEXSIZE));
        titleText.setTextSize(pixelsToSp(configuration.getParentHeight() * PERCENTAGE_TITLETEXSIZE));
        descriptionText.setTextSize(pixelsToSp(configuration.getParentHeight() * PERCENTAGE_TITLETEXSIZE));
    }

    private float pixelsToSp(float px) {
        float scaledDensity = configuration.getContext().getResources().getDisplayMetrics().scaledDensity;
        return px/scaledDensity;
    }

    public interface Listener {
        void onUpdate();
    }
}
