package hardware;

import java.util.ArrayList;
import java.util.List;

import util.Console;

import software.MemoryManager;
import software.PCB;
import software.Status;
import virtualmachine.TrapHandling;
import virtualmachine.VM;

public class CPU {
    // característica do processador: contexto da CPU ...
    public int pc; // ... composto de program counter,
    public Word ir; // instruction register,
    public int[] reg; // registradores da CPU
    public int currentProcessId;
    public int count; // instruction count antes de trocar pra outro programa

    public Memory m;
    public MemoryManager mm = MemoryManager.get();
    public List<Integer> paginas;

    public Interrupt interrupt;

    public CPU() { // ref a MEMORIA e interrupt handler passada na criacao da CPU
        m = Memory.get(); // usa o atributo 'm' para acessar a memoria.
        reg = new int[10]; // aloca o espaço dos registradores
        interrupt = Interrupt.NONE;
        count = 5;
    }

    public void setContext(ArrayList<Integer> _paginas, int _pc, int _id, int[] _reg) { // no futuro esta funcao vai ter
                                                                                        // que ser
        paginas = _paginas;
        pc = _pc; // limite e pc (deve ser zero nesta versao)
        currentProcessId = _id;
        reg = _reg;
        interrupt = Interrupt.NONE;
    }

    public void showState() {
        System.out.println("       " + pc);
        System.out.print("           ");
        for (int i = 0; i < 8; i++) {
            System.out.print("r" + i);
            System.out.print(": " + reg[i] + "     ");
        }
        ;
        System.out.println("");
        System.out.print("           ");
        mm.dump(ir);
    }

    public void run() { // execucao da CPU supoe que o contexto da CPU, vide acima, esta devidamente
                        // setado
        while (interrupt == Interrupt.NONE) {
            int aux = 0;
            // ciclo de instrucoes. acaba cfe instrucao, veja cada caso.
            // FETCH
            ir = m.data[translate(pc)]; // busca posicao da memoria apontada por pc, guarda em ir
            // if debug
            showState();
            // EXECUTA INSTRUCAO NO ir
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
                    pc = ir.r1;
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
                    pc = ir.p;
                    break;

                case JMPIGM: // If Rc > 0 Then PC ← [A] Else PC ← PC +1
                    if (reg[ir.r2] > 0) {
                        pc = reg[ir.p];
                    } else {
                        pc++;
                    }
                    break;

                case JMPILM: // If Rc < 0 Then PC ← [A] Else PC ← PC +1
                    if (reg[ir.r2] < 0) {
                        pc = reg[ir.p];
                    } else {
                        pc++;
                    }
                    break;

                case JMPIEM: // If Rc = 0 Then PC ← [A] Else PC ← PC +1
                    if (reg[ir.r2] == 0) {
                        pc = reg[ir.p];
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
                        reg[ir.r1] = m.data[translate(ir.p)].p; // m == memoria
                        pc++;
                    } catch (Exception e) {
                        interrupt = Interrupt.INVALID_ADDRESS;
                    }
                    break;

                case STD: // [A] ← Rs
                    try {
                        m.data[translate(ir.p)].opc = Opcode.DATA;
                        m.data[translate(ir.p)].p = reg[ir.r1];
                        pc++;
                    } catch (Exception e) {
                        interrupt = Interrupt.INVALID_ADDRESS;
                    }
                    break;

                case LDX: // Rd ← [Rs]
                    try {
                        reg[ir.r1] = m.data[translate(ir.r2)].p; // m == memoria
                        pc++;
                    } catch (Exception e) {
                        interrupt = Interrupt.INVALID_ADDRESS;
                    }
                    break;

                case STX: // [Rd] ←Rs
                    try {
                        m.data[translate(reg[ir.r1])].opc = Opcode.DATA;
                        m.data[translate(reg[ir.r1])].p = reg[ir.r2];
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

            switch (interrupt) {
                case NONE:
                    break;

                case INVALID_ADDRESS:
                    Console.print("\n");
                    Console.warn(" > Interrupt.INVALID_ADDRESS");
                    break;

                case INVALID_INSTRUCTION:
                    Console.print("\n");
                    Console.warn(" > Interrupt.INVALID_INSTRUCTION");
                    break;

                case OVERFLOW:
                    Console.print("\n");
                    Console.warn(" > Interrupt.OVERFLOW");
                    break;

                case TIMER:
                    Console.print("\n");
                    Console.warn(" > Interrupt.TIMER");
                    // saveCPUstate(currentprogram)
                    // loadCPUState(anotherprogram)

                    for (PCB p : VM.get().pm.pcbList) {
                        if (p.id == currentProcessId) {
                            p.status = Status.READY;
                            p.pc = pc;
                            p.reg = reg;
                            break;
                        }
                        Console.log(p);

                    }

                    count = 5;
                    interrupt = Interrupt.NONE;
                    break;

                case TRAP:
                    TrapHandling.trap(this);
                    interrupt = Interrupt.NONE;
                    break;

                case STOP:
                    Console.print("\n");
                    Console.warn(" > Interrupt.STOP");
                    break;
            }

            count--;
            if (count == 0) {
                interrupt = Interrupt.TIMER;
            }
        }

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

}
