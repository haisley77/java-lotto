package lotto;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;

import java.util.*;


public class Lotto {
    enum statistics {
        prize1(6, 4,"2,000,000,000"),
        prize2(5, 3,"30,000,000"),
        prize3(5, 2,"1,500,000"),
        prize4(4,1,"50,000"),
        prize5(3,0,"5,000"),
        noprize(0,-1,"0");

        // 상금
        private String money;
        // 일치하는 번호 개수
        private int count;
        // 통계를 저장할 통계 리스트 인덱스
        private int index;

        statistics( int count,int index, String money){

            this.money = money;
            this.count = count;
            this.index = index;
        }
    }
    private static int number_of_lotto;
    private static List<List<Integer>> purchased_lotto_numbers;
    public static int bonus_number = 0;
    private static List<statistics> win_lotto;
    private static int[] status_of_win = {0,0,0,0,0};
    private static double rate_of_earning;

    private final List<Integer> numbers;

    public Lotto(List<Integer> numbers) {
        validate(numbers);
        this.numbers = numbers;
    }

    private void validate(List<Integer> numbers) {

        if (numbers.size() != 6) {
            System.out.println("[ERROR] 유효한 로또 번호를 입력해야 합니다.");
            throw new IllegalArgumentException();
        }
        Set<Integer> set = new HashSet<>(numbers);
        if (set.size()!=numbers.size()){
            System.out.println("[ERROR] 로또 번호는 중복된 숫자가 없어야 합니다.");
            throw new IllegalArgumentException();
        }


    }

    // TODO: 추가 기능 구현

    public static void enterPriceOfLotto() {
        System.out.println("구입금액을 입력해주세요.");
        String price = Console.readLine();
        try {
            calculateNumOfLotto(Integer.parseInt(price));
        } catch (NumberFormatException e){
            System.out.println("[ERROR] 문자열을 포함하지 않은 숫자를 입력해야 합니다.");
            throw new IllegalArgumentException();
        }


    }

    public static void calculateNumOfLotto(int price_of_lotto) {
        number_of_lotto = price_of_lotto / 1000;
        System.out.println();
        System.out.println(number_of_lotto + "개를 구매했습니다.");
    }

    public static void createRandomNumbers() {
        purchased_lotto_numbers = new ArrayList<>(number_of_lotto);
        for (int count = 0; count < number_of_lotto; count++) {
            List<Integer> lotto = Randoms.pickUniqueNumbersInRange(1,45,6);
            purchased_lotto_numbers.add(lotto);
        }
    }

    public static void printPurchasedLotto() {
        for (int lotto = 0; lotto < purchased_lotto_numbers.size(); lotto++){
            List<Integer> arr = new ArrayList<Integer>();
            for (Integer i:purchased_lotto_numbers.get(lotto)){
                arr.add(i);
            }
            Collections.sort(arr);
            System.out.println(arr);
        }
    }


    public void enterBonusNumber() {
        System.out.println();
        System.out.println("보너스 번호를 입력해 주세요.");
        bonus_number = Integer.parseInt(Console.readLine());

        if (bonus_number>45 || bonus_number<1) {
            System.out.println("[ERROR] 1~45 숫자 범위의 수를 입력해야 합니다.");
            throw new IllegalArgumentException();
        }
        if (numbers.contains(bonus_number)){
            System.out.println("[ERROR] 당첨 번호가 아닌 보너스 번호를 입력해야 합니다.");
            throw new IllegalArgumentException();
        }
    }





    public void calculatePrize() {
        win_lotto = new ArrayList<statistics>(number_of_lotto);

        int count = 0;
        for (int i = 0;i<number_of_lotto;i++){
            count = 0;
            for (int j = 0; j < 6;j++){
                if (numbers.contains(purchased_lotto_numbers.get(i).get(j))){
                    count++;
                }
            }
            setPrize(count, purchased_lotto_numbers.get(i));

            int index = win_lotto.get(i).index;
            if (index != -1){
                // 로또 당첨 통계를 계산해 status_of_win 리스트에 저장
                status_of_win[index] = status_of_win[index] + 1;
            }
        }
    }

    public void setPrize(int count, List<Integer> my_lotto) {
        if (count==5){
            if (my_lotto.contains(bonus_number)){
                win_lotto.add(statistics.prize2);
            }
            if (!my_lotto.contains(bonus_number)){
                win_lotto.add(statistics.prize3);
            }
        }
        if (count == 6){
            win_lotto.add(statistics.prize1);
        }
        if (count == 4){
            win_lotto.add(statistics.prize4);
        }
        if (count == 3){
            win_lotto.add(statistics.prize5);
        }
        if (count <=2 || count >= 7){
            win_lotto.add(statistics.noprize);
        }

    }


    public void printStatisticsForLotto(){
        System.out.println("\n당첨 통계");
        System.out.println("---");

        System.out.println(statistics.prize5.count + "개 일치 (5,000원) - "+status_of_win[0]+"개");
        System.out.println(statistics.prize4.count + "개 일치 (50,000원) - "+status_of_win[1]+"개");
        System.out.println(statistics.prize3.count + "개 일치 (1,500,000원) - "+status_of_win[2]+"개");
        System.out.println(statistics.prize2.count + "개 일치, 보너스 볼 일치 (30,000,000원) - "+status_of_win[3]+"개");
        System.out.println(statistics.prize1.count + "개 일치 (2,000,000,000원) - "+status_of_win[4]+"개");
    }

    public void calculateRateOfEarning(){
        double total_money = 0.0;

        total_money += status_of_win[0]*5000 + status_of_win[1]*50000 + status_of_win[2]*1500000 + status_of_win[3]*30000000 + status_of_win[4]*2000000000;


       rate_of_earning = (total_money / (number_of_lotto * 1000.0)) * 100.0;
    }

    public void printRateOfEarning() {
        System.out.println("총 수익률은 "+String.format("%.1f",rate_of_earning)+"%입니다.");
    }

}
