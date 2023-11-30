package aoc;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.net.HttpHeaders;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Fetches puzzle inputs using an in-memory cache, the local disk, or the Advent of Code site.
 */
@Slf4j
public class PuzzleInputFetcher {
    public static final String PUZZLE = "puzzle";
    public static final String URL = "https://adventofcode.com/";

    private final Map<String, String> _cache = new ConcurrentHashMap<>();
    private final OkHttpClient _httpClient = new OkHttpClient();
    private final Path _puzzleStorePath;
    private final Path _sessionTokenPath;

    private String _sessionToken;

    public PuzzleInputFetcher(String year) {
        // cookie.txt is a file containing the session token
         this(Path.of(PUZZLE+"/"+year), Path.of("cookie.txt"));
    }

    @VisibleForTesting
    PuzzleInputFetcher(Path puzzleStorePath, Path sessionTokenPath) {
        _puzzleStorePath = puzzleStorePath;
        _sessionTokenPath = sessionTokenPath;
    }

    /**
     * Fetches the given day's puzzle input. It will try to fetch first from the in-memory cache, then the local disk,
     * and finally the Advent of Code site (storing the input to the local disk to avoid further fetches).
     *
     * @param day the puzzle's day
     * @return the puzzle input
     * @throws RuntimeException if the puzzle cannot be fetched
     */
    public String getPuzzleInput(int year, int day) {
        return _cache.computeIfAbsent(getKey(year, day), s -> {
            try {
                try {
                    log.info("Fetching puzzle input from local store for day {}", day);
                    return fetchLocalPuzzleInput(year,day);
                } catch (IOException e) {
                    log.warn("Unable to fetch puzzle input from local store for year {} day {}",year, day, e);
                }

                String input;
                try {
                    log.info("Fetching puzzle input from remote store for day {}", day);
                    input = fetchRemotePuzzleInput(year,day);
                } catch (IOException e) {
                    log.error("Unable to fetch puzzle input from remote store for year {}  day {}",year, day, e);
                    throw e;
                }

                try {
                    log.info("Storing puzzle input locally for day {}", day);
                    storePuzzleInputLocally(year, day, input);
                } catch (IOException e) {
                    log.warn("Unable to store puzzle input locally for year {}  day {}",year, day, e);
                }
                return input;
            } catch (IOException e) {
                throw new RuntimeException("Couldn't get puzzle input for year " + year + " day " + day, e);
            }
        });
    }

    private static String getKey(int year, int day) {

        return year + "/" + day;
    }


    @VisibleForTesting
    String fetchLocalPuzzleInput(int year, int day) throws IOException {
        log.info("Fetching puzzle input from disk for day {} year {}", day , year);
        return Files.readString(_puzzleStorePath.resolve(getDayInput(day)));
    }

    @VisibleForTesting
    void storePuzzleInputLocally(int year, int day, String puzzleInput) throws IOException {
        log.info("Storing puzzle input on disk for day {}", day);
        Files.createDirectories(_puzzleStorePath);
        var path = _puzzleStorePath.resolve(getDayInput(day));
        Files.writeString(path, puzzleInput);
    }

    @NotNull
    private static String getDayInput(int day) {
        return day + "/input.txt";
    }

    @VisibleForTesting
    String fetchRemotePuzzleInput(int year,int day) throws IOException {
        log.info("Fetching puzzle input from Advent of Code for year {} day {} ", year, day);
        HttpUrl remotePuzzleInputUrl = getRemotePuzzleInputUrl(year, day);
        log.info("Fetching url {}", remotePuzzleInputUrl);
        var request = new Request.Builder()
                .url(remotePuzzleInputUrl)
                .header(HttpHeaders.COOKIE, "session=" + getSessionToken())
                .get()
                .build();
        try (var response = _httpClient.newCall(request).execute()) {
            if (response.code() != 200) {
                throw new IOException("Request was not successful. Status code = " + response.code() +" body = "+response.body().string());
            }
            var body = response.body();
            if (body == null) {
                throw new IOException("Request body was empty");
            }
            return body.string();
        }
    }

    @VisibleForTesting
    HttpUrl getRemotePuzzleInputUrl(int year, int day) {
        // for 2, should be 02
        //String formatedDay = String.format("%02d", day);
        return HttpUrl.get(URL + year +"/day/" + day + "/input");
    }

    @VisibleForTesting
    synchronized String getSessionToken() throws IOException {
        try {
            if (_sessionToken == null) {
                // read the session token from cookie.txt, cookie.txt in the root of the project
                 _sessionToken = Files.readString(_sessionTokenPath).trim();
                //Paths.get(getClass().getResource(_sessionTokenPath).toURI()).toString();
                if (_sessionToken.isEmpty()) {
                    throw new IOException("Couldn't get session data from cookie.txt");
                }
            }
            return _sessionToken;
        } catch (IOException e) {
            throw new IOException("Couldn't get session data from cookie.txt", e);
        }
    }
}