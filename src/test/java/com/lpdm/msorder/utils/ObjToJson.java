package com.lpdm.msorder.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjToJson {

    public static String get(Object obj) throws JsonProcessingException {

        return new ObjectMapper().writeValueAsString(obj);
    }
}
