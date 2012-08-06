package org.infinitybot.x.scripts;

import java.awt.*;

public abstract class Strategy {

    private final String name;
    private String status = "Sleeping";

    public Strategy() {
        this.name = "strategy";
    }

    public Strategy(String name) {
        this.name = name;
    }

    public abstract boolean isValid();
    public abstract void process();
    public abstract int finish();
    public abstract void paint(final Graphics2D g);

    public void setStatus(final String status) {
        this.status = status;
    }

    public final String getStatus() {
        return this.status;
    }

    public final String getName() {
        return name;
    }
}