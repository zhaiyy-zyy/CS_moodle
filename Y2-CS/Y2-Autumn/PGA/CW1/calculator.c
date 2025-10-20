// 20514470 scyyz26 Yuyang Zhang

#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<ctype.h>
#include<math.h>
#define MAX_Expression_LENGTH 100
//Check if this character is a valid operator
int isOperator(char c)
{
    return(c == '+' || c == '-' || c == '*' || c == '/' || c =='%' || c == '(' || c == ')' );
}
// Function to check if a character is a digit or a dot
int isNumericChar(char c) 
{
    return (isdigit(c) || c == '.');
}
//check if a character is valid number
int isValidNumber(const char *str)
{
    
    char *endptr;
    strtod(str, &endptr);
    //Check if a character is valid
    if(*endptr != '\0'|| endptr == str)
    {
        return 0;
    }
    int len = strlen(str);
    //check if a character is valid
    if(len > 12)
    {
        return 0;
    }
    //check if a character contain more than one decimal point
    int decimalpointCount = 0;
    for(int i =0; i<len; i++)
    {
        if(!isdigit(str[i]) && str[i] != '.')
        {
            return 0;
        }
        if(str[i] == '.')
        {
            decimalpointCount++;
        }
    }
    if(decimalpointCount > 1)
    {
        return 0 ;
    }
    
    //check if a character has other wrong forms
    if(strchr(str, 'e') != NULL || strchr(str, '+') != NULL || str[0] == '0' || (str[0] == '-' && str[1]== '\0'))
    {
        return 0;
    }
    return 1;
}
//Check if a character is valid 
int isValidExpressionChar(char c) 
{
    return (isOperator(c) || isdigit(c) || c=='.' || isspace(c));
}
//check the number of parenrhses and check if the parenthses are put in the correct position
int isValidExpression(const char str[])
{
    int len = strlen(str);
    int ParentheseCount = 0;
    for(int i = 0; i<len; i++)
    {
        //check if the expression is whitespace,operator and newline opeartor
        if(isspace(str[i]))
        {
            continue;
        }
        //check valid character in expression
        if(!isValidExpressionChar(str[i]))
        {
            return 0;
        }
        //handle the whitespace in the expression
        if(isdigit(str[i])|| str[i] == '.' || isOperator(str[i]))
        {
            while(i+1<len && isspace(str[i+1]))
            {
                i++;
            }
        }
        //check the '+',if '+' as the beginning of digit as a positive sign is invalid
        if ((str[i] == '+' ) && (i==0 || isOperator(str[i - 1]) || isspace(str[i-1]))) 
        {
            return 0;
        }
        //check the number of parenrhses
        else if (str[i] == '(')
        {
            ParentheseCount++;
        }
        else if(str[i] == ')')
        {
            ParentheseCount--;
            if(ParentheseCount <  0)
            {
                return 0;
            }   
        }
    }
    return ParentheseCount == 0;
}

// check if a character has white space
int hasWhiteSpace(const char *expression)
{
    while(*expression)
    {
        if(isspace(*expression))
        {
            return 1;
        }
        expression++;
    }
    return 0;
}
//check the priority of opeartor
int Priority(char operator)
{
    if(operator == '(' || operator == ')' )
    {
        return 4;
    }
    else if(operator == '%')
    {
        return 3;
    }
    else if(operator == '*' || operator == '/')
    {
        return 2;
    }
    else if(operator == '+' || operator == '-')
    {
        return 1;
    }
    return 0;
}
//apply operator and return the result
double applyOperator(char op, double a, double b) 
{
    //check if the numbers before and after % are integers
    if (op == '%') 
    {
        if (fmod(a, 1.0) != 0 || fmod(b, 1.0) != 0) 
        {
            printf("Invalid input\n");
            exit(1);
        }
    }

    switch (op) 
    {
        case '+':
        return a + b;
        case '-':
        return a - b;
        case '*':
        return a * b;
        case '/':
        if (b == 0) 
        {
            printf("Invalid input\n");
            return 1;
        }
        return a / b;
        case '%':
        if (b == 0) 
        {
            printf("Invalid input\n");
            return 1;
        }
        return fmod(a, b);   
    }
    return 0.0;
}

//handle the expression inside parentheses
double evaluateParenthese(const char *expression, int *index)
{
    if(expression[*index] == '(')
    {
        (*index)++;
        double result = evaluateParenthese(expression, index);
        if(expression[*index] != ')')
        {
            printf("Invalid input\n");
            return 1;
        }
        (*index)++;
        return result;
    }
    else
    {
        //check negative number 
        char number[MAX_Expression_LENGTH];
        int numIndex = 0;
        int isNegative = 0;
        if(expression[*index] == '-')
        {
            isNegative = 1;
            (*index)++;
        }
        while(isNumericChar(expression[*index]))
        {
            number[numIndex++] = expression[(*index)++];
        }
        number[numIndex] = '\0';
        double result = atof(number);
        if(isNegative)
        {
            result = -result;
        }
        return result;
    }
}

//handle the expression
double evaluateExpression(const char *expression, int *index) 
{
    while (isspace(expression[*index]))
        {
            (*index)++;
        }

    //handle expression in parenthese
    double result = evaluateParenthese(expression, index);
    char op;
    //handle operator and expression in parenthese
    while ((op = expression[*index]) && (isOperator(op))) 
    {
        op = expression[(*index)++];
        while (isspace(expression[*index]))
        {
            (*index)++;
        }

        
        int currentPriority = Priority(op);
        double next = evaluateParenthese(expression, index);
        //handle the priority of operator
        while (Priority(expression[*index]) > currentPriority && isOperator(expression[*index])) 
        {
            char nextop = expression[*index];
            
            (*index)++;
            while (isspace(expression[*index]))
            {
                (*index)++;
            }
            
            double nextValue = evaluateParenthese(expression, index);
        
            next = applyOperator(nextop, next, nextValue);
        }
        result = applyOperator(op, result, next);
        while (isspace(expression[*index]))
            {
                (*index)++;
            }      
    }
    return result;
}

int main()
{
    char expression[MAX_Expression_LENGTH];
    printf("Input: ");
    fgets(expression, sizeof(expression), stdin);

    //remove newline character from input
    expression[strcspn(expression, "\n")] = '\0';
    
    //check if the input exceeds the maximun length
    if(strlen(expression) > MAX_Expression_LENGTH)
    {
        printf("Invalid input\n");
        return 1;
    }
    if(hasWhiteSpace(expression) || !isValidExpression(expression))
    {
        printf("Invalid input \n");
        return 1;
    }
    int index = 0;

    double result = evaluateExpression(expression, &index);
    //print the result with 6 decimal places
    printf("Output: %.6lf\n", result);
    return 0;
}

