package markus.wieland.dvbfahrplan.api.models.routes;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import markus.wieland.defaultappelements.uielements.adapter.QueryableEntity;
import markus.wieland.dvbfahrplan.api.Mode;
import markus.wieland.dvbfahrplan.api.TimeConverter;
import markus.wieland.dvbfahrplan.api.models.Platform;

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

    public List<PartialRoute> getRouteList() {
        List<PartialRoute> partialRoutesFiltered = new ArrayList<>();
        List<PartialRoute> baseSet = removeRedundant();

        for (int i = 0; i < baseSet.size(); i++) {
            PartialRoute partialRoute = baseSet.get(i);
            PartialRoute partialRouteNext = i == baseSet.size() - 1 ? null : baseSet.get(i + 1);

            if (partialRoute.getRegularStops() == null) continue;
            partialRoutesFiltered.add(partialRoute);

            addBetweenRoute(partialRoutesFiltered, partialRoute, partialRouteNext);

        }
        return partialRoutesFiltered;
    }

    private List<PartialRoute> removeRedundant() {
        List<PartialRoute> partialRoutesFiltered = new ArrayList<>();
        for (PartialRoute partialRoute : getPartialRoutes()) {
            if (partialRoute.getRegularStops() != null) partialRoutesFiltered.add(partialRoute);
        }
        return partialRoutesFiltered;
    }

    private void addBetweenRoute(List<PartialRoute> partialRoutes, PartialRoute partialRoute, PartialRoute next) {
        if (next == null) return;
        if (partialRoute.getLine().getMode().equals(Mode.WALKING) ||partialRoute.getLine().getMode().equals(Mode.STAY_FOR_CONNECTION)) return;
        if (next.getLine().getMode().equals(Mode.WALKING) || next.getLine().getMode().equals(Mode.STAY_FOR_CONNECTION)) return;
        if (partialRoute.getDestination() == null || next.getOrigin() == null) return;

        long durationBetweenRoutes = TimeConverter.getMinutesBetween(partialRoute.getDestination().getRealArrivalTimeAsLocalDateTime(),
                next.getOrigin().getRealDepartureTimeAsLocalDate());

        Platform platform = partialRoute.getDestination().getPlatform();
        Platform platformNext = partialRoute.getOrigin().getPlatform();

        boolean samePlatform;
        if (platform == null || platformNext == null) samePlatform = false;
        else samePlatform = platform.equals(platformNext);

        PartialRoute betweenRoute = new PartialRoute();
        Mot mot = new Mot();
        mot.setMode(samePlatform ? Mode.WAITING : Mode.CHANGE_PLATFORM);
        betweenRoute.setDuration((int) durationBetweenRoutes);
        betweenRoute.setLine(mot);

        List<Stop> stops = new ArrayList<>();
        stops.add(partialRoute.getDestination());
        stops.add(next.getOrigin());
        betweenRoute.setRegularStops(stops);

        partialRoutes.add(betweenRoute);
    }


}

