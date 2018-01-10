import java.io.BufferedReader;

import java.io.FileReader;

import java.io.IOException;

import java.math.BigDecimal;

import java.math.RoundingMode;

import java.util.HashSet;

import java.util.Scanner;

// classe principal do metodo 2 fases
public class Metodo2Fases {

	public static int[] x;	

	public static Double[] xAs = null;

	public static int chave, iteracoes = 1;

	public static HashSet<Integer> variaveisArtificiais = new HashSet<>();
	
	// metodo para ler a matriz e retornar o tableau preenchido
	public static Double[][] lerMatriz (String arg, int rows, int cols, BufferedReader in)
	{

		Double[][] matriz = null;		

		String linha = new String();	
			
		try {

			Scanner sc = null;			

			matriz = new Double[rows][cols];			
			
			while (in.ready())
			{				

				for (int i = 0; i < rows; i++)
				{

					linha = in.readLine();	

					sc =  new Scanner(linha);

					sc.useDelimiter(" ");					

					for (int j = 1; j < cols; j++)
					{

						if (i == 0 || (chave == 2 && i == 1))
							matriz[i][j] = Double.parseDouble(sc.next()) *(-1);
						else
							matriz[i][j] = Double.parseDouble(sc.next());
						
					}

					matriz[i][0] = Double.parseDouble(sc.next());						
				}			
			}	
			
			
		if (sc != null)
			sc.close();
			
		}

		catch (IOException|NullPointerException e)

		{
			System.out.println(e.getMessage());
		}	

		return matriz;
	}
	
	// verifica o indice para entrar no vetor das bases
	public static int posEntrarBase(Double[][] matriz, int rows, int cols)
	{

		double maior = -1;

		int pos = 0;

		for (int j = 1; j < cols; j++)
		{

			if (matriz[0][j] > 0 && matriz[0][j] > maior)
			{

				maior = matriz[0][j];
				pos = j;
			}
		}
		
		if (maior == -1)
			return -1;
		else
			return pos;
	}

	// verifica indice do vetor para sair da base
	public static int posSairBase(Double[][] matriz, int rows,  int posJ)
	{

		double menor = 999999;

		int posI = -1;

		for (int i = 1; i < rows; i++)
		{

			if (matriz[i][posJ] <= 0){}
			else
			{				
				if ((matriz[i][0]/matriz[i][posJ]) < menor)
				{

					menor = (matriz[i][0]/matriz[i][posJ]);

					posI = i;
				}
			}
		}

		if (posI > 0)
			return posI;
		else
		{

			System.out.println("Nao possui solucao!\nz = -inf");

			System.exit(1);

			return posI;
		}
		
	}

	// verifica indice do vetor para sair da base no caso da primeira fase
	public static int posSairBaseDF(Double[][] matriz, int rows,  int posJ)
	{

		double menor = 999999;

		int  posI = -1;

		for (int i = 2; i < rows; i++)
		{
			if (matriz[i][posJ] <= 0){}
			else
			{				

				if ((matriz[i][0]/matriz[i][posJ]) < menor)
				{

					menor = (matriz[i][0]/matriz[i][posJ]);

					posI = i;
				}
			}
		}

		if (posI > 0)
			return posI;
		else
		{

			System.out.println("Nao possui solucao!\nz = -inf");

			System.exit(1);

			return posI;
		}
		
	}

	// divide uma linha do tableau
	public static void dividirLinha(Double[][] matriz,int cols, int posI, int posJ)
	{

		double div = matriz[posI][posJ];
		
		for (int j = 0; j < cols; j++)
		{
		
			matriz[posI][j] = matriz[posI][j]/div;	
		}
	}
	
	// metodo auxiliar para zerar uma coluna
	public static void zerarColuna(Double[][] matriz,int rows, int cols,int posI,int posJ)
	{		
	
		for (int i = 0;i < rows; i++)
		{

			double mult = matriz[i][posJ];

			if (mult != 0 && i != posI)	
			{
								
				for (int j = 0;j < cols; j++)
				{

					matriz[posI][j] *= mult * (-1); 
					
					matriz[i][j] += matriz[posI][j];	

					matriz[posI][j] = (matriz[posI][j] /mult) * (-1);
					
				}
			}
			
		}	
		
	}

