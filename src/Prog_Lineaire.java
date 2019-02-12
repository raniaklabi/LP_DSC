import java.util.ArrayList;

import ilog.concert.*;
import ilog.cplex.*;


public class Prog_Lineaire {
	
	public static void main(String[] args) {
		int mat[][] = new int[10000][10000];
		int matelementaire[][] = new int[10000][10000];
		float y[]=new float[10000];
		Boolean test;
		int a=0;
		int n; //nombre des configurations  valides elementaires
		Partie_1 P1 = new Partie_1(); // Creer un instance pour manipuler les donnees
		Partie_2 P2 = new Partie_2(); // Creer un instance pour contruire les configurations valides
		//P1.Read_Data("test2.txt");
		P1.Read_Data_v1("Exemple20_30_1.txt");
		System.out.println("la lecture du fichier: ");
		P1.Print_Data();
		P1.Calculer_delta();
		System.out.println("***************la liste de configuration valide non elementaire********************");	
		
		for(int g=1;g<(Math.pow(2,P1.N))-1;g++)
		{
		P2.tc= new int[10000];
		P2.nbre_ligne_sommer=0;
		P2.ligne = new ArrayList<Integer>();
		P2.configuration = new int[10000][10000];
		P2.binaire(g,P1.N);
		P2.information(P1.N,P2.tc);
		P2.constuireConfiguration(P1.delta,P2.nbre_ligne_sommer,P2.ligne,P1.M);
		test=P2.testConfiguration(P2.configuration,P1.N,P1.M);
		
		if (test==true)
		{	for(int k=0;k<P1.N;k++)
			mat[a][k]=P2.tc[k];
			a++;
			P2.listeConfig.add(P2.ligne);
			System.out.print("configuration:");
			for(int i=0;i<P2.nbre_ligne_sommer;i++)
			{System.out.print(P2.ligne.get(i)+" ");
				
			}
			System.out.println("");
			for(int i=0;i<P2.nbre_ligne_sommer;i++)
		{
		    for(int j=0;j<P1.M;j++)
		    {
			 System.out.print(P2.configuration[i][j]+" ");
		    }
		    System.out.println("");
		}
			
		}
		}
		
		
		for(int i = 0; i< P2.listeConfig.size(); i++) 
		{
			
			System.out.println("config :"+i+": " +P2.listeConfig.get(i));
		
		}
		
		for(int i=0;i<P2.listeConfig.size();i++)
		{
		    for(int j=0;j<P1.N;j++)
		    {
		       
		      System.out.print(mat[i][j]+" ");

		    }
		  System.out.println("");
		}
		
		matelementaire[0]=mat[0];
		System.out.println("**********");
		int sizmatElem=1;
		boolean test1,test2;
		
	for(int i=0;i<P2.listeConfig.size();i++)
	{	
		int h=0;
		
		
		while(h<=sizmatElem)
		{	
			P2.test=true;
			P2.tand= new int[1000];
			P2.opeAnd(matelementaire[h],mat[i],P1.N);
			test1=P2.equal(P2.tand,matelementaire[h],P1.N);
			test2=P2.equal(P2.tand,mat[i],P1.N);
			//System.out.print(test1+" "+test2);
			//System.out.println("");
			if(test1==true)
			{	
				h=sizmatElem+1;
				
			}
			else
			{
				if(test2==true)
				{
					matelementaire[h]=mat[i];
					h=sizmatElem+1;
					
				}
				else
				{
					if(h+1==sizmatElem)
					{
						matelementaire[h+1]=mat[i];
						h=sizmatElem+1;
						sizmatElem++;
						
						
					}
					else
					{
						h++;
						
					}
				}
			}
		}
	}
	
	System.out.println("nombre de configuration valide elementaire="+sizmatElem);
	for(int i=0;i<sizmatElem;i++)
	{
	    for(int j=0;j<P1.N;j++)
	    {
	       
	      System.out.print(matelementaire[i][j]+" ");

	    }
	  System.out.println("");
	}
		
	System.out.println("***************la liste de configuration valide elementaire********************");	
	
	for(int i=0;i<sizmatElem;i++)
	{
	P2.nnbre_ligne_sommer_elem=0;
	P2.ligneElem = new ArrayList<Integer>();
	P2.configurationElem = new int[1000][1000];
	P2.informationElem(P1.N,matelementaire[i]);
	P2.constuireConfigurationElem(P1.delta,P2.nnbre_ligne_sommer_elem,P2.ligneElem,P1.M);
	P2.listeConfigElem.add(P2.ligneElem);
		
		System.out.print("configuration:");
		for(int j=0;j<P2.nnbre_ligne_sommer_elem;j++)
		{
			System.out.print(P2.ligneElem.get(j)+" ");
			
		}
		System.out.println("");
		for(int v=0;v<P2.nnbre_ligne_sommer_elem;v++)
	{
	    for(int b=0;b<P1.M;b++)
	    {
		 System.out.print(P2.configurationElem[v][b]+" ");
	    }
	    System.out.println("");
	}
		
	}
	for(int i = 0; i< P2.listeConfigElem.size(); i++) 
	{
		
		System.out.println("config :"+i+": " +P2.listeConfigElem.get(i));
	
	}
	
	System.out.println("");
	    n=P2.listeConfigElem.size();
	    try
	    {
	    	IloCplex cplex = new IloCplex();
	    
	    	// variables
	    	IloNumVar t [] = new IloNumVar[n];
	    	
	    	
	    	for ( int i = 0; i < n; i++)
			{
				t[i] = cplex.numVar(0, Double.MAX_VALUE);
			}
	 	
	 	
	
	    	// expressions
	    	IloLinearNumExpr objective = cplex.linearNumExpr();
	 	
	    	for(int i = 0; i < n; i++)
	    		objective.addTerm(1.0, t[i]);
	 		
	    	// define objective
	    	cplex.addMaximize(objective);
	    	
	    	
	    	// define constraints
			
			for(int i = 0; i < P1.N; i++)
			{
				IloLinearNumExpr Assignement =cplex.linearNumExpr();
				for(int j = 0; j < n; j++)
				{
					
					if(P2.listeConfigElem.get(j).contains(i+1))
					{
						//System.out.println("i = " + (i+1) + " &&  j = " + j);
						//System.out.println("A= " + config.get(j).get(0) + " " + config.get(j).get(1));
						Assignement.addTerm(1.0,t[j]);
					}
				}
				
				cplex.addLe(Assignement, P1.T[i]);
				Assignement.clear();
				//break;
			}
	
			// Print results 
			if(cplex.solve())
			{
				System.out.println("obj =" + cplex.getObjValue());
		
				System.out.println("Time =" + cplex.getCplexTime() );
				
				for (int i = 0; i < n; i++)
				{
					System.out.println("t["+i +"] = " + cplex.getValue(t[i]));
				}
			}
		}
		catch(IloException exc) {
			exc.printStackTrace();
		}
	  //****************************pour resoudre le problem DSC**************************
	    System.out.println("Number of covers set ="+P2.listeConfigElem.size());
	    
		for(int i = 0; i< P2.listeConfigElem.size(); i++) 
		{
			float yLocal[]=new float[1000];
			//System.out.println("sizey"+i+": " +P2.listeConfigElem.get(i).size());
			for(int j=0; j< P2.listeConfigElem.get(i).size(); j++)
			yLocal[j]=P1.T[(P2.listeConfigElem.get(i).get(j))-1];
			
			y[i]=P2.minTab(yLocal,P2.listeConfigElem.get(i).size());
			//System.out.print(y[i]+" ");
		
		}
		for(int i = 0; i< P2.listeConfigElem.size(); i++) 
		{
			
			System.out.println("cover set:"+i+": " +P2.listeConfigElem.get(i)+" et la duree de vie maximale de cette cover set est "+y[i]);
			
		
		}
	
		
		try
		{
			IloCplex cplex1=new IloCplex();
			IloNumVar c [] = new IloNumVar[n];
			//variable x y
			for ( int i = 0; i < n; i++)
			{
				c[i] = cplex1.numVar(0, Integer.MAX_VALUE);
			}
			
			//expression   y1c1+....yncn
			IloLinearNumExpr objective1=cplex1.linearNumExpr();
			for(int i = 0; i < n; i++)
	    		objective1.addTerm(y[i], c[i]);
			//define objective 
			cplex1.addMaximize(objective1);
			//define constrains  60x+60y>=300
			for(int i = 0; i <n ; i++)
			{
				IloLinearNumExpr Assignement =cplex1.linearNumExpr();
				for(int j = 0; j <P1.N; j++)
				{
					
					if(matelementaire[j][i]!=0)
					{
						//System.out.println("i = " + (i+1) + " &&  j = " + j);
						//System.out.println("A= " + config.get(j).get(0) + " " + config.get(j).get(1));
						Assignement.addTerm(matelementaire[j][i],c[j]);
					}
				}
				
				cplex1.addLe(Assignement, 1);
				Assignement.clear();
				//break;
			}

			//solve
			if(cplex1.solve())
			{
				System.out.println("obj1 =" + cplex1.getObjValue());
		
				System.out.println("Time =" + cplex1.getCplexTime() );
				
				for (int i = 0; i <n; i++)
				{
					System.out.print("c"+i +" = " + cplex1.getValue(c[i]));
					if(cplex1.getValue(c[i])!=0) {System.out.println(" avec une duree de vie="+y[i]);}
					else System.out.println(" ");
				}
			}
			else {
				System.out.println("Model not solved");
			}
			
		}
		catch(IloException exc)
		{
			
			exc.fillInStackTrace();
		}
		
		
		//***************fin*****************************
 
	    
	}
	
	

}
