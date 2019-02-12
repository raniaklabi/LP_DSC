import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;


public class Partie_1 {
	 static final int NC=500;
	 static final int NT=200;
	int M; // Nombre N de capteurs
	int ran;
	int N; // Nombre M de zones a surveiller 
	float T[] = new float[1000]; // duree de vie de chaque capteur separee par un espace
	int Z[][] = new int[1000][1000]; // numero de zones couvertes par le capteurs
	int delta[][] = new int[1000][1000];
	int nbreDeTypeDeSur;
	double Distance[][] = new double[NC][NT];
	float R[] =new float[NC];
	public void Read_Data_v1(String fichier)
	{
	    // Role:
	    //cette fonction permet de lire toutes  les donnees necessaires pour la simulation a partir d'un fichier S
	   String []survTarget=new String[NT];
	   int i,j,k,L;
	   
	   String line; /* or other suitable maximum line size */
	   String S;
	   
	   try {
		  

		    InputStream ips=new FileInputStream(fichier); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String ligne;
			ligne = br.readLine();
			ligne = br.readLine();
	        N = Integer.parseInt(ligne);
	  	   	ligne = br.readLine();
	  	   	ligne = br.readLine();
      
	  	   	M = Integer.parseInt(ligne);
	  	   	ligne = br.readLine();
	  	   	ligne = br.readLine();
  
	  	   	nbreDeTypeDeSur= Integer.parseInt(ligne);
	
	  	   	ligne = br.readLine();
	  	   	ligne = br.readLine();
	  	   	String[] parts;
	  	  	for( L = 0; L < N; L++)
	  	   	{
	  	   		ligne = br.readLine();
	  	   		parts= ligne.split(" ");
	  	   		R[L]= Float.parseFloat(parts[0]);
	  	   		
	  	   		T[L]= Float.parseFloat(parts[1]);
				 for(i = 4; i< parts.length;i++)
				 { Distance[L][i-4] = Float.parseFloat(parts[i]);
				 System.out.print(Distance[L][i-4]+ "|");
				 }
				 System.out.println("");
			
	      }
	  	  System.out.print("rayon");
	  	  for( L = 0; L < N; L++)
	  	  {System.out.print(R[L] + "| ");
	  	  }
			//System.out.print(T[i] + " ");
		      ligne = br.readLine();
		  	  ligne = br.readLine();
		  	 parts = ligne.split(" ");
		  	for( i =0; i < M; i++)
			{
		  		 survTarget[i] = parts[i];
				System.out.print(survTarget[i] + " ");
			
			} 
	      br.close();
	   }
	   catch (Exception e){
			System.out.println(e.toString());
		}	

	}
	public void Construct_Z()
	{
		 for(int i=0;i<N;i++)
		    {
		        for(int j=0;j<M;j++)
		        {

		            if(delta[i][j]!=0)
		            {
		            	Z[i][j]=j+1;
		            }
		        }

		    }
	}
	public void Read_Data(String fichier)
	{
		//lecture du fichier texte	
		try{
			InputStream ips=new FileInputStream(fichier); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String ligne;
			ligne = br.readLine();
			N = Integer.parseInt(ligne);
			//System.out.println("N = " + N);
					
			ligne = br.readLine();
			M = Integer.parseInt(ligne);
		   // System.out.println("M = " + M);
					
		    ligne = br.readLine();
			String[] parts = ligne.split(" ");
			for(int i =0; i < N; i++)
			{
				T[i] =  Float.parseFloat(parts[i]);
				//System.out.print(T[i] + " ");
			}
					
			//System.out.println("");
					
			for(int i = 0; i< N; i++)
			{
				ligne =br.readLine();
				parts = ligne.split(" ");
				for(int j = 0; j < M; j++)
				{
					if (j < parts.length)
					{
						Z[i][j] =  Integer.parseInt(parts[j]);
						//System.out.print(Z[i][j] + " ");
					}
					else
					{
						Z[i][j] = 0;
						//System.out.print(Z[i][j] + " ");
					}
							
				}
				//System.out.println("");
			}
			
			br.close(); 
		}		
		catch (Exception e){
			System.out.println(e.toString());
		}	
		
	}
	
	public void Print_Data()
	{
		System.out.println("Nombre de capteurs N = " + N);
		System.out.println("Nombre de cibles M = " + M);
		System.out.print("Dur�e de vie de chaque capteur: ");
		for(int i =0; i < N; i++)
		{
			System.out.print("E"+(i+1)+"="+T[i] + " ");
	    }
					
		System.out.println("");
		System.out.println("Les cibles couverts par chaque capteur : ");
		Calculer_delta();
		Construct_Z();
		for(int i = 0; i< N; i++)
		{System.out.print("S"+(i+1)+":");
			for(int j = 0; j<M; j++)
			{if(Z[i][j]!=0) {
				System.out.print("t"+Z[i][j] +" ");
			}
			}
			System.out.println("");
		}	
	}
	public void Calculer_delta()
	{
	    //Role:
	    //A partir de les diffrentes distances d'un capteur particulier on peut construire une ligne de delta.
	    //Retourne la matrice "delta" .
	    //Les lignes de la matrice presente les capteurs.
	    //les colonnes de la matrice presente les targets.
	    //losqu'on met par exemple 1 dans la case(1,1)  c'est_a_dire le capteur un couvre la target 1 .

	    int i,k,j,test;
	    System.out.println("Distance: ");
	    for(i=0;i<N;i++)
	    {
	        for(j=0;j<M;j++)
	        	System.out.print(Distance[i][j] +" |");
			System.out.println("");
	    }
	    System.out.println("Rayon: ");
	    for(i=0;i<N;i++)
	    {
	 
	        	System.out.print(R[i] +"| ");
			
	    }

	    for(i=0;i<N;i++)
	    {
	        for(j=0;j<M;j++)
	        {

	            delta[i][j]=0;
	        }

	    }

	    for(i=0;i<N;i++)
	    {
	        for(j=0;j<M;j++)
	        {
	            if(Distance[i][j]<=R[i])
	            {
	                delta[i][j]=1;
	            }
	            else
	            {
	                delta[i][j]=0;
	            }

	        }

	    }
	System.out.println("Delta: ");
	    for(i=0;i<N;i++)
	    {
	        for(j=0;j<M;j++)
	        	System.out.print(delta[i][j] +" ");
			System.out.println("");
	    }
	}
	

}
