package com.docpet.animalhospital.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PetTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Pet getPetSample1() {
        return new Pet()
            .id(1L)
            .name("name1")
            .species("species1")
            .breed("breed1")
            .sex("sex1")
            .allergies("allergies1")
            .notes("notes1")
            .imageUrl("imageUrl1");
    }

    public static Pet getPetSample2() {
        return new Pet()
            .id(2L)
            .name("name2")
            .species("species2")
            .breed("breed2")
            .sex("sex2")
            .allergies("allergies2")
            .notes("notes2")
            .imageUrl("imageUrl2");
    }

    public static Pet getPetRandomSampleGenerator() {
        return new Pet()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .species(UUID.randomUUID().toString())
            .breed(UUID.randomUUID().toString())
            .sex(UUID.randomUUID().toString())
            .allergies(UUID.randomUUID().toString())
            .notes(UUID.randomUUID().toString())
            .imageUrl(UUID.randomUUID().toString());
    }
}
