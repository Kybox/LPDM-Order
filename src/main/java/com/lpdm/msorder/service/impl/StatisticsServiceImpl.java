package com.lpdm.msorder.service.impl;

import com.lpdm.msorder.exception.DateNotFoundException;
import com.lpdm.msorder.model.order.Order;
import com.lpdm.msorder.model.order.OrderedProduct;
import com.lpdm.msorder.model.product.Category;
import com.lpdm.msorder.model.product.Product;
import com.lpdm.msorder.model.order.OrderStats;
import com.lpdm.msorder.service.OrderService;
import com.lpdm.msorder.service.ProxyService;
import com.lpdm.msorder.service.StatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.lpdm.msorder.utils.ValueType.*;

/**
 * @author Kybox
 * @version 1.0
 * @since 01/12/2018
 */

@Service
public class StatisticsServiceImpl implements StatisticsService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final OrderService orderService;
    private final ProxyService proxyService;

    @Autowired
    public StatisticsServiceImpl(OrderService orderService,
                                 ProxyService proxyService) {

        this.orderService = orderService;
        this.proxyService = proxyService;
    }

    @Override
    public OrderStats getOrderStatsByYear(int year) {

        OrderStats orderStats = new OrderStats();

        LocalDateTime startStatsDate = getDateOfYear(year, FIRST_DATE_OF_YEAR);
        LocalDateTime endStatsDate = getDateOfYear(year, LAST_DATE_OF_YEAR);

        for(LocalDateTime date = startStatsDate; date.isBefore(endStatsDate); date = date.plusMonths(1)){

            int month = date.getMonthValue();

            LocalDateTime start = LocalDateTime.of(year, month, 1, 0, 0);
            LocalDate tempDate = LocalDate.ofYearDay(year, 1);
            LocalDateTime end = checkLeapYear(tempDate, year, month);

            long totalOrders = orderService
                    .countAllByOrderDateBetween(
                            getSearchDates(year, month).get(START_DATE),
                            getSearchDates(year, month).get(END_DATE));

            log.info("Stats " + year + " : Month " + month + " : " + totalOrders + " order(s)");
            log.info("Between " + start + " and " + end);
            orderStats.getDataStats().put(month, totalOrders);
        }

        return orderStats;
    }

    @Override
    public OrderStats getOrderStatsByYearAndMonth(int year, int month) {


        OrderStats orderStats = new OrderStats();

        LocalDateTime startStatsDate = LocalDateTime.of(year, month, 1,0,0);
        LocalDateTime endStatsDate = LocalDateTime.of(year, month,
                startStatsDate.getMonth().maxLength(), 23,59);

        for(LocalDateTime date = startStatsDate; date.isBefore(endStatsDate); date = date.plusDays(1)){

            LocalDateTime start = LocalDateTime.of(year, month, date.getDayOfMonth(), 0, 0);
            LocalDateTime end = LocalDateTime.of(year, month, date.getDayOfMonth(), 23, 59);

            long totalOrders = orderService.countAllByOrderDateBetween(start, end);

            log.info("Stats " + year + " Month " + month + " day " + date.getDayOfMonth() + " : " + totalOrders + " order(s)");
            log.info("Between " + start + " and " + end);
            orderStats.getDataStats().put(date.getDayOfMonth(), totalOrders);
        }

        return orderStats;
    }

    @Override
    public OrderStats getOrderedProductsStatsByYear(int year) {

        OrderStats orderStats = new OrderStats();

        LocalDateTime startStatsDate = getDateOfYear(year, FIRST_DATE_OF_YEAR);
        LocalDateTime endStatsDate = getDateOfYear(year, LAST_DATE_OF_YEAR);

        for(LocalDateTime date = startStatsDate; date.isBefore(endStatsDate); date = date.plusMonths(1)){

            int month = date.getMonthValue();

            List<Order> orderList = orderService
                    .findAllOrdersByDateBetween(
                            getSearchDates(year, month).get(START_DATE),
                            getSearchDates(year, month).get(END_DATE));

            int totalOrderedProducts = 0;
            for(Order order : orderList) totalOrderedProducts += order.getOrderedProducts().size();

            orderStats.getDataStats().put(month, totalOrderedProducts);
        }

        return orderStats;
    }

    @Override
    public OrderStats getOrderedProductsStatsByYearAndCategory(int year) {

        OrderStats orderStats = new OrderStats();
        Map<Object, Object> data = orderStats.getDataStats();

        List<Category> categoryList = proxyService.findAllProductCategories();
        for(Category category : categoryList) data.put(category.getName(), 0);

        LocalDateTime start = LocalDateTime.of(year, 1, 1,0,0);
        LocalDateTime end = LocalDateTime.of(year, 12, 31, 23,59);

        List<Order> orderList = orderService.findAllOrdersByDateBetween(start, end);
        for(Order order : orderList){

            for(OrderedProduct orderedProduct : order.getOrderedProducts()){

                Product product = proxyService.findProductById(orderedProduct.getProductId());

                for(Category category : categoryList){

                    if(product.getCategory().getId() == category.getId()){

                        int total = ((int) data.get(category.getName())) + 1;
                        data.put(category.getName(), total);
                    }
                }
            }
        }

        return orderStats;
    }

    private LocalDateTime getDateOfYear(int year, String which){

        LocalDateTime firstDate = LocalDateTime.of(year, 1, 1,0,0);
        int monthMaxLength = firstDate.getMonth().maxLength();
        LocalDateTime lastDate = LocalDateTime.of(year, 12, monthMaxLength, 23,59);

        if(which.equals(FIRST_DATE_OF_YEAR)) return firstDate;
        if(which.equals(LAST_DATE_OF_YEAR)) return lastDate;

        throw new DateNotFoundException();
    }

    private LocalDateTime checkLeapYear(LocalDate date, int year, int month){

        LocalDateTime checkedDate;

        if(!date.isLeapYear() && date.getMonthValue() == 2)
            checkedDate = LocalDateTime.of(year, month, date.getMonth().maxLength() - 1, 23, 59);
        else checkedDate = LocalDateTime.of(year, month, date.getMonth().maxLength(), 23, 59);

        return checkedDate;
    }

    private Map<String, LocalDateTime> getSearchDates(int year, int month){

        Map<String, LocalDateTime> dateResult = new HashMap<>();
        dateResult.put(START_DATE, LocalDateTime.of(year, month, 1, 0, 0));
        LocalDate tempDate = LocalDate.ofYearDay(year, 1);
        dateResult.put(END_DATE, checkLeapYear(tempDate, year, month));

        return dateResult;
    }
}
