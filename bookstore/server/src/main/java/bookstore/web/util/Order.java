package bookstore.web.util;

import bookstore.data.entity.Address;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Order {

    private long orderId = 0;
    private List<Long> bookItemIds = new ArrayList<>();
    private Address address;

    public Order(List<Long> bookItemIds) {
        this.bookItemIds = bookItemIds;
    }

    public Order(long orderId, List<Long> bookItemIds){
        this.orderId = orderId;
        this.bookItemIds = bookItemIds;
    }
}
