.data
warn:    .asciiz "The input should be integer"
prompt1: .asciiz "Please enter an integer M:"
prompt2: .asciiz "Please enter an integer N:"
buffer1: .space 30
buffer2: .space 30
result:  .asciiz "Result:"
newline: .asciiz "\n"
error1:  .asciiz "Error: Overflow occurred!"
error2:  .asciiz "Error: Invalid input! Please input again!"

.text
.globl main

main:
#Display a warning message
li $v0, 4
la $a0, warn
syscall

#Prompt a newline
li $v0, 4
la $a0, newline   #Start a newline
syscall

li $t1,48        #ASCII '0'    #Define the error situation, check if a character is a digit or not
li $t2,57        #ASCII '9'    #Define the error situation, check if a character is a digit or not
li $t3,45        #ASCII '-'    #check if a character is negative
li $t4,10        #ASCII '\n'   #Define the '\n'

#Read the M as string and store it into s3
inputM:
li $v0,4
la $a0,prompt1    #Prompt M
syscall

li $v0, 8
la $a0, buffer1 
li $a1, 30
syscall
la $s0, buffer1
jal CheckInput1
move $s3, $s2    #Store M in $s3

#Read the N as string and store it into $s4
inputN:
li $v0,4
la $a0,prompt2   #Prompt N
syscall

li $v0, 8
la $a0, buffer2
li $a1, 30
syscall
la $s0, buffer2
jal CheckInput2
move $s4, $s2    #Store N in s4
j calculation    #jump to calculation 

CheckInput1:
li $t5, 10   
li $s2, 0    #Initialize $s2 to store the integer value of M
lb $t0, ($s0)   #Load the address stored in $s0 into register $t0
beq $t0, $t4, error3  #Branch to error3 if the current character is the newline character
bne $t0, $t3, loop1   #Branch to loop1 if the current character is not '-'
li $s1, 1    #Set $s1 to 1 to indicate a positive number
addi $s0, $s0,1   #Increment the pointer to the next character in the input string

loop1:
lb $t0,($s0)  #Load the next character from input string into $t0
beq $t0,$t4,convert1   #If a newline, proceed to convert1
bgt $t0,$t2,error3     #Check if it is invalid
blt $t0,$t1,error3
 
mult $s2,$t5
mflo $s2    #Store lower 32-bits in $s2
mfhi $t6    #Store higher 32-bits in $t6
bne $t6,$zero,error3   #Check if the overflow of input occurred
srl $t6,$s2,31       #Get the sign bit of the result (0 for positive, 1 for negative)
bne $t6,$zero,error3    #Branch to error handling if overflow occurred

sub $t0,$t0,'0'   #Change string into integer 
add $s2,$t0,$s2
srl $t6,$s2,31
bne $s6,$zero,error3   #Branch to error handling if overflow occurred

addi $s0,$s0,1      # Move to the next character in the input string
bne $t0,$t4,loop1

convert1:
beq $s1,$zero,isPositive1    # Check if the number is positive
sub $s2,$zero,$s2            #convert to negative

isPositive1:
jr $ra     #Return from subroutine

CheckInput2:
li $t5, 10
li $s2, 0   #Initialize $s2 to store the integer value of N
lb $t0, ($s0)   #Load the address stored in $s0 into register $t0
beq $t0, $t4, error4   #Branch to error3 if the current character is the newline character
bne $t0, $t3, loop2    #Branch to loop1 if the current character is not '-'
li $s1, 1     #Set $s1 to 1 to indicate a positive number
addi $s0, $s0,1

loop2:
lb $t0,($s0)    #Load the next character from input string into $t0
beq $t0,$t4,convert2  #If a newline, proceed to convert1
bgt $t0,$t2,error4   #Check if it is invalid
blt $t0,$t1,error4
 
mult $s2,$t5
mflo $s2   #Store lower 32-bits in $s2
mfhi $t6   #Store higher 32-bits in $t6
bne $t6,$zero,error4   #check the overflow of input
srl $t6,$s2,31     
bne $t6,$zero,error4

sub $t0,$t0,'0'   #change string into integer 
add $s2,$t0,$s2
srl $t6,$s2,31
bne $s6,$zero,error4

addi $s0,$s0,1
bne $t0,$t4,loop2

convert2:
beq $s1,$zero,isPositive2
sub $s2,$zero,$s2            #convert to negative

isPositive2:
jr $ra

#Calculation part
#M^3 + 3(M^2)N + 3M(N^2) + N^3 = (M+N)^3 + 8(N^3)
calculation:
addu $s2,$s3,$s4   # s2=M+N
li $t5,1
mult $s2,$t5
mflo $t0
mfhi $t2
beq $t2,-1,mult1
bne $t2,0,overflow  

mult1:
mult $t0,$s2 
mflo $t0       # t0=(M+N)^2
mfhi $t2
beq $t2,-1,mult2
bne $t2,0,overflow
 
mult2:
mflo $t0
mult $t0,$s2   # t0=(M+N)^3
mflo $t0
mfhi $t2
beq $t2,-1,mult3
bne $t2,0,overflow
 
mult3:
mult $s4,$s4  
mflo $t1    # t1=N^2
mfhi $t2
beq $t2,-1,mult4
bne $t2,0,overflow

mult4:
mult $t1,$s4
mflo $t1    # t1=N^3
mfhi $t2
beq $t2,-1,mult5
bne $t2,0,overflow

mult5:
sll $s2,$t1,3     # s2=8(N^3)
xor $t4,$s2,$t1    
srl $t4,$t4,31
bne $t4,$zero,overflow
 
#final add part
addu $s3,$t0,$s2   # s3=t0+s2=(M+N)^3+8(N^3)
li $t5,1
mult $t5,$s3
mflo $t5
mfhi $t7
beq $t7,-1,output
bne $t7,0,overflow
 
#output 
output:
la $a0,result
li $v0,4
syscall
li $v0,1
add $a0,$zero,$t5   # output the result t5
syscall
j end

end:
li $v0,10   # exit
syscall

overflow:
li $v0,4     # print the error message when overflow exisits
la $a0,error1
syscall
j end

error3:
li $v0,4
la $a0,error2
syscall     #Print error message
li $v0,4
la $a0,newline           
syscall   # Print newline
j inputM   # Jump back to inputM to ask for input again

error4:
li $v0,4
la $a0,error2   #Print error message
syscall 
li $v0,4
la $a0,newline       # Print newline     
syscall
j inputN   # Jump back to inputN to ask for input again