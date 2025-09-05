package com.example.ssg_tab.domain.category.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CategoryName {
    STOCK("주식"),
    JOBS("취업"),
    REAL_ESTATE("부동산"),
    HOUSING_SUBSCRIPTION("청약"),
    CRYPTO("암호화폐"),
    OVERSEAS_INVESTMENT("해외투자"),
    FINANCE("금융"),
    FUND("펀드"),
    ECONOMIC_SENSE("경제상식");

    private final String displayName;
}
