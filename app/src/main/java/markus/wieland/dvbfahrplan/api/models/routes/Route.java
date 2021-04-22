package markus.wieland.dvbfahrplan.api.models.routes;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;
import java.util.List;

import markus.wieland.defaultappelements.uielements.adapter.QueryableEntity;

public class Route implements QueryableEntity<Long> {

    @SerializedName("Duration")
    private int duration;

    @SerializedName("FareZoneDestination")
    private int destinationZone;

    @SerializedName("FareZoneOrigin")
    private int originZone;

    @SerializedName("Price")
    private String price;

    @SerializedName("Interchanges")
    private int interchanges;

    @SerializedName("PriceLevel")
    private int priceLevel;

    @SerializedName("MotChain")
    private List<Mot> motChain;

    @SerializedName("PartialRoutes")
    private List<PartialRoute> partialRoutes;

    @SerializedName("RouteId")
    private long routeId;

    public int getInterchanges() {
        return interchanges;
    }

    public void setInterchanges(int interchanges) {
        this.interchanges = interchanges;
    }

    public List<Mot> getMotChain() {
        return motChain;
    }

    public void setMotChain(List<Mot> motChain) {
        this.motChain = motChain;
    }

    public long getRouteId() {
        return routeId;
    }

    public void setRouteId(long routeId) {
        this.routeId = routeId;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDestinationZone() {
        return destinationZone;
    }

    public void setDestinationZone(int destinationZone) {
        this.destinationZone = destinationZone;
    }

    public int getOriginZone() {
        return originZone;
    }

    public void setOriginZone(int originZone) {
        this.originZone = originZone;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getPriceLevel() {
        return priceLevel;
    }

    public void setPriceLevel(int priceLevel) {
        this.priceLevel = priceLevel;
    }

    public List<PartialRoute> getPartialRoutes() {
        return partialRoutes;
    }

    public void setPartialRoutes(List<PartialRoute> partialRoutes) {
        this.partialRoutes = partialRoutes;
    }

    public String getDurationAsString() {
        int hours = duration / 60;
        int minutes = duration % 60;
        if (hours > 0) return hours + "h " + minutes + "min";
        return minutes + "min";
    }

    @Override
    public Long getId() {
        return getRouteId();
    }

    @Override
    public String getStringToApplyQuery() {
        return partialRoutes.toString();
    }
}
