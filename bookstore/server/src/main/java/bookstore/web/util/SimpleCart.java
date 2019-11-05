package bookstore.web.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleCart {

    private long id;

    private List<Long> bookItemIds = new ArrayList<>();
}
