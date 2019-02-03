package com.lpdm.msorder.controller;

import com.lpdm.msorder.exception.BadRequestException;
import com.lpdm.msorder.exception.OrderNotFoundException;
import com.lpdm.msorder.model.order.OrderStats;
import com.lpdm.msorder.service.StatisticsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.lpdm.msorder.utils.ValueType.ADMIN_PATH;

/**
 * @author Kybox
 * @version 1.0
 * @since 01/12/2018
 */

@RestController
@RequestMapping(ADMIN_PATH)
@Api(tags = {"Admin Rest API"})
public class StatisticsController {

    private final StatisticsService statisticsService;

    @Autowired
    public StatisticsController(StatisticsService statisticsService) {

        this.statisticsService = statisticsService;
    }

    /**
     * Get orders statistics by year
     * @param year The year param for the statistics
     * @return An {@link OrderStats} object with the statistics data
     */
    @ApiOperation(value = "Get orders statistics by year")
    @GetMapping(value = "/orders/stats/year/{year}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public OrderStats getOrderStatsByYear(@PathVariable Integer year){

        if(year == null) throw new BadRequestException();
        OrderStats orderStats = statisticsService.getOrderStatsByYear(year);
        if(orderStats.getDataStats().isEmpty()) throw new OrderNotFoundException();
        return orderStats;
    }

    /**
     * Get orders statistics by month and year
     * @param year The year param for the statistics
     * @param month The month param for the statistics
     * @return An {@link OrderStats} object with the statistics data
     */
    @ApiOperation(value = "Get orders statictics by month and year")
    @GetMapping(value = "/orders/stats/year/{year}/month/{month}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public OrderStats getOrderStatsByYearAndMonth(@PathVariable Integer year,
                                                  @PathVariable Integer month){

        if(year == null || month == null) throw new BadRequestException();
        OrderStats orderStats = statisticsService.getOrderStatsByYearAndMonth(year, month);
        if(orderStats.getDataStats().isEmpty()) throw  new OrderNotFoundException();
        return orderStats;
    }

    /**
     * Get ordered products statistics by year
     * @param year The year param for the statistics
     * @return An {@link OrderStats} object with the statistics data
     */
    @ApiOperation(value = "Get ordered products statistics by year")
    @GetMapping(value = "/orderedproducts/stats/year/{year}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public OrderStats getOrderedProductsByYear(@PathVariable Integer year){

        if(year == null) throw new BadRequestException();
        OrderStats orderStats = statisticsService.getOrderedProductsStatsByYear(year);
        if(orderStats.getDataStats().isEmpty()) throw new OrderNotFoundException();
        return orderStats;
    }

    /**
     * Get ordered products statistics by year and category
     * @param year The year param for the statistics
     * @return An {@link OrderStats} object with the statistics data
     */
    @GetMapping(value = "/orderedproducts/stats/year/{year}/category",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public OrderStats getOrderedProductsByYearAndCategories(@PathVariable Integer year){

        if(year == null) throw new BadRequestException();
        OrderStats orderStats = statisticsService.getOrderedProductsStatsByYearAndCategory(year);
        if(orderStats.getDataStats().isEmpty()) throw new OrderNotFoundException();
        return orderStats;
    }
}
