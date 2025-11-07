package com.docpet.animalhospital.service.mapper;

import static com.docpet.animalhospital.domain.VetAsserts.*;
import static com.docpet.animalhospital.domain.VetTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VetMapperTest {

    private VetMapper vetMapper;

    @BeforeEach
    void setUp() {
        vetMapper = new VetMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getVetSample1();
        var actual = vetMapper.toEntity(vetMapper.toDto(expected));
        assertVetAllPropertiesEquals(expected, actual);
    }
}
