package lotto;

import camp.nextstep.edu.missionutils.Console;

import java.util.List;

public class Lotto {
    private static int number_of_lotto;
    private final List<Integer> numbers;

    public Lotto(List<Integer> numbers) {
        validate(numbers);
        this.numbers = numbers;
    }

    private void validate(List<Integer> numbers) {
        if (numbers.size() != 6) {
            throw new IllegalArgumentException();
        }
    }

    // TODO: 추가 기능 구현

    private void enterPriceOfLotto(){
        System.out.println("구입금액을 입력해주세요.");
        int price_of_lotto = Integer.parseInt(Console.readLine());
        calculateNumOfLotto(price_of_lotto);
    }

    private void calculateNumOfLotto(int price_of_lotto){
        number_of_lotto = price_of_lotto / 1000;
        System.out.println("\n" + number_of_lotto + "개를 구매했습니다.");
    }
    




}
