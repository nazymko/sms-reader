package org.nazymko.stategy;

import java.time.LocalDateTime;

/**
 * Created by nazymko.patronus@gmail.com
 */
public class Change {
    private LocalDateTime time;
    private Long value;
    private String currecny;

    public Change(LocalDateTime time, Long value, String currecny) {
        this.time = time;
        this.value = value;
        this.currecny = currecny;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public String getCurrecny() {
        return currecny;
    }

    public void setCurrecny(String currecny) {
        this.currecny = currecny;
    }
}
