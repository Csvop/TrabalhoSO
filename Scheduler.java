import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Scheduler extends Thread {
    public LinkedList<PCB> readyQueue;
    public Semaphore semEsc;
    public Semaphore semCpu;
    public CPU cpu;
    public PCB currentProcess;

    public Scheduler(LinkedList<PCB> readyQueue, Semaphore semEsc, Semaphore semCpu, CPU cpu) {
        this.readyQueue = readyQueue;
        this.semEsc = semEsc;
        this.semCpu = semCpu;
        this.cpu = cpu;
        super.setName("Escalonador");
    }

    @Override
    public void run() {
        while (true) {
            try {
                semEsc.acquire();
                if (readyQueue.isEmpty()) {
                    continue;
                }
                PCB pcb = readyQueue.removeFirst();
                this.currentProcess = pcb;
                Thread.sleep(50);
                cpu.setContext(pcb.getContext());
                semCpu.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public PCB getCurrentProcess() {
        return currentProcess;
    }

    public void setCurrentProcess(PCB currentProcess) {
        this.currentProcess = currentProcess;
    }

    public List<PCB> getReadyQueue() {
        return readyQueue;
    }
}