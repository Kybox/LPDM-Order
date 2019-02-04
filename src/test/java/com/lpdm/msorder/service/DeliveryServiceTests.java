package com.lpdm.msorder.service;

import com.lpdm.msorder.exception.DeliveryNotFoundException;
import com.lpdm.msorder.model.order.Delivery;
import com.lpdm.msorder.repository.DeliveryRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class DeliveryServiceTests {

    @MockBean
    private DeliveryRepository deliveryRepository;

    @Autowired
    private DeliveryService deliveryService;

    int randomId;
    private Delivery delivery;
    private List<Delivery> deliveryList;

    @Before
    public void init() {

        randomId = (int) (Math.random() * 123);

        delivery = new Delivery(randomId);
        delivery.setAmount(123);
        delivery.setMethod("Method");

        deliveryList = new ArrayList<>();
        deliveryList.add(delivery);
    }

    @Test
    public void findAllDeliveryMethods() {

        when(deliveryRepository.findAll())
                .thenReturn(deliveryList);

        assertEquals(deliveryList, deliveryService.findAllDeliveryMethods());
    }

    @Test(expected = DeliveryNotFoundException.class)
    public void findDeliveryMethodByIdException() {

        when(deliveryRepository.findById(anyInt()))
                .thenReturn(Optional.empty());

        deliveryService.findDeliveryMethodById(randomId);
    }

    @Test
    public void findDeliveryMethodById() {

        when(deliveryRepository.findById(anyInt()))
                .thenReturn(Optional.of(delivery));

        assertEquals(delivery, deliveryService.findDeliveryMethodById(randomId));
    }

    @Test
    public void addNewDeliveryMethod() {

        when(deliveryRepository.save(any(Delivery.class)))
                .thenReturn(delivery);

        assertEquals(delivery, deliveryService.addNewDeliveryMethod(delivery));
    }

    @Test(expected = DeliveryNotFoundException.class)
    public void deleteDeliveryMethodException() {

        when(deliveryRepository.findById(anyInt()))
                .thenReturn(Optional.empty());

        deliveryService.deleteDeliveryMethod(delivery);
    }

    @Test
    public void deleteDeliveryMethod() {

        when(deliveryRepository.findById(anyInt()))
                .thenReturn(Optional.of(delivery))
                .thenReturn(Optional.empty());

        assertTrue(deliveryService.deleteDeliveryMethod(delivery));
    }

    @Test(expected = DeliveryNotFoundException.class)
    public void updateDeliveryMethodException() {

        when(deliveryRepository.findById(anyInt()))
                .thenReturn(Optional.empty());

        deliveryService.updateDeliveryMethod(delivery);
    }

    @Test
    public void updateDeliveryMethod() {

        when(deliveryRepository.findById(anyInt()))
                .thenReturn(Optional.of(delivery));

        when(deliveryRepository.save(any(Delivery.class)))
                .thenReturn(delivery);

        assertEquals(delivery, deliveryService.updateDeliveryMethod(delivery));
    }
}
