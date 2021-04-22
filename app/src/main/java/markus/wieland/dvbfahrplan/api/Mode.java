package markus.wieland.dvbfahrplan.api;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import markus.wieland.dvbfahrplan.R;

public enum Mode implements Serializable {

    @SerializedName("Tram") TRAM(R.drawable.ic_transport_tram, Color.parseColor("#dd0b30"), R.drawable.item_marker_tram),
    @SerializedName("Bus") BUS(R.drawable.ic_transport_bus, Color.parseColor("#005d79"), R.drawable.item_marker_bus),
    @SerializedName("CityBus") CITY_BUS(R.drawable.ic_transport_bus, Color.parseColor("#005d79"), R.drawable.item_marker_bus),
    @SerializedName(value = "IntercityBus", alternate = {"PlusBus"}) INTER_CITY_BUS(R.drawable.ic_transport_bus, Color.parseColor("#005d79"), R.drawable.item_marker_bus),
    @SerializedName("Train") TRAIN(R.drawable.ic_transport_train, Color.parseColor("#555555"), R.drawable.item_marker_train),
    @SerializedName("Lift") LIFT(R.drawable.ic_transport_lift, Color.parseColor("#009551"), R.drawable.item_marker_lift),
    @SerializedName("Ferry") FERRY(R.drawable.ic_transport_ferry, Color.parseColor("#00a5dd"), R.drawable.item_marker_ferry),
    @SerializedName("Alita") ALITA_TAXI(R.drawable.ic_transport_alita, Color.parseColor("#FFD700"), R.drawable.item_marker_alita),
    @SerializedName("Footpath") WALKING(R.drawable.ic_transport_footpath, Color.parseColor("#FFFFFF"), R.drawable.item_marker_footpath),
    @SerializedName(value = "RapidTransit", alternate = {"SuburbanRailway"}) RAPID_TRANSIT(R.drawable.ic_transport_metropolitan, Color.parseColor("#009551"), R.drawable.item_marker_metropolitan),
    WAITING(R.drawable.ic_transport_wait, Color.parseColor("#FFFFFF"), R.drawable.item_marker_footpath),
    CHANGE_PLATFORM(R.drawable.ic_transport_change, Color.parseColor("#FFFFFF"), R.drawable.item_marker_footpath),
    UNKNOWN(R.drawable.ic_transport_unkown, Color.parseColor("#555555"), R.drawable.item_marker_train);

    @DrawableRes
    private final int value;

    @DrawableRes
    private final int marker;

    @ColorInt
    private final int color;

    Mode(int value, int color, int marker) {
        this.value = value;
        this.color = color;
        this.marker = marker;
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

    public Drawable getMarker(Context context) {
        return AppCompatResources.getDrawable(context, marker);
    }
}
