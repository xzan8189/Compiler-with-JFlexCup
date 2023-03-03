#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <stdbool.h>
#include <string.h>

//funzioni di supporto
char * intToString(int n) {
	char * dest = malloc(sizeof(char)*16);
	sprintf(dest, "%d", n);
	return dest;
}

char * doubleToString(double n) {
	char * dest = malloc(sizeof(char)*16);
	sprintf(dest, "%lf", n);
	return dest;
}

char * boolToString(bool b) {
	char * dest = malloc(sizeof(char)*2);
	if(b == true)
		dest = "true";
	else
		dest = "false";
	return dest;
}

char * strConcat(char * str1, char * str2) {
	char * dest = malloc(sizeof(char)*256);
	strcat(dest, str1);
	strcat(dest, str2);
	return dest;
}

//dichiarazioni variabili globali

//dichiarazioni funzioni
double add(double a, double b){

	return a + b;

}

int mult(int a, int b){
	int i = 0;
	int result = 0;

	while(i < b){

		result = result + a;

		i = i + 1;

	}
;

	return result;

}

int division(double a, double b){

	return a / b;

}

double power(double a, double b){

	return pow(a, b);

}

int fibonacci(int a){

	if(a <= 1){

		return a;

	}
;

	return 	fibonacci(a - 1) + 	fibonacci(a - 2);

}


//main
int main(){
	char * ans = malloc (sizeof(char) * 128);
	double real_a, real_b, real_result =  -1.0;
	int int_a, int_b, int_result =  -1;

	printf("%s", "Welcome!");
	printf("\n");

	printf("%s", "Enter a number corresponding to the operations below: ");
	printf("\n");

	printf("%s", "1. The sum of two numbers");
	printf("\n");

	printf("%s", "2. Multiplying two numbers using the sum ");
	printf("\n");

	printf("%s", "3. the integer division between two positive numbers");
	printf("\n");

	printf("%s", "4. exponentiation");
	printf("\n");

	printf("%s", "5. the Fibonacci sequence ");
	printf("\n");

	printf("%s", "");
	printf("\n");

	scanf(" %s", ans);
	fflush(stdin);

	if((strcmp(ans, "1") !=  0 || strcmp(ans, "2") !=  0 || strcmp(ans, "3") !=  0 || strcmp(ans, "4") !=  0 || strcmp(ans, "5") !=  0)){

		printf("%s", "Insert the correct number below");
		printf("\n");

	}
;

	if(strcmp(ans, "1") ==  0){

		printf("Insert the 1st value:	");
		scanf(" %lf", &real_a);
		fflush(stdin);

		printf("Insert the 2nd value:	");
		scanf(" %lf", &real_b);
		fflush(stdin);

		real_result = 		add(real_a, real_b);

	}
;

	if(strcmp(ans, "2") ==  0){

		printf("Insert the 1st value:	");
		scanf(" %d", &int_a);
		fflush(stdin);

		printf("Insert the 2nd value:	");
		scanf(" %d", &int_b);
		fflush(stdin);

		int_result = 		mult(int_a, int_b);

	}
;

	if(strcmp(ans, "3") ==  0){

		printf("inserisci il primo valore:	");
		scanf(" %lf", &real_a);
		fflush(stdin);

		printf("inserisci il secondo valore:	");
		scanf(" %lf", &real_b);
		fflush(stdin);

		int_result = 		division(real_a, real_b);

	}
;

	if(strcmp(ans, "4") ==  0){

		printf("Insert the 1st value:	");
		scanf(" %lf", &real_a);
		fflush(stdin);

		printf("Insert the 2nd value:	");
		scanf(" %lf", &real_b);
		fflush(stdin);

		real_result = 		power(real_a, real_b);

	}
;

	if(strcmp(ans, "5") ==  0){

		printf("Enter the n-th number of fibonacci you want to receive:	");
		scanf(" %d", &int_a);
		fflush(stdin);

		int_result = 		fibonacci(int_a);

	}
;

	if((strcmp(ans, "1") ==  0 || strcmp(ans, "2") ==  0 || strcmp(ans, "3") ==  0 || strcmp(ans, "4") ==  0 || strcmp(ans, "5") ==  0)){

		printf("%s", strConcat("Real result: ", doubleToString(real_result)));
		printf("\n");

		printf("%s", strConcat("Int result: ", intToString(int_result)));
		printf("\n");

	}
;

}