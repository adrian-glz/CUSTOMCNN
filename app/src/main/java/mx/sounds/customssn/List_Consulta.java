package mx.sounds.customssn;

public class List_Consulta {
	private String id; 
	private String factura;
	private String marca; 
	private String modelo; 
	private String color; 
	private String origen; 
	private String serie; 
	private String foto; 

	public List_Consulta (String id, String factura, String marca, String modelo, String color, String origen, String serie, String foto) { 
	    this.id = id; 
	    this.factura = factura; 
	    this.marca = marca; 
	    this.modelo = modelo; 
	    this.color = color; 
	    this.origen = origen; 
	    this.serie = serie; 
	    this.foto = foto;
	}

	public String get_texto1() { 
	    return id; 
	}

	public String get_texto2() { 
	    return factura; 
	}
	
	public String get_texto3() { 
	    return marca; 
	}
	
	public String get_texto4() { 
	    return modelo; 
	}
	
	public String get_texto5() { 
	    return color; 
	}
	
	public String get_texto6() { 
	    return origen; 
	}
	
	public String get_texto7() { 
	    return serie; 
	}
	
	public String get_texto8() { 
	    return foto; 
	}

}
