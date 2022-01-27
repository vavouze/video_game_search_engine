package fr.lernejo.fileinjector;

import org.junit.jupiter.api.Test;

class LauncherTest {

    @Test
    void main_terminates_before_5_sec() {
        Launcher.main(new String[]{"src/test/resources/games.json"});
    }
}
