package com.rizvi.spring.repository;

import com.rizvi.spring.model.City;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CityRepository extends CrudRepository<City, Long> {

}
