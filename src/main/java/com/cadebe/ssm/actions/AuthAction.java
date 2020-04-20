package com.cadebe.ssm.actions;

import com.cadebe.ssm.domain.PaymentEvent;
import com.cadebe.ssm.domain.PaymentState;
import com.cadebe.ssm.service.PaymentServiceImpl;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class AuthAction implements Action<PaymentState, PaymentEvent> {

    @Override
    public void execute(StateContext<PaymentState, PaymentEvent> stateContext) {
        System.out.println("Auth was called!!!");

        if (new Random().nextInt(10) < 8) {
            System.out.println("Authorized");
            stateContext.getStateMachine().sendEvent(MessageBuilder.withPayload(PaymentEvent.AUTH_APPROVED)
                    .setHeader(PaymentServiceImpl.PAYMENT_ID_HEADER,
                            stateContext.getMessageHeader(PaymentServiceImpl.PAYMENT_ID_HEADER))
                    .build());

        } else {
            System.out.println("Declined! No Credit!!!!!!");
            stateContext.getStateMachine().sendEvent(MessageBuilder.withPayload(PaymentEvent.AUTH_DECLINED)
                    .setHeader(PaymentServiceImpl.PAYMENT_ID_HEADER,
                            stateContext.getMessageHeader(PaymentServiceImpl.PAYMENT_ID_HEADER))
                    .build());
        }
    }
}