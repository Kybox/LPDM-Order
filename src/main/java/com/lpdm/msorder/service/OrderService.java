package com.lpdm.msorder.service;

import com.lpdm.msorder.exception.OrderNotFoundException;
import com.lpdm.msorder.model.order.*;
import com.lpdm.msorder.model.order.SearchDates;
import com.lpdm.msorder.model.user.User;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Kybox
 * @version 1.0
 * @since 01/12/2018
 */

public interface OrderService {

    /**
     * Persist an {@link Order} object
     * @param order The {@link Order} object to persist
     * @return The {@link Order} object persisted
     */
    Order saveOrder(Order order);

    /**
     * Delete an {@link Order} object
     * @param order The {@link Order} object to delete
     */
    void deleteOrder(Order order);

    /**
     * Find an {@link Order} object by its id
     * @param id The {@link Order} id
     * @return The {@link Order} object found
     * @throws OrderNotFoundException Thrown if no {@link Order} was found
     */
    Order findOrderById(int id) throws OrderNotFoundException;

    /**
     * Check if an {@link Order} object exist in the database
     * @param id The {@link Order} id
     * @return True if the {@link Order} exist, otherwise false
     */
    boolean checkIfOrderExist(int id);

    /**
     * Find all {@link Order} objects of a {@link User} by its id
     * @param id The {@link User} id
     * @return The {@link List} of {@link Order} objects found
     */
    List<Order> findAllOrdersByCustomerId(int id);

    /**
     * Find all {@link Order} object with the defined {@link Payment}
     * @param payment The {@link Payment} object in the {@link Order}
     * @return The {@link List} of {@link Order} objects found
     */
    List<Order> findAllOrdersByPayment(Payment payment);

    /**
     * Find all {@link Order} objects by a {@link User} name or email
     * @param findBy Choose between email or name
     * @param keyword The defined name or email
     * @return The {@link List} of {@link Order} objects found
     */
    List<Order> findAllOrdersByCustomer(String findBy, String keyword);

    /**
     * Find all {@link Order} objects with a pagination
     * @param pageRequest The pagination defined
     * @return The {@link List} of {@link Order} objects found with the pagination limit
     */
    List<Order> findAllOrdersPageable(PageRequest pageRequest);

    /**
     * Find all {@link Order} by a {@link User} id and the {@link Order} status
     * @param id The {@link User} id
     * @param status The {@link Order} status
     * @return The {@link List} if {@link Order} objects found
     */
    List<Order> findAllOrdersByCustomerIdAndStatus(int id, Status status);

    /**
     * Find all {@link Order} by the order date between two dates
     * @param searchDates The searchDates object which contains two {@link LocalDateTime} objects
     * @return The {@link List} of {@link Order} found
     */
    List<Order> findAllOrdersBetweenTwoDates(SearchDates searchDates);

    /**
     * Find all {@link Order} objects by their status with a pagination
     * @param status The {@link Order} status
     * @param pageRequest The pagination data
     * @return The {@link List} of {@link Order} objects found
     */
    List<Order> findAllOrdersByStatusPageable(Status status, PageRequest pageRequest);

    /**
     * Find all {@link Order} objects by the {@link User} id
     * and sorted by the order date ascending and with a pagination
     * @param id The {@link Order} id
     * @param pageRequest The pagination data
     * @return The {@link List} of {@link Order} objects found
     */
    List<Order> findAllOrdersByCustomerIdOrderByOrderDateAsc(int id, PageRequest pageRequest);

    /**
     * Find all {@link Order} objects by the {@link User} id
     * and sorted by the order date descending and with a pagination
     * @param id The {@link Order} id
     * @param pageRequest The pagination data
     * @return The {@link List} of {@link Order} objects found
     */
    List<Order> findAllOrdersByCustomerIdOrderByOrderDateDesc(int id, PageRequest pageRequest);

    /**
     * Count all {@link Order} objects from the database between two dates
     * @param date1 The first {@link LocalDateTime} object
     * @param date2 The second {@link LocalDateTime} object
     * @return The total number of {@link Order} object between this two {@link LocalDateTime}
     */
    long countAllByOrderDateBetween(LocalDateTime date1, LocalDateTime date2);

    /**
     * Find all {@link Order} objects between two {@link LocalDateTime} objects
     * @param date1 The first {@link LocalDateTime} object
     * @param date2 The second {@link LocalDateTime} object
     * @return The {@link List} of {@link Order} objects found
     */
    List<Order> findAllOrdersByDateBetween(LocalDateTime date1, LocalDateTime date2);
}
