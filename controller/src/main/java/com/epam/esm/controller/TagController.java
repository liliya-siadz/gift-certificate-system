package com.epam.esm.controller;


import com.epam.esm.model.TagClientModel;
import com.epam.esm.model.TagListContainer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {

    @GetMapping
    public @ResponseBody TagListContainer getTags()
            throws Exception {
      throw new UnsupportedOperationException();
    }
}
