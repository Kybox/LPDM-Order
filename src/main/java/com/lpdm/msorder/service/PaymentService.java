package com.lpdm.msorder.service;

import com.lpdm.msorder.model.order.Order;
import com.lpdm.msorder.model.order.Payment;
import com.lpdm.msorder.model.paypal.PaypalPayUrl;
import com.paypal.api.payments.RedirectUrls;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * @author Kybox
 * @version 1.0
 * @since 01/12/2018
 */

public interface PaymentService {

    /**
     * Process to the order payment
     * @param orderId The {@link Order} id to pay
     * @param redirectUrls The {@link RedirectUrls} for the redirection
     * @param id The client id for the Paypal API
     * @param secret The secret for the Paypal API
     * @return A {@link PaypalPayUrl} object with the header and the url redirection
     * @throws Exception Thrown if something goes wrong
     */
    PaypalPayUrl paypalPaymentProcess(int orderId,
                                      RedirectUrls redirectUrls,
                                      String id, String secret) throws Exception;

    /**
     * Find all payment methods
     * @return The {@link List} of {@link Payment} objects
     */
    List<Payment> findAllPayments();

    /**
     * Find a {@link Payment} object by its id
     * @param id The {@link Payment} id
     * @return The {@link Payment} object found
     */
    Payment findPaymentById(int id);

    /**
     * Persist a new {@link Payment} object in the database
     * @param payment The {@link Payment} object to persist
     * @return The {@link Payment} object persisted
     */
    Payment savePayment(Payment payment);

    /**
     * Delete a {@link Payment} object in the database
     * @param payment The {@link Payment} object to delete
     */
    void deletePayment(Payment payment);

    /**
     * Check if a {@link Payment} object exist in the database
     * @param id The {@link Payment} id
     * @return True if the {@link Payment} exist, otherwise false
     */
    boolean checkIfPaymentExist(int id);
}
