package com.lotto.helper;

public class LottoViewHelper {

    private static final Integer PRIZE_OF_LOTTO = 1000;

    public static Integer getCountOfLotto(Integer depositMoney) {
        return depositMoney / PRIZE_OF_LOTTO;
    }
}
