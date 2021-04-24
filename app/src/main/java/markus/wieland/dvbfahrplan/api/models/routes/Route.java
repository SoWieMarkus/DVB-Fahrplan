package markus.wieland.dvbfahrplan.api.models.routes;

import android.content.Context;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import markus.wieland.defaultappelements.uielements.adapter.QueryableEntity;
import markus.wieland.dvbfahrplan.R;
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
        if (partialRoute.getLine().getMode().equals(Mode.WALKING) || partialRoute.getLine().getMode().equals(Mode.STAY_FOR_CONNECTION))
            return;
        if (next.getLine().getMode().equals(Mode.WALKING) || next.getLine().getMode().equals(Mode.STAY_FOR_CONNECTION))
            return;
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


    public String toString(Context context) {

        Stop origin = partialRoutes.get(0).getOrigin();
        Stop destination = partialRoutes.get(partialRoutes.size() - 1).getDestination();


        String routeString = context.getString(R.string.route_string_title);
        routeString += "\n" + context.getString(R.string.route_string_from) + " " + origin.toString();
        routeString += "\n" + context.getString(R.string.route_string_to) + " " + destination.toString();
        routeString += "\n" + origin.getFancyDepartureTime() + " - " + destination.getFancyArrivalTime() + ", " + getDurationAsString();

        /*routeString += "\n\n" + context.getString(R.string.route_string_route) + "\n======";


        StringBuilder routeStringBuilder = new StringBuilder(routeString);
        for (PartialRoute partialRoute : partialRoutes) {
            if (partialRoute.getRegularStops() == null || partialRoute.getRegularStops().isEmpty())
                continue;

            if (partialRoute.getLine().getMode().equals(Mode.WALKING)) {
                routeStringBuilder.append("\n\n");
                routeStringBuilder.append(context.getString(R.string.footpath));
            } else {
                routeStringBuilder.append("\n\n");
                routeStringBuilder.append(partialRoute.getLine().getName()).append(" ").append(partialRoute.getLine().getDirection());

            }
            routeStringBuilder.append("\n").append(context.getString(R.string.route_string_departure)).append(": ").append(partialRoute.getOrigin().getFancyDepartureTime()).append(", ").append(partialRoute.getOrigin().toString());
            routeStringBuilder.append("\n").append(context.getString(R.string.route_string_arrival)).append(": ").append(partialRoute.getDestination().getFancyArrivalTime()).append(", ").append(partialRoute.getDestination().toString());
        }
        routeString = routeStringBuilder.toString();*/


        return routeString;
    }
}

