package net.nonswag.tnl.listener.api.player.manager;

import org.bukkit.WeatherType;

public abstract class EnvironmentManager extends Manager {

    public long getTime() {
        return getPlayer().bukkit().getPlayerTime();
    }

    public void setTime(long time, boolean relative) {
        getPlayer().bukkit().setPlayerTime(time, relative);
    }

    public void setTime(long time) {
        setTime(time, false);
    }

    public void resetTime() {
        getPlayer().bukkit().resetPlayerTime();
    }

    public WeatherType getWeather() {
        WeatherType weather = getPlayer().bukkit().getPlayerWeather();
        return weather == null ? getPlayer().worldManager().getWeather() : weather;
    }

    public void setWeather(WeatherType weather) {
        getPlayer().bukkit().setPlayerWeather(weather);
    }

    public void resetWeather() {
        getPlayer().bukkit().resetPlayerWeather();
    }
}
