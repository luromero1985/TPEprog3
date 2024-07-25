package tpe;

import java.util.LinkedList;

public class Procesador {

	private String id_procesador;
	private String cod_procesador;
	private boolean esta_refrigerado;
	private int anio_funcionamiento;
	private int disponibilidadTareasCriticas;
	private final int MAXCANT=2;
	private LinkedList<Tarea> tareas;
	private int tiempoEjecucion;


	public Procesador(String id_procesador, String cod_procesador, boolean esta_refrigerado, int anio_funcionamiento) {
		this.id_procesador = id_procesador;
		this.cod_procesador = cod_procesador;
		this.esta_refrigerado = esta_refrigerado;
		this.anio_funcionamiento = anio_funcionamiento;
		this.disponibilidadTareasCriticas=MAXCANT;
		this.tareas= new LinkedList<>();
		this.tiempoEjecucion=0;
	}

	
	// GETERS Y SETERS 
	
	
	public int getTiempoEjecucion() {
		return tiempoEjecucion;
	}

	public void setTiempoEjecucion(int tiempoEjecucion) {
		this.tiempoEjecucion = tiempoEjecucion;
	}

	public void addTarea(Tarea t) {
		
		this.setTiempoEjecucion(this.tiempoEjecucion+ t.getTiempo_ejecucion());
		if (t.isEs_critica()) {
			this.disponibilidadTareasCriticas--;
		}
		this.tareas.add(t);
	}

	public void removeTarea(Tarea t) {
		
		this.setTiempoEjecucion(this.tiempoEjecucion- t.getTiempo_ejecucion());
		if (t.isEs_critica()) {
			this.disponibilidadTareasCriticas++;
			
		}
		this.tareas.remove(t);
	}

	public int getDisponibilidadTareasCriticas() {
		return this.disponibilidadTareasCriticas;
	}

	public void setDisponibilidadTareasCriticas(int dispTareasCriticas) {
		this.disponibilidadTareasCriticas = dispTareasCriticas;
	}



	public String getId_procesador() {
		return id_procesador;
	}

	public void setId_procesador(String id_procesador) {
		this.id_procesador = id_procesador;
	}

	public String getCod_procesador() {
		return cod_procesador;
	}

	public void setCod_procesador(String cod_procesador) {
		this.cod_procesador = cod_procesador;
	}


	public boolean isEsta_refrigerado() {
		return esta_refrigerado;
	}

	public void setEsta_refrigerado(boolean esta_refrigerado) {
		this.esta_refrigerado = esta_refrigerado;
	}

	public int getAnio_funcionamiento() {
		return anio_funcionamiento;
	}

	public void setAnio_funcionamiento(int anio_funcionamiento) {
		this.anio_funcionamiento = anio_funcionamiento;
	}

	@Override
	public int hashCode() {
		return id_procesador.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		try{
			Procesador p =(Procesador)obj;
			return this.getId_procesador().equals(p.getId_procesador());
		}
		catch(Exception e){
			return false;
		}
	}

	@Override
	public String toString() {
		String todasLasTareas ="";
		 for (Tarea tarea : tareas) {
			 todasLasTareas +=  tarea.toString() +"\n" ;
		    }
		
		
		
		return "Procesador [id_procesador=" + id_procesador
				+ ", cod_procesador=" + cod_procesador + ", esta_refrigerado="
				+ esta_refrigerado + ", anio_funcionamiento="
				+ anio_funcionamiento + ", disponibilidadTareasCriticas="
				+ this.getDisponibilidadTareasCriticas()  +"]"+"\n"+"\n"
		+ ", tareas=["+todasLasTareas + "]";
				
	}


	public boolean puedoAgregarTareas(Tarea t){
		if(
				(t.isEs_critica()&&this.getDisponibilidadTareasCriticas()==0)){
			return false;
		}

		return true;

	}


	public Procesador getCopia() {
		Procesador copia = new Procesador(this.id_procesador, this.cod_procesador, this.esta_refrigerado, this.anio_funcionamiento);
		for (Tarea tarea : this.tareas) {
			copia.addTarea(tarea); 
		}
		return copia;
	}


}
