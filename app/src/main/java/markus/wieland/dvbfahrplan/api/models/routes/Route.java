package markus.wieland.dvbfahrplan.api.models.routes;

import android.content.Context;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import markus.wieland.defaultappelements.uielements.adapter.QueryableEntity;
import markus.wieland.dvbfahrplan.R;
import markus.wieland.dvbfahrplan.api.Mode;
import markus.wieland.dvbfahrplan.api.models.Platform;
import markus.wieland.dvbfahrplan.helper.TimeConverter;

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

    @SerializedName("MapData")
    private List<String> mapData;

    @SerializedName("RouteId")
    private long routeId;

    public List<String> getMapData() {
        return mapData;
    }

    public void setMapData(List<String> mapData) {
        this.mapData = mapData;
    }

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
        if (price != null) return price + "€";
        return null;
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

    public String getDurationAsString(Context context) {
        int hours = duration / 60;
        int minutes = duration % 60;
        if (hours > 0)
            return hours + " " + context.getString(R.string.hour_short_term) + " " + minutes + " " + context.getString(R.string.minute_short);
        return minutes + " " + context.getString(R.string.minute_short);
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

            if (i == 0 && partialRoute.getLine().getMode().equals(Mode.WALKING)) {
                addSingleStopRoute(partialRoutesFiltered, partialRoute);
            }

            if (partialRoute.getRegularStops() == null) continue;
            partialRoutesFiltered.add(partialRoute);

            if (i == baseSet.size() - 1 && partialRoute.getLine().getMode().equals(Mode.WALKING)) {
                addSingleStopRoute(partialRoutesFiltered, partialRoute);
            }

            addBetweenRoute(partialRoutesFiltered, partialRoute, partialRouteNext);

        }
        return partialRoutesFiltered;
    }

    public String getInterchangesAsString(Context context) {
        return interchanges + " " + (interchanges == 1
                ? context.getString(R.string.route_interchange_singular)
                : context.getString(R.string.route_interchanges));
    }

    private List<PartialRoute> removeRedundant() {
        List<PartialRoute> partialRoutesFiltered = new ArrayList<>();
        for (PartialRoute partialRoute : getPartialRoutes()) {
            if (partialRoute.getRegularStops() != null) partialRoutesFiltered.add(partialRoute);
        }
        return partialRoutesFiltered;
    }

    private void addSingleStopRoute(List<PartialRoute> partialRoutes, PartialRoute partialRoute) {
        PartialRoute topRoute = new PartialRoute();
        Mot mot = new Mot();
        mot.setMode(Mode.ONLY_ONE_PART);
        topRoute.setLine(mot);
        topRoute.setRegularStops(new ArrayList<>(Arrays.asList(partialRoute.getOrigin(), partialRoute.getDestination())));
        partialRoutes.add(topRoute);
    }

    private void addBetweenRoute(List<PartialRoute> partialRoutes, PartialRoute partialRoute, PartialRoute next) {
        if (next == null) return;
        if (partialRoute.getLine().getMode().isGapBetweenPartialRoutes())
            return;
        if (next.getLine().getMode().isGapBetweenPartialRoutes())
            return;
        if (partialRoute.getDestination() == null || next.getOrigin() == null) return;

        long durationBetweenRoutes = TimeConverter.getMinutesBetween(partialRoute.getDestination().getRealArrivalTimeAsLocalDateTime(),
                next.getOrigin().getRealDepartureTimeAsLocalDate());

        Platform platform = partialRoute.getDestination().getPlatform();
        Platform platformNext = next.getOrigin().getPlatform();

        boolean samePlatform;
        boolean sameStation = next.getOrigin().getName().equals(partialRoute.getDestination().getName());
        if (platform == null || platformNext == null)
            samePlatform = false;
        else
            samePlatform = platform.equals(platformNext);

        PartialRoute betweenRoute = new PartialRoute();
        Mot mot = new Mot();

        Mode mode = Mode.WALKING;
        if (sameStation) {
            mode = samePlatform
                    ? Mode.WAITING
                    : Mode.CHANGE_PLATFORM;
        }
        mot.setMode(mode);
        betweenRoute.setDuration((int) durationBetweenRoutes);
        betweenRoute.setLine(mot);

        List<Stop> stops = new ArrayList<>();
        stops.add(partialRoute.getDestination());
        stops.add(next.getOrigin());
        betweenRoute.setRegularStops(stops);

        partialRoutes.add(betweenRoute);
    }

    public Stop getOrigin() {
        if (partialRoutes.isEmpty()) return null;
        return partialRoutes.get(0).getOrigin();
    }

    public Stop getDestination() {
        if (partialRoutes.isEmpty()) return null;
        return partialRoutes.get(partialRoutes.size() - 1).getDestination();
    }


    public String getDepartureTime(Context context) {
        return context.getString(R.string.route_departure) + " " + TimeConverter.getStringOfLocalDateWithDates(getOrigin().getDepartureTimeAsLocalDate());
    }

    public String getArrivalTime(Context context) {
        return context.getString(R.string.route_arrival) + " " + TimeConverter.getStringOfLocalDateWithDates(getDestination().getArrivalTimeAsLocalDate());
    }

    public String toString(Context context) {
        Stop origin = getOrigin();
        Stop destination = getDestination();

        String routeString = context.getString(R.string.route_string_title);
        routeString += "\n" + context.getString(R.string.route_string_from) + " " + origin.toString();
        routeString += "\n" + context.getString(R.string.route_string_to) + " " + destination.toString();
        routeString += "\n" + origin.getFancyDepartureTime() + " - " + destination.getFancyArrivalTime() + ", " + getDurationAsString(context);

        return routeString;
    }

}

