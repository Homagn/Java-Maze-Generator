//problem with using maze[][]in pathfinder()dont use it think of something else
import java.io.*;
class maze
{
    public static BufferedReader br=new BufferedReader (new InputStreamReader(System.in));
    static int sizer = 12; 
    static int sizec = 10;
    static char maz[][]=new char [sizer*2+1][sizec*2+1];
    static char dup[][]=new char[sizer+2][sizec+2];
    static char path[][]=new char[sizer][sizec];
    static int stack=0;
    static int flood[][]=new int[sizer][sizec];
    static int ec=0;
    static int startc=0;
    static int queue[]=new int[sizec*sizer];static int back=0;
    public static void orig()
    {
        for(int i=0;i<sizer;i++)
        {
            for(int j=0;j<sizec;j++)
            {
                dup[i+1][j+1]=' ';

                maz[2*i][2*j] = '+';
                maz[2*i+1][2*j] = '|';
                maz[2*i][2*j+1] = '-';
                maz[2*i+1][2*j+1] = ' ';
            }
            dup[i+1][0] = '|';
            dup[i+1][sizec+1]='|';

            maz[2*i][2*sizec] = '+';
            maz[2*i+1][2*sizec] = '|';
        }
        for(int j=0;j<sizec;j++)
        {
            dup[0][j+1] = '-';
            dup[sizer+1][j+1]='-';

            maz[2*sizer][2*j] = '+';
            maz[2*sizer][2*j+1] = '-';
        }
        dup[0][0] = '+';
        dup[0][sizec + 1] = '+';
        dup[sizer + 1][0] = '+';
        dup[sizer +1][sizec + 1] = '+';

        maz[2*sizer][2*sizec] = '+';
    }

    public static char roll(int i, int j)
    {
        char res=' ';
        boolean found = false;

        while(!found){
            int r=(int)(4*Math.random());
            if(r==0 ){
                res='l'; 
                if(dup[i][j-1] == ' ') 
                    found = true;

            }
            if(r==1){
                res='r';
                if(dup[i][j+1] == ' ') 
                    found = true;
            }
            if(r==2){
                res='u';
                if(dup[i-1][j] == ' ') 
                    found = true;
            }
            if(r==3){
                res='d';
                if(dup[i+1][j] == ' ') 
                    found = true;
            }
        }
        return res;
    }

    public static void creatensolve()
    {//int stack[]=new int[225];

        int sc=(int)(sizec*Math.random());
        dup[1][sc+1]='s';
        startc=sc;
        int i=1;
        int j=sc+1;

        boolean mazefull = false;

        while(!mazefull){
            //displaydup();
            if(deadend(i,j)==false){
                char dir = roll(i,j);
                if(dir=='l'){j--;dup[i][j]='r';}
                else if(dir=='r'){j++;dup[i][j]='l';}
                else if(dir=='u'){i--;dup[i][j]='d';}
                else if(dir=='d'){i++;dup[i][j]='u';}
            }else{
                if (dup[i][j] == 's'){mazefull = true;}
                else if (dup[i][j] == 'l') {j--;}            	
                else if (dup[i][j] == 'r') {j++;}
                else if (dup[i][j] == 'u') {i--;}            	
                else if (dup[i][j] == 'd') {i++;}

            }
        }
    }

    public static boolean deadend(int i,int j)
    {
        boolean res;

        try
        {
            if(dup[i-1][j]!=' '&&dup[i+1][j]!=' '&&dup[i][j-1]!=' '&&dup[i][j+1]!=' ') {return true;}
        }
        catch(ArrayIndexOutOfBoundsException e){}
        return false;
    }

    public static void replace()
    {
        for(int i=0;i<sizer;i++)
        {
            for(int j=0;j<sizec;j++)
            {
                if(dup[i+1][j+1]=='l')maz[(2*i+1)][(2*j+1)-1] = ' ';
                else if(dup[i+1][j+1]=='r')maz[(2*i+1)][(2*j+1)+1] = ' ';
                else if(dup[i+1][j+1]=='u')maz[(2*i+1)-1][(2*j+1)] = ' ';
                else if(dup[i+1][j+1]=='d')maz[(2*i+1)+1][(2*j+1)] = ' ';
                else if(dup[i+1][j+1]=='s'){maz[(2*i+1)-1][(2*j+1)] = ' ';maz[(2*i+1)][(2*j+1)]='s';}
            }
        }
        int endc=(int)(sizec*Math.random());
        maz[2*sizer - 1][(2*endc+1)] = 'e';
        maz[2*sizer][(2*endc+1)]=' ';
        ec=endc;
        //rendomly clear 8 walls
        for(int k=0; k< 8; k++){
            int i = (int)(Math.random()*(sizer-2))+1;
            int j = (int)(Math.random()*(sizec-2))+1;
            int d = (int)(4*Math.random());
            if(d==0)maz[(2*i+1)][(2*j+1)-1] = ' ';
            else if(d==1)maz[(2*i+1)][(2*j+1)+1] = ' ';
            else if(d==2)maz[(2*i+1)-1][(2*j+1)] = ' ';
            else if(d==3)maz[(2*i+1)+1][(2*j+1)] = ' ';
        }

    }

