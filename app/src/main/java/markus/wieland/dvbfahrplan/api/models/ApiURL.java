package markus.wieland.dvbfahrplan.api.models;

public class ApiURL {

    private String urlString;

    public ApiURL(String url) {
        this.urlString = url;
        this.urlString += "?format=json";
    }

    public ApiURL append(String key, String value) {
        this.urlString += "&" + key + "=" + value;
        return this;
    }

    public ApiURL append(String key, boolean value) {
        return append(key, String.valueOf(value));
    }

    public ApiURL append(String key, int value) {
        return append(key, String.valueOf(value));
    }

    @Override
    public String toString() {
        return urlString;
    }
}
