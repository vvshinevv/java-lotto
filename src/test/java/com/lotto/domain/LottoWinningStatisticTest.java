package com.lotto.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LottoWinningStatisticTest {

    @DisplayName("로또 당첨 통계 구하는 테스트")
    @ParameterizedTest
    @MethodSource("provideExtractedAndWinningLottoNumbers")
    void calculateLottoWinningStaticsTest(
            final List<Lotto> extractedLotto,
            final LottoWinningNumbers lottoWinningNumbers,
            final Map<LottoWinningType, Integer> expected,
            final Integer bonusBallNumber
    ) {

        LottoWinningStatistic lottoWinningStatistic = new LottoWinningStatistic();

        for (Lotto lotto : extractedLotto) {
            lottoWinningStatistic.calculateLottoWinningStatics(lotto, lottoWinningNumbers, new LottoBonusBall(bonusBallNumber));
        }
        Map<LottoWinningType, Integer> result = lottoWinningStatistic.getLottoWinningList();
        assertEquals(result, expected);
    }

    private static Stream<Arguments> provideExtractedAndWinningLottoNumbers() {
        return Stream.of(
                Arguments.of(provideExtractedLotto(), LottoWinningNumbers.manipulateInputWinningLottoNumbers("1, 2, 3, 4, 5, 6"), expectedValue1(), 0),
                Arguments.of(provideExtractedLotto(), LottoWinningNumbers.manipulateInputWinningLottoNumbers("2, 4, 6, 8, 10, 19"), expectedValue2(), 0),
                Arguments.of(provideExtractedLotto(), LottoWinningNumbers.manipulateInputWinningLottoNumbers("1, 2, 3, 4, 11, 13"), expectedValue3(), 0),
                Arguments.of(provideExtractedLotto(), LottoWinningNumbers.manipulateInputWinningLottoNumbers("2, 6, 8, 11, 13, 18"), expectedValue4(), 0),
                Arguments.of(provideExtractedLotto(), LottoWinningNumbers.manipulateInputWinningLottoNumbers("3, 5, 7, 9, 11, 18"), expectedValue5(), 13)
        );
    }

    private static List<Lotto> provideExtractedLotto() {
        List<Lotto> extractedLotto = new ArrayList<>();

        Lotto lotto1 = new Lotto(Arrays.asList(1, 2, 3, 4, 5, 6), 0);
        extractedLotto.add(lotto1);

        Lotto lotto2 = new Lotto(Arrays.asList(2, 4, 6, 8, 10, 12), 0);
        extractedLotto.add(lotto2);

        Lotto lotto3 = new Lotto(Arrays.asList(3, 5, 7, 9, 11, 13), 0);
        extractedLotto.add(lotto3);

        return extractedLotto;
    }

    private static Map<LottoWinningType, Integer> expectedValue1() {
        Map<LottoWinningType, Integer> expected = new TreeMap<>();
        expected.put(LottoWinningType.FIRST_CLASS, 1);
        expected.put(LottoWinningType.BONUS_BALL_CLASS, 0);
        expected.put(LottoWinningType.SECOND_CLASS, 0);
        expected.put(LottoWinningType.THIRD_CLASS, 0);
        expected.put(LottoWinningType.FORTH_CLASS, 1);

        return expected;
    }

    private static Map<LottoWinningType, Integer> expectedValue2() {
        Map<LottoWinningType, Integer> expected = new TreeMap<>();
        expected.put(LottoWinningType.FIRST_CLASS, 0);
        expected.put(LottoWinningType.BONUS_BALL_CLASS, 0);
        expected.put(LottoWinningType.SECOND_CLASS, 1);
        expected.put(LottoWinningType.THIRD_CLASS, 0);
        expected.put(LottoWinningType.FORTH_CLASS, 1);

        return expected;
    }

    private static Map<LottoWinningType, Integer> expectedValue3() {
        Map<LottoWinningType, Integer> expected = new TreeMap<>();
        expected.put(LottoWinningType.FIRST_CLASS, 0);
        expected.put(LottoWinningType.BONUS_BALL_CLASS, 0);
        expected.put(LottoWinningType.SECOND_CLASS, 0);
        expected.put(LottoWinningType.THIRD_CLASS, 1);
        expected.put(LottoWinningType.FORTH_CLASS, 1);

        return expected;
    }

    private static Map<LottoWinningType, Integer> expectedValue4() {
        Map<LottoWinningType, Integer> expected = new TreeMap<>();
        expected.put(LottoWinningType.FIRST_CLASS, 0);
        expected.put(LottoWinningType.BONUS_BALL_CLASS, 0);
        expected.put(LottoWinningType.SECOND_CLASS, 0);
        expected.put(LottoWinningType.THIRD_CLASS, 0);
        expected.put(LottoWinningType.FORTH_CLASS, 1);

        return expected;
    }

    private static Map<LottoWinningType, Integer> expectedValue5() {
        Map<LottoWinningType, Integer> expected = new TreeMap<>();
        expected.put(LottoWinningType.FIRST_CLASS, 0);
        expected.put(LottoWinningType.BONUS_BALL_CLASS, 1);
        expected.put(LottoWinningType.SECOND_CLASS, 0);
        expected.put(LottoWinningType.THIRD_CLASS, 0);
        expected.put(LottoWinningType.FORTH_CLASS, 0);

        return expected;
    }

    @DisplayName("로또 당첨 수익률 구하는 테스트")
    @ParameterizedTest
    @MethodSource("provideExtractedAndWinningLottoNumbersForRateOfReturn")
    void getLottoRateOfReturnTest(final List<Lotto> extractedLotto, final LottoWinningNumbers lottoWinningNumbers, final Double expected) {
        LottoWinningStatistic lottoWinningStatistic = new LottoWinningStatistic();
        for (Lotto lotto : extractedLotto) {
            lottoWinningStatistic.calculateLottoWinningStatics(lotto, lottoWinningNumbers, new LottoBonusBall(0));
        }

        Double result = lottoWinningStatistic.getLottoRateOfReturn(extractedLotto.size());
        BigDecimal bigDecimal = new BigDecimal(result);
        assertEquals(bigDecimal.setScale(2, BigDecimal.ROUND_FLOOR).doubleValue(), expected);
    }

    private static Stream<Arguments> provideExtractedAndWinningLottoNumbersForRateOfReturn() {
        return Stream.of(
                Arguments.of(provideExtractedLotto(), LottoWinningNumbers.manipulateInputWinningLottoNumbers("1, 2, 3, 4, 5, 6"), 666668.33),
                Arguments.of(provideExtractedLotto(), LottoWinningNumbers.manipulateInputWinningLottoNumbers("2, 4, 6, 8, 10, 19"), 501.66),
                Arguments.of(provideExtractedLotto(), LottoWinningNumbers.manipulateInputWinningLottoNumbers("1, 2, 3, 4, 11, 13"), 18.33),
                Arguments.of(provideExtractedLotto(), LottoWinningNumbers.manipulateInputWinningLottoNumbers("2, 6, 8, 11, 13, 18"), 1.66)
        );
    }

}
