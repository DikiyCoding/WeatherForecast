package example.homework10.application;

import android.app.Application;
import android.arch.persistence.room.Room;

import java.util.Locale;

import example.homework10.database.AppDatabase;

public class App extends Application {

    public static App instance;
    private AppDatabase database;
    private Locale locale;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, AppDatabase.class, "database").build();
        locale = getApplicationContext().getResources().getConfiguration().locale;
    }

    public static App getInstance() {
        return instance;
    }

    public AppDatabase getDatabase() {
        return database;
    }

    public Locale getLocale() {
        return locale;
    }
}
