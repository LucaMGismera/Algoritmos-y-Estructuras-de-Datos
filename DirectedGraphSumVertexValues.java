package aed.individual6;

import es.upm.aedlib.graph.Edge;
import es.upm.aedlib.graph.Vertex;
import es.upm.aedlib.graph.DirectedGraph;
import es.upm.aedlib.map.Map;
import es.upm.aedlib.positionlist.NodePositionList;
import es.upm.aedlib.positionlist.PositionList;
import es.upm.aedlib.set.HashTableMapSet;
import es.upm.aedlib.set.Set;
import es.upm.aedlib.map.HashTableMap;

public class Suma {
	public static <E> Map<Vertex<Integer>, Integer> sumVertices(DirectedGraph<Integer, E> g) {
		Map<Vertex<Integer>, Integer> map = new HashTableMap<Vertex<Integer>, Integer>();
		
		//Entramos en todos los vertices, y desde cada uno sumamos el valor de todos los vertices que
		//podemos alcanzar desde el (que habremos guardado en una lista).
		for (Vertex<Integer> vertex : g.vertices()) {
			int value = 0;
			//Sumamos el valor de todos los vertices que alcanzamos (creamos funcion auxiliar para conseguirlos).
			for (Vertex<Integer> reachableVertex : reachableVertexList(g, vertex)) {
				value += reachableVertex.element();
			}
			//Ponemos el vertice con su valor en el map solucion.
			map.put(vertex, value);
		}

		return map;
	}
	
	//FUNCIONES AUXILIARES_________________________________________
	
	//Funcion auxiliar que devuelve todos los vetices alcanzables
	public static <V, E> PositionList<Vertex<V>> reachableVertexList(DirectedGraph<V, E> g, Vertex<V> inicialVertex) {
		//Creamos una lista de vertices a devolver y un map sin values con los ya visitados, para
		PositionList<Vertex<V>> reachableVertexList = new NodePositionList<Vertex<V>>();
		Set<Vertex<V>> visitedVertexMap = new HashTableMapSet<Vertex<V>>();
		
		//Funcion auxiliar que recorre todos los vertices desde uno inicial.
		visit(g, inicialVertex, reachableVertexList, visitedVertexMap);
		return reachableVertexList;
	}
	
	//Funcion que actualiza la lista de vertices alcanzados.
	private static <V, E> void visit(DirectedGraph<V, E> g, Vertex<V> vertex, PositionList<Vertex<V>> reachableVertexList,
			Set<Vertex<V>> visitedVertexMap) {
		//Si el vertice actual no se ha visitado lo aniadimos en la lista de visitados y en los alcanzables.
		//Lo ponemos y seguimos buscando desde este.
		if (!visitedVertexMap.contains(vertex)) {
			reachableVertexList.addLast(vertex);
			visitedVertexMap.add(vertex);
			//Visitamos todos los alcanzables desde este.
			for (Edge<E> edge : g.outgoingEdges(vertex))
				visit(g, g.endVertex(edge), reachableVertexList, visitedVertexMap);
		}
	}
}
