package tpe;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import tpe.utils.CSVReader;

public class Servicios {
	/*La solución planteada en el tpe no es escalable, ya que separamos en 
	tareas criticas y no criticas, en el caso de haber una tercera opción, deberiamos
	modificar el código*/

	private Hashtable<String, Tarea> tareasCriticas;
	private Hashtable<String, Tarea> tareasNoCriticas;
	private Hashtable<String, Procesador> procesadores;
	private Hashtable<Procesador, Procesador> asignacion;
	private int mejorEvaluacionTiempo = Integer.MAX_VALUE;
	private int estadosGenerados;

	/* La complejidad del contructor es: O(n) */
	public Servicios(String pathProcesadores, String pathTareas){

		CSVReader reader = new CSVReader();
		LinkedList<Procesador> listaProcesadores= reader.readProcessors(pathProcesadores);
		LinkedList<Tarea> listaTareas=reader.readTasks(pathTareas);

		this.tareasCriticas= new Hashtable<>();
		this.tareasNoCriticas= new Hashtable<>();
		this.procesadores= new Hashtable<>();

		this.asignacion= new Hashtable<>();
		this.estadosGenerados=0;


		for(Tarea t: listaTareas){
			if(t.isEs_critica()){
				tareasCriticas.put(t.getId_tarea(), t);
			}
			else{
				tareasNoCriticas.put(t.getId_tarea(), t);
			}			
		}

		for (Procesador p : listaProcesadores) {
			this.procesadores.put(p.getId_procesador(), p);
			this.asignacion.put(p,p);
		}

	}


	// GETERS Y SETERS

	public int getEstadosGenerados() {
		return estadosGenerados;
	}

	public void setEstadosGenerados(int estadosGenerados) {
		this.estadosGenerados = estadosGenerados;
	}

	public int getMejorEvaluacionTiempo() {
		return mejorEvaluacionTiempo;
	}


	public void setMejorEvaluacionTiempo(int mejorEvaluacionTiempo) {
		this.mejorEvaluacionTiempo = mejorEvaluacionTiempo;
	}



	//PRIMERA PARTE DEL TPE

	/*En el servicio1 buscamos una tarea utilizando su ID, 
	 *primero buscamos en el Hashtable de tareasCriticas,
	 *en el caso de no encontrarla buscamos en el Hashtable de tareas
	 *no criticas.
	 * La busqueda en un hashtable se considera que tiene una complejidad O(1),
	 * por lo que este servicio tendrá complejidad O(1).
	 * */
	public Tarea servicio1(String ID) {	
		Tarea rtaTarea=this.tareasCriticas.get(ID);
		if(rtaTarea!=null){
			return rtaTarea;
		}
		else{
			return this.tareasNoCriticas.get(ID);
		}
	}




	/* El siguiente servicio permite obtener la lista de
	 * las tareas criticas o no criticas según se solicite por parámetro.
	 *La complejidad del servicio es O(n), siendo n la cantidad de tareas totales.
	 *Ya que en el peor de los casos todas las tareas estarán en un 
	 *único Hashtable.   */
	public List<Tarea> servicio2(boolean es_critica){
		LinkedList<Tarea> rtaTareas= new LinkedList<>();
		if(es_critica){
			for (String key : this.tareasCriticas.keySet()) {
				rtaTareas.add(this.tareasCriticas.get(key));
			}
		}
		else{
			for (String key : this.tareasNoCriticas.keySet()) {
				rtaTareas.add(this.tareasNoCriticas.get(key));
			}
		}
		return rtaTareas;
	}


