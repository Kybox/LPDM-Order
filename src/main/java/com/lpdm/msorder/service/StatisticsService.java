package com.lpdm.msorder.service;

import com.lpdm.msorder.model.user.OrderStats;

public interface StatisticsService {

    OrderStats getOrderStatsByYear(int year);
    OrderStats getOrderStatsByYearAndMonth(int year, int month);
    OrderStats getOrderedProductsStatsByYear(int year);
    OrderStats getOrderedProductsStatsByYearAndCategory(int year);
}
