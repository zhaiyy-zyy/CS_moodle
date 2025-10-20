//20514470 Yuyang Zhang
//Using Selection Sort method to solve this coursework

//Check if RAM[0] is equal to 0, if RAM[0] = 0, then skip to END without sorting
@R0
D=M
@END
D;JEQ

//Sorting prefenrecce
//if RAM[0] > 0, then skip to ASC order; if RAM[0] < 0, then skip to DESC order.
@R0
D=M
@ASC  //if RAM[0]>0, skip to ASC
D;JGT
@DESC
D;JLT  //if RAM[0]<0 skip to DESC

//ASC order
//Initializes two variables N1 and N2, and initializes a loop variable i.
(ASC)
@R0
D=M
@N1   
M=D   //N1=R0 
@N2
M=D-1  //N2=R0-1  
@11
D=A
@i
M=D   //i=11 
@N1
M=D+M  //N1+11
@N2 
M=D+M  //N2+11

//the beginning of outerloop: for(int i=0; i<N2; i++)
(outerloopASC)
@N2
D=M
@i         
D=M-D    //D=i-N2，when i-N2=0，skip to end. it is a condition to check if the outerloop ends
@END
D;JEQ  

@i
D=M
@j
M=D+1 //j=i+1
@position
M=D //position = i, store i in position and use it for swap elements

//beginning of innerloop: for(j=i+1;j<N1;j++)
(innerloopASC)     
@N1
D=M    
@j
D=M-D   //j-N1
@swapelementASC
D;JEQ //if j=N1, skip to swapvalue 

//{if(a[position]>a[j])
//    position=j;}
// find the minimum of the following array, if a[j]<a[position], update the position set j
@position  
A=M    //a[position]
D=M    //D=a[position]
@j
A=M      //a[j]
D=M-D   //D=a[position]-a[j]
@stopASC
D;JGT  //if a[position]>a[j], skip to stop
@j
D=M 
@position
M=D //Store j in position and it is the smallest element so far

(stopASC)
@j
M=M+1 //j+1
@innerloopASC
0;JMP

//end of the innerloop
//end of the outerloop

//Swap element
//firstly, swap arr[i] and arr[position]
//then increment the outer loop variable i, and skip back to the outer loop for next swapping
(swapelementASC)
@i
A=M
D=M   //D=arr[i]
@temp1
M=D  //temp1=arr[i]
@position
A=M
D=M //D=arr[position]
@i
A=M
M=D  //arr[i]=arr[position]
@temp1
D=M
@position
A=M
M=D  //arr[position]=temp1
@i
M=M+1 //i+1
@outerloopASC
0;JMP

//DESC order
//Initializes two variables N1 and N2, and initializes a loop variable i.
(DESC)
@R0
D=M
@C
M=-D
@C
D=M
@N1   
M=D   //N1=-R0  
@N2
M=D-1  //N2=-R0-1  
@11
D=A
@i
M=D   //i=11
@N1
M=D+M  //N1+11
@N2 
M=D+M   //N2+11

//the beginning of outerloop
//for(int i=0; i<N2; i++)
(outerloopDESC)
@N2
D=M
@i         
D=M-D    //D=i-N2 when i-N2=0, skip to end. it is a condition to check if the outerloop ends
@END
D;JEQ  

@i
D=M
@j
M=D+1 //j+1
@position
M=D 

//beginning of innerloop
//for(j=i+1;j<N1;j++)
(innerloopDESC)     
@N1
D=M    
@j
D=M-D   
@swapelementDESC
D;JEQ

//{if(a[position]<a[j])
//    position=j;}
// find the maximum of the following array
@position  
A=M    //a[position]
D=M    //D=a[position]
@j
A=M      //a[j]
D=M-D   //D=a[position]-a[j]
@stopDESC
D;JLT  //if a[position]<a[j], skip to stop
@j
D=M
@position //Store j in position and it is the biggest element so fa
M=D

(stopDESC)
@j
M=M+1
@innerloopDESC
0;JMP

//end of the innerloop
//end of the outerloop

(swapelementDESC)
@i
A=M
D=M   //D=arr[i]
@temp2
M=D  //temp2=arr[i]
@position
A=M
D=M //D=arr[position]
@i
A=M
M=D  //arr[i]=arr[position]
@temp2
D=M
@position
A=M
M=D  //arr[position]=temp2
@i
M=M+1 //i+1
@outerloopDESC
0;JMP

(END)
@END
0;JMP