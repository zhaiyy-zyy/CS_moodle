package stylethread;

import javafx.concurrent.Task;

public class ProgressWorker extends Task {

    @Override
    protected Object call() throws Exception {
        int n = 250000;

        for(int i = 0; i < n; i++) {
            System.out.println(i);
            updateProgress(i, n);

            if(isCancelled())
                break;
        }

        return null;
    }
}
