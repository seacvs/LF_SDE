package proyecto.LF_SDE;

public class Equ {
	public String elements = "";// estados de la clase
	public String name = "";

	public Equ() {
		this.elements = "";
		this.name = "";
	}

	public Equ(String elements, String name) {
		super();
		this.elements = elements;
		this.name = name;
	}

	// pone nombre y agrega los estados a los elemeentos de la clase
	public void setArrayElement(String s) {
		this.elements += (s);
		this.name = s;
	}

	// busca un elemento entre los que ay en esta clase
	public boolean findElement(int a) {
		String s = elements;
		for (int i = 0; i < s.length(); i++) {
			int tempo = Integer.parseInt(s.substring(i, i + 1));
			if (a == tempo)
				return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "Equ [elements=" + elements + ", name=" + name + "]";
	}
	

}
