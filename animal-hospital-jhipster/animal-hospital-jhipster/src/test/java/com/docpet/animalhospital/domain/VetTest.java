package com.docpet.animalhospital.domain;

import static com.docpet.animalhospital.domain.VetTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.docpet.animalhospital.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VetTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vet.class);
        Vet vet1 = getVetSample1();
        Vet vet2 = new Vet();
        assertThat(vet1).isNotEqualTo(vet2);

        vet2.setId(vet1.getId());
        assertThat(vet1).isEqualTo(vet2);

        vet2 = getVetSample2();
        assertThat(vet1).isNotEqualTo(vet2);
    }
}
