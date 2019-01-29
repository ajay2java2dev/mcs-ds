package terminals;

import entities.Message;
import entities.TerminalPrintType;

public abstract class Terminal {

    private MessageQueue mq;
    private Thread printMachineThread;

    public Terminal() {
        mq = new MessageQueue();
    }

    public void printToScreen(String msg) {
        printToScreen(msg, TerminalPrintType.INFORMATION);
    }

    public void printToScreen(String msg, TerminalPrintType type) {
        String printMsg = this.getClass().getName() + ": " + type.name() + " - " + msg;
        System.out.println(printMsg);
    }

    public void start() {
        printToScreen("Starting...");
        Runnable runnable = new Runnable() {
            public void run() {
                printMachineThread = Thread.currentThread();

                while (!Thread.currentThread().isInterrupted()) {
                    Message msg = mq.take();
                    if (msg == null) {
                        break;
                    } else {
                        //Hmm
                    }
                    printToScreen(msg.getContent());
                }

            }
        };
        printMachineThread = new Thread(runnable);
        printMachineThread.start();
    }

    public void end() {
        printToScreen("Ending...");
        printMachineThread.interrupt();
    }

}
