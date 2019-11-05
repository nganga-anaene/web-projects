package bookstore.web.controller;

import bookstore.data.entity.ShoppingCart;
import bookstore.web.service.ShoppingCartService;
import bookstore.web.util.SimpleCart;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("shopping-cart")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(final ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource<ShoppingCart>> getShoppingCart(@PathVariable long id) {
        return ResponseEntity.ok(this.shoppingCartService.getShoppingCart(id));
    }

    @GetMapping("/create")
    public ResponseEntity<Resource<ShoppingCart>> createShoppingCart() {
        return ResponseEntity.ok(this.shoppingCartService.createShoppingCart());
    }

    @PostMapping("/{id}")
    public ResponseEntity<Resource<ShoppingCart>> updateShoppingCart(@RequestBody SimpleCart simpleCart) {
        return ResponseEntity.ok(this.shoppingCartService.saveShoppingCart(simpleCart));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteShoppingCart(@PathVariable long id) {
        try {
            this.shoppingCartService.deleteShoppingCart(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{cartId}/book-item/{bookItemId}")
    public ResponseEntity<HttpStatus> deleteBookItem(@PathVariable long cartId, @PathVariable long bookItemId) {
        try {
            this.shoppingCartService.deleteBookItemId(cartId, bookItemId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
