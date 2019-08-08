package com.anaene.airlineserver.web.resource;

import com.anaene.airlineserver.data.entity.PaymentCard;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;

public class PaymentCardResource implements ResourceAssembler<PaymentCard, Resource<PaymentCard>> {
    @Override
    public Resource<PaymentCard> toResource(PaymentCard entity) {
        return null;
    }
}
