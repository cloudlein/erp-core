package com.learn.erp_core.shared.util;

import com.learn.erp_core.shared.dto.PaginationResponse;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PaginationAssemblerTest {

    @Test
    void shouldAssemblePaginationResponseCorrectly() {
        Page<String> mockPage = mock(Page.class);
        List<String> content = List.of("Item1", "Item2");

        when(mockPage.getNumber()).thenReturn(1);
        when(mockPage.getTotalPages()).thenReturn(5);
        when(mockPage.getTotalElements()).thenReturn(50L);
        when(mockPage.getSize()).thenReturn(10);
        when(mockPage.isLast()).thenReturn(false);

        PaginationResponse<String> response = PaginationAssembler.from(mockPage, content);

        assertEquals(content, response.getContent());
        assertEquals(1, response.getCurrentPage());
        assertEquals(5, response.getTotalPages());
        assertEquals(50L, response.getTotalElements());
        assertEquals(10, response.getSize());
        assertEquals(false, response.isLast());
    }
}
