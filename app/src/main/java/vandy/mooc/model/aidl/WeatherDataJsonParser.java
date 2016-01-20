package vandy.mooc.model.aidl;

import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import vandy.mooc.model.aidl.WeatherData.Main;
import vandy.mooc.model.aidl.WeatherData.Sys;
import vandy.mooc.model.aidl.WeatherData.Weather;
import vandy.mooc.model.aidl.WeatherData.Wind;

/**
 * Parses the Json weather data returned from the Weather Services API
 * and returns a List of WeatherData objects that contain this data.
 */
public class WeatherDataJsonParser {
    /**
     * Used for logging purposes.
     */
    private final String TAG =
        this.getClass().getCanonicalName();

    /**
     * Parse the @a inputStream and convert it into a List of JsonWeather
     * objects.
     */
    public List<WeatherData> parseJsonStream(InputStream inputStream)
        throws IOException {

        // TODO -- DONE
        // Creating reader for input stream
        try (JsonReader reader =
                     new JsonReader(
                             new InputStreamReader(inputStream,
                                                StandardCharsets.UTF_8)))
        {
            return parseJsonWeatherDataArray(reader);
        }

    }

    /**
     * Parse a Json stream and convert it into a List of WeatherData
     * objects.
     */
    public List<WeatherData> parseJsonWeatherDataArray(JsonReader reader)
        throws IOException {
        // TODO -- DONE
        // is json object empty?
        if (reader.peek() == JsonToken.END_ARRAY) {
            Log.d(TAG, "Result array is empty");
            return null;
        }

        final List<WeatherData> weatherList =
                new ArrayList<>();

        // Assume that response hat only one entity
        weatherList.add(parseJsonWeatherData(reader));

        return weatherList;
    }

