package entities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Grafo {
	private static Map<String, Set<String>> listaDeAdjacencia = new HashMap<>();
	private static Map<Integer,Integer> tabela = new HashMap<>();
	private static Map<String, Boolean> distanciasCalculadas = new HashMap<>();
	private static Set<String> verticesGigantes = new HashSet<>();
	
	private static void adicionar_aresta(String id1, String id2) {
		Set<String> set = listaDeAdjacencia.get(id1);
		if (set == null) set = new HashSet<String>();
		set.add(id2);
		listaDeAdjacencia.put(id1, set);
	}
	
	public static void gerar_lista_de_adjacencia(String arquivo) {
		try {
			BufferedReader bf = new BufferedReader(new FileReader(arquivo));
			bf.readLine(); // vertices
			bf.readLine(); // arestas
			String linha = bf.readLine();
			int i = 1;
			while (linha != null) {
				String[] dados = linha.split(" ");

				adicionar_aresta(dados[0], dados[1]);
				adicionar_aresta(dados[1], dados[0]);

				System.out.println("Linha " + i++ + " lida...");				
				linha = bf.readLine();
			}
			bf.close();
		}catch (IOException e) {
			e.getMessage();
			e.printStackTrace();
		}
    }
	
	public static void realizar_buscas() {
		int i = 1;
		for(String s: verticesGigantes) {
			Map<String, String> edgeTo = new HashMap<>();
			String source = s;
			busca_em_largura(source, edgeTo);
			System.out.println(i++ + " buscas realizadas...");
		}
	}
	
	private static void busca_em_largura(String source, Map<String, String> edgeTo) {
		Deque<String> fila = new ArrayDeque<>();
		Map<String, Boolean> marcados = new HashMap<>();
		marcados.put(source, true);
		fila.add(source);
		while(!fila.isEmpty()) {
			String atual = fila.remove();
			for(String id: listaDeAdjacencia.get(atual)){
				if(marcados.get(id) == null) {
					edgeTo.put(id, atual);
					marcados.put(id, true);
					fila.add(id);
					int distancia = calcular_distancia(source, id, edgeTo);
					adicionar_na_tabela(distancia);
				}
			}
		}		
	}
	
	private static int calcular_distancia(String source, String id, Map<String, String> edgeTo) {
		if(distanciasCalculadas.get(id) == null) {
			int distancia = 0;
			while(!id.equals(source)) {
				id = edgeTo.get(id);
				distancia++;
			}
			distanciasCalculadas.put(source, true);
			return distancia;
		}
		return -1;
	}
	
	private static void adicionar_na_tabela(int distancia) {
		if(distancia != -1) {
			int frequencia;
			if(tabela.get(distancia) == null) frequencia = 0;
			else frequencia = tabela.get(distancia);
			frequencia++;
			tabela.put(distancia, frequencia);
		}		
	}
	
	public static void printar() {
		System.out.println("Distancia Frequencia");
		for(Map.Entry<Integer, Integer> entry: tabela.entrySet()) {
			System.out.println(entry.getKey() + " " + entry.getValue());
		}
	}
	
	public static void gerar_tabela(String saida) {				
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(saida));
			bw.write("Distancia, Frequência");
			for(Map.Entry<Integer, Integer> entry : tabela.entrySet()){
				bw.newLine();
				bw.write(entry.getKey() + ", " + entry.getValue());
			}
			bw.close();
		}catch(IOException e) {
			e.getMessage();
			e.printStackTrace();
		}		
	}
	
	public static void setarVerticesGigantes(String vertices) {
		try {
			BufferedReader bf = new BufferedReader(new FileReader(vertices));
			String linha = bf.readLine();
			while(linha != null) {
				verticesGigantes.add(linha);
				linha = bf.readLine();
			}
			bf.close();
		}catch(IOException e) {
			e.getMessage();
			e.printStackTrace();
		}
		System.out.println(verticesGigantes.size());
	}
}
