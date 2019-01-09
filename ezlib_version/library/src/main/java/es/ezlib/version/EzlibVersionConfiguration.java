package es.ezlib.version;

import android.content.Context;
import android.graphics.Typeface;

public class EzlibVersionConfiguration {

    private String preferencesName;
    private Context context;
    private int attempToAskForUpdate;
    private String titleUpdate;
    private String textUpdate;
    private int iconSmallUpdate;
    private int iconLargeUpdate;
    private int parentHeight;
    private int parentWidth;
    private boolean hasTypeface;
    private Typeface typeface;
    private int dialogMainColor;
    private int dialogSecondColor;
    private int dialogTextColor;

    public String getPreferencesName() {
        return preferencesName;
    }

    public void setPreferencesName(String preferencesName) {
        this.preferencesName = preferencesName;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getAttempToAskForUpdate() {
        return attempToAskForUpdate;
    }

    public void setAttempToAskForUpdate(int attempToAskForUpdate) {
        this.attempToAskForUpdate = attempToAskForUpdate;
    }

    public String getTextUpdate() {
        return textUpdate;
    }

    public void setTextUpdate(String textUpdate) {
        this.textUpdate = textUpdate;
    }

    public int getIconSmallUpdate() {
        return iconSmallUpdate;
    }

    public void setIconSmallUpdate(int iconSmallUpdate) {
        this.iconSmallUpdate = iconSmallUpdate;
    }

    public int getIconLargeUpdate() {
        return iconLargeUpdate;
    }

    public void setIconLargeUpdate(int iconLargeUpdate) {
        this.iconLargeUpdate = iconLargeUpdate;
    }

    public String getTitleUpdate() {
        return titleUpdate;
    }

    public void setTitleUpdate(String titleUpdate) {
        this.titleUpdate = titleUpdate;
    }

    public int getParentHeight() {
        return parentHeight;
    }

    public void setParentHeight(int parentHeight) {
        this.parentHeight = parentHeight;
    }

    public int getParentWidth() {
        return parentWidth;
    }

    public void setParentWidth(int parentWidth) {
        this.parentWidth = parentWidth;
    }

    public boolean isHasTypeface() {
        return hasTypeface;
    }

    public void setHasTypeface(boolean hasTypeface) {
        this.hasTypeface = hasTypeface;
    }

    public Typeface getTypeface() {
        return typeface;
    }

    public void setTypeface(Typeface typeface) {
        this.typeface = typeface;
    }

    public int getDialogMainColor() {
        return dialogMainColor;
    }

    public void setDialogMainColor(int dialogMainColor) {
        this.dialogMainColor = dialogMainColor;
    }

    public int getDialogSecondColor() {
        return dialogSecondColor;
    }

    public void setDialogSecondColor(int dialogSecondColor) {
        this.dialogSecondColor = dialogSecondColor;
    }

    public int getDialogTextColor() {
        return dialogTextColor;
    }

    public void setDialogTextColor(int dialogTextColor) {
        this.dialogTextColor = dialogTextColor;
    }
}
