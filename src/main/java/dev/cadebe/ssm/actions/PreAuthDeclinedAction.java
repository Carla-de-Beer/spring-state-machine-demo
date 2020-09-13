package dev.cadebe.ssm.actions;

import dev.cadebe.ssm.domain.PaymentEvent;
import dev.cadebe.ssm.domain.PaymentState;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

@Component
public class PreAuthDeclinedAction implements Action<PaymentState, PaymentEvent> {

    @Override
    public void execute(StateContext<PaymentState, PaymentEvent> stateContext) {
        System.out.println("Sending notification of PRE_AUTH_DECLINED");
    }
}
