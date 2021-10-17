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

    @Autowired
    private TagServiceImpl tagService;

    @GetMapping
    public List<TagClientModel> getTags() {
        return tagService.findAll();
    }

    @GetMapping("/{id}")
    public TagClientModel getTag(@PathVariable Long id) {
        return tagService.findById(id);
    }

    @PostMapping
    public TagClientModel createTag(@RequestBody TagClientModel tag) {
        return tagService.create(tag);
    }

    @DeleteMapping("/{id}")
    public TagClientModel deleteTag(@PathVariable Long id) {
        return tagService.delete(id);
    }
}
