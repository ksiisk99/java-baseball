package baseball.game.service;

import baseball.game.domain.Computer;
import baseball.game.domain.User;
import baseball.game.dto.BallCount;
import baseball.utils.InputValidator;
import baseball.utils.NumberUtil;

import java.util.List;

public class GameService {
    private Computer computer;
    private User user;

    public GameService() {
        computer = new Computer();
        user = new User();
    }

    public void computeComputerNumbers() {
        computer.setNumberList(NumberUtil.getRandomNumberList());
    }

    public void setUserNumbers(String numbers) {
        if (!InputValidator.isValidNumbers(numbers)) {
            throw new IllegalArgumentException();
        }

        user.setNumberList(NumberUtil.getNumberList(numbers));
    }

    public BallCount computeBallCount() {
        List<Integer> computerNumbers = computer.getNumberList();
        List<Integer> userNumbers = user.getNumberList();

        int numberOfStrike = computeNumberOfStrike(computerNumbers, userNumbers);
        int numberOfBall = computeNumberOfBall(numberOfStrike, computerNumbers, userNumbers);
        //System.out.println(numberOfStrike + " " + numberOfBall);

        return new BallCount(numberOfStrike, numberOfBall);
    }

    private int computeNumberOfBall(int numberOfStrike, List<Integer> computerNumbers, List<Integer> userNumbers) {
        int sameNumberCount = (int) computerNumbers.stream()
                .filter(n -> userNumbers.contains(n))
                .count();

        return sameNumberCount - numberOfStrike;
    }

    private int computeNumberOfStrike(List<Integer> computerNumbers, List<Integer> userNumbers) {
        int numberOfStrike = 0;

        for (int i = 0; i < computerNumbers.size(); i++) {
            if (isStrike(computerNumbers.get(i), userNumbers.get(i))) {
                numberOfStrike++;
            }
        }
        return numberOfStrike;
    }

    private boolean isStrike(int computerNumber, int userNumber) {
        if (computerNumber == userNumber) {
            return true;
        }
        return false;
    }

}
