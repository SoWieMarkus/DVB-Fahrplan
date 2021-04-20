package markus.wieland.dvbfahrplan.api.models.lines;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Lines {

    @SerializedName("Lines")
    private List<Line> linesList;

    public Lines(List<Line> linesList) {
        this.linesList = linesList;
    }

    public List<Line> getLinesList() {
        return linesList;
    }

    public void setLinesList(List<Line> linesList) {
        this.linesList = linesList;
    }
}
