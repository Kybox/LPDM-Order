package com.lpdm.msorder.service;

import com.lpdm.msorder.model.order.OrderStats;

/**
 * @author Kybox
 * @version 1.0
 * @since 01/12/2018
 */

public interface StatisticsService {

    OrderStats getOrderStatsByYear(int year);
    OrderStats getOrderStatsByYearAndMonth(int year, int month);
    OrderStats getOrderedProductsStatsByYear(int year);
    OrderStats getOrderedProductsStatsByYearAndCategory(int year);
}