	// metodo para apenas checar se a solucao eh degenerada
	public static void checarDegenerada(Double[][] matriz,int rows)
	{
		for (int i = 2;i < rows; i++)
		{			
			if (matriz[i][0] == 0)
			{

				System.out.println("Solucao Degenerada!" );

				//System.exit(1);
			}
		}		
	}
	
	// metodo para imprimir o tableau
	public static void imprimir(Double[][] matriz, int rows, int cols)
	{			

		String aux = new String();

		int len;
		
		for (int i = 0;i < rows; i++)
		{			

			if (i > 0)
				System.out.print("X" + x[i-1] + " |");
			else
				System.out.print("Z  |");

			for (int j = 0; j < cols; j++)
			{

				aux = String.valueOf(new BigDecimal(matriz[i][j]).setScale(4, RoundingMode.HALF_UP));

				len = aux.length();

				if (j == 0)
				{

					System.out.print("  " + aux + "   ");

					for (int k = 0; k < 10 - len; k++)
						System.out.print(" ");					
				}
				else
				{				

					System.out.print("  " + aux + "   ");

					for (int k = 0; k < 10 - len; k++)
						System.out.print(" ");
				}
			}
			
			System.out.println();
		}		
		
	}

	// imprime o tableau da duas fases
	public static void imprimirDF(Double[][] matriz, int rows, int cols)
	{				

		String aux = new String();

		int len;
		
		for (int i = 0; i < rows; i++)
		{			

			if (i > 1)
				System.out.print("X" + x[i-2] + " |");
			else
			{
				if (i == 0)
					System.out.print("Za |");
				
				if (i == 1)
					System.out.print("Z  |");
			}
			for (int j = 0; j < cols; j++)
			{

				aux = String.valueOf(new BigDecimal(matriz[i][j]).setScale(4, RoundingMode.HALF_UP));

				len = aux.length();

				if (j == 0)
				{

					System.out.print("  " + aux + "   ");

					for (int k = 0; k < 10 - len; k++)
						System.out.print(" ");					
				}
				else
				{					

					System.out.print("  " + aux + "   ");

					for (int k = 0; k < 10 - len; k++)
						System.out.print(" ");
				}
			}
			
			System.out.println();
		}		
		
	}
	
	// metodo para imprimir cada iteracao do algoritmo
	public static void imprimirIte(int cols)
	{		

		System.out.println();

		System.out.print("ITERACAO " + iteracoes + ".           ");

		for (int i = 0; i < cols - 1; i++)
			System.out.print("X" + (i + 1) + "             ");

		System.out.println();	

		iteracoes++;
	}
	
	// metodo principal que executa duas fases
	public static void duasFases(Double[][] matriz, int rows, int cols)
	{
		for (int j = 0; j < cols; j++)
		{

			if (matriz[0][j] == -1)
			{

				variaveisArtificiais.add(j);

				for (int i = 2; i < rows; i++)
				{							
					if (matriz[i][j] == 1)
						zerarColuna(matriz, rows, cols, i, j);
				}
			}
		}		
		
		imprimirIte(cols);
		
		imprimirDF(matriz, rows, cols);

		int posEntBase = 0, posSairBase = 0;
		
		while (true)
		{
			
			if ((posEntBase = posEntrarBase(matriz,rows,cols)) == -1)
				break;			
			
			posSairBase = posSairBaseDF(matriz, rows, posEntBase);		

			dividirLinha(matriz, cols, posSairBase, posEntBase);	

			zerarColuna(matriz, rows, cols, posSairBase, posEntBase);	

			x[posSairBase - 2] = posEntBase;			

			imprimirIte(cols);

			imprimirDF(matriz, rows, cols);

			//checarDegenerada(matriz,rows);			
		}
		
		xAs = new Double[cols - 1];

		for (int i = 0; i < cols - 1; i++)
			xAs[i] = 0.0;
		
		for (int i = 0; i < rows - 2; i++)
			xAs[x[i] - 1] = matriz[ i + 2][0]; 			
		
		for (int i = 0; i < rows - 2; i++)
			if (variaveisArtificiais.contains(x[i]))
			{

				System.out.println("Nao possui solucao, ainda existe variavel artificial na base!");

				System.exit(1);
			}		
		
	}	
	
