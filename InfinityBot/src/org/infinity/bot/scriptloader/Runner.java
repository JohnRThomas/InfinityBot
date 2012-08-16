package org.infinity.bot.scriptloader;
 
import org.infinity.bot.Client;
 
import org.infinity.bot.api.script.Script;
 
import org.infinity.bot.api.utils.Time;
 
public class Runner extends Thread implements Runnable {
 
    private final Script script;
    private Client client;
 
    private boolean paused = false;
 
    private boolean interrupted = false;
     
    public Runner(final Script script, final Client client) {
        this.script = script;
        setClient(client);
    }
 
    @Override
    public final boolean isInterrupted() {
        return interrupted;
    }
 
    @Override
    public final void interrupt() {
        if (interrupted) {
            return;
        }
        super.interrupt();
        interrupted = true;
    }
 
    @Override
    public final void run() {
        if (script.onStart()) {
            while (!interrupted) {
                if (paused) {
                    Time.sleep(5, 10);
                    continue;
                }
                final int sleep = script.loop();
                if (sleep < 0) {
                    interrupt();
                    break;
                }
                Time.sleep(sleep);
            }
            script.onStop();
        } else {
            getClient().log("ScriptLoader", "Script bloked itself from starting!");
        }
    }
 
    public final void setPaused(final boolean paused) {
        this.paused = paused;
    }
 
    public final boolean isPaused() {
        return paused;
    }
 
    public final void setClient(final Client client) {
        this.client = client;
    }
 
    public final Client getClient() {
        return client;
    }
}