package lotto.controller;

import java.util.List;
import lotto.model.Judgement;
import lotto.model.Lottos;
import lotto.model.Money;
import lotto.model.WinningLotto;
import lotto.util.WinningLottoGenerator;
import lotto.validator.NumberValidator;
import lotto.view.InputView;
import lotto.view.OutputView;

public class LottoController {
    private final InputView inputView;
    private final OutputView outputView;
    private final Judgement judgement;

    public LottoController(InputView inputView, OutputView outputView, Judgement judgement) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.judgement = judgement;
    }

    public void run() {
        Money money = getMoneyFromUser();
        Lottos lottos = new Lottos(money.getTryNumber());
        displayBuyLottos(lottos, money.getTryNumber());
        WinningLotto winningLotto = getWinningLottoNumber();
        addBonusNumber(winningLotto, getBonusNumber());
        judgement.checkLottoNumber(lottos, winningLotto);
        displayWinningCount(lottos, money);
    }

    private Money getMoneyFromUser() {
        try {
            String input = requestMoneyFromUser();
            return new Money(input);
        } catch (IllegalArgumentException e) {
            outputView.displayERRORMESSAGE(e.getMessage());
            return getMoneyFromUser();
        }
    }

    private String requestMoneyFromUser() {
        outputView.displayRequestMoney();
        return inputView.requestMoneyFromUser();
    }

    private void displayBuyLottos(Lottos lottos, int tryNumber) {
        outputView.displayBuyLottoCount(lottos, tryNumber);
        System.out.println();
    }

    private WinningLotto getWinningLottoNumber() {
        try {
            String WinningNumberFromUser = requestWinningNumberFromUser();
            return new WinningLotto(WinningLottoGenerator.generateLottoNumber(WinningNumberFromUser));
        } catch (IllegalArgumentException e) {
            outputView.displayERRORMESSAGE(e.getMessage());
            return getWinningLottoNumber();
        }

    }

    private String requestWinningNumberFromUser() {
        outputView.displayRequestWinningNumber();
        return inputView.requestWinningLottoFromUser();
    }

    private void addBonusNumber(WinningLotto winningLotto, int bonus_number) {
        try {
            winningLotto.addBonusNumber(bonus_number);
        } catch (IllegalArgumentException e) {
            outputView.displayERRORMESSAGE(e.getMessage());
            winningLotto.removeBonusNumber();
            addBonusNumber(winningLotto, getBonusNumber());
        }
    }

    private int getBonusNumber() {
        try {
            String BonusNumberFromUser = requestBonusNumberFromUser();
            return NumberValidator.validate(BonusNumberFromUser);
        } catch (IllegalArgumentException e) {
            outputView.displayERRORMESSAGE(e.getMessage());
            return getBonusNumber();
        }
    }

    private String requestBonusNumberFromUser() {
        outputView.displayRequestBonusNumber();
        return inputView.requestBonusNumberFromUser();
    }

    private void displayWinningCount(Lottos lottos, Money money) {
        outputView.displayResult();
        List<Integer> ranks = judgement.judgeAllLottoRank(lottos);
        outputView.displayWinningLotto(ranks);
        float earningRate = judgement.calculateEarningRate(ranks, money);
        outputView.displayEarningRate(earningRate);
    }

}