	/* El Servicio3 permite retornar una lista donde se encuentren todas las
	 * tareas compredidas entre dos niveles de prioridad.Por ello,
	 *  en primer lugar buscamos las tareas  que se encuentran dentro de la condición en
	 *  el Hashtable de tareasCriticas y luego lo
	 * hacemos para el de tareasNoCriticas.
	 * 
	 * El nivel de complejdad es O(n), siendo n la cantidad total de tareas, tanto criticas 
	 * como no críticas.
	 * Consideramos la complejidad O(n) ya que en el peor de los casos debemos recorrer los 
	 * dos HashTable de tareas completos.
	 */
	public List<Tarea> servicio3(int prioridadInferior, int prioridadSuperior) {
		LinkedList<Tarea> rtaTareas= new LinkedList<>();
		int nivel=0;
		for (String key : this.tareasCriticas.keySet()) {
			nivel=this.tareasCriticas.get(key).getNivel_prioridad();
			if(nivel>prioridadInferior && nivel<prioridadSuperior){
				rtaTareas.add(this.tareasCriticas.get(key));
			}
		}
		for (String key : this.tareasNoCriticas.keySet()) {
			nivel=this.tareasNoCriticas.get(key).getNivel_prioridad();
			if(nivel>prioridadInferior && nivel<prioridadSuperior){
				rtaTareas.add(this.tareasNoCriticas.get(key));
			}
		}
		return rtaTareas;
	}


	//SEGUNDA PARTE DEL TPE


	public void inicializacionAsignacion() {
		this.setEstadosGenerados(0);
		this.mejorEvaluacionTiempo = Integer.MAX_VALUE;
		// Nos aseguramos que asignacion empiece vacía:
		this.asignacion.clear();
		for (Procesador p : procesadores.values()) {
			this.asignacion.put(p,p); 
		}
	}



	public LinkedList<Tarea> getTodasLasTareas(){
		//inicializamos con un LinkedList donde se cargan todas las tareas
		LinkedList<Tarea> todasLasTareas = new LinkedList<>();
		todasLasTareas.addAll(tareasCriticas.values());
		todasLasTareas.addAll(tareasNoCriticas.values());
		return todasLasTareas;
	}


	/*Backtracking */

	/* La complejidad del Backtracking es: O(n^m) 
	 * n: cantidad de procesadores
	 * m: cantidad de tareas
	 * 
	 * Árbol de exploración:
    /*Partimos de un estado inicial donde se encuentran todos los  procesadores 
	 * sin asignar ninguna tarea. Para cada entrada de Backtracking
	 *  elejimos una tarea, probamos ponerla en cada uno de los procesadores utilizando recursión. 
	 *  Si cumple con la condición para ser solución posible,
	 *   generamos un nuevo estado, y seguimos probado hasta que se terminen las tareas.	
	 */

	public void backtracking(int tiempo) {
		this.inicializacionAsignacion();
		LinkedList<Tarea> todasLasTareas = this.getTodasLasTareas();
		/*Generamos el primer estado:*/
		Estado estado = new Estado(this.asignacion);
		this.backtracking(0, estado,todasLasTareas, tiempo);
	}



	private void backtracking(int tareaIndex, Estado estado, LinkedList<Tarea> todasLasTareas, int tiempo) {
		/*Si encontramos una solución:*/
		if (tareaIndex == todasLasTareas.size()) {
			this.estadosGenerados++;
			int evaluacionActual = estado.getTiempoEjecucionTotal();

			/* si es mejor, pasa a ser procesadores: */
			if (evaluacionActual < this.mejorEvaluacionTiempo) {
				this.mejorEvaluacionTiempo = evaluacionActual;
				this.asignacion = estado.getCopia();

			}
		}
		else {
			Tarea tareaActual = todasLasTareas.get(tareaIndex);
			for (Procesador p : this.procesadores.values()) {
				/*Si comprobamos que podemos agregarla, entonces continuamos por ese camino*/
				if (this.comprobar(p, tareaActual, tiempo, estado)){

					/*Agregamos la tarea al procesador*/
					estado.addSolucion(p,tareaActual);

					/* PODA: si el estado hasta el momento posee un tiempo de ejecución
					 * menor al mejor que tenemos entonces continuamos. */
					if(estado.getTiempoEjecucionTotal()<this.mejorEvaluacionTiempo) {
						/*Continuamos haciendo backtracking*/
						this.backtracking(tareaIndex + 1,estado, todasLasTareas,tiempo);
					}

					/*Quitamos la tarea al procesador*/
					estado.removeSolucion(p,tareaActual);

				}
			}
		}
	}