    public static void push(int a)
    {
        queue[back++]=a;
    }

    public static int pop()
    {
        int res=queue[0];
        for(int i=0;i<sizer*sizec-1;i++)
        {
            queue[i]=queue[i+1];
        }
        back--;
        return res;
    }

    public static void floodfillalgo()
    {   
        boolean flooded=false;
        int a=sizer-1;int b=ec;
        int c=1;
        int code=a*1000+b*100+c;
        push(code);
        flood[a][b]=1;
        while(flooded!=true)
        {
            int cod= pop();
            a=cod/1000;
            b=(cod-(a*1000))/100;
            c=cod-a*1000-b*100;
            if(maz[2*a+1][2*b+1]=='s')flooded=true;
            try{
                if(maz[2*a+1+1][2*b+1]==' '&&maz[2*a+1][2*b+1]!='e'&&flood[a+1][b]==0){flood[a+1][b]=c+1;path[a+1][b]='^';push((a+1)*1000+b*100+(c+1));}
            } catch(ArrayIndexOutOfBoundsException e){}
            try{
                if(maz[2*a+1-1][2*b+1]==' '&&flood[a-1][b]==0){flood[a-1][b]=c+1;path[a-1][b]='v';push((a-1)*1000+b*100+(c+1));}
            } catch(ArrayIndexOutOfBoundsException e){}
            try{
                if(maz[2*a+1][2*b+1+1]==' '&&flood[a][b+1]==0){flood[a][b+1]=c+1;path[a][b+1]='<';push(a*1000+(b+1)*100+(c+1));}
            } catch(ArrayIndexOutOfBoundsException e){}
            try{
                if(maz[2*a+1][2*b+1-1]==' '&&flood[a][b-1]==0){flood[a][b-1]=c+1;path[a][b-1]='>';push(a*1000+(b-1)*100+(c+1));}
            } catch(ArrayIndexOutOfBoundsException e){}
        }
    }

    /* public static void displaydup()
    {
    System.out.println("Dup Matrix:");
    for(int i=0;i<sizer+2;i++)
    {
    for(int j=0;j<sizec+2;j++)
    {
    System.out.print(dup[i][j]);
    }
    System.out.println();
    }
    }*/
    public static void tracepath()
    {
        int a=0;int b=startc;
        while(flood[a][b]!=1)
        {
            if(path[a][b]=='>'){maz[2*a+1][2*b+1]=path[a][b];b++;}
            else if(path[a][b]=='<'){maz[2*a+1][2*b+1]=path[a][b];b--;}
            else if(path[a][b]=='^'){maz[2*a+1][2*b+1]=path[a][b];a--;}
            else if(path[a][b]=='v'){maz[2*a+1][2*b+1]=path[a][b];a++;}
        }
    }

    public static void main(String args[])throws IOException 
    {
        orig();
        //displaydup();
        creatensolve();
        replace();
        for(int i=0;i<sizer*2+1;i++)
        {
            for(int j=0;j<sizec*2+1;j++)
            {
                System.out.print(maz[i][j]);
            }
            System.out.println();
        }
        floodfillalgo();
        for(int i=0;i<sizer;i++)
        {
            for(int j=0;j<sizec;j++)
            {   if(flood[i][j]>=10)
                    System.out.print(flood[i][j]+" "+path[i][j]+" ");
                else
                    System.out.print(flood[i][j]+" "+path[i][j]+"  ");
            }
            System.out.println();
        }
        tracepath();
        for(int i=0;i<sizer*2+1;i++)
        {
            for(int j=0;j<sizec*2+1;j++)
            {
                System.out.print(maz[i][j]);
            }
            System.out.println();
        }

    }

}
