import java.util.ArrayList;
import java.util.List;
import java.math.*;

public class Partie_2 {
	int M; // Nombre M de zones a surveiller
	int N; // Nombre N de capteurs
	int Z[][] = new int[1000][1000]; // numero de zones couvertes par le capteurs
	
	int configuration[][] = new int[10000][10000];
	int configurationElem[][] = new int[10000][10000];
	int delta[][] = new int[1000][1000];
	int nbre_ligne_sommer;
	int nnbre_ligne_sommer_elem;
	int tc[]= new int[10000];
	int tand[]= new int[10000];
	boolean test=true;
	ArrayList<Integer> ligne = new ArrayList<Integer>();
	ArrayList<Integer> ligneElem = new ArrayList<Integer>();
	ArrayList<ArrayList<Integer>> listeConfig = new ArrayList<ArrayList<Integer>>();
	ArrayList<ArrayList<Integer>> listeConfigElem = new ArrayList<ArrayList<Integer>>();
	public boolean testConfiguration(int configuration[][],int N ,int M)
	{
		int s=0,i=0,j;
		boolean test=true;
		
		while((test==true)&&(i<M))
		
		{
			for(j = 0; j< N; j++)
			{
				s=s+configuration[j][i];
			}
			if (s==0) 
			{
				test=false;
			}
			else 
			{ 
				i=i+1;
				s=0;
			}
		}
		//System.out.print(test);
		return test;
	}
	public void binaire(int n,int N)
	{
	    //Role:
	    //Retourne le tableau "tc" qui contient  la conversion binaire
		//n:l'entier quand vas le convertir en binaire
		//tc:presente le nombre en binaire
		//N : nombre de capteur
	int i=0;	
	while(n/2!=0)
	{
	    tc[i]=n%2;
	   // System.out.print(tc[i]);
	    i++;
	    n=n/2;
	}
	tc[i]=n%2;
	 //System.out.println(tc[i]);

	}
	public void information(int N,int tc[])
	{
	    //Role:
	    //cette fonction retourne 2 résultats .
	    //la 1 ere résultat est: "nbre_ligne_sommer" c'est le nombre de ligne a sommer de la configuration tc
	    //la 2 eme résultat est: "ligne" c'est un tableau qui contient les indices  des lignes qui font ensemble une configuration
		
	   int j=0;
	   for(int i=0;i<N;i++)
	   {
	       if(tc[i]!=0 )
	       {
	           ligne.add(i+1);
	           //ligneElem.add(i+1);
	         
	       //System.out.print(ligne.get(j)+" ");
				j++;
	       }
	   }
	 //  System.out.println("\n");
	  nbre_ligne_sommer=ligne.size();
	//  nnbre_ligne_sommer_elem=ligne.size();
	//  System.out.println(" le nombre de ligne a sommer :"+nbre_ligne_sommer);
	}
	public void informationElem(int N,int tc[])
	{
	    
		
	   int j=0;
	   for(int i=0;i<N;i++)
	   {
	       if(tc[i]!=0 )
	       {
	           
	           ligneElem.add(i+1);
	         
	       //System.out.print(ligne.get(j)+" ");
				j++;
	       }
	   }
	
	 nnbre_ligne_sommer_elem=ligneElem.size();
	//  System.out.println(" le nombre de ligne a sommer :"+nbre_ligne_sommer);
	}
	public void constuireConfiguration(int delta[][],int nbre_ligne_sommer,ArrayList<Integer> ligne,int M)
	{
	//Role:
	// cette fonction construire une configuration "configuration" a partir de la matrice "delta".
	
	int i;
	int j;
	/*System.out.print("configuration:");
	for(i=0;i<nbre_ligne_sommer;i++)
	{System.out.print(ligne.get(i)+" ");
		
	}
	System.out.println("");*/
	for(i=0;i<nbre_ligne_sommer;i++)
	{
	    for(j=0;j<M;j++)
	    {
	        configuration[i][j]=delta[ligne.get(i)-1][j];
	       // configurationElem[i][j]=delta[ligne.get(i)-1][j];
	      //  System.out.print(configuration[i][j]+" ");

	    }
	    //System.out.println("");
	}
	}
	public float minTab(float []Lconf,int h)
	{
		float min=Lconf[0];
		for(int i=1;i<h;i++)
			if (Lconf[i]<min )
			{
				min=Lconf[i];
			}
		return min;
	}
	public void constuireConfigurationElem(int delta[][],int nbre_ligne_sommer,ArrayList<Integer> ligne,int M)
	{
	//Role:
	// cette fonction construire une configuration "configuration" a partir de la matrice "delta".
	
	int i;
	int j;
	
	for(i=0;i<nbre_ligne_sommer;i++)
	{
	    for(j=0;j<M;j++)
	    {
	        configurationElem[i][j]=delta[ligne.get(i)-1][j];
	     
	    }
	    
	}
	}
	public void opeAnd(int t1[],int t2[],int M)
	{
	
	int i;
	for(i=0;i<M;i++)
	{
	    
	        tand[i]=t1[i]*t2[i];
	        // System.out.print(texor[i]+" ");

	    }
	   //System.out.println("");
	}

	public boolean equal(int t1[],int t2[],int M)
	{
		
	int i=0;
	while((test==true)&&(i<M))
	{if(t1[i]!=t2[i])
	{
		test=false;
	}
	i++;
	}
	 return test;  
	}
	 
}