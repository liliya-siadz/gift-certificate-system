package com.epam.esm.service.change_predicate;

import com.epam.esm.model.TagClientModel;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;

@Component
public class CreateAndBoundTagPredicate implements Predicate<TagClientModel> {

    @Override
    public boolean test(TagClientModel clientModel) {
        return ((clientModel.getName() != null) && (clientModel.getId() == null));
    }
}
