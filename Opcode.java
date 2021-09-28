public enum Opcode {
    DATA, ___,		    // se memoria nesta posicao tem um dado, usa DATA, se nao usada ee NULO ___

    // Instruções JUMP

    /** (-1, -1, k)  || Rd ← k */                                   JMP, 
    /** (Rs, -1, -1) || PC ← Rs */                                  JMPI, 
    /** (Rs, Rc, -1) || if Rc > 0 then PC ← Rs Else PC ← PC +1 */   JMPIG, 
    /** (Rs, Rc, -1) || if Rc < 0 then PC ← Rs Else PC ← PC +1 */   JMPIL, 
    /** (Rs, Rc, -1) || if Rc = 0 then PC ← Rs Else PC ← PC +1 */   JMPIE, 
    /** (-1, -1, A)  || PC ← [A] */                                 JMPIM, 
    /** (-1, Rc, A)  || if Rc > 0 then PC ← [A] Else PC ← PC +1 */  JMPIGM, 
    /** (-1, Rc, A)  || if Rc < 0 then PC ← [A] Else PC ← PC +1 */  JMPILM, 
    /** (-1, Rc, A)  || if Rc = 0 then PC ← [A] Else PC ← PC +1 */  JMPIEM, 
    
    // Instruções Aritméticas

    /** (Rd, -1, k)  || Rd ← Rd + k */      ADDI, 
    /** (Rd, -1, k)  || Rd ← Rd – k */      SUBI, 
    /** (Rd, Rs, -1) || Rd ← Rd + Rs */     ADD, 
    /** (Rd, Rs, -1) || Rd ← Rd - Rs */     SUB, 
    /** (Rd, Rs, -1) || Rd ← Rd * Rs */     MULT,

    // Instruções de Movimentação

    /** (Rd, -1, k)  || Rd ← k */           LDI,
    /** (Rd, -1, A)  || Rd ← [A] */         LDD, 
    /** (Rs, -1, A)  || [A] ← Rs */         STD,
    /** (Rd, Rs, -1) || Rd ← [Rs] */        LDX, 
    /** (Rd, Rs, -1) || [Rd] ←Rs */         STX, 

    /** T ← Ra, Ra ← Rb, Rb ← T */          SWAP, 
    /** parâmetros em r[8] e r[9] */        TRAP,
    
    STOP, 
}
