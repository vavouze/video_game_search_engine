package fr.lernejo.fileinjector;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class LauncherTest {

    @Test
    void main_terminates_before_5_sec() throws IOException {
        Launcher.main(new String[]{"src/test/resources/games.json"});
    }

    @Test
    void without_arg() throws IOException {
        String[] args = new String[0];
        Launcher.main(args);
    }
}
