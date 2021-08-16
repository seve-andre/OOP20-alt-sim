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
    private static final User user1 = new UserImpl("Luca", 500);
    private static final User user2 = new UserImpl("Giacomo", 300);
    private static final User user3 = new UserImpl("Paolo", 100);
    private static final User user4 = new UserImpl("Daniel", 200);
    private static final User user5 = new UserImpl("Marco", 400);

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
        List<User> users = List.of(user1, user2, user3, user4, user5);
        users.forEach(user -> userRecords.put(user.getName(), user.getScore()));
    }

    @Test
    void testLeaderboard() {
        List<String> topFive = getTopFive();
        System.out.println(topFive);

        assertEquals(user1.getName(), topFive.get(0));
        assertEquals(user4.getName(), topFive.get(3));
    }
}