public class Btree {
	// atributos da classe
	Bnode raiz;

	// construtor
	public Btree(){
		raiz = null;
	}

	public Bnode getRaiz() {
		return raiz;
	}

	public void setRaiz(Bnode raiz) {
		this.raiz = raiz;
	}

	public void adicionar(int valor){
		if(raiz!=null){
			raiz.adicionar(valor);

			System.out.println("Antes do balanceamento:");
			definirAtr(); // Definir fb e h
			System.out.println("-----------------------------");

			raiz.balanceamento(raiz, this); // balancea árvore

			System.out.println("Pós balanceamento:"); // se nada foi balanceado, mostra do mesmo jeito
			definirAtr(); // Definir fb e h
			System.out.println("-----------------------------");
		}
		else{
			raiz = new Bnode(valor);

			definirAtr(); // Definir fb e h
			System.out.println("-----------------------------");
		}
	}

	public void mostrar(){
		if(raiz!=null) raiz.mostrar();
		else System.out.println("Arvore Vazia!!! ");
	}

	public int tamanho(){
		if(raiz!=null) return raiz.tamanho();
		return 0;
	}

	public int altura(){
		if (raiz != null){
			return raiz.altura();
		}
		else{
			return 0;
		}
	}

	public int soma(){
		if(raiz!=null) return raiz.soma();
		return 0;
	}

	public void definirAtr(){
		if(raiz!=null) raiz.definirAtr(1);
		else System.out.println("Arvore Vazia!!! ");
	}
}
