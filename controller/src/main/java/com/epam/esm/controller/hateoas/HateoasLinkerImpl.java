package com.epam.esm.controller.hateoas;

import com.epam.esm.clientmodel.GiftCertificateClientModel;
import com.epam.esm.clientmodel.OrderClientModel;
import com.epam.esm.clientmodel.PageableClientModel;
import com.epam.esm.clientmodel.TagClientModel;
import com.epam.esm.clientmodel.UserClientModel;
import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.controller.OrderController;
import com.epam.esm.controller.TagController;
import com.epam.esm.controller.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Implementation of {@link HateoasLinker} interface,
 * for adding links to client models .
 */
@Component
public class HateoasLinkerImpl implements HateoasLinker {

    /**
     * Page linker for adding links to client models type of {@link PageableClientModel} .
     */
    private final PageLinker pageLinker;

    /**
     * Constructs class <code>HateoasLinkerImpl</code> with injected page linker .
     *
     * @param pageLinker {@link #pageLinker}
     */
    @Autowired
    public HateoasLinkerImpl(PageLinker pageLinker) {
        this.pageLinker = pageLinker;
    }

    @Override
    public void addLinks(Link selfLink, PageableClientModel<?> page) {
        pageLinker.addPageLinks(selfLink, page);
    }

    @Override
    public void addLinks(TagClientModel tag) {
        if (tag != null) {
            tag.add(linkTo(methodOn(TagController.class).deleteById(tag.getId())).withRel("delete"))
                    .add(linkTo(TagController.class).slash(tag.getId()).withSelfRel());
        }
    }

    @Override
    public void addLinks(OrderClientModel order) {
        order.getCertificates().forEach(this::addLinks);
        addLinks(order.getUser());
        order.add(linkTo(OrderController.class).slash(order.getId()).withSelfRel());
    }

    @Override
    public void addLinks(UserClientModel user) {
        user.add(linkTo(UserController.class).slash(user.getId()).slash("orders").withRel("orders"))
                .add(linkTo(UserController.class).slash(user.getId()).withSelfRel());
    }

    @Override
    public void addLinks(GiftCertificateClientModel certificate) {
        certificate.getTags().forEach(this::addLinks);
        certificate.add(linkTo(methodOn(GiftCertificateController.class)
                        .deleteById(certificate.getId())).withRel("delete"))
                .add(linkTo(methodOn(GiftCertificateController.class)
                        .updatePrice(certificate.getId(), new BigDecimal("0.01"))).withRel("update price"))
                .add(linkTo(GiftCertificateController.class).slash(certificate.getId()).withRel("update"))
                .add(linkTo(GiftCertificateController.class).slash(certificate.getId()).withSelfRel());
    }
}
