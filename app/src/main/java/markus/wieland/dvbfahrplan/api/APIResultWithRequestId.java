package markus.wieland.dvbfahrplan.api;

import markus.wieland.defaultappelements.api.APIResult;

public interface APIResultWithRequestId<T> {

    void onLoad(int requestId, T t);

}
