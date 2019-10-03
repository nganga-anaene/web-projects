package bookstore.web.resource;

import bookstore.data.entity.ShoppingCart;
import bookstore.web.controller.ShoppingCartController;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class ShoppingCartResourceAssembler implements ResourceAssembler<ShoppingCart, Resource<ShoppingCart>> {
    @Override
    public Resource<ShoppingCart> toResource(ShoppingCart shoppingCart) {
        Resource<ShoppingCart> resource = new Resource<>(shoppingCart);
        resource.add(linkTo(methodOn(ShoppingCartController.class).getShoppingCart(shoppingCart.getId())).withSelfRel());
        return resource;
    }
}
