package markus.wieland.dvbfahrplan.api;

public interface APIResultWithRequestId<T> {

    void onLoad(int requestId, T t);

}
