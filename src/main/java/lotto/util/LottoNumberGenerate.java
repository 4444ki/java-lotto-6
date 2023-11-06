package lotto.util;

import camp.nextstep.edu.missionutils.Randoms;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lotto.model.Lotto;


public class LottoNumberGenerate {
    private final static int NUMBER_MIN_VALUE = 1;
    private final static int NUMBER_MAX_VALUE = 45;
    private final static int NUMBER_PICK_VALUE = 6;
    private LottoNumberGenerate() {}

    public static List<Lotto> generateLottos(int tryNumber){
        List<Lotto> lottos = new ArrayList<>();
        for(int i =0;i<tryNumber;i++){
            Lotto lotto = new Lotto(sortNumber(generateLottoNumber()));
            lottos.add(lotto);
        }
        return lottos;
    }
    private static List<Integer> generateLottoNumber(){
        return Randoms.pickUniqueNumbersInRange(NUMBER_MIN_VALUE, NUMBER_MAX_VALUE, NUMBER_PICK_VALUE);
    }
    private static List<Integer> sortNumber(List<Integer> numbers) {
        Collections.sort(numbers);
        return numbers;
    }
}
