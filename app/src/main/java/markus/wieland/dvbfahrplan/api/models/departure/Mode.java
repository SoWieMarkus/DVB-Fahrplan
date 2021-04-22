package markus.wieland.dvbfahrplan.api.models.departure;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;

import com.google.gson.annotations.SerializedName;

import markus.wieland.dvbfahrplan.R;

public enum Mode {

    @SerializedName("Tram") TRAM(R.drawable.ic_transport_tram, Color.parseColor("#dd0b30")),
    @SerializedName("Bus") BUS(R.drawable.ic_transport_bus, Color.parseColor("#005d79")),
    @SerializedName("CityBus") CITY_BUS(R.drawable.ic_transport_bus, Color.parseColor("#005d79")),
    @SerializedName("IntercityBus") INTER_CITY_BUS(R.drawable.ic_transport_bus, Color.parseColor("#005d79")),
    @SerializedName("Train") TRAIN(R.drawable.ic_transport_train, Color.parseColor("#555555")),
    @SerializedName("Lift") LIFT(R.drawable.ic_transport_lift, Color.parseColor("#009551")),
    @SerializedName("Ferry") FERRY(R.drawable.ic_transport_ferry, Color.parseColor("#00a5dd")),
    @SerializedName("Alita") ALITA_TAXI(R.drawable.ic_transport_alita, Color.parseColor("#FFD700")),
    @SerializedName("Footpath") WALKING(R.drawable.ic_transport_footpath, Color.parseColor("#FFFFFF")),
    @SerializedName("RapidTransit") RAPID_TRANSIT(R.drawable.ic_transport_metropolitan, Color.parseColor("#009551")),
    UNKNOWN(R.drawable.ic_transport_footpath, Color.parseColor("#FFF000"));

    @DrawableRes
    private final int value;

    @ColorInt
    private final int color;

    Mode(int value, int color) {
        this.value = value;
        this.color = color;
    }

    @ColorInt
    public int getColor() {
        return color;
    }

    public int getValue() {
        return value;
    }

    public Drawable getIcon(Context context) {
        return AppCompatResources.getDrawable(context, value);
    }

    public Drawable getBackground(Context context) {
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(context, R.drawable.item_background);
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, getColor());
        return wrappedDrawable;
    }
}
