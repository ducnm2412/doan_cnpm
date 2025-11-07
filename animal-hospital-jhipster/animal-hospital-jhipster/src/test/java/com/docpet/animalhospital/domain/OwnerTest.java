package com.docpet.animalhospital.domain;

import static com.docpet.animalhospital.domain.OwnerTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.docpet.animalhospital.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OwnerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Owner.class);
        Owner owner1 = getOwnerSample1();
        Owner owner2 = new Owner();
        assertThat(owner1).isNotEqualTo(owner2);

        owner2.setId(owner1.getId());
        assertThat(owner1).isEqualTo(owner2);

        owner2 = getOwnerSample2();
        assertThat(owner1).isNotEqualTo(owner2);
    }
}
