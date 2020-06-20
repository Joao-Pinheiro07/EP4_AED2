package program;

import java.util.Scanner;

import entities.Grafo;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Digite o caminho dos vertices gigantes a serem lidos: ");
		String vertices = sc.nextLine();
		Grafo.setarVerticesGigantes(vertices);
		
		System.out.print("Digite o caminho do arquivo a ser lido: ");
		String arquivo = sc.nextLine();		
		
		long start = System.currentTimeMillis();
		Grafo.gerar_lista_de_adjacencia(arquivo);
		long checkPoint1 = System.currentTimeMillis();
		Grafo.realizar_buscas();
		long checkPoint2 = System.currentTimeMillis();
		
		System.out.println();
		System.out.print("Digite o caminho do arquivo de saída: ");		
		String saida = sc.nextLine();
		
		long checkPoint3 = System.currentTimeMillis();
		Grafo.gerar_tabela(saida);
		long end = System.currentTimeMillis();
		
		long tempo1 = checkPoint1 - start;
		long tempo2 = checkPoint2 - checkPoint1;
		long tempo3 = end - checkPoint3;
		long tempoTotal = tempo1 + tempo2 + tempo3;
		
		System.out.println();		
		System.out.print("Tempo de geração da lista de adjacência: ");
		exibirTempo(tempo1);
		System.out.print("Tempo de buscas em largura: ");
		exibirTempo(tempo2);
		System.out.print("Tempo de escrita da tabela: ");
		exibirTempo(tempo3);
		System.out.println();
		System.out.print("Tempo total de execução: ");
		exibirTempo(tempoTotal);
		
		sc.close();
		
	}
	
	public static void exibirTempo(long tempo) {
		int horas = (int) (tempo / 3600000);
		int minutos = (int) ((tempo % 3600000) / 60000);
		int segundos = (int) (((tempo % 3600000) % 60000) / 1000);
		int millissegundos = (int) ((((tempo % 3600000) % 60000) % 1000));
		System.out.println(horas + ":" + minutos + ":" + segundos + ":" + millissegundos);
		
	}
}
