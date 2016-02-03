package android.dorianamouroux.fr.epiandroid.item;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;

/**
 * Created by patoche on 1/30/16.
 */
public class Activity {

    private String  _title;
    private String  _start;
    private String  _end;
    private String  _registered;
    private String  _scolaryear;
    private String  _codemodule;
    private String  _codeinstance;
    private String  _codeacti;
    private String  _codeevent;
    private String  _room;
    private String  _hourStart;
    private String  _hourEnd;

    public void setTitle(String title) {
        _title = title;
    }
    public void setStart(String start) {
        _start = start;
        setHourStart(start);
    }
    public void setEnd(String end) {
        _end = end;
        setHourEnd(end);
    }
    public void setRegistered(String registered) {
        _registered = registered;
    }
    public void setScolaryear(String scolaryear) {
        _scolaryear = scolaryear;
    }
    public void setCodemodule(String codemodule) {
        _codemodule = codemodule;
    }
    public void setCodeinstance(String codeinstance) {
        _codeinstance = codeinstance;
    }
    public void setCodeacti(String codeacti) {
        _codeacti = codeacti;
    }
    public void setCodeevent(String codeevent) {
        _codeevent = codeevent;
    }
    public void setRoom(String room) {
        if (room == "null") {
            _room = "No Room";
            return;
        }

        _room = room.split("\\/")[room.split("\\/").length - 1].split("\"")[0];
    }
    private void setHourStart(String start) {
        String tmp = start.split("\\s+")[1].split(":")[0];
        _hourStart = tmp + ":" + start.split(":")[1];
    }
    private void setHourEnd(String end) {
        String tmp = end.split("\\s+")[1].split(":")[0];
        _hourEnd = tmp + ":" + end.split(":")[1];
    }

    public String getStart() {
        return _start;
    }
    public String getTitle() {
        return _title;
    }
    public String getEnd() {
        return _end;
    }
    public String getRegistered() {
        return _registered;
    }
    public String getScolaryear() {
        return _scolaryear;
    }
    public String getCodemodule() {
        return _codemodule;
    }
    public String getCodeinstance() {
        return _codeinstance;
    }
    public String getCodeacti() {
        return _codeacti;
    }
    public String getCodeevent() {
        return _codeevent;
    }
    public String getRoom() {
        return _room;
    }
    public String getHourStart() {
        return _hourStart;
    }
    public String getHourEnd() {
        return _hourEnd;
    }
}
