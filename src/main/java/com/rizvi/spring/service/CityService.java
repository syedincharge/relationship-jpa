package com.rizvi.spring.service;
import com.rizvi.spring.dto.requestDto.CityRequestDto;
import com.rizvi.spring.model.City;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CityService {

    public City addCity(CityRequestDto cityRquestDto);

    public List<City> getCities();

    public City getCity(Long cityId);

    public City deleteCity(Long cityId);

    public City editCity(Long cityId, CityRequestDto cityRequestDto);

}