    /**
     * Parse a Json stream and return a WeatherData object.
     */
    public WeatherData parseJsonWeatherData(JsonReader reader) 
        throws IOException {

        // TODO -- DONE
        reader.beginObject();

        final WeatherData weatherData = new WeatherData();

        while (reader.hasNext()) {
            final String nodeName = reader.nextName();
            Log.d("NodeName:", nodeName);
            switch (nodeName) {
                case WeatherData.name_JSON:
                    weatherData.setName(reader.nextString());
                    break;
                case WeatherData.dt_JSON:
                    weatherData.setDate(reader.nextLong());
                    break;
                case WeatherData.cod_JSON:
                    weatherData.setCod(reader.nextLong());
                    break;
                case WeatherData.sys_JSON:
                    weatherData.setSys(parseSys(reader));
                    break;
                case WeatherData.main_JSON:
                    weatherData.setMain(parseMain(reader));
                    break;
                case WeatherData.wind_JSON:
                    weatherData.setWind(parseWind(reader));
                    break;
                case WeatherData.weather_JSON:
                    weatherData.setWeathers(parseWeathers(reader));
                    break;
                case WeatherData.message_JSON:
                    weatherData.setMessage(reader.nextString());
                    break;
                default:
                    Log.d(TAG, "Unknown value \"" + nodeName + "\"");
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();

        return weatherData;
    }
    
    /**
     * Parse a Json stream and return a List of Weather objects.
     */
    public List<Weather> parseWeathers(JsonReader reader) throws IOException {

        // TODO -- DONE
        reader.beginArray();

        final List<Weather> weatherList =
                new ArrayList<>();

        while (reader.hasNext())
            weatherList.add(parseWeather(reader));

        reader.endArray();
        return weatherList;
    }

    /**
     * Parse a Json stream and return a Weather object.
     */
    public Weather parseWeather(JsonReader reader) throws IOException {
        // TODO -- DONE
        // starting object de-marshaling
        reader.beginObject();

        // creating object, using default (empty) constructor
        final Weather weather = new Weather();

        // while wie having nodes in json/xml wie searching
        // for object preferences and adding them to result object
        while (reader.hasNext()) {
            // name of node
            final String nodeName = reader.nextName();
            Log.d("Weather NodeName: ", nodeName);

            // comparing node name with searched prefs
            switch (nodeName) {
                case Weather.description_JSON:
                    weather.setDescription(reader.nextString());
                    break;
                case Weather.icon_JSON:
                    weather.setIcon(reader.nextString());
                    break;
                case Weather.id_JSON:
                    weather.setId(reader.nextInt());
                    break;
                case Weather.main_JSON:
                    weather.setMain(reader.nextString());
                    break;
                // wie using old POJO version?
                default:
                    Log.d(TAG, "Unknown value \"" + nodeName + "\"");
                    reader.skipValue();
                    break;
            }
        }
        // end object de-marshaling
        reader.endObject();

        return weather;
    }

    /**
     * Parse a Json stream and return a Main Object.
     */
    public Main parseMain(JsonReader reader) 
        throws IOException {
        // TODO -- DONE
        // starting object de-marshaling
        reader.beginObject();

        // creating object, using default (empty) constructor
        Main main = new Main();

        // while wie having nodes in json/xml wie searching
        // for object preferences and adding them to result object
        while (reader.hasNext()) {

            // name of node
            final String nodeName = reader.nextName();
            Log.d("Main NodeName:", nodeName);

            // comparing node name with searched prefs
            switch (nodeName) {
                case Main.temp_JSON:
                    main.setTemp(reader.nextDouble());
                    break;
                case Main.pressure_JSON:
                    main.setPressure(reader.nextDouble());
                    break;
                case Main.humidity_JSON:
                    main.setHumidity(reader.nextInt());
                    break;
                // wie using old POJO version?
                default:
                    Log.d(TAG, "Unknown value \"" + nodeName + "\"");
                    reader.skipValue();
                    break;
            }
        }
        // end object de-marshaling
        reader.endObject();

        return main;
    }

    /**
     * Parse a Json stream and return a Wind Object.
     */
    public Wind parseWind(JsonReader reader) throws IOException {
        // TODO -- DONE
        // starting object de-marshaling
        reader.beginObject();

        // creating object, using default (empty) constructor
        Wind wind = new Wind();

        // while wie having nodes in json/xml wie searching
        // for object preferences and adding them to result object
        while (reader.hasNext()) {

            // name of node
            final String nodeName = reader.nextName();
            Log.d("Wind NodeName:", nodeName);

            // comparing node name with searched prefs
            switch (nodeName) {
                case Wind.deg_JSON:
                    wind.setDeg(reader.nextDouble());
                    break;
                case Wind.speed_JSON:
                    wind.setSpeed(reader.nextDouble());
                    break;
                // wie using old POJO version?
                default:
                    Log.d(TAG, "Unknown value \"" + nodeName + "\"");
                    reader.skipValue();
                    break;
            }
        }
        // end object de-marshaling
        reader.endObject();

        return wind;
    }

    /**
     * Parse a Json stream and return a Sys Object.
     */
    public Sys parseSys(JsonReader reader)
        throws IOException {
        // TODO -- DONE

        reader.beginObject();

        final Sys sys = new Sys();

        while (reader.hasNext()) {
            final String nodeName = reader.nextName();
            Log.d("SYS NodeName:", nodeName);

            switch (nodeName) {
                case Sys.country_JSON:
                    sys.setCountry(reader.nextString());
                    break;
                case Sys.message_JSON:
                    sys.setMessage(reader.nextDouble());
                    break;
                case Sys.sunrise_JSON:
                    sys.setSunrise(reader.nextLong());
                    break;
                case Sys.sunset_JSON:
                    sys.setSunset(reader.nextLong());
                    break;
                // wie using old POJO version?
                default:
                    Log.d(TAG, "Unknown value \"" + nodeName + "\"");
                    reader.skipValue();
                    break;
            }
        }
        // end object de-marshaling
        reader.endObject();

        return sys;
    }
}