	public boolean comprobar(Procesador p, Tarea tareaActual, int tiempo, Estado estado) {
		/*Hay que comprobar:
		1) Si la tarea es critica, ver que el procesador tenga disponibilidad*/
		if(tareaActual.isEs_critica() && !estado.getDiponibilidadCriticas(p)) {
			return false;
		}

		/*2) Si el procesador no esta refrigerado controlamos que al sumar 
		 * la nueva tarea no supere el máximo tiempo aceptado. */
		if(!p.isEsta_refrigerado()) {
			int tiempoActual = estado.getTiempoEjecucionProcesador(p);
			if ((tiempoActual + tareaActual.getTiempo_ejecucion()) > tiempo) {
				return false;
			}
		}
		return true;

	}


	/* GREEDY */

	/* La complejidad de Greedy es: O(n*m) 
	 * n: cantidad de procesadores
	 * m: cantidad de tareas
	 * 
	 * La estrategia que utilizamos para greedy es la de elegir asignar la tarea al
	 * procesador que menos tiempo de ejecución tenga cargado.
	 */


	public void greedy(int tiempo) {
		LinkedList<Tarea> todasLasTareas = this.getTodasLasTareas();
		this.inicializacionAsignacion();
		this.estadosGenerados = todasLasTareas.size();
		this.mejorEvaluacionTiempo = 0;
		while (!todasLasTareas.isEmpty()) {
			Tarea tarea = todasLasTareas.remove(0);

			/* Criterio Greedy*/
			Procesador procesadorMenorCarga = null;
			int menorCarga = Integer.MAX_VALUE;
			for (Procesador p : procesadores.values()) {
				int cargaActual = p.getTiempoEjecucion();
				if (cargaActual < menorCarga && cumpleCondicion(p, tarea, tiempo)) {
					menorCarga = cargaActual;
					procesadorMenorCarga = p;
				}
			}

			/* Asignar la tarea al procesador con menor carga:*/
			if (procesadorMenorCarga != null) {

				/* Agregamos a asignacion la tarea*/
				asignacion.get(procesadorMenorCarga).addTarea(tarea);


				if(procesadorMenorCarga.getTiempoEjecucion()> this.mejorEvaluacionTiempo) {
					this.mejorEvaluacionTiempo=procesadorMenorCarga.getTiempoEjecucion();
				}

			}
			else {
				/* Si para alguna tarea no se encontró un procesador al que se pueda asignar,
				 * cortamos la ejecución y nuestra solución vuelve a estar vacía*/
				todasLasTareas.clear();
				this.inicializacionAsignacion();
				this.mejorEvaluacionTiempo = 0;

				return;
			}
		}





	}


	public boolean cumpleCondicion(Procesador p,Tarea tarea,int tiempo) {

		/*Hay que comprobar:
			1) Si la tarea es critica, ver que el procesador tenga disponibilidad*/
		if(tarea.isEs_critica() && p.getDisponibilidadTareasCriticas()==0) {
			return false;
		}

		/*2) Que el procesador no esta refrigerado, al sumar la nueva tarea no supere el
		máximo tiempo aceptado. */
		if(!p.isEsta_refrigerado()) {
			//System.out.println(p.isEsta_refrigerado());
			int tiempoActual = p.getTiempoEjecucion();
			if ((tiempoActual + tarea.getTiempo_ejecucion()) > tiempo) {
				return false;
			}
		}
		return true;

	}










	@Override
	public String toString() {
		if(0==this.getEstadosGenerados()) {
			return "No hay solución";
		}
		else {
			String procesadoresObtenidos = "";
			for(Procesador p: this.asignacion.keySet()) {
				procesadoresObtenidos += p.toString() + "\n\n";
			}
			return 	"Solución obtenida: " + procesadoresObtenidos

					+"\n\n\n"					+ "Solución obtenida: "+this.getEstadosGenerados() +"\n\n\n"
					+ "Métrica para analizar el costo de la solución: " +  this.mejorEvaluacionTiempo  +"\n\n\n";
		}
	}






}


