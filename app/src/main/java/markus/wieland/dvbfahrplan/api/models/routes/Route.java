package markus.wieland.dvbfahrplan.api.models.routes;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Route {

    @SerializedName("Duration")
    private int duration;

    @SerializedName("FareZoneDestination")
    private int destinationZone;

    @SerializedName("FareZoneOrigin")
    private int originZone;

    @SerializedName("Price")
    private String price;

    @SerializedName("PriceLevel")
    private int priceLevel;

    @SerializedName("PartialRoutes")
    private List<PartialRoute> partialRoutes;

    public Route(int duration, int destinationZone, int originZone, String price, int priceLevel, List<PartialRoute> partialRoutes) {
        this.duration = duration;
        this.destinationZone = destinationZone;
        this.originZone = originZone;
        this.price = price;
        this.priceLevel = priceLevel;
        this.partialRoutes = partialRoutes;
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
}
