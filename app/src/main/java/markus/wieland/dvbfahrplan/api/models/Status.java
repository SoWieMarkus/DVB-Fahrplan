package markus.wieland.dvbfahrplan.api.models;

import com.google.gson.annotations.SerializedName;

public class Status {

    @SerializedName("Code")
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isOk(){
        return code.equalsIgnoreCase("ok");
    }
}
