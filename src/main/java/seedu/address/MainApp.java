package seedu.address;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.address.appmanager.AppManager;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Version;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.ConfigUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.appsettings.AppSettings;
import seedu.address.model.appsettings.ReadOnlyAppSettings;
import seedu.address.model.globalstatistics.GlobalStatistics;
import seedu.address.model.wordbanklist.ReadOnlyWordBankList;
import seedu.address.model.wordbanklist.WordBankList;
import seedu.address.model.wordbankstatslist.WordBankStatisticsList;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.appsettings.AppSettingsStorage;
import seedu.address.storage.appsettings.JsonAppSettingsStorage;
import seedu.address.storage.globalstatistics.GlobalStatisticsStorage;
import seedu.address.storage.globalstatistics.JsonGlobalStatisticsStorage;
import seedu.address.storage.statistics.JsonWordBankStatisticsListStorage;
import seedu.address.storage.statistics.WordBankStatisticsListStorage;
import seedu.address.storage.userprefs.JsonUserPrefsStorage;
import seedu.address.storage.userprefs.UserPrefsStorage;
import seedu.address.storage.wordbanks.JsonWordBankListStorage;
import seedu.address.storage.wordbanks.WordBankListStorage;
import seedu.address.ui.Ui;
import seedu.address.ui.UiManager;

/*
Step 0.

import seedu.address.Game.*;
 */

/**
 * Runs the application.
 */
public class MainApp extends Application {
    public static final Version VERSION = new Version(0, 6, 0, true);
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;
    protected AppManager appManager;

    /*
    Step 1.
    protected Game game;
     */

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing Dukemon ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        WordBankListStorage wordBankListStorage = new JsonWordBankListStorage(userPrefs.getDataFilePath());
        WordBankStatisticsListStorage wbStatsStorage =
                new JsonWordBankStatisticsListStorage(userPrefs.getDataFilePath());
        GlobalStatisticsStorage globalStatsStorage = new JsonGlobalStatisticsStorage(userPrefs.getDataFilePath());
        AppSettingsStorage appSettingsStorage = new JsonAppSettingsStorage(userPrefs.getAppSettingsFilePath());
        storage = new StorageManager(wordBankListStorage, userPrefsStorage,
                wbStatsStorage, globalStatsStorage, appSettingsStorage);

        initLogging(config);

        /*
        Step 2. Create game here.

        game = initGameManager(storage, userPrefs);
         */

        model = initModelManager(storage, userPrefs);

        /*
        Step 8.
        Pass game to LogicManager

        logic = new LogicManager(game, model, storage);
         */
        logic = new LogicManager(model, storage);

        /*
        Step 9.
        Create GameManager using logic and pass to UIManager.
         */
        appManager = new AppManager(logic);

        /*
        Step 10
        Initialize UIManager using GameManager
         */

        ui = new UiManager(appManager);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s address book and {@code userPrefs}. <br>
     * The data from the sample address book will be used instead if {@code storage}'s address book is not found,
     * or an empty address book will be used instead if errors occur when reading {@code storage}'s address book.
     */

    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        Optional<ReadOnlyWordBankList> optionalWbl = storage.getWordBankList();
        WordBankList wbl = (WordBankList) optionalWbl.get();
        WordBankStatisticsList wbStatsList = storage.getWordBankStatisticsList();
        GlobalStatistics globalStatistics = storage.getGlobalStatistics();
        ReadOnlyAppSettings appSettings = null;
        try {
            Optional<AppSettings> settingsOptional = storage.readAppSettings();
            appSettings = settingsOptional.orElse(new AppSettings());
        } catch (IOException | DataConversionException e) {
            logger.warning("Init model failed. IO exception.");
        }
        return new ModelManager(wbl, wbStatsList, globalStatistics, userPrefs, appSettings);
    }

    /*
    Step 3.
    Extends to Step 4 : storage.readGame()
    Extends to Step 5 : define ReadOnlyGame class;
    Extends to Step 6 : constructor for new Game();
    Extends to Step 7 : constructor for new GameManger;


    private Game initGameManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        ReadOnlyGame initialData;
        Optional<ReadOnlyGame> gameOptional;

        gameOptional = storage.readGame();
        initialData = new Game();
        return new GameManager(initialData, userPrefs);
    }
    */

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@code Config#DEFAULT_CONFIG_FILE} will be used instead
     * if {@code configFilePath} is null.
     */
    protected Config initConfig(Path configFilePath) {
        Config initializedConfig;
        Path configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataConversionException e) {
            logger.warning("Config file at " + configFilePathUsed + " is not in the correct format. "
                    + "Using default config properties");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
     */
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using prefs file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataConversionException e) {
            logger.warning("UserPrefs file at " + prefsFilePath + " is not in the correct format. "
                    + "Using default user prefs");
            initializedPrefs = new UserPrefs();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty AddressBook");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting AddressBook " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Dukemon ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
