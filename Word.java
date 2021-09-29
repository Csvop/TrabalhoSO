public class Word { 	
    public static final Word BLANK = new Word(Opcode.___, -1, -1, -1);

    public Opcode opc; 	
    public int r1; 		
    public int r2; 		
    public int p; 		

    public Word(Opcode _opc, int _r1, int _r2, int _p) {  
        opc = _opc;   r1 = _r1;    r2 = _r2;	p = _p;
    }

    /**
     * Retorna um novo objeto `Word` com os mesmos 'opc', 'r1', 'r2' e 'p' que o parâmetro.
     */
    public static Word copy(Word _word) {
		Word word = new Word(_word.opc, _word.r1, _word.r2, _word.p);
		return word;
	}

    @Override
    public String toString() {
        return "[" + opc + ", " + r1 + ", " + r2 + ", " + p + "]";
    }
}
