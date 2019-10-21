package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.globalstatistics.GlobalStatistics;
import seedu.address.model.wordbank.ReadOnlyWordBank;
import seedu.address.statistics.WordBankStatistics;
import seedu.address.storage.globalstatistics.GlobalStatisticsStorage;
import seedu.address.storage.statistics.WordBankStatisticsStorage;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private AddressBookStorage addressBookStorage;
    private UserPrefsStorage userPrefsStorage;
    private WordBankStatisticsStorage wbStatsStorage;
    private GlobalStatisticsStorage globalStatsStorage;

    public StorageManager(AddressBookStorage addressBookStorage, UserPrefsStorage userPrefsStorage,
                          WordBankStatisticsStorage wbStatsStorage, GlobalStatisticsStorage globalStatsStorage) {
        super();
        this.addressBookStorage = addressBookStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.wbStatsStorage = wbStatsStorage;
        this.globalStatsStorage = globalStatsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ AddressBook methods ==============================

    @Override
    public Path getAddressBookFilePath() {
        return addressBookStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyWordBank> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyWordBank> readAddressBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return addressBookStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyWordBank addressBook) throws IOException {
        saveAddressBook(addressBook, addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyWordBank addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        addressBookStorage.saveAddressBook(addressBook, filePath);
    }

    // ================ WordBankStatistics methods ==============================

    @Override
    public Optional<WordBankStatistics> readWordBankStatistics() throws DataConversionException, IOException {
        return readWordBankStatistics(wbStatsStorage.getWordBankStatisticsFilePath());
    }

    @Override
    public Optional<WordBankStatistics> readWordBankStatistics(Path filePath)
            throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return wbStatsStorage.readWordBankStatistics(filePath);
    }

    @Override
    public void saveWordBankStatistics(WordBankStatistics wbStats) throws IOException {
        saveWordBankStatistics(wbStats, wbStatsStorage.getWordBankStatisticsFilePath());
    }

    @Override
    public void saveWordBankStatistics(WordBankStatistics wbStats, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        wbStatsStorage.saveWordBankStatistics(wbStats, filePath);
    }

    @Override
    public Path getWordBankStatisticsFilePath() {
        return wbStatsStorage.getWordBankStatisticsFilePath();
    }

    // ================ GlobalStatistics methods ==============================
    @Override
    public Optional<GlobalStatistics> readGlobalStatistics() throws DataConversionException, IOException {
        return readGlobalStatistics(globalStatsStorage.getGlobalStatisticsFilePath());
    }

    @Override
    public Optional<GlobalStatistics> readGlobalStatistics(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return globalStatsStorage.readGlobalStatistics(filePath);
    }

    @Override
    public void saveGlobalStatistics(GlobalStatistics globalStatistics) throws IOException {
        saveGlobalStatistics(globalStatistics, globalStatsStorage.getGlobalStatisticsFilePath());
    }

    @Override
    public void saveGlobalStatistics(GlobalStatistics globalStatistics, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        globalStatsStorage.saveGlobalStatistics(globalStatistics, filePath);
    }

    @Override
    public Path getGlobalStatisticsFilePath() {
        return globalStatsStorage.getGlobalStatisticsFilePath();
    }


    // ================ static methods ==============================
    /**
     * Get the path to the wordbank statistics given the path to wordbank
     * e.g. getWbStatsStoragePath("data/pokemon.json") == "data/wbstats/pokemon.json"
     * @param wbPath The path of the wordbank
     */
    public static Path getWbStatsStoragePath(Path wbPath) {
        return Path.of(wbPath.getParent().toString(), "wbstats", wbPath.getFileName().toString());
    }
}
