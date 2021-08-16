package alt.sim.model.leaderboard;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import alt.sim.model.user.User;
import alt.sim.model.user.UserImpl;

class LeaderboardTest {
    private static final int MAX_USERS = 5;

    private static final User USER_1 = new UserImpl("Luca", 500);
    private static final User USER_2 = new UserImpl("Giacomo", 300);
    private static final User USER_3 = new UserImpl("Paolo", 100);
    private static final User USER_4 = new UserImpl("Daniel", 200);
    private static final User USER_5 = new UserImpl("Marco", 400);

    private static Map<String, Integer> userRecords = new HashMap<>();

    public List<String> getTopFive() {
        return userRecords.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(MAX_USERS)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    @BeforeAll
    static void addToLeaderboard() {
        List<User> users = List.of(USER_1, USER_2, USER_3, USER_4, USER_5);
        users.forEach(user -> userRecords.put(user.getName(), user.getScore()));
    }

    @Test
    void testLeaderboard() {
        List<String> topFive = getTopFive();
        System.out.println(topFive);

        assertEquals(USER_1.getName(), topFive.get(0));
        assertEquals(USER_4.getName(), topFive.get(3));
    }
}
