public class Routine {
    private ProcessManager pm;
    //private Scheduler scheduler;

    public Routine(ProcessManager pm, Scheduler scheduler) {
        this.pm = pm;
        //this.scheduler = scheduler;
    }

    //finaliza o processo, chamando o grenciador de processos e escalona novo processo
    public void stop(PCB processo){
        pm.endProcess(processo);
    }
}