	// metodo auxiliar para apenas ajustar a matriz
	public static void ajustarMat(Double[][] matriz,int rows, int cols)
	{
		for (int i = 0; i < rows - 1; i++)
		{
			for (int j = 0; j < cols; j++)
				matriz[i][j] = matriz[i + 1][j];
		}
	}
	
	// metodo para checar se ha solucao multipla
	public static void checarMultSolu(Double[][] matriz, int rows, int cols)	
	{
		int cont = 0;
		for (int j = 1; j < cols; j++)					
			if (matriz[0][j] == 0)			
				cont++;		
		
		if (cont == x.length)
			return;
		else
		{

			System.out.println("Multiplas Solucoes!");

			System.exit(1);
		}
	}

	// main principal do algoritmo
	public static void main(String[] args) {	

		Double[][] matriz = null;	

		int rows = 0 , cols = 0;

		if (args.length != 1)
			System.err.println("Numero incorreto de argumentos.\nEsperado: 1\nIdentificado: " + args.length);
		
		try (BufferedReader in = new BufferedReader(new FileReader(args[0])))
		{			

			String linha = new String();

			linha = in.readLine();

			Scanner sc = new Scanner(linha);

			sc.useDelimiter(" ");

			chave = Integer.parseInt(sc.next());

			sc.close();
			
			linha = in.readLine();

			sc = new Scanner(linha);
			
			rows = Integer.parseInt(sc.next());

			cols = Integer.parseInt(sc.next());		

			sc.close();
			
			if (rows == 0 || cols == 0)
				System.exit(0);

			matriz = lerMatriz(args[0],rows,cols,in);
			
			if (chave == 2)
			{

				x = new int[rows - 2];	

				for (int i = 2; i < rows; i++)
				{
					for (int j = cols - rows + 1;j < cols; j++)
					{						
						
						if (matriz[i][j] == 1)
						{
							x[i - 2] = j;

							break;
						}
					}
				}
			}
			else
			{
				x = new int[rows - 1];

				for (int i = 0; i < rows - 1; i++)
					x[i] = cols - rows + i + 1;
			}
			
			System.out.print("Tableau Original.     ");
			
			for (int i = 0; i < cols - 1; i++)
				System.out.print("X" + (i + 1) + "             ");
					
			System.out.println();
			
			sc.close();
			in.close();	
			
		}
		
		catch(IOException|NullPointerException e)
		{
			System.out.println(e.getMessage());
		}		
		
		
		if (chave == 2)
		{
			imprimirDF(matriz, rows, cols);

			duasFases(matriz, rows, cols);

			ajustarMat(matriz, rows, cols);	

			cols = cols - variaveisArtificiais.size();

			rows = rows - 1;	

			imprimirIte(cols);

			imprimir(matriz, rows, cols);

			checarMultSolu(matriz, rows, cols);

		}
		else
			imprimir(matriz, rows, cols);
		
		int posEntBase = 0, posSairBase = 0;
		
		while (true)
		{
			
			if ((posEntBase = posEntrarBase(matriz,rows,cols)) == -1)
				break;					
					
			posSairBase = posSairBase(matriz, rows, posEntBase);	

			dividirLinha(matriz, cols, posSairBase, posEntBase);	

			zerarColuna(matriz, rows, cols, posSairBase, posEntBase);

			x[posSairBase - 1] = posEntBase;			

			imprimirIte(cols);

			imprimir(matriz, rows, cols);

			checarMultSolu(matriz, rows, cols);

			checarDegenerada(matriz,rows);	

		}

		System.out.println();
		
		if (xAs == null)
			xAs = new Double[cols - 1];

		for (int i = 0; i < cols - 1; i++)
			xAs[i] = 0.0;
		
		for(int i = 0; i < rows - 1; i++)		
			xAs[x[i] - 1] = matriz[i + 1][0];
		
		System.out.println("Solucao unica.");

		checarDegenerada(matriz,rows);	

		System.out.print("x*=( ");

		for(int i = 0; i < cols - 1; i++)
			System.out.print(new BigDecimal(xAs[i]).setScale(4, RoundingMode.HALF_UP) + " ");

		System.out.println(')');

		System.out.println("z*= " + new BigDecimal(matriz[0][0]).setScale(4, RoundingMode.HALF_UP));	
			
	}
}