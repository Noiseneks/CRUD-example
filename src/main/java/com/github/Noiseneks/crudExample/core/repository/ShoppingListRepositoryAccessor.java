package com.github.Noiseneks.crudExample.core.repository;

import com.github.Noiseneks.crudExample.domain.entity.ShoppingListItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingListRepositoryAccessor extends JpaRepository<ShoppingListItem, Long> {

    private void letMeDown() {
    }

}


