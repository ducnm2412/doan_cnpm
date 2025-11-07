package com.docpet.animalhospital.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class VetTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Vet getVetSample1() {
        return new Vet().id(1L).licenseNo("licenseNo1").specialization("specialization1");
    }

    public static Vet getVetSample2() {
        return new Vet().id(2L).licenseNo("licenseNo2").specialization("specialization2");
    }

    public static Vet getVetRandomSampleGenerator() {
        return new Vet()
            .id(longCount.incrementAndGet())
            .licenseNo(UUID.randomUUID().toString())
            .specialization(UUID.randomUUID().toString());
    }
}
