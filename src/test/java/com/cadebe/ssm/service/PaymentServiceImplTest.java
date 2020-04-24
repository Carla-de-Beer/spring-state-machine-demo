package com.cadebe.ssm.service;

import com.cadebe.ssm.domain.Payment;
import com.cadebe.ssm.domain.PaymentEvent;
import com.cadebe.ssm.domain.PaymentState;
import com.cadebe.ssm.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;

import javax.transaction.Transactional;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest
class PaymentServiceImplTest {

    @Autowired
    PaymentService paymentService;

    @Autowired
    PaymentRepository paymentRepository;

    Payment payment;

    @BeforeEach
    void setup() {
        payment = Payment.builder()
                .amount(new BigDecimal("12.99"))
                .build();
    }

    @Test
    @Transactional
    void preAuth() {
        Payment savedPayment = paymentService.newPayment(payment);

        System.out.println("Should be NEW");
        System.out.println(savedPayment.getState());

        assertThat(savedPayment.getState())
                .withFailMessage("Could not match correct sate for new transaction")
                .isEqualTo(PaymentState.NEW);

        assertThat(savedPayment.getState())
                .withFailMessage("Could not match correct saved payment state")
                .isEqualTo(PaymentState.NEW);

        StateMachine<PaymentState, PaymentEvent> sm = paymentService.preAuth(savedPayment.getId());

        paymentService.preAuth(savedPayment.getId());

        Payment preAuthedPayment = paymentRepository.getOne(savedPayment.getId());

        System.out.println("Should be PRE_AUTH or PRE_AUTH_ERROR");
        System.out.println(sm.getState().getId());
        System.out.println(preAuthedPayment);

        assertTrue("Could not match correct state machine ID",
                sm.getState().getId().name().contains("PRE_AUTH"));

        assertThat(preAuthedPayment.getAmount())
                .withFailMessage("Could not match correct payment value")
                .isEqualTo(payment.getAmount().toString());
    }

    @RepeatedTest(10)
    @Transactional
    void authorize() {
        Payment savedPayment = paymentService.newPayment(payment);

        StateMachine<PaymentState, PaymentEvent> preAuthSM = paymentService.preAuth(savedPayment.getId());

        if (preAuthSM.getState().getId() == PaymentState.PRE_AUTH) {

            System.out.println("Payment is Pre-Authorized");

            StateMachine<PaymentState, PaymentEvent> authSM = paymentService.authorizePayment(savedPayment.getId());

            System.out.println("Result of Auth: " + authSM.getState().getId());

            assertTrue("Could not match correct authorization state", authSM.getState().getId().toString().equals("AUTH") ||
                    authSM.getState().getId().toString().equals("AUTH_ERROR")
            );

        } else {
            System.out.println("Payment failed Pre-Auth");
        }
    }
}