package markus.wieland.dvbfahrplan.api.models.routes;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Routes {

    @SerializedName("Routes")
    private List<Route> routeList;

    public Routes(List<Route> routeList) {
        this.routeList = routeList;
    }

    public List<Route> getRouteList() {
        return routeList;
    }

    public void setRouteList(List<Route> routeList) {
        this.routeList = routeList;
    }
}
