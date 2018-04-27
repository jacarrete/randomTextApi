package com.betvictor.dao;

import com.betvictor.model.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jcarretero on 25/04/2018.
 */
@Repository
public interface DataRepository extends JpaRepository<Data, Long> {

    default List<Data> findTop10Elements() {
        Page<Data> result = findAll(PageRequest.of(0, 10));
        return result.getContent();
    }
}
