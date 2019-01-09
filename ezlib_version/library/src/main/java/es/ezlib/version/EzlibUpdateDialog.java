package es.ezlib.version;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EzlibUpdateDialog extends Dialog {

    private final double PERCENTAGE_WIDH = 0.95;
    private final double PERCENTAGE_HEIGHT = 0.35;
    private final float PERCENTAGE_BUTTONTEXSIZE = 0.022f;
    private final float PERCENTAGE_TITLETEXSIZE = 0.026f;

    private EzlibVersionConfiguration configuration;
    private Listener listener;

    private TextView noUpdateText;
    private TextView updateText;
    private TextView descriptionText;
    private TextView titleText;

    private LinearLayout noUpdateButton;
    private LinearLayout updateButton;

    public EzlibUpdateDialog(EzlibVersionConfiguration configuration, Listener listener) {
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
        this.setContentView(R.layout.dialog_update);
        this.setCancelable(true);

        getWindow().setLayout((int) (configuration.getParentWidth() * PERCENTAGE_WIDH), (int) (configuration.getParentHeight() * PERCENTAGE_HEIGHT));
    }

    private void findViews() {
        noUpdateText = findViewById(R.id.update_noUpdate_text);
        updateText = findViewById(R.id.update_update_text);
        titleText = findViewById(R.id.update_title_text);
        descriptionText = findViewById(R.id.update_description_text);

        noUpdateButton = findViewById(R.id.update_noUpdate_button);
        updateButton = findViewById(R.id.update_update_button);
    }

    private void setDinamicSize() {

        noUpdateText.setTextSize(pixelsToSp(configuration.getParentHeight() * PERCENTAGE_BUTTONTEXSIZE));
        updateText.setTextSize(pixelsToSp(configuration.getParentHeight() * PERCENTAGE_BUTTONTEXSIZE));
        titleText.setTextSize(pixelsToSp(configuration.getParentHeight() * PERCENTAGE_TITLETEXSIZE));
        descriptionText.setTextSize(pixelsToSp(configuration.getParentHeight() * PERCENTAGE_TITLETEXSIZE));
    }

    private void setText(){
        if (descriptionText != null)
            descriptionText.setText(configuration.getTextUpdate());
        if (titleText != null)
            titleText.setText(configuration.getTitleUpdate());
    }

    private void setColor() {

        findViewById(R.id.update_mainLayout).setBackgroundColor(configuration.getDialogMainColor());
        findViewById(R.id.update_line_topseparator).setBackgroundColor(configuration.getDialogTextColor());
        findViewById(R.id.update_line_bottomSeparator).setBackgroundColor(configuration.getDialogTextColor());

        this.titleText.setTextColor(configuration.getDialogTextColor());
        this.noUpdateText.setTextColor(configuration.getDialogTextColor());
        this.updateText.setTextColor(configuration.getDialogTextColor());
        this.descriptionText.setTextColor(configuration.getDialogTextColor());
    }

    private void setTypeface(){

        if (configuration.isHasTypeface() && configuration.getTypeface() != null){
            this.titleText.setTypeface(configuration.getTypeface());
            this.noUpdateText.setTypeface(configuration.getTypeface());
            this.updateText.setTypeface(configuration.getTypeface());
            this.descriptionText.setTypeface(configuration.getTypeface());
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void prepareListeners() {

        this.noUpdateButton.setOnTouchListener((view, event) -> {

            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                noUpdateButton.setBackgroundColor(configuration.getDialogSecondColor());
                noUpdateText.setTextColor(configuration.getDialogMainColor());
            }

            if (event.getAction() == MotionEvent.ACTION_UP) {
                noUpdateButton.setBackgroundColor(configuration.getDialogMainColor());
                noUpdateText.setTextColor(configuration.getDialogTextColor());
            }

            return false;
        });

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

        this.noUpdateButton.setOnClickListener(v -> dismiss());
        this.updateButton.setOnClickListener(v -> {
            dismiss();
            listener.onUpdate();
        });
    }

    private float pixelsToSp(float px) {
        float scaledDensity = configuration.getContext().getResources().getDisplayMetrics().scaledDensity;
        return px/scaledDensity;
    }

    public interface Listener {
       void onUpdate();
    }
}
