package com.epam.esm.controller;

import com.epam.esm.model.GiftCertificateClientModel;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/gift_certificates")
public class GiftCertificateController {
    private final GiftCertificateService service;

    @Autowired
    public GiftCertificateController(GiftCertificateService service) {
        this.service = service;
    }

    @PostMapping
    public GiftCertificateClientModel create(
            @RequestBody GiftCertificateClientModel certificate) {
        return service.create(certificate);
    }

    @GetMapping
    public List<GiftCertificateClientModel> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public GiftCertificateClientModel getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @DeleteMapping("/{id}")
    public GiftCertificateClientModel deleteById(@PathVariable Long id) {
        return service.delete(id);
    }

    @PutMapping("/{id}")
    public GiftCertificateClientModel update(@PathVariable Long id,
                                             @RequestBody GiftCertificateClientModel certificate) {
        return service.update(id, certificate);
    }

    @GetMapping("/search")
    public List<GiftCertificateClientModel> search(
            @RequestParam(required = false) String tagName,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String sort) {
        return service.search(tagName, name, description, sort);
    }
}
