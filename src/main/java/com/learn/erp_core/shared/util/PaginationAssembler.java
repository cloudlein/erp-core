package com.learn.erp_core.shared.util;

import com.learn.erp_core.shared.dto.PaginationResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public class PaginationAssembler {
    public static <T, D> PaginationResponse<D> from(
            Page<T> page,
            List<D> content
    ) {
        return PaginationResponse.<D>builder()
                .content(content)
                .currentPage(page.getNumber())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .size(page.getSize())
                .last(page.isLast())
                .build();
    }
}
