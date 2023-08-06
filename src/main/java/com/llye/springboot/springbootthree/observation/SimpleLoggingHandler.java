package com.llye.springboot.springbootthree.observation;

import io.micrometer.common.lang.NonNull;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class SimpleLoggingHandler implements ObservationHandler<Observation.Context> {

    private static final Logger LOG = LoggerFactory.getLogger(SimpleLoggingHandler.class);

    private static String toString(Observation.Context context) {
        return Objects.isNull(context) ? "(no context)" : context.getName()
                + " (" + context.getClass().getName() + "@" + System.identityHashCode(context) + ")";
    }

    private static String toString(Observation.Event event) {
        return Objects.isNull(event) ? "(no event)" : event.getName();
    }

    @Override
    public boolean supportsContext(@NonNull Observation.Context context) {
        return true;
    }

    @Override
    public void onStart(@NonNull Observation.Context context) {
        String format = String.format("Starting context %s", toString(context));
        LOG.info(format);
    }

    @Override
    public void onError(@NonNull Observation.Context context) {
        String format = String.format("Error for context %s", toString(context));
        LOG.info(format);
    }

    @Override
    public void onEvent(@NonNull Observation.Event event, @NonNull Observation.Context context) {
        String format = String.format("Event for context %s [%s]", toString(context), toString(event));
        LOG.info(format);
    }

    @Override
    public void onScopeOpened(@NonNull Observation.Context context) {
        String format = String.format("Scope opened for context %s", toString(context));
        LOG.info(format);

    }

    @Override
    public void onScopeClosed(@NonNull Observation.Context context) {
        String format = String.format("Scope closed for context %s", toString(context));
        LOG.info(format);
    }

    @Override
    public void onStop(@NonNull Observation.Context context) {
        String format = String.format("Stopping context %s", toString(context));
        LOG.info(format);
    }

}
