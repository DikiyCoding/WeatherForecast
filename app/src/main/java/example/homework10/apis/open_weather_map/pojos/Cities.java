package example.homework10.apis.open_weather_map.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cities {

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("cod")
    @Expose
    private String cod;

    @SerializedName("count")
    @Expose
    private Integer count;

    @SerializedName("list")
    @Expose
    private java.util.List<CityList> list = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public java.util.List<CityList> getList() {
        return list;
    }

    public void setList(java.util.List<CityList> list) {
        this.list = list;
    }
}
