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

    @SerializedName("Tram") TRAM(Vehicle.TRAM),
    @SerializedName("Bus") BUS(Vehicle.BUS),
    @SerializedName("CityBus") CITY_BUS(Vehicle.BUS),
    @SerializedName(value = "IntercityBus", alternate = {"PlusBus"}) INTER_CITY_BUS(Vehicle.BUS),
    @SerializedName("Train") TRAIN(Vehicle.TRAIN),
    @SerializedName(value = "Lift", alternate = {"OverheadRailway"}) LIFT(Vehicle.LIFT),
    @SerializedName("Ferry") FERRY(Vehicle.FERRY),
    @SerializedName(value = "Alita", alternate = {"Taxi", "HailedSharedTaxi"}) ALITA_TAXI(Vehicle.TAXI),
    @SerializedName("Footpath") WALKING(Vehicle.WALKING),
    @SerializedName(value = "RapidTransit", alternate = {"SuburbanRailway"}) RAPID_TRANSIT(Vehicle.METROPOLITAN),
    @SerializedName("StayForConnection") STAY_FOR_CONNECTION(R.drawable.ic_transport_wait, Vehicle.WALKING.getColor(), R.drawable.item_marker_footpath, Vehicle.WAITING),
    CHANGE_PLATFORM(R.drawable.ic_transport_change, Vehicle.WALKING.getColor(), R.drawable.item_marker_footpath, "footpath"),
    WAITING(R.drawable.ic_transport_wait, Vehicle.WALKING.getColor(), R.drawable.item_marker_footpath, Vehicle.WAITING),
    ONLY_ONE_PART(R.drawable.ic_transport_footpath, Vehicle.WALKING.getColor(), R.drawable.item_marker_footpath, Vehicle.WAITING),
    UNKNOWN(Vehicle.UNKNOWN);

    @DrawableRes
    private final int value;

    @DrawableRes
    private final int marker;

    @ColorInt
    private final int color;

    private final String mapId;

    Mode(Vehicle vehicle) {
        this.value = vehicle.getValue();
        this.color = vehicle.getColor();
        this.marker = vehicle.getMarker();
        this.mapId = vehicle.getMapId();
    }

    Mode(int value, int color, int marker, String mapId) {
        this.value = value;
        this.marker = marker;
        this.color = color;
        this.mapId = mapId;
    }

    public String getMapId() {
        return mapId;
    }

    @ColorInt
    public int getColor() {
        return color;
    }

    public Drawable getIcon(Context context) {
        return AppCompatResources.getDrawable(context, value);
    }

    public Drawable getBackground(Context context) {
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(context, R.drawable.item_background);
        assert unwrappedDrawable != null;
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, getColor());
        return wrappedDrawable;
    }

    public Drawable getMarker(Context context) {
        return AppCompatResources.getDrawable(context, marker);
    }

    private enum Vehicle {
        TRAM(R.drawable.ic_transport_tram, Color.parseColor("#dd0b30"), R.drawable.item_marker_tram, "tram"),
        TAXI(R.drawable.ic_transport_alita, Color.parseColor("#FFD700"), R.drawable.item_marker_alita, "alita"),
        BUS(R.drawable.ic_transport_bus, Color.parseColor("#005d79"), R.drawable.item_marker_bus, "bus"),
        TRAIN(R.drawable.ic_transport_train, Color.parseColor("#555555"), R.drawable.item_marker_train, "train"),
        FERRY(R.drawable.ic_transport_ferry, Color.parseColor("#00a5dd"), R.drawable.item_marker_ferry, "ferry"),
        UNKNOWN(R.drawable.ic_transport_unkown, Color.parseColor("#555555"), R.drawable.item_marker_train, "unknown"),
        WALKING(R.drawable.ic_transport_footpath, Color.parseColor("#FFFFFF"), R.drawable.item_marker_footpath, "footpath"),
        METROPOLITAN(R.drawable.ic_transport_metropolitan, Color.parseColor("#009551"), R.drawable.item_marker_metropolitan, "metropolitan"),
        LIFT(R.drawable.ic_transport_lift, Color.parseColor("#009551"), R.drawable.item_marker_lift, "lift");

        public static final String WAITING = "waiting";

        @DrawableRes
        private final int value;

        @DrawableRes
        private final int marker;

        @ColorInt
        private final int color;

        private final String mapId;

        Vehicle(int value, int color, int marker, String mapId) {
            this.color = color;
            this.value = value;
            this.marker = marker;
            this.mapId = mapId;
        }

        public int getValue() {
            return value;
        }

        public int getMarker() {
            return marker;
        }

        public int getColor() {
            return color;
        }

        public String getMapId() {
            return mapId;
        }
    }
}
