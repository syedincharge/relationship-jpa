package com.rizvi.spring.service;


import com.rizvi.spring.dto.requestDto.ZipcodeRequestDto;
import com.rizvi.spring.model.Zipcode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ZipcodeService {

    public Zipcode addZipcode(ZipcodeRequestDto zipcoderequsetDto);

    public List<Zipcode> getZipcodes();

    public Zipcode getZipcode(Long zipcodeId);

    public Zipcode deleteZipcode(Long zipcodeId);

    public Zipcode editZipcode(Long zipcodeId, ZipcodeRequestDto zipcodeRequestDto );

    public Zipcode addCityToZipcode(Long zipcodeId, Long cityId);

    public Zipcode removeCityFromZipcode(Long zipcodeId );


}
