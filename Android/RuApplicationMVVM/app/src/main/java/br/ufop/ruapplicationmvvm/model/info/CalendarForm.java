package br.ufop.ruapplicationmvvm.model.info;

import androidx.annotation.Nullable;

public class CalendarForm {
    @Nullable
    private String date;
    @Nullable
    private boolean status;
    @Nullable
    private boolean type;
    @Nullable
    private String reason;
    private boolean isValid;


    public CalendarForm(@Nullable String date, @Nullable String reason) {
        this.date = date;
        this.reason = reason;
    }

    public CalendarForm(boolean isValid) {
        this.date = null;
        this.reason = null;
        this.isValid = isValid;
    }

    @Nullable
    public String getDate() {
        return date;
    }

    public void setDate(@Nullable String date) {
        this.date = date;
    }

    @Nullable
    public String getReason() {
        return reason;
    }

    public void setReason(@Nullable String reason) {
        this.reason = reason;
    }

    public boolean isDataValid() {
        return isValid;
    }


}

