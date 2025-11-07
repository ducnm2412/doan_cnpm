package com.docpet.animalhospital.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class OwnerTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Owner getOwnerSample1() {
        return new Owner().id(1L).name("name1").phone("phone1").address("address1");
    }

    public static Owner getOwnerSample2() {
        return new Owner().id(2L).name("name2").phone("phone2").address("address2");
    }

    public static Owner getOwnerRandomSampleGenerator() {
        return new Owner()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .phone(UUID.randomUUID().toString())
            .address(UUID.randomUUID().toString());
    }
}
