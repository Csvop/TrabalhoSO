import java.util.List;
import java.util.concurrent.Semaphore;

public class Routines {
    private ProcessManager pm;
    private Scheduler scheduler;
    private Semaphore semScheduler;
    private List<PCB> blockedQueue;

    public Routines(ProcessManager pm, Scheduler scheduler, 
                    Semaphore semScheduler, List<PCB> blockedQueue) {
        this.pm = pm;
        this.scheduler = scheduler;
        this.semScheduler = semScheduler;
        this.blockedQueue = blockedQueue;
    }

    //finaliza o processo, chamando o grenciador de processos e escalona novo processo
    public void stop(PCB processo){
        pm.endProcess(processo);
    }

    // Interrompe a execucao do processo e tira ele da vm
    public void interruption() {
        pm.endProcess(scheduler.getCurrentProcess());
        scheduler.setCurrentProcess(null);
        semScheduler.release();
    }

    // Interrompe a execucao do processo por tempo e poe ele na fila
    public void timer(Context context) {
        PCB process = scheduler.getCurrentProcess();
        scheduler.setCurrentProcess(null);
        process.setContext(context);
        scheduler.getReadyQueue().add(process);
        semScheduler.release();
    }

    // Interrompe a execucao do processo por trap IO e poe ele na fila de bloqueados
    public void trap(Context context) {
        PCB process = scheduler.getCurrentProcess();
        scheduler.setCurrentProcess(null);
        process.setContext(context);
        blockedQueue.add(process);

        Word instruction = process.getContext().getIr();
        String type = (instruction.r1 == 1) ? "IN" : "OUT";
        //Chama o console aqui pra fazer o out
        //fila de pedidos <- nova chamada de console
        semScheduler.release();
    }

    public void inputOutputTreatment(Context context) {
        PCB process = scheduler.getCurrentProcess();
        
        if(process != null) {
            scheduler.setCurrentProcess(null);
            process.setContext(context);
            scheduler.getReadyQueue().add(process);
        }

        scheduler.getReadyQueue().add(blockedQueue.remove(0));
        semScheduler.release();
    }
}
