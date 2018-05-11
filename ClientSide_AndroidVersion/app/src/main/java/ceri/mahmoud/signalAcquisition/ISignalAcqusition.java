package ceri.mahmoud.signalAcquisition;

import android.content.Context;

public interface ISignalAcqusition {
    public String getCommand(Context context);
    public String getSignalMessage();
}
