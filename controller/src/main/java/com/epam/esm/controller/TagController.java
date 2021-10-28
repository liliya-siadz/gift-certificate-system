package com.epam.esm.controller;

import com.epam.esm.model.TagClientModel;
import com.epam.esm.service.impl.TagServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {
    private final TagServiceImpl service;

    @Autowired
    public TagController(TagServiceImpl service) {
        this.service = service;
    }

    @GetMapping
    public List<TagClientModel> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public TagClientModel getById(@PathVariable(value = "id") Long id) {
        return service.findById(id);
    }

    @PostMapping
    public TagClientModel create(@RequestBody TagClientModel tag) {
        return service.create(tag);
    }

    @DeleteMapping("/{id}")
    public TagClientModel deleteById(@PathVariable Long id) {
        return service.delete(id);
    }
}
