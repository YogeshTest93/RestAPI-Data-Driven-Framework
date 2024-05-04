package Base;

import Utils.ExcelUtils;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;

public class base {

    protected static final String BASE_URL = "https://api.openweathermap.org/";
    protected static final String API_KEY = "5b279c18979f93726bf60b8af25c96da";
    protected static final String EXCEL_PATH = "C:/Users/yoges/eclipse-workspace/APIAutomation/src/main/java/TestData/WeatherAPI_TestData.xlsx";
    protected static final String SHEET_NAME = "Sheet";

    protected static ExcelUtils excel;

    static {
        excel = new ExcelUtils(EXCEL_PATH, SHEET_NAME);
    }

    protected static String getApiKey() {
        return API_KEY;
    }

    protected static String getBaseUrl() {
        return BASE_URL;
    }

    @DataProvider(name = "cityData")
    public static Object[][] getCityData(Method testMethod) {
        int rowIndex = 0;
        String methodName = testMethod.getName();
        switch (methodName) {
            case "testGetCurrentWeatherForCity":
                rowIndex = 0;
                break;
            case "testGet5DayForecastForCity":
                rowIndex = 1;
                break;
            case "testInvalidCityName":
                rowIndex = 2;
                break;
            case "testInvalidAPIKey":
                rowIndex = 3;
                break;
        }
        String cityName = excel.getCellData(rowIndex, 0);
        return new Object[][]{{cityName}};
    }
}
