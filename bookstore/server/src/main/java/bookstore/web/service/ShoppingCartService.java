package bookstore.web.service;

import bookstore.data.entity.BookItem;
import bookstore.data.entity.ShoppingCart;
import bookstore.data.repository.BookItemRepository;
import bookstore.data.repository.ShoppingCartRepository;
import bookstore.web.resource.ShoppingCartResourceAssembler;
import bookstore.web.util.SimpleCart;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingCartService {

    private ShoppingCartRepository repository;
    private final BookItemRepository bookItemRepository;
    private final ShoppingCartResourceAssembler resourceAssembler;

    public ShoppingCartService(ShoppingCartRepository repository, BookItemRepository bookItemRepository, ShoppingCartResourceAssembler resourceAssembler) {
        this.repository = repository;
        this.bookItemRepository = bookItemRepository;
        this.resourceAssembler = resourceAssembler;
    }

    public Resource<ShoppingCart> getShoppingCart(long id) {
        ShoppingCart shoppingCart = this.repository.findById(id).orElseThrow(EntityNotFoundException::new);
        return this.resourceAssembler.toResource(shoppingCart);
    }

    public Resource<ShoppingCart> createShoppingCart() {
        ShoppingCart cart = repository.save(new ShoppingCart());
        return this.resourceAssembler.toResource(cart);
    }

    public void deleteShoppingCart(long id) {
        this.repository.deleteById(id);
    }

    public Resource<ShoppingCart> saveShoppingCart(SimpleCart simpleCart) {
        List<BookItem> bookItems = getBookItems(simpleCart.getBookItemIds());
        ShoppingCart cart = new ShoppingCart(simpleCart.getId(), bookItems);
        cart = repository.save(cart);
        return resourceAssembler.toResource(cart);
    }

    private List<BookItem> getBookItems(List<Long> bookItemIds) {
        return bookItemIds.stream().filter(bookItemRepository::existsById)
                .map(id -> bookItemRepository.findById(id).get())
                .collect(Collectors.toList());
    }

    public void deleteBookItemId(long cartId, long bookItemId) {
        ShoppingCart cart = repository.findById(cartId).get();
        BookItem bookItem = cart.getBookItems().stream().filter(item -> item.getId() == bookItemId).findFirst().get();
        List<BookItem> bookItems = cart.getBookItems();
        bookItems.remove(bookItem);
        cart.setBookItems(bookItems);
        repository.save(cart);
    }
}
