package com.lpdm.msorder.service;

import com.lpdm.msorder.model.order.OrderStats;

/**
 * @author Kybox
 * @version 1.0
 * @since 01/12/2018
 */

public interface StatisticsService {

    /**
     * Get orders statistics by year
     * @param year The year param for the statistics
     * @return An {@link OrderStats} object with the statistics data
     */
    OrderStats getOrderStatsByYear(int year);

    /**
     * Get orders statistics by month and year
     * @param year The year param for the statistics
     * @param month The month param for the statistics
     * @return An {@link OrderStats} object with the statistics data
     */
    OrderStats getOrderStatsByYearAndMonth(int year, int month);

    /**
     * Get ordered products statistics by year
     * @param year The year param for the statistics
     * @return An {@link OrderStats} object with the statistics data
     */
    OrderStats getOrderedProductsStatsByYear(int year);

    /**
     * Get ordered products statistics by year and category
     * @param year The year param for the statistics
     * @return An {@link OrderStats} object with the statistics data
     */
    OrderStats getOrderedProductsStatsByYearAndCategory(int year);
}
