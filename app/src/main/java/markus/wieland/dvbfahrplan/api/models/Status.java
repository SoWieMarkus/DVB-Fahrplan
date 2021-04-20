package markus.wieland.dvbfahrplan.api.models;

public class Status {

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
