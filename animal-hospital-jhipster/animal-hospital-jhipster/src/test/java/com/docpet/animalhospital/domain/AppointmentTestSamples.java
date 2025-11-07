package com.docpet.animalhospital.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AppointmentTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Appointment getAppointmentSample1() {
        return new Appointment().id(1L).type("type1").status("status1").notes("notes1");
    }

    public static Appointment getAppointmentSample2() {
        return new Appointment().id(2L).type("type2").status("status2").notes("notes2");
    }

    public static Appointment getAppointmentRandomSampleGenerator() {
        return new Appointment()
            .id(longCount.incrementAndGet())
            .type(UUID.randomUUID().toString())
            .status(UUID.randomUUID().toString())
            .notes(UUID.randomUUID().toString());
    }
}
