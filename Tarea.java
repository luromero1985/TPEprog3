package tpe;


public class Tarea {
	private String id_tarea;
	private String nombre_tarea;
	private int tiempo_ejecucion;
	private boolean es_critica;
	private int nivel_prioridad;
	private final int MAXPRIORIDAD=100;
	private final int MINPRIORIDAD=1;

	public Tarea(String id_tarea, String nombre_tarea, int tiempo_ejecucion,
			boolean es_critica, int nivel_prioridad) {

		this.id_tarea = id_tarea;
		this.nombre_tarea = nombre_tarea;
		this.tiempo_ejecucion = tiempo_ejecucion;
		this.es_critica = es_critica;
		this.setNivel_prioridad(nivel_prioridad);
	}


	public String getId_tarea() {
		return id_tarea;
	}
	public void setId_tarea(String id_tarea) {
		this.id_tarea = id_tarea;
	}
	public String getNombre_tarea() {
		return nombre_tarea;
	}
	public void setNombre_tarea(String nombre_tarea) {
		this.nombre_tarea = nombre_tarea;
	}
	public int getTiempo_ejecucion() {
		return tiempo_ejecucion;
	}
	public void setTiempo_ejecucion(int tiempo_ejecucion) {
		this.tiempo_ejecucion = tiempo_ejecucion;
	}
	public boolean isEs_critica() {
		return es_critica;
	}
	public void setEs_critica(boolean es_critica) {
		this.es_critica = es_critica;
	}
	public int getNivel_prioridad() {
		return nivel_prioridad;
	}
	
	public void setNivel_prioridad(int nivel_prioridad) {
		if(nivel_prioridad>=MINPRIORIDAD && nivel_prioridad<=MAXPRIORIDAD)
		this.nivel_prioridad = nivel_prioridad;
	}


	@Override
	public boolean equals(Object obj) {
		try{
			Tarea p =(Tarea)obj;
			return this.getId_tarea().equals(p.getId_tarea());
		}
		catch(Exception e){
			return false;
		}
	}


	@Override
	public String toString() {
		return "Tarea [id_tarea=" + this.id_tarea + ", nombre_tarea=" + this.nombre_tarea
				+ ", tiempo_ejecucion=" + this.tiempo_ejecucion + ", es_critica="
				+ this.es_critica + ", nivel_prioridad=" + this.nivel_prioridad
				+  "]"+"\n"+"\n";
	}
	
	
}

