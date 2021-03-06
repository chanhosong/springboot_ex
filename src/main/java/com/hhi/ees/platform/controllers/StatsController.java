package com.hhi.ees.platform.controllers;

import com.hhi.ees.platform.handlers.StatsTSDBHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping(value = "/api/stats")
public class StatsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(StatsController.class);

    @Autowired
    private StatsTSDBHandler statsTSDBHandler;

    @RequestMapping(method = RequestMethod.GET )
    public Object getStats() {

        //TODO ALL value
        return statsTSDBHandler.getAllStats(HttpMethod.GET);
    }

    @RequestMapping(value = "/products/equipments", method = RequestMethod.GET )
    public Object getProductsOfEquipments(@RequestParam(value="vdm") String vdm) throws UnsupportedEncodingException {

        // TODO add Validator for PathVariable
        LOGGER.debug("query : vdm = {}", vdm);
        return StringUtils.isEmpty(vdm) ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) : statsTSDBHandler.getProductsOfEquipments(vdm, HttpMethod.GET);
    }

    @RequestMapping(value = "/products/equipments/devices", method = RequestMethod.GET )
    public Object getProductsOfDevices() throws UnsupportedEncodingException {

        // TODO add Validator for PathVariable
        return statsTSDBHandler.getProductsOfDevices(HttpMethod.GET);
    }

    @RequestMapping(value = "/accumulation/equipments", method = RequestMethod.GET )
    public Object getAccumulationOfEquipments(@RequestParam(value="vdm") String vdm) throws UnsupportedEncodingException {

        // TODO add Validator for PathVariable
        LOGGER.debug("query : vdm = {}", vdm);
        return StringUtils.isEmpty(vdm) ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) : statsTSDBHandler.getAccumulationOfEquipments(vdm, HttpMethod.GET);
    }

    @RequestMapping(value = "/accumulation/equipments/devices", method = RequestMethod.GET )
    public Object getAccumulationOfDevices() throws UnsupportedEncodingException {

        // TODO add Validator for PathVariable
        return statsTSDBHandler.getAccumulationOfDevices(HttpMethod.GET);
    }
}
