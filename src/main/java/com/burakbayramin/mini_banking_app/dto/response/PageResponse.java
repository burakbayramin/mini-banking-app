package com.burakbayramin.mini_banking_app.dto.response;

import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageResponse<T> {

    /**
     * Sayfadaki içerik listesi
     */
    private List<T> content;

    /**
     * Mevcut sayfa numarası
     */
    private int page;

    /**
     * Sayfa başına öğe sayısı
     */
    private int size;

    /**
     * Toplam öğe sayısı
     */
    private long totalElements;

    /**
     * Toplam sayfa sayısı
     */
    private int totalPages;

    /**
     * Son sayfa olup olmadığını belirten bayrak
     */
    private boolean last;

    /**
     * Page nesnesinden PagedResponse oluşturur
     *
     * @param pageData Page nesnesi
     * @param <T>      İçerik tipi
     * @return PagedResponse nesnesi
     */
    public static <T> PageResponse<T> fromPage(Page<T> pageData) {
        return PageResponse.<T>builder()
                .content(pageData.getContent())
                .page(pageData.getNumber())
                .size(pageData.getSize())
                .totalElements(pageData.getTotalElements())
                .totalPages(pageData.getTotalPages())
                .last(pageData.isLast())
                .build();
    }
}
