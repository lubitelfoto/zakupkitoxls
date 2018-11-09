package chekanov.space.zakupkitoxls;

import android.util.Log;

import java.math.BigDecimal;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class ZakupkiItem {
    private long id;
    private long number;
    private Date publishDate;
    private URL link;
    private String objectInfo;
    private String placingWay;
    private Date startDate;
    private String placeProcedure;
    private Date endDate;
    private BigDecimal maxPrice;
    private String cutomerName;
    private String deliveryPlace;
    private String purchaseObjectDescription;

    public long getId() {
        return id;
    }

    public long getNumber() {
        return number;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public URL getLink() {
        return link;
    }

    public String getObjectInfo() {
        return objectInfo;
    }

    public String getPlacingWay() {
        return placingWay;
    }

    public Date getStartDate() {
        return startDate;
    }

    public String getPlaceProcedure() {
        return placeProcedure;
    }

    public Date getEndDate() {
        return endDate;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public String getCutomerName() {
        return cutomerName;
    }

    public String getDeliveryPlace() {
        return deliveryPlace;
    }

    public String getPurchaseObjectDescription() {
        return purchaseObjectDescription;
    }

    public ZakupkiItem (Map <String, String> tagMap){
        for (Map.Entry <String, String> pair: tagMap.entrySet()) {
        switch (pair.getKey()) {
            case("id"):
                id = Long.parseLong(pair.getValue());
                break;
            case("purchaseNumber"):
                number = Long.parseLong(pair.getValue());
                break;
            case ("docPublishDate"):

            default:
                break;
        }
        }
        Log.d(MainActivity.TAG, "id " + id + " number " + number);
    }
}
