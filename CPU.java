import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class CPU extends Thread {
    // característica do processador: contexto da CPU ...
    public int pc; // ... composto de program counter,
    public Word ir; // instruction register,
    public int[] reg; // registradores da CPU
    public Word[] memory;
    public MemoryManager mm;
    public ArrayList<Integer> paginas; // Paginas Alocadas
    public Interrupt interrupt;
    public int timer; // conta instrucoes
    public Semaphore semCPU;
    public Semaphore semESC;
    public Routines routine;
    public int processId;

    public CPU(Word[] memory, Semaphore semCPU, Semaphore semESC) {
        this.memory = memory;
        reg = new int[10];
        this.semCPU = semCPU;
        this.semESC = semESC;
        this.interrupt = Interrupt.NONE;
        super.setName("CPU");
    }

    void configure(Routines routine, MemoryManager mm) {
        this.routine = routine;
        this.mm = mm;
    }

    /**
     * Converte de um endereço lógico em um endereço físico.
     */
    private int translate(int pc) {

        boolean isValid = true;

        int pageSize = mm.pageSize;
        int index = pc / pageSize;
        int res = 0;

        try {
            paginas.get(index);
        } catch (Exception e) {
            interrupt = Interrupt.INVALID_ADDRESS;
            isValid = false;
        }

        if (isValid) {
            res = (paginas.get(index) * pageSize) + (pc % pageSize);
        }

        return res;
    }

    public Context getContext() {
        return new Context(paginas, reg, pc, ir, processId);
    }

    public void setContext(Context context) {
        paginas = context.getAllocatedPages();
        pc = context.getPc();
        reg = context.getReg();
        interrupt = Interrupt.NONE;
        processId = context.getProcessId();
    }

    public void run() { // execucao da CPU supoe que o contexto da CPU, vide acima, esta devidamente
                        // setado

        timer = 0;

        while (true) {
            try {
                semCPU.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            while (interrupt == Interrupt.NONE) {
                ir = memory[translate(pc)]; // busca posicao da memoria apontada por pc, guarda em ir

                int aux = 0;

                timer++;
                if (timer >= 5) {
                    interrupt = Interrupt.TIMER;
                }

                switch (ir.opc) { // para cada opcode, sua execução

                    // Instruções JUMP
                    case JMP: // PC ← k
                        if (!(ir.p < -1 || ir.p > 1023)) {
                            pc = ir.p;
                        } else {
                            interrupt = Interrupt.OVERFLOW;
                        }
                        break;

                    case JMPI: // PC ← Rs
                        pc = reg[ir.r1];
                        break;

                    case JMPIG: // If Rc > 0 Then PC ← Rs Else PC ← PC +1
                        if (reg[ir.r2] > 0) {
                            pc = reg[ir.r1];
                        } else {
                            pc++;
                        }
                        break;

                    case JMPIL: // If Rc < 0 Then PC ← Rs Else PC ← PC +1
                        if (reg[ir.r2] < 0) {
                            pc = reg[ir.r1];
                        } else {
                            pc++;
                        }
                        break;

                    case JMPIE: // If Rc = 0 Then PC ← Rs Else PC ← PC +1
                        if (reg[ir.r2] == 0) {
                            pc = reg[ir.r1];
                        } else {
                            pc++;
                        }
                        break;

                    case JMPIM: // PC ← [A]
                        pc = memory[translate(ir.p)].p;
                        break;

                    case JMPIGM: // If Rc > 0 Then PC ← [A] Else PC ← PC +1
                        if (reg[ir.r2] > 0) {
                            pc = memory[translate(ir.p)].p;
                        } else {
                            pc++;
                        }
                        break;

                    case JMPILM: // If Rc < 0 Then PC ← [A] Else PC ← PC +1
                        if (reg[ir.r2] < 0) {
                            pc = memory[translate(ir.p)].p;
                        } else {
                            pc++;
                        }
                        break;

                    case JMPIEM: // If Rc = 0 Then PC ← [A] Else PC ← PC +1
                        if (reg[ir.r2] == 0) {
                            pc = memory[translate(ir.p)].p;
                        } else {
                            pc++;
                        }
                        break;

                    // Instruções Aritméticas
                    case ADDI: // Rd ← Rd + k
                        aux = reg[ir.r1] + ir.p;
                        if (aux == -2) {
                            interrupt = Interrupt.OVERFLOW;
                        } else {
                            reg[ir.r1] = reg[ir.r1] + ir.p;
                            pc++;
                        }
                        break;

                    case SUBI: // Rd ← Rd – k
                        aux = reg[ir.r1] - ir.p;
                        if (aux == -2) {
                            interrupt = Interrupt.OVERFLOW;
                        } else {
                            reg[ir.r1] = reg[ir.r1] - ir.p;
                            pc++;
                        }
                        break;

                    case ADD: // Rd ← Rd + Rs
                        aux = reg[ir.r1] + reg[ir.r2];
                        if (aux == -2) {
                            interrupt = Interrupt.OVERFLOW;
                        } else {
                            reg[ir.r1] = reg[ir.r1] + reg[ir.r2];
                            pc++;
                        }
                        break;

                    case SUB: // Rd ← Rd - Rs
                        aux = reg[ir.r1] - reg[ir.r2];
                        if (aux == -2) {
                            interrupt = Interrupt.OVERFLOW;
                        } else {
                            reg[ir.r1] = reg[ir.r1] - reg[ir.r2];
                            pc++;
                        }
                        break;

                    case MULT: // Rd ← Rd * Rs
                        aux = reg[ir.r1] * reg[ir.r2];
                        if (aux == -2) {
                            interrupt = Interrupt.OVERFLOW;
                        } else {
                            reg[ir.r1] = reg[ir.r1] * reg[ir.r2];
                            pc++;
                        }
                        break;

                    // Instruções de Movimentação
                    case LDI: // Rd ← k
                        try {
                            reg[ir.r1] = ir.p;
                            pc++;
                        } catch (Exception e) {
                            interrupt = Interrupt.INVALID_ADDRESS;
                        }
                        break;

                    case LDD: // Rd ← [A]
                        try {
                            reg[ir.r1] = memory[translate(ir.p)].p; // m == memoria
                            pc++;
                        } catch (Exception e) {
                            interrupt = Interrupt.INVALID_ADDRESS;
                        }
                        break;

                    case STD: // [A] ← Rs
                        try {
                            memory[translate(ir.p)].opc = Opcode.DATA;
                            memory[translate(ir.p)].p = reg[ir.r1];
                            pc++;
                        } catch (Exception e) {
                            interrupt = Interrupt.INVALID_ADDRESS;
                        }
                        break;

                    case LDX: // Rd ← [Rs]
                        try {
                            reg[ir.r1] = memory[translate(reg[ir.r2])].p; // m == memoria
                            pc++;
                        } catch (Exception e) {
                            interrupt = Interrupt.INVALID_ADDRESS;
                        }
                        break;

                    case STX: // [Rd] ←Rs
                        try {
                            memory[translate(reg[ir.r1])].opc = Opcode.DATA;
                            memory[translate(reg[ir.r1])].p = reg[ir.r2];
                            pc++;
                        } catch (Exception e) {
                            interrupt = Interrupt.INVALID_ADDRESS;
                        }
                        break;

                    case STOP: // por enquanto, para execucao
                        interrupt = Interrupt.STOP;
                        break;

                    case TRAP: // trap
                        interrupt = Interrupt.TRAP;
                        pc++;
                        break;

                    default:
                        interrupt = Interrupt.INVALID_INSTRUCTION;
                        break;
                }
            }

            if (interrupt != Interrupt.NONE) {
                switch (interrupt) {
                    case INVALID_ADDRESS:
                        routine.interruption();
                        break;
                    case INVALID_INSTRUCTION:
                        routine.interruption();
                        break;
                    case OVERFLOW:
                        routine.interruption();
                        break;
                    case STOP:
                        routine.interruption();
                        break;
                    case TIMER:
                        routine.timer(getContext());
                        System.out.println(processId + " -> Timer");
                        try {
                            // Thread.sleep(200);
                        } catch (Exception e) {
                        }
                        break;
                    case TRAP:
                        routine.trap(getContext());
                        break;
                    case NONE:
                }
            }
        }
    }

    public void printRegistradores() {
        int index = 0;
        SystemOut.print(Emoji.BEETLE);
        for (int reg : this.reg) {
            SystemOut.print(Dye.yellow("[R" + index + "=" + reg + "]"));
            index++;
        }
        SystemOut.print("\n");
    }
}