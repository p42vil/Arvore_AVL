public class Bnode {
	//atributos
	private int x; // valor armazenado
	private Bnode esq, dir; // esquerda, direita
	private int h = 0; // altura
	private int fb = 0; // fator de balanceamento

	//construtor
	public Bnode(int valor){
		x = valor;
		esq = null;
		dir = null;
	}

	public void adicionar(int valor){
		if(valor > x){
			if(dir!=null) dir.adicionar(valor);
			else dir = new Bnode(valor);
		}
		else if(valor < x){
			if(esq!=null) esq.adicionar(valor);
			else esq = new Bnode(valor);
		}
		else{ // se o valor for igual, olhar para qual lado tem altura maior
			int eh = 0;
			int dh = 0;

			if(esq!=null) eh = esq.altura();
			if(dir!=null) dh = dir.altura();

			if (eh > dh){
				if(dir!=null) dir.adicionar(valor);
				else dir = new Bnode(valor);
			}
			else {
				if(esq!=null) esq.adicionar(valor);
				else esq = new Bnode(valor);
			}
		}
	}

	public void mostrar(){
		System.out.println(x);
		if(esq!=null) esq.mostrar();
		if(dir!=null) dir.mostrar();
	}

	public int tamanho(){
		int e = 0, d = 0;
		if(esq!=null) e = esq.tamanho();
		if(dir!=null) d = dir.tamanho();
		return 1 + e + d;
	}

	public int altura(){
		int qe = 0, qd = 0;

		if (esq != null) qe = esq.altura();
		if (dir != null) qd = dir.altura();

		if (qe > qd){
			return 1 + qe;
		}
		else{
			return 1 + qd;
		}
	}

	public int soma(){
		int e = 0, d = 0;
		if(esq!=null) e = esq.soma();
		if(dir!=null) d = dir.soma();
		return x + e + d;
	}

	public void definirAtr(int val){ // define altura e fb
		if(esq!=null) esq.definirAtr(val + 1);
		if(dir!=null) dir.definirAtr(val + 1);

		h = val;
		int eh = 0;
		int dh = 0;

		if(esq!=null) eh = esq.altura();
		if(dir!=null) dh = dir.altura();

		fb = eh - dh;

		System.out.println("Valor: " + x + " | FB: " + fb + " | Altura: " + h);
	}

	public boolean fatorDesbalanceado(){ // checa se tem nó desbalanceado como filho
		boolean val = false;
		if(esq!=null) if (esq.fatorDesbalanceado()) val = true;
		if(dir!=null) if (dir.fatorDesbalanceado()) val = true;

		if (fb < -1 || fb > 1) return true;

		return val;
	}

	//        ( C )            ( C )
	//         /                  \
	//      ( B )                ( B )
	//       /                      \
	//    ( A )                    ( A )

	public void rotacaoEsq(Bnode A, Bnode B, Bnode C, Bnode anterior, Btree arvore) {
		if (B.dir == null && B.esq != null) {
			// ficar reto, para fazer rotação
			// A e B não trocam de posição, só vals
			B.dir = B.esq;
			B.esq = null;

			int reg_val = A.x;
			A.x = B.x;
			B.x = reg_val;
		}

		if (B.esq == null) { // se o B não tiver valor do lado
			B.esq = C;
			C.dir = null;
		} else { // se tiver
			Bnode reg_node = B.esq;
			B.esq = C;
			C.dir = reg_node;
		}

		// consertar herança
		if (anterior == C) { // se anterior ao C, for o C (então ele é raiz)
			arvore.setRaiz(B);
		} else { // quando houver o anterior de C
			if (anterior.esq == C) anterior.esq = B;
			else if (anterior.dir == C) anterior.dir = B;
		}
	}

	public void rotacaoDir(Bnode A, Bnode B, Bnode C, Bnode anterior, Btree arvore) {
		if (B.esq == null && B.dir != null) {
			// ficar reto, para fazer rotação
			// A e B não trocam de posição, só vals
			B.esq = B.dir;
			B.dir = null;

			int reg_val = A.x;
			A.x = B.x;
			B.x = reg_val;
		}

		if (B.dir == null) { // se o B não tiver valor do lado
			B.dir = C;
			C.esq = null;
		} else { // se tiver
			Bnode reg_node = B.dir;
			B.dir = C;
			C.esq = reg_node;
		}

		// consertar herança
		if (anterior == C) { // se anterior ao C, for o C (então ele é raiz)
			arvore.setRaiz(B);
		} else { // quando houver o anterior de C
			if (anterior.esq == C) anterior.esq = B;
			else if (anterior.dir == C) anterior.dir = B;
		}
	}

	public void balanceamento(Bnode anterior, Btree arvore){
		int eh = 0;
		int dh = 0;

		if(esq!=null) eh = esq.altura();
		if(dir!=null) dh = dir.altura();

		// se tiver filhos desbalanceados pular até chegar no filho
		boolean jump = false;

		if(esq!=null && esq.fatorDesbalanceado()) jump = true;
		if(dir!=null && dir.fatorDesbalanceado()) jump = true;

		if (!jump) {
			if (fb < -1 || fb > 1) {
				//System.out.println(x); //printa o nó balanceado
				if (eh > dh) {
					rotacaoDir(esq.esq, esq, this, anterior, arvore);
					return;
				} else if (eh < dh) {
					rotacaoEsq(dir.dir, dir, this, anterior, arvore);
					return;
				}
			}
		}

		// continuar balanceamento
		if(esq!=null) esq.balanceamento(this,arvore);
		if(dir!=null) dir.balanceamento(this,arvore);
	}
}
