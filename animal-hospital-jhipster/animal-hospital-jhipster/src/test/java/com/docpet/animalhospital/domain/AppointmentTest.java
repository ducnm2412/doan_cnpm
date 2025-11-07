package com.docpet.animalhospital.domain;

import static com.docpet.animalhospital.domain.AppointmentTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.docpet.animalhospital.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AppointmentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Appointment.class);
        Appointment appointment1 = getAppointmentSample1();
        Appointment appointment2 = new Appointment();
        assertThat(appointment1).isNotEqualTo(appointment2);

        appointment2.setId(appointment1.getId());
        assertThat(appointment1).isEqualTo(appointment2);

        appointment2 = getAppointmentSample2();
        assertThat(appointment1).isNotEqualTo(appointment2);
    }
}
