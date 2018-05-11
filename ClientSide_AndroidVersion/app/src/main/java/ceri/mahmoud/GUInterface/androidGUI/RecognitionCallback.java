package ceri.mahmoud.GUInterface.androidGUI;

import java.util.ArrayList;

public interface RecognitionCallback
{
    abstract void onRecoginitionFinished(ArrayList<String> matches);
}
